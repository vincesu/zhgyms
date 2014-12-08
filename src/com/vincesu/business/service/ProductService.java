/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.business.entity.Product;
/*   5:    */import com.vincesu.business.entity.SalesProgram;
/*   6:    */import com.vincesu.framework.entity.SysUser;
/*   7:    */import com.vincesu.framework.remote.RemoteUtil;
/*   8:    */import com.vincesu.framework.remote.RequestEntity;
/*   9:    */import com.vincesu.framework.util.BeanUtil;
/*  10:    */import com.vincesu.framework.util.PathUtil;
/*  11:    */import com.vincesu.framework.util.QueryUtil;
/*  12:    */import com.vincesu.persistence.PMF;
/*  13:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  14:    */import java.io.File;
/*  15:    */import java.io.FileInputStream;
/*  16:    */import java.io.IOException;
/*  17:    */import java.io.OutputStream;
/*  18:    */import java.io.PrintStream;
/*  19:    */import java.sql.Connection;
/*  20:    */import java.sql.PreparedStatement;
/*  21:    */import java.sql.SQLException;
/*  22:    */import java.util.ArrayList;
/*  23:    */import java.util.Date;
/*  24:    */import java.util.HashMap;
/*  25:    */import java.util.Iterator;
/*  26:    */import java.util.List;
/*  27:    */import java.util.Map;
/*  28:    */import jxl.Cell;
/*  29:    */import jxl.Sheet;
/*  30:    */import jxl.Workbook;
/*  31:    */import jxl.write.Label;
/*  32:    */import jxl.write.WritableImage;
/*  33:    */import jxl.write.WritableSheet;
/*  34:    */import jxl.write.WritableWorkbook;
/*  35:    */import jxl.write.WriteException;
/*  36:    */import org.apache.tools.zip.ZipOutputStream;
/*  37:    */import org.hibernate.jdbc.Work;
/*  38:    */
/*  39:    */public class ProductService
/*  40:    */{
/*  41:    */  public com.vincesu.framework.remote.ResponseEntity queryList(RequestEntity req) throws Exception
/*  42:    */  {
/*  43: 43 */    StringBuffer sql = new StringBuffer();
/*  44: 44 */    if ((((Map)req.getData().get(0)).get("category") != null) && 
/*  45: 45 */      (((Map)req.getData().get(0)).get("category").equals("-1")))
/*  46:    */    {
/*  47: 47 */      sql.append("SELECT * from VW_ProductWithSalesProgram a where 1=1 ");
/*  48: 48 */      sql.append(" and a.name like '%:name%' ");
/*  49: 49 */      sql.append(" and a.number like '%:number%' ");
/*  50: 50 */      sql.append(" and a.category not in (select field_value from sys_encoding where field_name='productcategory' and field_value!='-1') ");
/*  51: 51 */      sql.append(" order by :theorder ");
/*  52:    */    }
/*  53:    */    else
/*  54:    */    {
/*  55: 55 */      sql.append("SELECT * from VW_ProductWithSalesProgram a where 1=1 ");
/*  56: 56 */      sql.append(" and a.name like '%:name%' ");
/*  57: 57 */      sql.append(" and a.number like '%:number%' ");
/*  58: 58 */      sql.append(" and a.category=':category' ");
/*  59: 59 */      sql.append(" order by :theorder ");
/*  60:    */    }
/*  61: 61 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  62: 62 */      new String[] { "fid", "downpath", "path", "id", "name", "number", "size", 
/*  63: 63 */      "referencePrice", "moq", "des", "category", "haveSalesProgram", 
/*  64: 64 */      "username" }, 
/*  65: 65 */      new String[] { "fid", "downpath", "path", "id", "name", "number", "size", 
/*  66: 66 */      "referencePrice", "moq", "des", "category", "haveSalesProgram", 
/*  67: 67 */      "username" }, null, null);
/*  68:    */  }
/*  69:    */  
/*  70:    */  public com.vincesu.framework.remote.ResponseEntity queryObject(RequestEntity req) throws Exception
/*  71:    */  {
/*  72: 72 */    StringBuffer sql = new StringBuffer();
/*  73: 73 */    sql.append("select a.id,a.name,a.number,a.size,a.reference_price,a.MOQ,a.description,a.category,b.webpath,b.id fileid from product a LEFT JOIN files b ON a.id=b.related_object and b.type=2 where 1=1 ");
/*  74: 74 */    sql.append(" and a.id=:id ");
/*  75: 75 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  76: 76 */      new String[] { "id", "name", "number", "size", "referencePrice", "moq", 
/*  77: 77 */      "description", "category", "path", "fileid" }, null, null);
/*  78:    */  }
/*  79:    */  
/*  80:    */  public com.vincesu.framework.remote.ResponseEntity add(RequestEntity req)
/*  81:    */  {
/*  82: 82 */    PMF pmf = RemoteUtil.getPMF(req);
/*  83: 83 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/*  84:    */    try
/*  85:    */    {
/*  86: 86 */      Map val = (Map)req.getData().get(0);
/*  87: 87 */      Product product = new Product();
/*  88: 88 */      BeanUtil.copyProperty(val, product);
/*  89:    */      
/*  90: 90 */      if (!checkProductNumber(pmf, product.getNumber()))
/*  91:    */      {
/*  92: 92 */        resp.setType(0);
/*  93: 93 */        resp.setMessage("添加失败，此产品编号已存在");
/*  94: 94 */        return resp;
/*  95:    */      }
/*  96: 96 */      product.setPermissions(req.getUser().getId().toString());
/*  97: 97 */      pmf.save(product);
/*  98: 98 */      String fileId = val.get("fileid").toString();
/*  99: 99 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 100:100 */      file.setType(new Integer("2"));
/* 101:101 */      file.setRelatedObject(product.getId());
/* 102:102 */      pmf.update(file);
/* 103:    */    }
/* 104:    */    catch (Exception e)
/* 105:    */    {
/* 106:106 */      resp.setType(0);
/* 107:107 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 108:    */    }
/* 109:109 */    return resp;
/* 110:    */  }
/* 111:    */  
/* 112:    */  private boolean checkProductNumber(PMF pmf, String number)
/* 113:    */  {
/* 114:114 */    Long count = Long.valueOf(pmf
/* 115:115 */      .count("select * from product where number='" + number + "'"));
/* 116:116 */    if (count.longValue() > 0L)
/* 117:    */    {
/* 118:118 */      return false;
/* 119:    */    }
/* 120:120 */    return true;
/* 121:    */  }
/* 122:    */  
/* 123:    */  public com.vincesu.framework.remote.ResponseEntity remove(RequestEntity req)
/* 124:    */  {
/* 125:125 */    PMF pmf = RemoteUtil.getPMF(req);
/* 126:126 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 127:    */    try
/* 128:    */    {
/* 129:129 */      Map val = (Map)req.getData().get(0);
/* 130:130 */      String id = val.get("id").toString();
/* 131:131 */      CommonService.delete(pmf, new Long(id), 
/* 132:132 */        new Integer("2").intValue(), req.getUser());
/* 133:133 */      Product product = (Product)pmf.get(Product.class, new Long(id));
/* 134:134 */      pmf.remove(product);
/* 135:    */    }
/* 136:    */    catch (Exception e)
/* 137:    */    {
/* 138:138 */      resp.setType(0);
/* 139:139 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 140:    */    }
/* 141:141 */    return resp;
/* 142:    */  }
/* 143:    */  
/* 144:    */  public com.vincesu.framework.remote.ResponseEntity update(RequestEntity req) throws Exception
/* 145:    */  {
/* 146:146 */    PMF pmf = RemoteUtil.getPMF(req);
/* 147:147 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 148:    */    try
/* 149:    */    {
/* 150:150 */      Map val = (Map)req.getData().get(0);
/* 151:151 */      String id = val.get("id").toString();
/* 152:152 */      Product product = (Product)pmf.get(Product.class, new Long(id));
/* 153:    */      
/* 154:154 */      if (val.get("number") == null)
/* 155:    */      {
/* 156:156 */        resp.setType(0);
/* 157:157 */        resp.setMessage("产品编号有误，请输入产品编号");
/* 158:158 */        return resp;
/* 159:    */      }
/* 160:160 */      if (product == null)
/* 161:    */      {
/* 162:162 */        resp.setType(0);
/* 163:163 */        resp.setMessage("产品编号有误，请输入产品编号");
/* 164:164 */        return resp;
/* 165:    */      }
/* 166:166 */      BeanUtil.copyProperty(val, product);
/* 167:167 */      pmf.update(product);
/* 168:168 */      pmf.flush();
/* 169:169 */      String fileId = val.get("fileid").toString();
/* 170:170 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 171:171 */      file.setType(new Integer("2"));
/* 172:172 */      file.setRelatedObject(product.getId());
/* 173:173 */      pmf.update(file);
/* 174:174 */      pmf.flush();
/* 175:175 */      CommonService.deleteFilesByRelatedObject(pmf, product.getId(), 
/* 176:176 */        file.getId(), "2", req.getUser());
/* 177:177 */      pmf.flush();
/* 178:178 */      pmf.save(file);
/* 179:179 */      pmf.flush();
/* 180:    */    }
/* 181:    */    catch (Exception e)
/* 182:    */    {
/* 183:183 */      resp.setType(0);
/* 184:184 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 185:    */    }
/* 186:186 */    return resp;
/* 187:    */  }
/* 188:    */  
/* 189:    */  public com.vincesu.framework.remote.ResponseEntity exportData(RequestEntity req)
/* 190:    */    throws IOException, WriteException
/* 191:    */  {
/* 192:192 */    PMF pmf = RemoteUtil.getPMF(req);
/* 193:193 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 194:194 */    WritableWorkbook workbook = null;
/* 195:    */    try
/* 196:    */    {
/* 197:197 */      Map val = (Map)req.getData().get(0);
/* 198:198 */      OutputStream os = (OutputStream)val.get("filestream");
/* 199:199 */      workbook = Workbook.createWorkbook(os);
/* 200:200 */      WritableSheet sheet = workbook.createSheet("product", 0);
/* 201:201 */      Label label = null;
/* 202:202 */      int i = 0;
/* 203:203 */      int j = 0;
/* 204:    */      
/* 205:205 */      String[] titles = 
/* 206:206 */        { "产品名称", "编号", "尺寸", "参考价格", "起订量", "描述", "分类", "图片" };
/* 207:207 */      for (i = 0; i < titles.length; i++)
/* 208:    */      {
/* 209:209 */        label = new Label(i, 0, titles[i]);
/* 210:210 */        sheet.addCell(label);
/* 211:    */      }
/* 212:    */      
/* 213:213 */      sheet.setColumnView(7, 20);
/* 214:    */      
/* 215:215 */      StringBuffer hql = new StringBuffer();
/* 216:    */      
/* 217:217 */      hql.append(
/* 218:218 */        "select {pppp.*},{ffff.*} from product pppp left join files ffff on pppp.id=ffff.related_object and ffff.type=")
/* 219:219 */        .append("2")
/* 220:    */        
/* 221:221 */        .append(" order by pppp.id desc ");
/* 222:    */      
/* 223:223 */      Product p = null;
/* 224:224 */      Files f = null;
/* 225:    */      
/* 226:226 */      List<Object[]> data = pmf.get(hql.toString(), 
/* 227:227 */        new Class[] { Product.class, Files.class }, 
/* 228:228 */        new String[] { "pppp", "ffff" });
/* 229:229 */      List<Product> datas = new ArrayList();
/* 230:230 */      for (Object[] objs : data)
/* 231:    */      {
/* 232:232 */        p = (Product)objs[0];
/* 233:233 */        f = (Files)objs[1];
/* 234:234 */        if ((f != null) && (f.getPngpath() != null) && 
/* 235:235 */          (!f.getPngpath().equals("")))
/* 236:    */        {
/* 237:237 */          p.setWebPath(f.getWebpath());
/* 238:238 */          p.setPath(f.getPath());
/* 239:239 */          p.setPngpath(f.getPngpath());
/* 240:    */        }
/* 241:    */        else
/* 242:    */        {
/* 243:243 */          p.setWebPath(null);
/* 244:244 */          p.setPath(null);
/* 245:245 */          p.setPngpath(null);
/* 246:    */        }
/* 247:247 */        datas.add(p);
/* 248:    */      }
/* 249:    */      
/* 250:250 */      for (i = 0; i < datas.size(); i++)
/* 251:    */      {
/* 252:252 */        j = 0;
/* 253:253 */        label = new Label(j++, i + 1, 
/* 254:254 */          ((Product)datas.get(i)).getName());
/* 255:255 */        sheet.addCell(label);
/* 256:256 */        label = new Label(j++, i + 1, 
/* 257:257 */          ((Product)datas.get(i)).getNumber());
/* 258:258 */        sheet.addCell(label);
/* 259:259 */        label = new Label(j++, i + 1, 
/* 260:260 */          ((Product)datas.get(i)).getSize());
/* 261:261 */        sheet.addCell(label);
/* 262:262 */        label = new Label(j++, i + 1, ((Product)datas.get(i))
/* 263:263 */          .getReferencePrice().toString());
/* 264:264 */        sheet.addCell(label);
/* 265:265 */        label = new Label(j++, i + 1, ((Product)datas.get(i)).getMoq());
/* 266:266 */        sheet.addCell(label);
/* 267:267 */        label = new Label(j++, i + 1, 
/* 268:268 */          ((Product)datas.get(i)).getDescription());
/* 269:269 */        sheet.addCell(label);
/* 270:270 */        label = new Label(j++, i + 1, 
/* 271:271 */          ((Product)datas.get(i)).getCategory());
/* 272:272 */        sheet.addCell(label);
/* 273:273 */        if (((Product)datas.get(i)).getPngpath() != null)
/* 274:    */        {
/* 275:275 */          if (new File(((Product)datas.get(i)).getPngpath()).exists())
/* 276:    */          {
/* 277:277 */            WritableImage ri = new WritableImage(j++, i + 1, 1.0D, 
/* 278:278 */              1.0D, new File(
/* 279:279 */              ((Product)datas.get(i)).getPngpath()));
/* 280:280 */            sheet.addImage(ri);
/* 281:    */            break;
/* 282:    */          }
/* 283:    */        }
/* 284:284 */        j++;
/* 285:    */        label820:
/* 286:286 */        sheet.setRowView(i + 1, 2000);
/* 287:    */      }
/* 288:    */      
/* 289:    */    }
/* 290:    */    catch (Exception e)
/* 291:    */    {
/* 292:292 */      System.out.println(e.getMessage());
/* 293:293 */      resp.setType(0);
/* 294:294 */      resp.setMessage("导出失败");
/* 295:    */    }
/* 296:    */    finally
/* 297:    */    {
/* 298:298 */      if (workbook != null)
/* 299:    */      {
/* 300:300 */        workbook.write();
/* 301:301 */        workbook.close();
/* 302:    */      }
/* 303:    */    }
/* 304:304 */    return resp;
/* 305:    */  }
/* 306:    */  
/* 307:    */  public com.vincesu.framework.remote.ResponseEntity addSalesProgram(RequestEntity req)
/* 308:    */  {
/* 309:309 */    PMF pmf = RemoteUtil.getPMF(req);
/* 310:310 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 311:    */    try
/* 312:    */    {
/* 313:313 */      Map val = (Map)req.getData().get(0);
/* 314:314 */      SalesProgram sp = new SalesProgram();
/* 315:315 */      BeanUtil.copyProperty(val, sp);
/* 316:316 */      sp.setSalesman(req.getUser().getUsername());
/* 317:317 */      sp.setDate(new Date());
/* 318:318 */      sp.setPermissions(req.getUser().getReserved());
/* 319:319 */      pmf.save(sp);
/* 320:    */    }
/* 321:    */    catch (Exception e)
/* 322:    */    {
/* 323:323 */      resp.setType(0);
/* 324:324 */      resp.setMessage("添加失败");
/* 325:    */    }
/* 326:326 */    return resp;
/* 327:    */  }
/* 328:    */  
/* 329:    */  public com.vincesu.framework.remote.ResponseEntity removeSalesProgram(RequestEntity req)
/* 330:    */  {
/* 331:331 */    PMF pmf = RemoteUtil.getPMF(req);
/* 332:332 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 333:    */    
/* 334:    */    try
/* 335:    */    {
/* 336:336 */      Map val = null;
/* 337:337 */      SalesProgram sp = null;
/* 338:338 */      Iterator localIterator = req.getData().iterator();
/* 339:339 */      while (localIterator.hasNext())
/* 340:    */      {
/* 341:341 */        Object obj = localIterator.next();
/* 342:    */        
/* 343:343 */        val = (Map)obj;
/* 344:344 */        sp = new SalesProgram();
/* 345:345 */        BeanUtil.copyProperty(val, sp);
/* 346:346 */        pmf.remove(sp);
/* 347:    */      }
/* 348:    */    }
/* 349:    */    catch (Exception e)
/* 350:    */    {
/* 351:351 */      resp.setType(0);
/* 352:352 */      resp.setMessage("删除失败");
/* 353:    */    }
/* 354:354 */    return resp;
/* 355:    */  }
/* 356:    */  
/* 357:    */  public com.vincesu.framework.remote.ResponseEntity viewSalesProgram(RequestEntity req) throws Exception
/* 358:    */  {
/* 359:359 */    StringBuffer sql = new StringBuffer();
/* 360:360 */    sql.append("select s.id,s.salesman,s.quantity,s.price,s.reserved,s.date from sales_program s ");
/* 361:361 */    sql.append("where s.productid=:productid and s.permissions like '");
/* 362:362 */    sql.append(req.getUser().getReserved()).append("%' ");
/* 363:363 */    sql.append(" order by s.date desc");
/* 364:364 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 365:365 */      new String[] { "id", "salesman", "quantity", "price", "reserved", "date" }, 
/* 366:    */      
/* 367:367 */      new String[] { "id", "salesman", "quantity", "price", "reserved", "date" }, 
/* 368:    */      
/* 369:369 */      new String[] { "date" }, 
/* 370:370 */      new String[] { "yyyy-MM-dd" });
/* 371:    */  }
/* 372:    */  
/* 373:    */  public com.vincesu.framework.remote.ResponseEntity importData(final RequestEntity req)
/* 374:    */  {
/* 375:375 */    PMF pmf = RemoteUtil.getPMF(req);
/* 376:376 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 377:377 */    Workbook workbook = null;
/* 378:    */    try
/* 379:    */    {
/* 380:380 */      Map data = (Map)req.getData().get(0);
/* 381:381 */      FileInputStream fis = (FileInputStream)data.get("filestream");
/* 382:382 */      workbook = Workbook.getWorkbook(fis);
/* 383:383 */      final Sheet sheet = workbook.getSheet(0);
/* 384:384 */      pmf.doWorkWithConnection(new Work()
/* 385:    */      {
/* 386:    */        public void execute(Connection conn) throws SQLException
/* 387:    */        {
/* 388:388 */          PMF p = new PMF(SessionFactoryHelper.getSession());
/* 389:389 */          List<Product> products = p.list(
/* 390:390 */            "from Product order by id desc", null, 0, 1);
/* 391:391 */          Long id = new Long(1L);
/* 392:392 */          if ((products != null) && (products.size() != 0))
/* 393:393 */            id = ((Product)products.get(0)).getId();
/* 394:394 */          String sql = "";
/* 395:    */          try
/* 396:    */          {
/* 397:397 */            sql = 
/* 398:398 */              "insert into product(id,name,number,size,reference_price,MOQ,description,category,permissions) values(?,?,?,?,?,?,?,?,'" + req.getUser().getReserved() + "')";
/* 399:    */          }
/* 400:    */          catch (Exception e1)
/* 401:    */          {
/* 402:402 */            throw new SQLException(e1.getMessage());
/* 403:    */          }
/* 404:404 */          PreparedStatement prest = conn.prepareStatement(sql);
/* 405:    */          try
/* 406:    */          {
/* 407:407 */            for (int i = 1; i < sheet.getRows(); i++)
/* 408:    */            {
/* 409:409 */              prest.setLong(1, 
/* 410:410 */                (id = Long.valueOf(id.longValue() + 1L))
/* 411:411 */                .longValue());
/* 412:412 */              prest.setString(2, sheet.getCell(0, i)
/* 413:413 */                .getContents());
/* 414:414 */              prest.setString(3, sheet.getCell(1, i)
/* 415:415 */                .getContents());
/* 416:416 */              prest.setString(4, sheet.getCell(2, i)
/* 417:417 */                .getContents());
/* 418:418 */              prest.setString(5, sheet.getCell(3, i)
/* 419:419 */                .getContents());
/* 420:420 */              prest.setString(6, sheet.getCell(4, i)
/* 421:421 */                .getContents());
/* 422:422 */              prest.setString(7, sheet.getCell(5, i)
/* 423:423 */                .getContents());
/* 424:424 */              prest.setString(8, sheet.getCell(6, i)
/* 425:425 */                .getContents());
/* 426:426 */              prest.addBatch();
/* 427:    */            }
/* 428:428 */            prest.executeBatch();
/* 429:429 */            conn.commit();
/* 430:    */          }
/* 431:    */          catch (Exception e)
/* 432:    */          {
/* 433:433 */            conn.rollback();
/* 434:    */          }
/* 435:    */          finally
/* 436:    */          {
/* 437:437 */            prest.close();
/* 438:438 */            conn.close();
/* 439:    */          }
/* 440:    */        }
/* 441:    */      });
/* 442:    */    }
/* 443:    */    catch (Exception e)
/* 444:    */    {
/* 445:445 */      resp.setType(0);
/* 446:446 */      resp.setMessage("导入数据出错,原因：" + e.getMessage());
/* 447:    */    }
/* 448:    */    finally
/* 449:    */    {
/* 450:450 */      if (workbook != null)
/* 451:451 */        workbook.close();
/* 452:    */    }
/* 453:453 */    resp.setMessage("数据已导入");
/* 454:454 */    return resp;
/* 455:    */  }
/* 456:    */  
/* 457:    */  public com.vincesu.framework.remote.ResponseEntity bulkProductImport(RequestEntity req)
/* 458:    */  {
/* 459:459 */    PMF pmf = RemoteUtil.getPMF(req);
/* 460:460 */    boolean override = false;
/* 461:461 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 462:462 */    StringBuffer result = new StringBuffer();
/* 463:463 */    int count = req.getData().size();
/* 464:    */    
/* 465:    */    try
/* 466:    */    {
/* 467:467 */      Map data = (Map)req.getData().get(0);
/* 468:    */      
/* 469:469 */      if ((data.get("override") != null) && 
/* 470:470 */        (data.get("override").toString().equals("1")))
/* 471:471 */        override = true;
/* 472:472 */      String[] fileids = data.get("fileid").toString().split("\\|");
/* 473:473 */      Product product = null;
/* 474:474 */      Map map = null;
/* 475:475 */      for (int i = 0; i < req.getData().size(); i++)
/* 476:    */      {
/* 477:477 */        map = (Map)req.getData().get(i);
/* 478:478 */        product = new Product();
/* 479:479 */        BeanUtil.copyProperty(data, product);
/* 480:480 */        if (map.get("number") != null)
/* 481:    */        {
/* 482:482 */          if (!checkProductNumber(pmf, map.get("number").toString()))
/* 483:    */          {
/* 484:484 */            if (override)
/* 485:    */            {
/* 486:486 */              Map param = new HashMap();
/* 487:487 */              param.put("number", map.get("number"));
/* 488:488 */              List<Product> plist = pmf.list(
/* 489:489 */                "from Product where Number=:number", param);
/* 490:490 */              if ((plist != null) && (plist.size() != 0))
/* 491:491 */                pmf.remove((Product)plist.get(0));
/* 492:492 */              product.setNumber(map.get("number").toString());
/* 493:493 */              product.setPermissions(req.getUser().getId()
/* 494:494 */                .toString());
/* 495:495 */              product.setPermissions(req.getUser().getId()
/* 496:496 */                .toString());
/* 497:497 */              pmf.save(product);
/* 498:    */            }
/* 499:    */            else
/* 500:    */            {
/* 501:501 */              if (result.length() == 0)
/* 502:502 */                result.append("重复产品编号:");
/* 503:503 */              result.append(map.get("number")).append(",");
/* 504:504 */              count--;
/* 505:    */            }
/* 506:    */            
/* 507:    */          }
/* 508:    */          else
/* 509:    */          {
/* 510:510 */            product.setNumber(map.get("number").toString());
/* 511:511 */            product.setPermissions(req.getUser().getId().toString());
/* 512:512 */            pmf.save(product);
/* 513:    */          }
/* 514:    */        }
/* 515:    */        
/* 516:516 */        String fileId = fileids[i];
/* 517:517 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 518:518 */        file.setType(new Integer("2"));
/* 519:519 */        file.setRelatedObject(product.getId());
/* 520:520 */        pmf.update(file);
/* 521:    */      }
/* 522:522 */      if (result.length() == 0)
/* 523:    */      {
/* 524:524 */        result.append("所有产品上传成功");
/* 526:    */      }
/* 527:527 */      else if (count == 0)
/* 528:    */      {
/* 529:529 */        result.append("无产品上传成功。");
/* 530:    */      }
/* 531:    */      else
/* 532:    */      {
/* 533:533 */        result.append("其余").append(count).append("个产品上传成功。");
/* 534:    */      }
/* 535:535 */      resp.setMessage(result.toString());
/* 536:    */    }
/* 537:    */    catch (Exception e)
/* 538:    */    {
/* 539:539 */      resp.setType(0);
/* 540:540 */      resp.setMessage("导入产品错误,原因：" + e.getMessage());
/* 541:    */    }
/* 542:542 */    return resp;
/* 543:    */  }
/* 544:    */  
/* 545:    */  public com.vincesu.framework.remote.ResponseEntity productPicBulkDownload(RequestEntity req)
/* 546:    */    throws IOException
/* 547:    */  {
/* 548:548 */    PMF pmf = RemoteUtil.getPMF(req);
/* 549:549 */    com.vincesu.framework.remote.ResponseEntity resp = new com.vincesu.framework.remote.ResponseEntity();
/* 550:550 */    Map val = (Map)req.getData().get(0);
/* 551:551 */    OutputStream os = (OutputStream)val.get("filestream");
/* 552:552 */    String params = val.get("params").toString();
/* 553:553 */    String[] fileIds = null;
/* 554:    */    try
/* 555:    */    {
/* 556:556 */      fileIds = params.split(",");
/* 557:557 */      if (fileIds.length <= 0)
/* 558:    */      {
/* 559:559 */        resp.setType(0);
/* 560:560 */        resp.setMessage("入参有误");
/* 561:561 */        return resp;
/* 562:    */      }
/* 563:563 */      ZipOutputStream zos = new ZipOutputStream(os);
/* 564:    */      
/* 565:565 */      Files file = null;
/* 566:566 */      for (String fid : fileIds)
/* 567:    */      {
/* 568:568 */        if ((fid != null) && (!fid.equals("")))
/* 569:    */        {
/* 570:570 */          file = (Files)pmf.get(Files.class, new Long(fid));
/* 571:    */          
/* 572:572 */          if (!PathUtil.exportFiles(pmf, zos, file.getPath(), "", file.getName()))
/* 573:    */          {
/* 574:574 */            PathUtil.exportFiles(pmf, zos, file.getPngpath(), "", 
/* 575:575 */              file.getName());
/* 576:    */          }
/* 577:    */        }
/* 578:    */      }
/* 579:    */      
/* 580:580 */      zos.flush();
/* 581:581 */      zos.close();
/* 582:    */    }
/* 583:    */    catch (Exception e)
/* 584:    */    {
/* 585:585 */      System.out.println(e);
/* 586:    */    }
/* 587:587 */    return resp;
/* 588:    */  }
/* 589:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ProductService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */