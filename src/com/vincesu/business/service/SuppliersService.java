/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Suppliers;
/*   4:    */import com.vincesu.framework.entity.SysEncoding;
/*   5:    */import com.vincesu.framework.model.EncodingModel;
/*   6:    */import com.vincesu.framework.remote.RemoteUtil;
/*   7:    */import com.vincesu.framework.remote.RequestEntity;
/*   8:    */import com.vincesu.framework.remote.ResponseEntity;
/*   9:    */import com.vincesu.framework.util.BeanUtil;
/*  10:    */import com.vincesu.framework.util.EncodingUtil;
/*  11:    */import com.vincesu.framework.util.QueryUtil;
/*  12:    */import com.vincesu.persistence.PMF;
/*  13:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  14:    */import java.io.FileInputStream;
/*  15:    */import java.io.IOException;
/*  16:    */import java.io.OutputStream;
/*  17:    */import java.io.PrintStream;
/*  18:    */import java.sql.Connection;
/*  19:    */import java.sql.PreparedStatement;
/*  20:    */import java.sql.SQLException;
/*  21:    */import java.util.List;
/*  22:    */import java.util.Map;
/*  23:    */import jxl.Cell;
/*  24:    */import jxl.Sheet;
/*  25:    */import jxl.Workbook;
/*  26:    */import jxl.write.Label;
/*  27:    */import jxl.write.WritableSheet;
/*  28:    */import jxl.write.WritableWorkbook;
/*  29:    */import jxl.write.WriteException;
/*  30:    */import org.hibernate.jdbc.Work;
/*  31:    */
/*  32:    */public class SuppliersService
/*  33:    */{
/*  34:    */  public ResponseEntity queryList(RequestEntity req) throws Exception
/*  35:    */  {
/*  36: 36 */    StringBuffer sql = new StringBuffer();
/*  37: 37 */    sql.append("select a.* from suppliers a ");
/*  38: 38 */    sql.append("where a.contact like '%:contact%' ");
/*  39: 39 */    sql.append(" and a.unit like '%:unit%' ");
/*  40: 40 */    sql.append(" and a.main_items like '%:mainitems%' ");
/*  41: 41 */    sql.append(" order by a.id desc");
/*  42: 42 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  43: 43 */      new String[] { "id", "number", "contact", "unit", "phone", "mainItems", 
/*  44: 44 */      "contactWay", "address", "bank", "bankAccount", 
/*  45: 45 */      "bankAccountName", "reserved" }, 
/*  46: 46 */      new String[] { "id", "number", "contact", "unit", "phone", "mainItems", 
/*  47: 47 */      "contactWay", "address", "bank", "bankAccount", 
/*  48: 48 */      "bankAccountName", "reserved" }, null, null);
/*  49:    */  }
/*  50:    */  
/*  51:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  52:    */  {
/*  53: 53 */    StringBuffer sql = new StringBuffer();
/*  54: 54 */    sql.append("select a.* from suppliers a ");
/*  55: 55 */    sql.append("where a.id=:id ");
/*  56: 56 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  57: 57 */      new String[] { "id", "number", "contact", "unit", "phone", "mainItems", 
/*  58: 58 */      "contactWay", "address", "bank", "bankAccount", 
/*  59: 59 */      "bankAccountName", "reserved" }, null, null);
/*  60:    */  }
/*  61:    */  
/*  62:    */  public ResponseEntity add(RequestEntity req)
/*  63:    */  {
/*  64: 64 */    PMF pmf = RemoteUtil.getPMF(req);
/*  65: 65 */    ResponseEntity resp = new ResponseEntity();
/*  66:    */    try
/*  67:    */    {
/*  68: 68 */      Map val = (Map)req.getData().get(0);
/*  69: 69 */      Suppliers supplier = new Suppliers();
/*  70: 70 */      BeanUtil.copyProperty(val, supplier);
/*  71:    */      
/*  74: 74 */      if (pmf.count("select * from suppliers where contact='" + supplier.getContact() + "' and unit='" + supplier.getUnit() + "'") > 0L)
/*  75:    */      {
/*  76: 76 */        resp.setType(0);
/*  77: 77 */        resp.setMessage("添加失败，已存在此供应商信息（联系人与单位信息重复）");
/*  78: 78 */        return resp;
/*  79:    */      }
/*  80:    */      
/*  81: 81 */      if (supplier.getMainItems().equals("其他"))
/*  82:    */      {
/*  83: 83 */        if ((val.get("othermainitems") != null) && 
/*  84: 84 */          (!val.get("othermainitems").equals("")))
/*  85:    */        {
/*  86: 86 */          checkMainItems(req, pmf, val.get("othermainitems")
/*  87: 87 */            .toString());
/*  88: 88 */          supplier.setMainItems(val.get("othermainitems").toString());
/*  89:    */        }
/*  90:    */        else
/*  91:    */        {
/*  92: 92 */          resp.setType(0);
/*  93: 93 */          resp.setMessage("添加失败，请输入其他主营项目");
/*  94: 94 */          return resp;
/*  95:    */        }
/*  96:    */      }
/*  97: 97 */      pmf.save(supplier);
/*  98:    */    }
/*  99:    */    catch (Exception e)
/* 100:    */    {
/* 101:101 */      resp.setType(0);
/* 102:102 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 103:    */    }
/* 104:104 */    return resp;
/* 105:    */  }
/* 106:    */  
/* 107:    */  public ResponseEntity remove(RequestEntity req)
/* 108:    */  {
/* 109:109 */    PMF pmf = RemoteUtil.getPMF(req);
/* 110:110 */    ResponseEntity resp = new ResponseEntity();
/* 111:    */    try
/* 112:    */    {
/* 113:113 */      Map val = (Map)req.getData().get(0);
/* 114:114 */      String id = val.get("id").toString();
/* 115:115 */      Suppliers supplier = (Suppliers)pmf.get(Suppliers.class, new Long(
/* 116:116 */        id));
/* 117:117 */      pmf.remove(supplier);
/* 118:    */    }
/* 119:    */    catch (Exception e)
/* 120:    */    {
/* 121:121 */      resp.setType(0);
/* 122:122 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 123:    */    }
/* 124:124 */    return resp;
/* 125:    */  }
/* 126:    */  
/* 127:    */  public ResponseEntity update(RequestEntity req)
/* 128:    */  {
/* 129:129 */    PMF pmf = RemoteUtil.getPMF(req);
/* 130:130 */    ResponseEntity resp = new ResponseEntity();
/* 131:    */    try
/* 132:    */    {
/* 133:133 */      Map val = (Map)req.getData().get(0);
/* 134:134 */      String id = val.get("id").toString();
/* 135:135 */      Suppliers supplier = (Suppliers)pmf.get(Suppliers.class, new Long(
/* 136:136 */        id));
/* 137:137 */      BeanUtil.copyProperty(val, supplier);
/* 138:    */      
/* 139:139 */      if (supplier.getMainItems().equals("其他"))
/* 140:    */      {
/* 141:141 */        if ((val.get("othermainitems") != null) && 
/* 142:142 */          (!val.get("othermainitems").equals("")))
/* 143:    */        {
/* 144:144 */          checkMainItems(req, pmf, val.get("othermainitems")
/* 145:145 */            .toString());
/* 146:146 */          supplier.setMainItems(val.get("othermainitems").toString());
/* 147:    */        }
/* 148:    */        else
/* 149:    */        {
/* 150:150 */          resp.setType(0);
/* 151:151 */          resp.setMessage("添加失败，请输入其他主营项目");
/* 152:152 */          return resp;
/* 153:    */        }
/* 154:    */      }
/* 155:    */      
/* 156:156 */      pmf.update(supplier);
/* 157:    */    }
/* 158:    */    catch (Exception e)
/* 159:    */    {
/* 160:160 */      resp.setType(0);
/* 161:161 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 162:    */    }
/* 163:163 */    return resp;
/* 164:    */  }
/* 165:    */  
/* 166:    */  public ResponseEntity exportData(RequestEntity req)
/* 167:    */    throws IOException, WriteException
/* 168:    */  {
/* 169:169 */    PMF pmf = RemoteUtil.getPMF(req);
/* 170:170 */    ResponseEntity resp = new ResponseEntity();
/* 171:171 */    WritableWorkbook workbook = null;
/* 172:    */    try
/* 173:    */    {
/* 174:174 */      Map val = (Map)req.getData().get(0);
/* 175:175 */      OutputStream os = (OutputStream)val.get("filestream");
/* 176:176 */      workbook = Workbook.createWorkbook(os);
/* 177:177 */      WritableSheet sheet = workbook.createSheet("product", 0);
/* 178:178 */      Label label = null;
/* 179:179 */      int i = 0;
/* 180:180 */      int j = 0;
/* 181:    */      
/* 182:182 */      String[] titles = 
/* 183:183 */        { "序号", "联系人", "单位", "电话", "主营项目", "联系方式", "地址", "开户行", "银行账户", 
/* 184:184 */        "银行账户名" };
/* 185:185 */      for (i = 0; i < titles.length; i++)
/* 186:    */      {
/* 187:187 */        label = new Label(i, 0, titles[i]);
/* 188:188 */        sheet.addCell(label);
/* 189:    */      }
/* 190:    */      
/* 191:191 */      sheet.setColumnView(1, 15);
/* 192:192 */      sheet.setColumnView(2, 20);
/* 193:193 */      sheet.setColumnView(3, 13);
/* 194:194 */      sheet.setColumnView(4, 20);
/* 195:195 */      sheet.setColumnView(5, 15);
/* 196:196 */      sheet.setColumnView(6, 25);
/* 197:197 */      sheet.setColumnView(7, 12);
/* 198:198 */      sheet.setColumnView(8, 12);
/* 199:199 */      sheet.setColumnView(9, 12);
/* 200:    */      
/* 201:201 */      String hql = "from Suppliers";
/* 202:202 */      List<Suppliers> datas = pmf.list(hql);
/* 203:203 */      for (i = 0; i < datas.size(); i++)
/* 204:    */      {
/* 205:205 */        j = 0;
/* 206:206 */        label = new Label(j++, i + 1, 
/* 207:207 */          ((Suppliers)datas.get(i)).getNumber());
/* 208:208 */        sheet.addCell(label);
/* 209:209 */        label = new Label(j++, i + 1, 
/* 210:210 */          ((Suppliers)datas.get(i)).getContact());
/* 211:211 */        sheet.addCell(label);
/* 212:212 */        label = new Label(j++, i + 1, 
/* 213:213 */          ((Suppliers)datas.get(i)).getUnit());
/* 214:214 */        sheet.addCell(label);
/* 215:215 */        label = new Label(j++, i + 1, 
/* 216:216 */          ((Suppliers)datas.get(i)).getPhone());
/* 217:217 */        sheet.addCell(label);
/* 218:218 */        label = new Label(j++, i + 1, 
/* 219:219 */          ((Suppliers)datas.get(i)).getMainItems());
/* 220:220 */        sheet.addCell(label);
/* 221:221 */        label = new Label(j++, i + 1, 
/* 222:222 */          ((Suppliers)datas.get(i)).getContactWay());
/* 223:223 */        sheet.addCell(label);
/* 224:224 */        label = new Label(j++, i + 1, 
/* 225:225 */          ((Suppliers)datas.get(i)).getAddress());
/* 226:226 */        sheet.addCell(label);
/* 227:227 */        label = new Label(j++, i + 1, 
/* 228:228 */          ((Suppliers)datas.get(i)).getBank());
/* 229:229 */        sheet.addCell(label);
/* 230:230 */        label = new Label(j++, i + 1, 
/* 231:231 */          ((Suppliers)datas.get(i)).getBankAccount());
/* 232:232 */        sheet.addCell(label);
/* 233:233 */        label = new Label(j++, i + 1, 
/* 234:234 */          ((Suppliers)datas.get(i)).getBankAccountName());
/* 235:235 */        sheet.addCell(label);
/* 236:    */      }
/* 237:    */      
/* 238:    */    }
/* 239:    */    catch (Exception e)
/* 240:    */    {
/* 241:241 */      System.out.println(e.getMessage());
/* 242:242 */      resp.setType(0);
/* 243:243 */      resp.setMessage("导出失败");
/* 244:    */    }
/* 245:    */    finally
/* 246:    */    {
/* 247:247 */      if (workbook != null)
/* 248:    */      {
/* 249:249 */        workbook.write();
/* 250:250 */        workbook.close();
/* 251:    */      }
/* 252:    */    }
/* 253:253 */    return resp;
/* 254:    */  }
/* 255:    */  
/* 256:    */  public ResponseEntity importData(RequestEntity req)
/* 257:    */  {
/* 258:258 */    PMF pmf = RemoteUtil.getPMF(req);
/* 259:259 */    ResponseEntity resp = new ResponseEntity();
/* 260:260 */    Workbook workbook = null;
/* 261:    */    try
/* 262:    */    {
/* 263:263 */      Map data = (Map)req.getData().get(0);
/* 264:264 */      FileInputStream fis = (FileInputStream)data.get("filestream");
/* 265:265 */      workbook = Workbook.getWorkbook(fis);
/* 266:266 */      final Sheet sheet = workbook.getSheet(0);
/* 267:267 */      pmf.doWorkWithConnection(new Work()
/* 268:    */      {
/* 269:    */        public void execute(Connection conn) throws SQLException
/* 270:    */        {
/* 271:271 */          PMF p = new PMF(SessionFactoryHelper.getSession());
/* 272:272 */          List<Suppliers> suppliers = p.list(
/* 273:273 */            "from Suppliers order by id desc", null, 0, 1);
/* 274:274 */          Long id = new Long(1L);
/* 275:275 */          if ((suppliers != null) && (suppliers.size() != 0))
/* 276:276 */            id = ((Suppliers)suppliers.get(0)).getId();
/* 277:277 */          String sql = "insert into suppliers values(?,?,?,?,?,?,?,?,?,?,?,null)";
/* 278:278 */          PreparedStatement prest = conn.prepareStatement(sql);
/* 279:    */          try
/* 280:    */          {
/* 281:281 */            for (int i = 1; i < sheet.getRows(); i++)
/* 282:    */            {
/* 283:283 */              prest.setLong(1, 
/* 284:284 */                (id = Long.valueOf(id.longValue() + 1L))
/* 285:285 */                .longValue());
/* 286:286 */              prest.setString(2, sheet.getCell(0, i)
/* 287:287 */                .getContents());
/* 288:288 */              prest.setString(3, sheet.getCell(1, i)
/* 289:289 */                .getContents());
/* 290:290 */              prest.setString(4, sheet.getCell(2, i)
/* 291:291 */                .getContents());
/* 292:292 */              prest.setString(5, sheet.getCell(3, i)
/* 293:293 */                .getContents());
/* 294:294 */              prest.setString(6, sheet.getCell(4, i)
/* 295:295 */                .getContents());
/* 296:296 */              prest.setString(7, sheet.getCell(5, i)
/* 297:297 */                .getContents());
/* 298:298 */              prest.setString(8, sheet.getCell(6, i)
/* 299:299 */                .getContents());
/* 300:300 */              prest.setString(9, sheet.getCell(7, i)
/* 301:301 */                .getContents());
/* 302:302 */              prest.setString(10, sheet.getCell(8, i)
/* 303:303 */                .getContents());
/* 304:304 */              prest.setString(11, sheet.getCell(9, i)
/* 305:305 */                .getContents());
/* 306:306 */              prest.addBatch();
/* 307:    */            }
/* 308:308 */            prest.executeBatch();
/* 309:309 */            conn.commit();
/* 310:    */          }
/* 311:    */          catch (Exception e)
/* 312:    */          {
/* 313:313 */            System.out.println(e);
/* 314:314 */            System.out.println(e.getMessage());
/* 315:315 */            conn.rollback();
/* 316:    */          }
/* 317:    */          finally
/* 318:    */          {
/* 319:319 */            prest.close();
/* 320:320 */            conn.close();
/* 321:    */          }
/* 322:    */        }
/* 323:    */      });
/* 324:    */    }
/* 325:    */    catch (Exception e)
/* 326:    */    {
/* 327:327 */      resp.setType(0);
/* 328:328 */      resp.setMessage("导入数据出错");
/* 329:    */    }
/* 330:    */    finally
/* 331:    */    {
/* 332:332 */      if (workbook != null)
/* 333:333 */        workbook.close();
/* 334:    */    }
/* 335:335 */    resp.setMessage("数据已导入");
/* 336:336 */    return resp;
/* 337:    */  }
/* 338:    */  
/* 339:    */  private void checkMainItems(RequestEntity req, PMF pmf, String mainItem)
/* 340:    */    throws Exception
/* 341:    */  {
/* 342:342 */    if (!EncodingUtil.exists(pmf, "mainitems", mainItem))
/* 343:    */    {
/* 344:344 */      SysEncoding se = new SysEncoding();
/* 345:345 */      se.setFieldName("mainitems");
/* 346:346 */      se.setFieldValue(mainItem);
/* 347:347 */      se.setCodingValue(mainItem);
/* 348:348 */      pmf.save(se);
/* 349:349 */      req.setEncodingTable(new EncodingModel(pmf));
/* 350:    */    }
/* 351:    */  }
/* 352:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.SuppliersService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */