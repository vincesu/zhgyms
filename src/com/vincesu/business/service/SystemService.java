/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.framework.entity.SysEncoding;
/*   4:    */import com.vincesu.framework.entity.SysParam;
/*   5:    */import com.vincesu.framework.entity.SysRole;
/*   6:    */import com.vincesu.framework.entity.SysUser;
/*   7:    */import com.vincesu.framework.remote.RemoteUtil;
/*   8:    */import com.vincesu.framework.remote.RequestEntity;
/*   9:    */import com.vincesu.framework.remote.ResponseEntity;
/*  10:    */import com.vincesu.framework.service.SysParamService;
/*  11:    */import com.vincesu.framework.util.BeanUtil;
/*  12:    */import com.vincesu.framework.util.Encript;
/*  13:    */import com.vincesu.framework.util.PathUtil;
/*  14:    */import com.vincesu.framework.util.QueryUtil;
/*  15:    */import com.vincesu.framework.util.SystemUtil;
/*  16:    */import com.vincesu.persistence.PMF;
/*  17:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  18:    */import java.io.File;
/*  19:    */import java.io.FileInputStream;
/*  20:    */import java.io.IOException;
/*  21:    */import java.io.InputStream;
/*  22:    */import java.io.OutputStream;
/*  23:    */import java.io.PrintStream;
/*  24:    */import java.util.Date;
/*  25:    */import java.util.List;
/*  26:    */import java.util.Map;
/*  27:    */import org.hibernate.SessionFactory;
/*  28:    */
/*  30:    */public class SystemService
/*  31:    */{
/*  32: 32 */  String[] zero = { "0", "0", "00", "00", "0000", "00000" };
/*  33:    */  
/*  34: 34 */  String firstOne = "001";
/*  35:    */  
/*  36:    */  public ResponseEntity addUser(RequestEntity req)
/*  37:    */  {
/*  38: 38 */    PMF pmf = RemoteUtil.getPMF(req);
/*  39: 39 */    ResponseEntity resp = new ResponseEntity();
/*  40:    */    try
/*  41:    */    {
/*  42: 42 */      Map val = (Map)req.getData().get(0);
/*  43: 43 */      SysUser user = new SysUser();
/*  44: 44 */      BeanUtil.copyProperty(val, user);
/*  45:    */      
/*  46: 46 */      long count = pmf.count("select * from sys_user where username='" + 
/*  47: 47 */        user.getUsername() + "'");
/*  48: 48 */      if (count > 0L)
/*  49:    */      {
/*  50: 50 */        resp.setType(0);
/*  51: 51 */        resp.setMessage("该用户名已存在");
/*  52: 52 */        return resp;
/*  53:    */      }
/*  54:    */      
/*  55: 55 */      String newPwd = Encript.md5(user.getPwd());
/*  56: 56 */      if (newPwd.length() > 79)
/*  57:    */      {
/*  58: 58 */        newPwd = newPwd.substring(0, 80);
/*  59:    */      }
/*  60: 60 */      user.setPwd(newPwd);
/*  61: 61 */      SysRole role = (SysRole)pmf.get(SysRole.class, user.getRoleId());
/*  62:    */      
/*  63: 63 */      if (role.getReserved().equals("0"))
/*  64:    */      {
/*  65: 65 */        user.setReserved("0");
/*  66:    */      }
/*  67:    */      else
/*  68:    */      {
/*  69: 69 */        List users = pmf.list("from SysUser where Reserved like '" + 
/*  70: 70 */          role.getReserved() + "%' order by Reserved desc ", 
/*  71: 71 */          null, 0, 1);
/*  72: 72 */        if ((users.size() == 0) || 
/*  73: 73 */          (((SysUser)users.get(0)).getReserved().equals("")))
/*  74:    */        {
/*  75: 75 */          user.setReserved(role.getReserved() + "001");
/*  76:    */        }
/*  77:    */        else
/*  78:    */        {
/*  79: 79 */          String reserved = "";
/*  80: 80 */          int i = Integer.parseInt(((SysUser)users.get(0))
/*  81: 81 */            .getReserved().substring(2, 5));
/*  82: 82 */          i++;
/*  83: 83 */          if (Integer.toString(i).length() < 3)
/*  84:    */          {
/*  85: 85 */            reserved = 
/*  86: 86 */              this.zero[(3 - Integer.toString(i).length())] + Integer.toString(i);
/*  87: 87 */            user.setReserved(role.getReserved() + reserved);
/*  88:    */          }
/*  89:    */        }
/*  90:    */      }
/*  91: 91 */      user.setJoinedTime(new Date());
/*  92: 92 */      pmf.save(user);
/*  93:    */      
/*  94: 94 */      if ((user.getReserved().startsWith("01")) || 
/*  95: 95 */        (user.getReserved().equals("0")))
/*  96:    */      {
/*  97: 97 */        SysEncoding se = new SysEncoding();
/*  98: 98 */        se.setCodingValue(user.getRealname());
/*  99: 99 */        se.setFieldValue(user.getRealname());
/* 100:100 */        se.setFieldName("userrealname");
/* 101:101 */        pmf.save(se);
/* 102:    */      }
/* 103:    */      
/* 104:    */    }
/* 105:    */    catch (Exception e)
/* 106:    */    {
/* 107:107 */      resp.setType(0);
/* 108:108 */      resp.setMessage(e.getMessage());
/* 109:    */    }
/* 110:110 */    return resp;
/* 111:    */  }
/* 112:    */  
/* 113:    */  public ResponseEntity backup(RequestEntity req) throws IOException
/* 114:    */  {
/* 115:115 */    PMF pmf = RemoteUtil.getPMF(req);
/* 116:116 */    ResponseEntity resp = new ResponseEntity();
/* 117:117 */    Map val = (Map)req.getData().get(0);
/* 118:118 */    OutputStream os = (OutputStream)val.get("filestream");
/* 119:119 */    InputStream is = null;
/* 120:    */    try
/* 121:    */    {
/* 122:122 */      List params = SysParamService.queryParameters(pmf, "backupdir", 1);
/* 123:123 */      if (params.size() == 0)
/* 124:    */      {
/* 125:125 */        resp.setType(0);
/* 126:126 */        resp.setMessage("未配置备份脚本路径");
/* 127:127 */        ResponseEntity localResponseEntity1 = resp;return localResponseEntity1;
/* 128:    */      }
/* 129:129 */      File file = new File(System.getProperty("user.home"), "backup.rar");
/* 130:130 */      if (file.exists())
/* 131:131 */        file.delete();
/* 132:132 */      StringBuffer cmdStr = new StringBuffer();
/* 133:133 */      cmdStr.append(((SysParam)params.get(0)).getValue() + " ");
/* 134:134 */      cmdStr.append(" \"").append(SystemUtil.getLocation())
/* 135:135 */        .append("/resource/backup.sql").append("\" ");
/* 136:136 */      cmdStr.append(" \"").append(file.getAbsolutePath()).append("\" ");
/* 137:137 */      cmdStr.append(" \"").append(SystemUtil.getLocation())
/* 138:138 */        .append("/resource").append("\" ");
/* 139:139 */      Process process = Runtime.getRuntime().exec(cmdStr.toString());
/* 140:140 */      InputStream in = process.getInputStream();
/* 141:141 */      while (in.read() != -1) {}
/* 142:    */      
/* 143:143 */      in.close();
/* 144:144 */      process.waitFor();
/* 145:145 */      if (file.exists())
/* 146:    */      {
/* 147:147 */        is = new FileInputStream(file);
/* 148:148 */        PathUtil.write(is, os);
/* 149:    */      }
/* 150:    */    }
/* 151:    */    catch (Exception e)
/* 152:    */    {
/* 153:153 */      resp.setType(0);
/* 154:154 */      resp.setMessage("备份数据出错,错误信息：" + e);
/* 155:    */    }
/* 156:    */    finally
/* 157:    */    {
/* 158:158 */      os.flush();
/* 159:159 */      if (is != null)
/* 160:    */      {
/* 161:161 */        is.close();
/* 162:    */      }
/* 163:    */    }
/* 164:164 */    return resp;
/* 165:    */  }
/* 166:    */  
/* 167:    */  public ResponseEntity restoreDB(RequestEntity req)
/* 168:    */  {
/* 169:169 */    ResponseEntity resp = new ResponseEntity();
/* 170:    */    try
/* 171:    */    {
/* 172:172 */      for (int i = 0; i < 3; i++)
/* 173:173 */        SessionFactoryHelper.closeSession();
/* 174:174 */      SessionFactoryHelper.getSessionFactory().close();
/* 175:    */      
/* 176:176 */      Process process = Runtime.getRuntime().exec("I:/restore.bat");
/* 181:181 */      process.waitFor();
/* 182:    */      
/* 183:183 */      System.out.println("finish");
/* 184:    */      
/* 185:185 */      SessionFactoryHelper.initFactory();
/* 186:    */    }
/* 187:    */    catch (Exception e)
/* 188:    */    {
/* 189:189 */      resp.setType(0);
/* 190:190 */      resp.setMessage(e.toString());
/* 191:    */    }
/* 192:192 */    return resp;
/* 193:    */  }
/* 194:    */  
/* 195:    */  public ResponseEntity querySysEncodingList(RequestEntity req)
/* 196:    */    throws Exception
/* 197:    */  {
/* 198:198 */    StringBuffer sql = new StringBuffer();
/* 199:199 */    sql.append("select * from sys_encoding a where a.reserved like '%:reserved%' and a.coding_value like '%:codingvalue%' and a.field_name=':fieldname'  order by a.field_name,a.ordernum  ");
/* 200:200 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 201:201 */      new String[] { "id", "fieldName", "fieldValue", "codingValue", "reserved", 
/* 202:202 */      "ordernum" }, 
/* 203:203 */      new String[] { "id", "fieldName", "fieldValue", "codingValue", "reserved", 
/* 204:204 */      "ordernum" }, null, null);
/* 205:    */  }
/* 206:    */  
/* 207:    */  public ResponseEntity querySysEncodingObject(RequestEntity req)
/* 208:    */    throws Exception
/* 209:    */  {
/* 210:210 */    StringBuffer sql = new StringBuffer();
/* 211:211 */    sql.append("select * from sys_encoding a ");
/* 212:212 */    sql.append(" where a.id=:id ");
/* 213:213 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 214:214 */      new String[] { "id", "fieldName", "fieldValue", "codingValue", "reserved", 
/* 215:215 */      "ordernum" }, null, null);
/* 216:    */  }
/* 217:    */  
/* 218:    */  public ResponseEntity addSysEncoding(RequestEntity req)
/* 219:    */  {
/* 220:220 */    PMF pmf = RemoteUtil.getPMF(req);
/* 221:221 */    ResponseEntity resp = new ResponseEntity();
/* 222:    */    try
/* 223:    */    {
/* 224:224 */      Map val = (Map)req.getData().get(0);
/* 225:225 */      SysEncoding se = new SysEncoding();
/* 226:226 */      BeanUtil.copyProperty(val, se);
/* 227:227 */      pmf.save(se);
/* 228:    */    }
/* 229:    */    catch (Exception e)
/* 230:    */    {
/* 231:231 */      resp.setType(0);
/* 232:232 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 233:    */    }
/* 234:234 */    return resp;
/* 235:    */  }
/* 236:    */  
/* 237:    */  public ResponseEntity removeSysEncoding(RequestEntity req)
/* 238:    */  {
/* 239:239 */    PMF pmf = RemoteUtil.getPMF(req);
/* 240:240 */    ResponseEntity resp = new ResponseEntity();
/* 241:    */    try
/* 242:    */    {
/* 243:243 */      Map val = (Map)req.getData().get(0);
/* 244:244 */      String id = val.get("id").toString();
/* 245:245 */      SysEncoding se = (SysEncoding)pmf.get(SysEncoding.class, new Long(
/* 246:246 */        id));
/* 247:247 */      pmf.remove(se);
/* 248:    */    }
/* 249:    */    catch (Exception e)
/* 250:    */    {
/* 251:251 */      resp.setType(0);
/* 252:252 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 253:    */    }
/* 254:254 */    return resp;
/* 255:    */  }
/* 256:    */  
/* 257:    */  public ResponseEntity updateSysEncoding(RequestEntity req)
/* 258:    */  {
/* 259:259 */    PMF pmf = RemoteUtil.getPMF(req);
/* 260:260 */    ResponseEntity resp = new ResponseEntity();
/* 261:    */    try
/* 262:    */    {
/* 263:263 */      Map val = (Map)req.getData().get(0);
/* 264:264 */      String id = val.get("id").toString();
/* 265:265 */      SysEncoding se = (SysEncoding)pmf.get(SysEncoding.class, new Long(
/* 266:266 */        id));
/* 267:267 */      BeanUtil.copyProperty(val, se);
/* 268:268 */      pmf.update(se);
/* 269:    */    }
/* 270:    */    catch (Exception e)
/* 271:    */    {
/* 272:272 */      resp.setType(0);
/* 273:273 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 274:    */    }
/* 275:275 */    return resp;
/* 276:    */  }
/* 277:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.SystemService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */