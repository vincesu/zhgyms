/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.business.entity.MaterialsInformation;
/*   5:    */import com.vincesu.framework.remote.RemoteUtil;
/*   6:    */import com.vincesu.framework.remote.RequestEntity;
/*   7:    */import com.vincesu.framework.remote.ResponseEntity;
/*   8:    */import com.vincesu.framework.util.BeanUtil;
/*   9:    */import com.vincesu.framework.util.QueryUtil;
/*  10:    */import com.vincesu.persistence.PMF;
/*  11:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  12:    */import java.io.File;
/*  13:    */import java.io.FileInputStream;
/*  14:    */import java.io.IOException;
/*  15:    */import java.io.OutputStream;
/*  16:    */import java.io.PrintStream;
/*  17:    */import java.sql.Connection;
/*  18:    */import java.sql.PreparedStatement;
/*  19:    */import java.sql.SQLException;
/*  20:    */import java.util.ArrayList;
/*  21:    */import java.util.List;
/*  22:    */import java.util.Map;
/*  23:    */import jxl.Cell;
/*  24:    */import jxl.Sheet;
/*  25:    */import jxl.Workbook;
/*  26:    */import jxl.write.Label;
/*  27:    */import jxl.write.WritableImage;
/*  28:    */import jxl.write.WritableSheet;
/*  29:    */import jxl.write.WritableWorkbook;
/*  30:    */import jxl.write.WriteException;
/*  31:    */import org.hibernate.jdbc.Work;
/*  32:    */
/*  33:    */public class MaterialsService
/*  34:    */{
/*  35:    */  public ResponseEntity queryList(RequestEntity req)
/*  36:    */    throws Exception
/*  37:    */  {
/*  38: 38 */    StringBuffer sql = new StringBuffer();
/*  39: 39 */    sql.append("select b.webpath, a.* from materials_information a LEFT JOIN files b ON a.id=b.related_object and b.type=5 ");
/*  40: 40 */    sql.append("where 1=1 ");
/*  41: 41 */    sql.append(" and a.name like '%:name%' ");
/*  42: 42 */    sql.append(" and a.number like '%:number%' ");
/*  43: 43 */    sql.append(" and a.category=':category' ");
/*  44: 44 */    sql.append(" order by a.id desc");
/*  45: 45 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  46: 46 */      new String[] { "path", "id", "number", "name", "size", "price", "manufacturers", "contact", "phone", "category" }, 
/*  47: 47 */      new String[] { "path", "id", "number", "name", "size", "price", "manufacturers", "contact", "phone", "category" }, null, null);
/*  48:    */  }
/*  49:    */  
/*  50:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  51:    */  {
/*  52: 52 */    StringBuffer sql = new StringBuffer();
/*  53: 53 */    sql.append("select a.*,b.webpath from materials_information a LEFT JOIN files b ON a.id=b.related_object and b.type=5 ");
/*  54: 54 */    sql.append("where 1=1 ");
/*  55: 55 */    sql.append(" and a.id=:id ");
/*  56: 56 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  57: 57 */      new String[] { "id", "number", "name", "size", "price", "manufacturers", "contact", "phone", "category", "path" }, null, null);
/*  58:    */  }
/*  59:    */  
/*  60:    */  public ResponseEntity add(RequestEntity req)
/*  61:    */  {
/*  62: 62 */    PMF pmf = RemoteUtil.getPMF(req);
/*  63: 63 */    ResponseEntity resp = new ResponseEntity();
/*  64:    */    try
/*  65:    */    {
/*  66: 66 */      Map val = (Map)req.getData().get(0);
/*  67: 67 */      MaterialsInformation mi = new MaterialsInformation();
/*  68: 68 */      BeanUtil.copyProperty(val, mi);
/*  69: 69 */      pmf.save(mi);
/*  70: 70 */      if ((val.get("fileid") != null) && (val.get("fileid").toString() != ""))
/*  71:    */      {
/*  72: 72 */        String fileId = val.get("fileid").toString();
/*  73: 73 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/*  74: 74 */        file.setType(new Integer("5"));
/*  75: 75 */        file.setRelatedObject(mi.getId());
/*  76: 76 */        pmf.update(file);
/*  77:    */      }
/*  78:    */    }
/*  79:    */    catch (Exception e)
/*  80:    */    {
/*  81: 81 */      resp.setType(0);
/*  82: 82 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  83:    */    }
/*  84: 84 */    return resp;
/*  85:    */  }
/*  86:    */  
/*  87:    */  public ResponseEntity remove(RequestEntity req)
/*  88:    */  {
/*  89: 89 */    PMF pmf = RemoteUtil.getPMF(req);
/*  90: 90 */    ResponseEntity resp = new ResponseEntity();
/*  91:    */    try
/*  92:    */    {
/*  93: 93 */      Map val = (Map)req.getData().get(0);
/*  94: 94 */      String id = val.get("id").toString();
/*  95:    */      
/*  96: 96 */      CommonService.delete(pmf, new Long(id), new Integer("5").intValue(), req.getUser());
/*  97: 97 */      MaterialsInformation mi = (MaterialsInformation)pmf.get(MaterialsInformation.class, new Long(id));
/*  98: 98 */      pmf.remove(mi);
/*  99:    */    }
/* 100:    */    catch (Exception e)
/* 101:    */    {
/* 102:102 */      resp.setType(0);
/* 103:103 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 104:    */    }
/* 105:105 */    return resp;
/* 106:    */  }
/* 107:    */  
/* 108:    */  public ResponseEntity update(RequestEntity req)
/* 109:    */  {
/* 110:110 */    PMF pmf = RemoteUtil.getPMF(req);
/* 111:111 */    ResponseEntity resp = new ResponseEntity();
/* 112:    */    try
/* 113:    */    {
/* 114:114 */      Map val = (Map)req.getData().get(0);
/* 115:115 */      String id = val.get("id").toString();
/* 116:116 */      MaterialsInformation mi = (MaterialsInformation)pmf.get(MaterialsInformation.class, new Long(id));
/* 117:117 */      BeanUtil.copyProperty(val, mi);
/* 118:118 */      pmf.update(mi);
/* 119:    */      
/* 120:120 */      if ((val.get("fileid") != null) && (!val.get("fileid").toString().equals(""))) {
/* 121:121 */        String fileId = val.get("fileid").toString();
/* 122:122 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 123:123 */        file.setType(new Integer("5"));
/* 124:124 */        file.setRelatedObject(mi.getId());
/* 125:125 */        pmf.update(file);
/* 126:    */        
/* 127:127 */        CommonService.deleteFilesByRelatedObject(pmf, mi.getId(), file.getId(), 
/* 128:128 */          "5", req.getUser());
/* 129:    */        
/* 130:130 */        pmf.save(file);
/* 131:    */      }
/* 132:    */    }
/* 133:    */    catch (Exception e)
/* 134:    */    {
/* 135:135 */      resp.setType(0);
/* 136:136 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 137:    */    }
/* 138:138 */    return resp;
/* 139:    */  }
/* 140:    */  
/* 141:    */  public ResponseEntity exportData(RequestEntity req) throws IOException, WriteException
/* 142:    */  {
/* 143:143 */    PMF pmf = RemoteUtil.getPMF(req);
/* 144:144 */    ResponseEntity resp = new ResponseEntity();
/* 145:145 */    WritableWorkbook workbook = null;
/* 146:    */    try
/* 147:    */    {
/* 148:148 */      Map val = (Map)req.getData().get(0);
/* 149:149 */      OutputStream os = (OutputStream)val.get("filestream");
/* 150:150 */      workbook = Workbook.createWorkbook(os);
/* 151:151 */      WritableSheet sheet = workbook.createSheet("materials", 0);
/* 152:152 */      Label label = null;
/* 153:153 */      int i = 0;int j = 0;
/* 154:    */      
/* 155:155 */      String[] titles = {
/* 156:    */      
/* 157:157 */        "名称", 
/* 158:158 */        "尺寸", "价格", "厂家", "联系人", "电话", "类别", "图片" };
/* 159:    */      
/* 160:160 */      for (i = 0; i < titles.length; i++)
/* 161:    */      {
/* 162:162 */        label = new Label(i, 0, titles[i]);
/* 163:163 */        sheet.addCell(label);
/* 164:    */      }
/* 165:    */      
/* 166:166 */      sheet.setColumnView(7, 20);
/* 167:    */      
/* 168:168 */      StringBuffer sql = new StringBuffer();
/* 169:169 */      sql.append("select {pppp.*},{ffff.*} from materials_information pppp left join files ffff on pppp.id=ffff.related_object and ffff.type=")
/* 170:170 */        .append("5").append(" order by pppp.id desc ");
/* 171:    */      
/* 172:172 */      List<Object[]> data = pmf.get(sql.toString(), new Class[] { MaterialsInformation.class, Files.class }, new String[] { "pppp", "ffff" });
/* 173:173 */      List<MaterialsInformation> datas = new ArrayList();
/* 174:174 */      MaterialsInformation mi = null;
/* 175:175 */      Files f = null;
/* 176:176 */      for (Object[] objs : data)
/* 177:    */      {
/* 178:178 */        mi = (MaterialsInformation)objs[0];
/* 179:179 */        f = (Files)objs[1];
/* 180:180 */        if (f != null) {
/* 181:181 */          mi.setPicPath(f.getPath());
/* 182:    */        } else
/* 183:183 */          mi.setPicPath(null);
/* 184:184 */        datas.add(mi);
/* 185:    */      }
/* 186:    */      
/* 187:187 */      for (i = 0; i < datas.size(); i++)
/* 188:    */      {
/* 189:189 */        j = 0;
/* 190:    */        
/* 191:191 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getName());
/* 192:192 */        sheet.addCell(label);
/* 193:193 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getSize());
/* 194:194 */        sheet.addCell(label);
/* 195:195 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getPrice().toString());
/* 196:196 */        sheet.addCell(label);
/* 197:197 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getManufacturers());
/* 198:198 */        sheet.addCell(label);
/* 199:199 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getContact());
/* 200:200 */        sheet.addCell(label);
/* 201:201 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getPhone());
/* 202:202 */        sheet.addCell(label);
/* 203:203 */        label = new Label(j++, i + 1, ((MaterialsInformation)datas.get(i)).getCategory());
/* 204:204 */        sheet.addCell(label);
/* 205:205 */        if (((MaterialsInformation)datas.get(i)).getPicPath() != null)
/* 206:    */        {
/* 207:207 */          WritableImage ri = new WritableImage(j++, i + 1, 1.0D, 1.0D, new File(((MaterialsInformation)datas.get(i)).getPicPath()));
/* 208:208 */          sheet.addImage(ri);
/* 209:    */        }
/* 210:    */        else
/* 211:    */        {
/* 212:212 */          j++;
/* 213:    */        }
/* 214:214 */        sheet.setRowView(i + 1, 2000);
/* 215:    */      }
/* 216:    */      
/* 217:    */    }
/* 218:    */    catch (Exception e)
/* 219:    */    {
/* 220:220 */      System.out.println(e.getMessage());
/* 221:221 */      resp.setType(0);
/* 222:222 */      resp.setMessage("导出失败");
/* 223:    */    }
/* 224:    */    finally
/* 225:    */    {
/* 226:226 */      if (workbook != null)
/* 227:    */      {
/* 228:228 */        workbook.write();
/* 229:229 */        workbook.close();
/* 230:    */      }
/* 231:    */    }
/* 232:232 */    return resp;
/* 233:    */  }
/* 234:    */  
/* 235:    */  public ResponseEntity importData(RequestEntity req)
/* 236:    */  {
/* 237:237 */    PMF pmf = RemoteUtil.getPMF(req);
/* 238:238 */    ResponseEntity resp = new ResponseEntity();
/* 239:239 */    Workbook workbook = null;
/* 240:    */    try
/* 241:    */    {
/* 242:242 */      Map data = (Map)req.getData().get(0);
/* 243:243 */      FileInputStream fis = (FileInputStream)data.get("filestream");
/* 244:244 */      workbook = Workbook.getWorkbook(fis);
/* 245:245 */      final Sheet sheet = workbook.getSheet(0);
/* 246:246 */      pmf.doWorkWithConnection(new Work()
/* 247:    */      {
/* 248:    */        public void execute(Connection conn) throws SQLException
/* 249:    */        {
/* 250:250 */          PMF p = new PMF(SessionFactoryHelper.getSession());
/* 251:251 */          List<MaterialsInformation> miList = p.list("from MaterialsInformation order by id desc", null, 0, 1);
/* 252:252 */          Long id = new Long(1L);
/* 253:253 */          if ((miList != null) && (miList.size() != 0))
/* 254:254 */            id = ((MaterialsInformation)miList.get(0)).getId();
/* 255:255 */          String sql = "insert into materials_information values(?,null,?,?,?,?,?,?,?)";
/* 256:256 */          PreparedStatement prest = conn.prepareStatement(sql);
/* 257:    */          try
/* 258:    */          {
/* 259:259 */            for (int i = 1; i < sheet.getRows(); i++)
/* 260:    */            {
/* 261:261 */              prest.setLong(1, (id = Long.valueOf(id.longValue() + 1L)).longValue());
/* 262:262 */              prest.setString(2, sheet.getCell(0, i).getContents());
/* 263:263 */              prest.setString(3, sheet.getCell(1, i).getContents());
/* 264:264 */              prest.setString(4, sheet.getCell(2, i).getContents());
/* 265:265 */              prest.setString(5, sheet.getCell(3, i).getContents());
/* 266:266 */              prest.setString(6, sheet.getCell(4, i).getContents());
/* 267:267 */              prest.setString(7, sheet.getCell(5, i).getContents());
/* 268:268 */              prest.setString(8, sheet.getCell(6, i).getContents());
/* 269:269 */              prest.addBatch();
/* 270:    */            }
/* 271:271 */            prest.executeBatch();
/* 272:272 */            conn.commit();
/* 273:    */          }
/* 274:    */          catch (Exception e)
/* 275:    */          {
/* 276:276 */            conn.rollback();
/* 277:    */          }
/* 278:    */          finally
/* 279:    */          {
/* 280:280 */            prest.close();
/* 281:281 */            conn.close();
/* 282:    */          }
/* 283:    */        }
/* 284:    */      });
/* 285:    */    }
/* 286:    */    catch (Exception e)
/* 287:    */    {
/* 288:288 */      resp.setType(0);
/* 289:289 */      resp.setMessage("导入数据出错");
/* 290:    */    }
/* 291:    */    finally
/* 292:    */    {
/* 293:293 */      if (workbook != null)
/* 294:294 */        workbook.close();
/* 295:    */    }
/* 296:296 */    resp.setMessage("数据已导入");
/* 297:297 */    return resp;
/* 298:    */  }
/* 299:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.MaterialsService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */