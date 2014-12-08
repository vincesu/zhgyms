/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.business.entity.Trackprogress;
/*   5:    */import com.vincesu.framework.entity.SysUser;
/*   6:    */import com.vincesu.framework.remote.RemoteUtil;
/*   7:    */import com.vincesu.framework.remote.RequestEntity;
/*   8:    */import com.vincesu.framework.remote.ResponseEntity;
/*   9:    */import com.vincesu.framework.util.BeanUtil;
/*  10:    */import com.vincesu.framework.util.QueryUtil;
/*  11:    */import com.vincesu.framework.util.TimeUtil;
/*  12:    */import com.vincesu.persistence.PMF;
/*  13:    */import java.util.List;
/*  14:    */import java.util.Map;
/*  15:    */
/*  28:    */public class TrackProgressService
/*  29:    */{
/*  30:    */  public ResponseEntity queryList(RequestEntity req)
/*  31:    */    throws Exception
/*  32:    */  {
/*  33: 33 */    StringBuffer sql = new StringBuffer();
/*  34: 34 */    sql.append("select ");
/*  35: 35 */    sql.append("a.unit,");
/*  36: 36 */    sql.append("a.contact,");
/*  37: 37 */    sql.append("b.id,");
/*  38: 38 */    sql.append("b.clientid,");
/*  39: 39 */    sql.append("b.tracktime,");
/*  40: 40 */    sql.append("b.product,");
/*  41: 41 */    sql.append("b.progress,");
/*  42: 42 */    sql.append("b.havequote, ");
/*  43: 43 */    sql.append("c.realname, ");
/*  44: 44 */    sql.append("IFNULL(b.fileid1,''), ");
/*  45: 45 */    sql.append("IFNULL(b.fileid2,''), ");
/*  46: 46 */    sql.append("IFNULL(b.fileid3,''), ");
/*  47: 47 */    sql.append("IFNULL(b.fileid4,''), ");
/*  48: 48 */    sql.append("IFNULL(b.filename1,''), ");
/*  49: 49 */    sql.append("IFNULL(b.filename2,''), ");
/*  50: 50 */    sql.append("IFNULL(b.filename3,''), ");
/*  51: 51 */    sql.append("IFNULL(b.filename4,''), ");
/*  52: 52 */    sql.append("' ' tk ");
/*  53: 53 */    sql.append(" from client a,trackprogress b,sys_user c ");
/*  54: 54 */    sql.append(" where a.id=b.clientid and c.uid=a.saleman and a.reserved like '")
/*  55: 55 */      .append(req.getUser().getReserved()).append("%' ");
/*  56: 56 */    sql.append(" and a.number='-1' ");
/*  57: 57 */    sql.append("and contact like '%:contact%' ");
/*  58: 58 */    sql.append("and c.realname like '%:saleman%' ");
/*  59: 59 */    sql.append("and unit like '%:unit%' ");
/*  60: 60 */    sql.append("and a.reserved like '").append(req.getUser().getReserved()).append("%' ");
/*  61: 61 */    sql.append(" order by b.id desc");
/*  62: 62 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  63: 63 */      new String[] { "unit", "contact", "id", "clientid", "tracktime", "product", 
/*  64: 64 */      "progress", "havequote", "realname", "fileid1", "fileid2", "fileid3", "fileid4", 
/*  65: 65 */      "filename1", "filename2", "filename3", "filename4", "tk" }, 
/*  66: 66 */      new String[] { "unit", "contact", "id", "clientid", "tracktime", "product", 
/*  67: 67 */      "progress", "havequote", "realname", "fileid1", "fileid2", "fileid3", "fileid4", 
/*  68: 68 */      "filename1", "filename2", "filename3", "filename4", "tk" }, null, null);
/*  69:    */  }
/*  70:    */  
/*  71:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  72:    */  {
/*  73: 73 */    StringBuffer sql = new StringBuffer();
/*  74: 74 */    sql.append("select ");
/*  75: 75 */    sql.append("a.unit,");
/*  76: 76 */    sql.append("a.contact,");
/*  77: 77 */    sql.append("b.id,");
/*  78: 78 */    sql.append("b.clientid,");
/*  79: 79 */    sql.append("b.tracktime,");
/*  80: 80 */    sql.append("b.product,");
/*  81: 81 */    sql.append("b.progress,");
/*  82: 82 */    sql.append("b.havequote ");
/*  83: 83 */    sql.append(" from client a,trackprogress b ");
/*  84: 84 */    sql.append(" where a.id=b.clientid and a.reserved like '")
/*  85: 85 */      .append(req.getUser().getReserved()).append("%' ");
/*  86: 86 */    sql.append(" and a.number='-1' ");
/*  87: 87 */    sql.append("and b.id=:id ");
/*  88: 88 */    sql.append(" order by b.id desc");
/*  89: 89 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  90: 90 */      new String[] { "unit", "contact", "id", "clientid", "tracktime", "product", 
/*  91: 91 */      "progress", "havequote" }, null, null);
/*  92:    */  }
/*  93:    */  
/*  94:    */  public ResponseEntity add(RequestEntity req)
/*  95:    */  {
/*  96: 96 */    PMF pmf = RemoteUtil.getPMF(req);
/*  97: 97 */    ResponseEntity resp = new ResponseEntity();
/*  98:    */    try
/*  99:    */    {
/* 100:100 */      Map val = (Map)req.getData().get(0);
/* 101:101 */      Trackprogress tp = new Trackprogress();
/* 102:102 */      BeanUtil.copyProperty(val, tp);
/* 103:    */      try
/* 104:    */      {
/* 105:105 */        if ((val.get("tracktime") != null) && (!val.get("tracktime").equals(""))) {
/* 106:106 */          tp.setTracktime(TimeUtil.toDate(val.get("tracktime").toString(), "yyyy-MM-dd"));
/* 107:    */        }
/* 108:    */      } catch (Exception localException1) {}
/* 109:109 */      pmf.save(tp);
/* 110:    */    }
/* 111:    */    catch (Exception e)
/* 112:    */    {
/* 113:113 */      resp.setType(0);
/* 114:114 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 115:    */    }
/* 116:116 */    return resp;
/* 117:    */  }
/* 118:    */  
/* 119:    */  public ResponseEntity remove(RequestEntity req)
/* 120:    */  {
/* 121:121 */    PMF pmf = RemoteUtil.getPMF(req);
/* 122:122 */    ResponseEntity resp = new ResponseEntity();
/* 123:    */    try
/* 124:    */    {
/* 125:125 */      Map val = (Map)req.getData().get(0);
/* 126:126 */      String id = val.get("id").toString();
/* 127:127 */      Trackprogress tp = (Trackprogress)pmf.get(Trackprogress.class, new Long(id));
/* 128:128 */      if (tp != null)
/* 129:    */      {
/* 130:130 */        pmf.remove(tp);
/* 131:    */      }
/* 132:    */      else
/* 133:    */      {
/* 134:134 */        resp.setType(0);
/* 135:135 */        resp.setMessage("删除失败");
/* 136:    */      }
/* 137:    */    }
/* 138:    */    catch (Exception e)
/* 139:    */    {
/* 140:140 */      resp.setType(0);
/* 141:141 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 142:    */    }
/* 143:143 */    return resp;
/* 144:    */  }
/* 145:    */  
/* 146:    */  public ResponseEntity update(RequestEntity req)
/* 147:    */  {
/* 148:148 */    PMF pmf = RemoteUtil.getPMF(req);
/* 149:149 */    ResponseEntity resp = new ResponseEntity();
/* 150:    */    try
/* 151:    */    {
/* 152:152 */      Map val = (Map)req.getData().get(0);
/* 153:153 */      String id = val.get("id").toString();
/* 154:154 */      Trackprogress tp = (Trackprogress)pmf.get(Trackprogress.class, new Long(id));
/* 155:155 */      if (tp != null)
/* 156:    */      {
/* 157:157 */        BeanUtil.copyProperty(val, tp);
/* 158:158 */        pmf.update(tp);
/* 159:    */      }
/* 160:    */      else
/* 161:    */      {
/* 162:162 */        resp.setType(0);
/* 163:163 */        resp.setMessage("修改失败");
/* 164:    */      }
/* 165:    */      
/* 166:    */    }
/* 167:    */    catch (Exception e)
/* 168:    */    {
/* 169:169 */      resp.setType(0);
/* 170:170 */      resp.setMessage("修改失败，错误信息：" + e.getMessage());
/* 171:    */    }
/* 172:172 */    return resp;
/* 173:    */  }
/* 174:    */  
/* 175:    */  public ResponseEntity addTrackProgressFile(RequestEntity req)
/* 176:    */  {
/* 177:177 */    PMF pmf = RemoteUtil.getPMF(req);
/* 178:178 */    ResponseEntity resp = new ResponseEntity();
/* 179:    */    try
/* 180:    */    {
/* 181:181 */      Map val = (Map)req.getData().get(0);
/* 182:182 */      String fileId = val.get("id").toString();
/* 183:183 */      String relatedId = val.get("relatedId").toString();
/* 184:184 */      Long tpid = new Long(val.get("relatedId").toString());
/* 185:185 */      Trackprogress tp = (Trackprogress)pmf.get(Trackprogress.class, tpid);
/* 186:186 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 187:187 */      file.setType(Integer.valueOf(Integer.parseInt("26")));
/* 188:188 */      file.setRelatedObject(new Long(relatedId));
/* 189:189 */      if ((tp.getFileid1() == null) || (tp.getFileid1().longValue() == 0L))
/* 190:    */      {
/* 191:191 */        tp.setFileid1(file.getId());
/* 192:192 */        tp.setFilename1(file.getName());
/* 193:    */      }
/* 194:194 */      else if ((tp.getFileid2() == null) || (tp.getFileid2().longValue() == 0L))
/* 195:    */      {
/* 196:196 */        tp.setFileid2(file.getId());
/* 197:197 */        tp.setFilename2(file.getName());
/* 198:    */      }
/* 199:199 */      else if ((tp.getFileid3() == null) || (tp.getFileid3().longValue() == 0L))
/* 200:    */      {
/* 201:201 */        tp.setFileid3(file.getId());
/* 202:202 */        tp.setFilename3(file.getName());
/* 203:    */      }
/* 204:204 */      else if ((tp.getFileid4() == null) || (tp.getFileid4().longValue() == 0L))
/* 205:    */      {
/* 206:206 */        tp.setFileid4(file.getId());
/* 207:207 */        tp.setFilename4(file.getName());
/* 208:    */      }
/* 209:    */      else
/* 210:    */      {
/* 211:211 */        resp.setType(0);
/* 212:212 */        resp.setMessage("已经添加了四个文件，不能再添加文件了");
/* 213:213 */        return resp;
/* 214:    */      }
/* 215:215 */      pmf.update(file);
/* 216:    */      
/* 217:217 */      pmf.flush();
/* 218:    */    }
/* 219:    */    catch (Exception e)
/* 220:    */    {
/* 221:221 */      resp.setType(0);
/* 222:222 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 223:    */    }
/* 224:224 */    return resp;
/* 225:    */  }
/* 226:    */  
/* 227:    */  public ResponseEntity removeTrackProgressFile(RequestEntity req)
/* 228:    */  {
/* 229:229 */    PMF pmf = RemoteUtil.getPMF(req);
/* 230:230 */    ResponseEntity resp = new ResponseEntity();
/* 231:    */    try
/* 232:    */    {
/* 233:233 */      Map val = (Map)req.getData().get(0);
/* 234:234 */      String fileId = val.get("fileid").toString();
/* 235:    */      
/* 236:236 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 237:237 */      Trackprogress tp = (Trackprogress)pmf.get(Trackprogress.class, file.getRelatedObject());
/* 238:    */      
/* 239:239 */      if ((file == null) || (tp == null))
/* 240:    */      {
/* 241:241 */        resp.setType(0);
/* 242:242 */        resp.setMessage("删除失败");
/* 243:243 */        return resp;
/* 244:    */      }
/* 245:    */      
/* 246:246 */      if ((tp.getFileid1() != null) && (tp.getFileid1().equals(file.getId())))
/* 247:    */      {
/* 248:248 */        tp.setFileid1(null);
/* 249:249 */        tp.setFilename1(null);
/* 250:    */      }
/* 251:251 */      if ((tp.getFileid2() != null) && (tp.getFileid2().equals(file.getId())))
/* 252:    */      {
/* 253:253 */        tp.setFileid2(null);
/* 254:254 */        tp.setFilename2(null);
/* 255:    */      }
/* 256:256 */      if ((tp.getFileid3() != null) && (tp.getFileid3().equals(file.getId())))
/* 257:    */      {
/* 258:258 */        tp.setFileid3(null);
/* 259:259 */        tp.setFilename3(null);
/* 260:    */      }
/* 261:261 */      if ((tp.getFileid4() != null) && (tp.getFileid4().equals(file.getId())))
/* 262:    */      {
/* 263:263 */        tp.setFileid4(null);
/* 264:264 */        tp.setFilename4(null);
/* 265:    */      }
/* 266:    */      
/* 267:267 */      pmf.remove(file);
/* 268:268 */      pmf.update(tp);
/* 269:    */      
/* 270:270 */      pmf.flush();
/* 271:    */    }
/* 272:    */    catch (Exception e)
/* 273:    */    {
/* 274:274 */      resp.setType(0);
/* 275:275 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 276:    */    }
/* 277:277 */    return resp;
/* 278:    */  }
/* 279:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.TrackProgressService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */