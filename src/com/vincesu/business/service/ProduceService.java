/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Contract;
/*   4:    */import com.vincesu.business.entity.Files;
/*   5:    */import com.vincesu.business.entity.ManufactureOrder;
/*   6:    */import com.vincesu.business.entity.Product;
/*   7:    */import com.vincesu.business.entity.Production;
/*   8:    */import com.vincesu.framework.entity.SysUser;
/*   9:    */import com.vincesu.framework.remote.RemoteUtil;
/*  10:    */import com.vincesu.framework.remote.RequestEntity;
/*  11:    */import com.vincesu.framework.remote.ResponseEntity;
/*  12:    */import com.vincesu.framework.util.BeanUtil;
/*  13:    */import com.vincesu.framework.util.OfficeUtil;
/*  14:    */import com.vincesu.framework.util.QueryUtil;
/*  15:    */import com.vincesu.framework.util.SystemUtil;
/*  16:    */import com.vincesu.framework.util.TextUtil;
/*  17:    */import com.vincesu.framework.util.TimeUtil;
/*  18:    */import com.vincesu.persistence.PMF;
/*  19:    */import java.awt.image.BufferedImage;
/*  20:    */import java.io.File;
/*  21:    */import java.io.FileOutputStream;
/*  22:    */import java.io.IOException;
/*  23:    */import java.io.OutputStream;
/*  24:    */import java.util.ArrayList;
/*  25:    */import java.util.HashMap;
/*  26:    */import java.util.List;
/*  27:    */import java.util.Map;
/*  28:    */import javax.imageio.ImageIO;
/*  29:    */import jxl.SheetSettings;
/*  30:    */import jxl.Workbook;
/*  31:    */import jxl.format.Alignment;
/*  32:    */import jxl.format.Border;
/*  33:    */import jxl.format.BorderLineStyle;
/*  34:    */import jxl.format.VerticalAlignment;
/*  35:    */import jxl.write.Label;
/*  36:    */import jxl.write.WritableCellFormat;
/*  37:    */import jxl.write.WritableFont;
/*  38:    */import jxl.write.WritableFont.FontName;
/*  39:    */import jxl.write.WritableImage;
/*  40:    */import jxl.write.WritableSheet;
/*  41:    */import jxl.write.WritableWorkbook;
/*  42:    */import jxl.write.WriteException;
/*  43:    */import jxl.write.biff.RowsExceededException;
/*  44:    */
/*  45:    */public class ProduceService
/*  46:    */{
/*  47:    */  public ResponseEntity queryList(RequestEntity req) throws Exception
/*  48:    */  {
/*  49: 49 */    StringBuffer sql = new StringBuffer();
/*  50: 50 */    sql.append("select a.id,a.name,a.number,a.date,a.delivery_time,c.realname salesman,a.destination,a.is_audit,a.audit,a.contract_id,a.remark,b.id fileid,b.pdfpath path,b.name filename,a.createc createc from manufacture_order a,files b,sys_user c ");
/*  51: 51 */    sql.append("where a.salesman=c.uid and a.id=b.related_object and b.type=4 ");
/*  52: 52 */    sql.append(" and a.number like '%:number%' ");
/*  53: 53 */    sql.append(" and c.realname like '%:salesman%' ");
/*  54: 54 */    sql.append(" and a.date >= ':bt' ");
/*  55: 55 */    sql.append(" and a.date <= ':et' ");
/*  56: 56 */    sql.append(" order by a.id desc");
/*  57: 57 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  58: 58 */      new String[] { "id", "name", "number", "date", "deliveryTime", "salesman", 
/*  59: 59 */      "destination", "isAudit", "audit", "contractId", "remark", 
/*  60: 60 */      "fileid", "path", "filename", "createc" }, 
/*  61: 61 */      new String[] { "id", "name", "number", "date", "deliveryTime", "salesman", 
/*  62: 62 */      "destination", "isAudit", "audit", "contractId", "remark", 
/*  63: 63 */      "fileid", "path", "filename", "createc" }, null, null);
/*  64:    */  }
/*  65:    */  
/*  66:    */  public ResponseEntity getContractProduct(RequestEntity req)
/*  67:    */    throws Exception
/*  68:    */  {
/*  69: 69 */    StringBuffer sql = new StringBuffer();
/*  70: 70 */    sql.append("SELECT a.*,c.webpath path FROM contract_detail a LEFT JOIN files c  ON c.`type`=2 AND a.`product_id`=c.`related_object`  WHERE a.`contract_id`=:contractid ");
/*  71: 71 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  72: 72 */      new String[] { "id", "contractId", "productId", "number", "description", "qty", 
/*  73: 73 */      "unit", "amount", "remark", "reserved", "path" }, null, null);
/*  74:    */  }
/*  75:    */  
/*  76:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  77:    */  {
/*  78: 78 */    StringBuffer sql = new StringBuffer();
/*  79: 79 */    sql.append("select a.*,b.id fileid,b.pdfpath path,b.name filename from manufacture_order a,files b ");
/*  80: 80 */    sql.append("where a.id=b.related_object and b.type=4 ");
/*  81: 81 */    sql.append(" and a.id=:id ");
/*  82: 82 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  83: 83 */      new String[] { "id", "name", "number", "date", "deliveryTime", "salesman", 
/*  84: 84 */      "destination", "isAudit", "audit", "contractId", "fileid", 
/*  85: 85 */      "path", "filename" }, null, null);
/*  86:    */  }
/*  87:    */  
/*  88:    */  public ResponseEntity add(RequestEntity req)
/*  89:    */  {
/*  90: 90 */    PMF pmf = RemoteUtil.getPMF(req);
/*  91: 91 */    ResponseEntity resp = new ResponseEntity();
/*  92:    */    try
/*  93:    */    {
/*  94: 94 */      Map val = (Map)req.getData().get(0);
/*  95: 95 */      ManufactureOrder produce = new ManufactureOrder();
/*  96: 96 */      BeanUtil.copyProperty(val, produce, 
/*  97: 97 */        new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/*  98: 98 */      produce.setSalesman(req.getUser().getId());
/*  99: 99 */      pmf.save(produce);
/* 100:100 */      OperationLogService.addLog(pmf, req.getUser(), produce, 1, "添加生产单");
/* 101:101 */      String fileId = val.get("fileid").toString();
/* 102:102 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 103:103 */      file.setType(new Integer("4"));
/* 104:104 */      file.setRelatedObject(produce.getId());
/* 105:105 */      pmf.update(file);
/* 106:    */    }
/* 107:    */    catch (Exception e)
/* 108:    */    {
/* 109:109 */      resp.setType(0);
/* 110:110 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 111:    */    }
/* 112:112 */    return resp;
/* 113:    */  }
/* 114:    */  
/* 115:    */  public ResponseEntity remove(RequestEntity req)
/* 116:    */  {
/* 117:117 */    PMF pmf = RemoteUtil.getPMF(req);
/* 118:118 */    ResponseEntity resp = new ResponseEntity();
/* 119:    */    try
/* 120:    */    {
/* 121:121 */      Map val = (Map)req.getData().get(0);
/* 122:122 */      String id = val.get("id").toString();
/* 123:123 */      ManufactureOrder produce = (ManufactureOrder)pmf.get(
/* 124:124 */        ManufactureOrder.class, new Long(id));
/* 125:    */      
/* 126:126 */      if (produce.getIsAudit())
/* 127:    */      {
/* 128:128 */        resp.setType(0);
/* 129:129 */        resp.setMessage("删除失败，此生产单已经审核，不能再次修改");
/* 130:130 */        return resp;
/* 131:    */      }
/* 132:    */      
/* 133:133 */      CommonService.delete(pmf, new Long(id), 
/* 134:134 */        new Integer("4").intValue(), req.getUser());
/* 135:    */      
/* 136:136 */      StringBuffer hql = new StringBuffer();
/* 137:137 */      hql.append("from Files where Type=").append("4")
/* 138:138 */        .append(" and RelatedObject=").append(produce.getId());
/* 139:139 */      List<Files> list = pmf.list(hql.toString());
/* 140:    */      
/* 141:141 */      if (list.size() > 0)
/* 142:    */      {
/* 143:143 */        pmf.remove((Files)list.get(0));
/* 144:    */      }
/* 145:145 */      deleteProductions(pmf, produce.getId());
/* 146:    */      
/* 147:147 */      pmf.remove(produce);
/* 148:148 */      OperationLogService.addLog(pmf, req.getUser(), produce, 2, "删除生产单");
/* 149:    */    }
/* 150:    */    catch (Exception e)
/* 151:    */    {
/* 152:152 */      resp.setType(0);
/* 153:153 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 154:    */    }
/* 155:155 */    return resp;
/* 156:    */  }
/* 157:    */  
/* 158:    */  public ResponseEntity update(RequestEntity req)
/* 159:    */  {
/* 160:160 */    PMF pmf = RemoteUtil.getPMF(req);
/* 161:161 */    ResponseEntity resp = new ResponseEntity();
/* 162:    */    try
/* 163:    */    {
/* 164:164 */      Map val = (Map)req.getData().get(0);
/* 165:165 */      String id = val.get("id").toString();
/* 166:166 */      ManufactureOrder produce = (ManufactureOrder)pmf.get(
/* 167:167 */        ManufactureOrder.class, new Long(id));
/* 168:    */      
/* 169:169 */      if (produce.getIsAudit())
/* 170:    */      {
/* 171:171 */        resp.setType(0);
/* 172:172 */        resp.setMessage("修改失败，此生产单已经审核，不能再次修改");
/* 173:173 */        return resp;
/* 174:    */      }
/* 175:    */      
/* 176:176 */      BeanUtil.copyProperty(val, produce, 
/* 177:177 */        new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/* 178:178 */      pmf.update(produce);
/* 179:    */      
/* 180:180 */      String fileId = val.get("fileid").toString();
/* 181:181 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 182:182 */      file.setType(new Integer("4"));
/* 183:183 */      file.setRelatedObject(produce.getId());
/* 184:184 */      pmf.update(file);
/* 185:185 */      OperationLogService.addLog(pmf, req.getUser(), produce, 3, "修改生产单");
/* 186:    */      
/* 187:187 */      CommonService.deleteFilesByRelatedObject(pmf, produce.getId(), 
/* 188:188 */        file.getId(), "4", req.getUser());
/* 189:    */      
/* 190:190 */      pmf.save(file);
/* 191:    */    }
/* 192:    */    catch (Exception e)
/* 193:    */    {
/* 194:194 */      resp.setType(0);
/* 195:195 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 196:    */    }
/* 197:197 */    return resp;
/* 198:    */  }
/* 199:    */  
/* 200:    */  public static List<ManufactureOrder> getLatestProduceList(PMF pmf)
/* 201:    */  {
/* 202:202 */    List<ManufactureOrder> result = null;
/* 203:203 */    String hql = "select new ManufactureOrder(a.Id,a.Name,a.Number,a.Date,a.DeliveryTime,a.Salesman,a.Destination,b.Id,b.Name,s.Realname) from ManufactureOrder a,Files b,SysUser s where a.Id=b.RelatedObject and s.Id=a.Salesman and b.Type=4 order by a.Id desc ";
/* 204:    */    
/* 205:205 */    result = pmf.list(hql, null, 0, 6);
/* 206:206 */    return result;
/* 207:    */  }
/* 208:    */  
/* 209:    */  public ResponseEntity addProduction(RequestEntity req)
/* 210:    */  {
/* 211:211 */    PMF pmf = RemoteUtil.getPMF(req);
/* 212:212 */    ResponseEntity resp = new ResponseEntity();
/* 213:213 */    ManufactureOrder produce = null;
/* 214:    */    try
/* 215:    */    {
/* 216:216 */      List<Production> list = new ArrayList();
/* 217:217 */      Map val = (Map)req.getData().get(0);
/* 218:218 */      if ((val.get("id") == null) || 
/* 219:219 */        (val.get("id").toString().equals("")))
/* 220:    */      {
/* 221:221 */        List<Contract> contracts = pmf
/* 222:222 */          .list("from Contract where Number='" + 
/* 223:223 */          val.get("number") + "'");
/* 224:224 */        if ((contracts == null) || (contracts.size() == 0))
/* 225:    */        {
/* 226:226 */          resp.setType(0);
/* 227:227 */          resp.setMessage("没有此合同单号！");
/* 228:228 */          return resp;
/* 229:    */        }
/* 230:    */        
/* 231:231 */        if (!((Contract)contracts.get(0)).getReserved().startsWith("locked"))
/* 232:    */        {
/* 233:233 */          resp.setType(0);
/* 234:234 */          resp.setMessage("此合同单号还未审核通过，不能添加生产单");
/* 235:235 */          return resp;
/* 236:    */        }
/* 237:237 */        produce = new ManufactureOrder();
/* 238:    */      }
/* 239:    */      else
/* 240:    */      {
/* 241:241 */        produce = (ManufactureOrder)pmf.get(ManufactureOrder.class, 
/* 242:242 */          new Long(val.get("id").toString()));
/* 243:243 */        if (produce.getIsAudit())
/* 244:    */        {
/* 245:245 */          resp.setType(0);
/* 246:246 */          resp.setMessage("修改失败，此生产单已经审核，不能再次修改");
/* 247:247 */          return resp;
/* 248:    */        }
/* 249:    */      }
/* 250:    */      
/* 251:251 */      BeanUtil.copyProperty(val, produce, 
/* 252:252 */        new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/* 253:253 */      produce.setSalesman(req.getUser().getId());
/* 254:254 */      if ((val.get("id") == null) || 
/* 255:255 */        (val.get("id").toString().equals("")))
/* 256:    */      {
/* 257:257 */        pmf.save(produce);
/* 258:258 */        OperationLogService.addLog(pmf, req.getUser(), produce, 1, 
/* 259:259 */          "添加生产单");
/* 260:    */      }
/* 261:    */      else
/* 262:    */      {
/* 263:263 */        pmf.update(produce);
/* 264:264 */        OperationLogService.addLog(pmf, req.getUser(), produce, 3, 
/* 265:265 */          "修改生产单");
/* 266:    */      }
/* 267:    */      
/* 268:268 */      deleteProductions(pmf, produce.getId());
/* 269:    */      
/* 270:270 */      for (int i = 1; i < req.getData().size(); i++)
/* 271:    */      {
/* 272:272 */        val = (Map)req.getData().get(i);
/* 273:273 */        Production production = new Production();
/* 274:274 */        BeanUtil.copyProperty(val, production);
/* 275:275 */        production.setOrderId(produce.getId());
/* 276:276 */        pmf.save(production);
/* 277:277 */        list.add(production);
/* 278:    */      }
/* 279:    */      
/* 280:280 */      val = (Map)req.getData().get(0);
/* 281:281 */      if ((val.get("cpf") == null) || 
/* 282:282 */        (val.get("cpf").toString().equals("none")))
/* 283:    */      {
/* 284:284 */        if (createProduceFile(pmf, produce, list, req.getUser()))
/* 285:    */        {
/* 286:286 */          produce.setCreatec(Integer.valueOf(1));
/* 287:287 */          pmf.update(produce);
/* 288:    */        }
/* 289:    */        else
/* 290:    */        {
/* 291:291 */          resp.setType(0);
/* 292:292 */          resp.setMessage("生成生产单失败");
/* 293:    */        }
/* 294:    */        
/* 295:    */      }
/* 296:296 */      else if (val.get("cpf").toString().equals("produce"))
/* 297:    */      {
/* 298:298 */        produce.setCreatec(Integer.valueOf(2));
/* 299:299 */        pmf.update(produce);
/* 300:    */      }
/* 301:    */      else
/* 302:    */      {
/* 303:303 */        Files file = (Files)pmf.get(Files.class, 
/* 304:304 */          new Long(val.get("cpf").toString()));
/* 305:305 */        if (file != null)
/* 306:    */        {
/* 307:307 */          CommonService.delete(pmf, produce.getId(), new Integer(
/* 308:308 */            "4").intValue(), req.getUser());
/* 309:    */          
/* 310:310 */          file.setType(new Integer("4"));
/* 311:311 */          file.setRelatedObject(produce.getId());
/* 312:312 */          pmf.update(file);
/* 313:313 */          produce.setCreatec(Integer.valueOf(2));
/* 314:314 */          pmf.update(produce);
/* 315:    */        }
/* 316:    */        
/* 317:    */      }
/* 318:    */      
/* 319:    */    }
/* 320:    */    catch (Exception e)
/* 321:    */    {
/* 322:322 */      resp.setType(0);
/* 323:323 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 324:    */    }
/* 325:325 */    resp.setMessage(produce.getId().toString());
/* 326:326 */    return resp;
/* 327:    */  }
/* 328:    */  
/* 330:    */  private Files exportExcel(PMF pmf, ManufactureOrder produce, List<Production> productions)
/* 331:    */    throws IOException, RowsExceededException, WriteException
/* 332:    */  {
/* 333:333 */    Files file = new Files();
/* 334:    */    
/* 335:335 */    File targetPath = new File(SystemUtil.getLocation() + 
/* 336:336 */      "/resource/production/");
/* 337:337 */    if (!targetPath.exists())
/* 338:338 */      targetPath.mkdirs();
/* 339:339 */    File targetFilePath = new File(targetPath, produce.getId() + ".xls");
/* 340:340 */    File targetPDFFilePath = new File(targetPath, produce.getId() + ".pdf");
/* 341:341 */    if (targetFilePath.exists())
/* 342:    */    {
/* 343:343 */      targetFilePath.delete();
/* 344:    */    }
/* 345:345 */    OutputStream os = new FileOutputStream(targetFilePath);
/* 346:346 */    WritableWorkbook workbook = Workbook.createWorkbook(os);
/* 347:347 */    WritableSheet sheet = workbook.createSheet("生产单", 0);
/* 348:    */    
/* 349:    */    try
/* 350:    */    {
/* 351:351 */      setExcelData(pmf, sheet, produce, productions);
/* 352:    */    }
/* 353:    */    catch (Exception e)
/* 354:    */    {
/* 355:355 */      return null;
/* 356:    */    }
/* 357:    */    finally
/* 358:    */    {
/* 359:359 */      if (workbook != null)
/* 360:    */      {
/* 361:361 */        workbook.write();
/* 362:362 */        workbook.close();
/* 363:    */      }
/* 364:364 */      if (os != null)
/* 365:    */      {
/* 366:366 */        os.close();
/* 367:    */      }
/* 368:    */    }
/* 369:369 */    OfficeUtil.office2PDF(targetFilePath, targetPDFFilePath);
/* 370:    */    
/* 371:371 */    file.setPath(targetFilePath.getAbsolutePath());
/* 372:372 */    file.setType(new Integer("4"));
/* 373:373 */    file.setRelatedObject(produce.getId());
/* 374:374 */    file.setPdfpath(targetPDFFilePath.getAbsolutePath());
/* 375:    */    
/* 376:376 */    return file;
/* 377:    */  }
/* 378:    */  
/* 380:    */  private void setExcelData(PMF pmf, WritableSheet sheet, ManufactureOrder produce, List<Production> productions)
/* 381:    */    throws WriteException, IOException
/* 382:    */  {
/* 383:383 */    setExcelFormat(sheet);
/* 384:384 */    setHeaderText(sheet, produce);
/* 385:385 */    setTableHeader(sheet, produce);
/* 386:386 */    setTableData(pmf, sheet, produce, productions);
/* 387:387 */    setFooter(pmf, sheet, produce, productions);
/* 388:    */  }
/* 389:    */  
/* 391:    */  private void setFooter(PMF pmf, WritableSheet sheet, ManufactureOrder produce, List<Production> productions)
/* 392:    */    throws WriteException, IOException
/* 393:    */  {
/* 394:394 */    Map<Long, Files> pics = getProductFile(pmf, productions);
/* 395:395 */    Map<Long, Product> products = getProduct(pmf, productions);
/* 396:396 */    WritableImage ri = null;
/* 397:397 */    WritableFont font = null;
/* 398:398 */    WritableCellFormat cFormat = null;
/* 399:399 */    BufferedImage image = null;
/* 400:400 */    Label label = null;
/* 401:    */    
/* 402:402 */    String pic = "图片：";
/* 403:    */    
/* 404:404 */    SysUser sysuser = (SysUser)pmf.get(SysUser.class, 
/* 405:405 */      produce.getSalesman());
/* 406:406 */    if (sysuser == null)
/* 407:    */    {
/* 408:408 */      sysuser = new SysUser();
/* 409:409 */      sysuser.setRealname("");
/* 410:    */    }
/* 411:    */    
/* 412:412 */    String[] text = 
/* 413:413 */      { "负责人:", sysuser.getRealname(), "下单日期:", 
/* 414:414 */      TimeUtil.toString(produce.getDate(), "yyyy-MM-dd"), "审核人:", 
/* 415:415 */      "审核日期:" };
/* 416:416 */    int[] x = 
/* 417:417 */      { 1, 2, 1, 2, 6, 6 };
/* 418:418 */    int[] y = 
/* 419:419 */      { 8 + productions.size() * 2, 8 + productions.size() * 2, 
/* 420:420 */      9 + productions.size() * 2, 9 + productions.size() * 2, 
/* 421:421 */      8 + productions.size() * 2, 9 + productions.size() * 2 };
/* 422:    */    
/* 423:423 */    WritableFont.FontName fontname = WritableFont.createFont("宋体");
/* 424:    */    
/* 425:425 */    int fontsize = 12;
/* 426:    */    
/* 427:427 */    font = new WritableFont(fontname, fontsize, WritableFont.NO_BOLD, false);
/* 428:428 */    cFormat = new WritableCellFormat(font);
/* 429:429 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 430:430 */    label = new Label(1, 6 + productions.size(), pic, cFormat);
/* 431:431 */    sheet.addCell(label);
/* 432:    */    
/* 433:433 */    for (int i = 0; i < productions.size(); i++)
/* 434:    */    {
/* 435:435 */      sheet.setRowView(6 + productions.size() + (i - i % 2), 2730);
/* 436:    */      
/* 437:437 */      if ((((Production)productions.get(i)).getProductId() != null) && 
/* 438:438 */        (pics.get(((Production)productions.get(i))
/* 439:439 */        .getProductId()) != null)) {
/* 440:440 */        if (((Files)pics.get(((Production)productions.get(i))
/* 441:441 */          .getProductId())).getPngpath() != null)
/* 442:    */        {
/* 443:443 */          File f = new File(((Files)pics.get(
/* 444:444 */            ((Production)productions.get(i)).getProductId())).getPngpath());
/* 445:445 */          if (f.exists())
/* 446:    */          {
/* 447:447 */            image = ImageIO.read(f);
/* 448:448 */            if (i % 2 == 0)
/* 449:    */            {
/* 450:450 */              ri = new WritableImage(2 + i % 2 * 2, 6 + 
/* 451:451 */                productions.size() + (i - i % 2), 
/* 452:452 */                TextUtil.countWidthPercentage(
/* 453:453 */                image.getHeight() / 
/* 454:454 */                image.getWidth(), 
/* 455:    */                
/* 456:456 */                new int[] { 2730 }, 1.0D, 
/* 457:457 */                new int[] { 15, 26 }), 1.0D, f);
/* 458:    */            }
/* 459:    */            else
/* 460:    */            {
/* 461:461 */              ri = new WritableImage(2 + i % 2 * 2, 6 + 
/* 462:462 */                productions.size() + (i - i % 2), 
/* 463:463 */                TextUtil.countWidthPercentage(
/* 464:464 */                image.getHeight() / 
/* 465:465 */                image.getWidth(), 
/* 466:    */                
/* 467:467 */                new int[] { 2730 }, 1.0D, 
/* 468:468 */                new int[] { 12, 8, 15 }), 1.0D, f);
/* 469:    */            }
/* 470:    */            
/* 471:471 */            font = new WritableFont(fontname, fontsize, 
/* 472:472 */              WritableFont.NO_BOLD, false);
/* 473:473 */            cFormat = new WritableCellFormat(font);
/* 474:474 */            cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 475:475 */            cFormat.setAlignment(Alignment.CENTRE);
/* 476:476 */            label = new Label(2 + i % 2 * 2, 6 + productions.size() + (
/* 477:477 */              i - i % 2) + 1, 
/* 478:478 */              ((Production)productions.get(i)).getNumber(), 
/* 479:479 */              cFormat);
/* 480:480 */            sheet.addCell(label);
/* 481:    */            
/* 482:482 */            sheet.addImage(ri);
/* 483:    */          }
/* 484:    */        }
/* 485:    */      }
/* 486:    */    }
/* 487:487 */    sheet.mergeCells(
/* 488:488 */      2, 
/* 489:489 */      8 + 
/* 490:490 */      productions.size() + (
/* 491:491 */      productions.size() - 1 - (productions.size() - 1) % 2), 
/* 492:492 */      7, 
/* 493:493 */      8 + 
/* 494:494 */      productions.size() + (
/* 495:495 */      productions.size() - 1 - (productions.size() - 1) % 2));
/* 496:    */    
/* 497:497 */    int h = TextUtil.countRowHeight(produce.getRemark(), 89, false);
/* 498:    */    
/* 499:499 */    sheet.setRowView(8 + productions.size() + (
/* 500:500 */      productions.size() - 1 - (productions.size() - 1) % 2), h);
/* 501:    */    
/* 502:502 */    font = new WritableFont(fontname, fontsize, WritableFont.NO_BOLD, false);
/* 503:503 */    cFormat = new WritableCellFormat(font);
/* 504:504 */    cFormat.setVerticalAlignment(VerticalAlignment.TOP);
/* 505:505 */    label = new Label(1, 8 + productions.size() + (
/* 506:506 */      productions.size() - 1 - (productions.size() - 1) % 2), 
/* 507:507 */      "备注", cFormat);
/* 508:508 */    sheet.addCell(label);
/* 509:    */    
/* 510:510 */    font = new WritableFont(fontname, fontsize, WritableFont.NO_BOLD, false);
/* 511:511 */    cFormat = new WritableCellFormat(font);
/* 512:512 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 513:513 */    cFormat.setAlignment(Alignment.CENTRE);
/* 514:514 */    cFormat.setWrap(true);
/* 515:515 */    label = new Label(2, 8 + productions.size() + (
/* 516:516 */      productions.size() - 1 - (productions.size() - 1) % 2), 
/* 517:517 */      produce.getRemark(), cFormat);
/* 518:518 */    sheet.addCell(label);
/* 519:    */    
/* 520:520 */    for (int i = 0; i < text.length; i++)
/* 521:    */    {
/* 522:522 */      font = new WritableFont(fontname, fontsize, WritableFont.NO_BOLD, 
/* 523:523 */        false);
/* 524:524 */      cFormat = new WritableCellFormat(font);
/* 525:525 */      if ((i == 4) || (i == 5))
/* 526:    */      {
/* 527:527 */        cFormat.setAlignment(Alignment.RIGHT);
/* 528:    */      }
/* 529:    */      else
/* 530:530 */        cFormat.setAlignment(Alignment.LEFT);
/* 531:531 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 532:532 */      label = new Label(x[i], y[i], text[i], cFormat);
/* 533:533 */      sheet.addCell(label);
/* 534:    */    }
/* 535:    */  }
/* 536:    */  
/* 537:    */  private void setHeaderText(WritableSheet sheet, ManufactureOrder produce)
/* 538:    */    throws WriteException
/* 539:    */  {
/* 540:540 */    WritableFont font = null;
/* 541:541 */    WritableCellFormat cFormat = null;
/* 542:542 */    Label label = null;
/* 543:    */    
/* 544:544 */    String[] text = 
/* 545:545 */      { "温州中昊工艺品有限公司", "Wenzhou zhonghao crafts & gifts co.,ltd", "生产单", 
/* 546:546 */      "No.：", produce.getNumber(), "发货日期：", 
/* 547:547 */      TimeUtil.toString(produce.getDeliveryTime(), "yyyy-MM-dd") };
/* 548:548 */    int[] x = 
/* 549:549 */      { 1, 1, 4, 6, 7, 6, 7 };
/* 550:550 */    int[] y = 
/* 551:551 */      { 1, 2, 1, 1, 1, 2, 2 };
/* 552:    */    
/* 553:553 */    WritableFont.FontName[] fontnames = 
/* 554:554 */      { WritableFont.createFont("宋体"), WritableFont.TIMES, 
/* 555:555 */      WritableFont.createFont("宋体"), WritableFont.TIMES, 
/* 556:556 */      WritableFont.TIMES, WritableFont.TIMES, WritableFont.TIMES };
/* 557:    */    
/* 558:558 */    int[] fontsizes = 
/* 559:559 */      { 15, 14, 18, 12, 12, 12, 12 };
/* 560:    */    
/* 561:561 */    for (int i = 0; i < text.length; i++)
/* 562:    */    {
/* 563:563 */      if (i == 2)
/* 564:    */      {
/* 565:565 */        font = new WritableFont(fontnames[i], fontsizes[i], 
/* 566:566 */          WritableFont.BOLD, false);
/* 567:    */      }
/* 568:    */      else
/* 569:569 */        font = new WritableFont(fontnames[i], fontsizes[i], 
/* 570:570 */          WritableFont.NO_BOLD, false);
/* 571:571 */      cFormat = new WritableCellFormat(font);
/* 572:572 */      if ((i == 3) || (i == 5))
/* 573:    */      {
/* 574:574 */        cFormat.setAlignment(Alignment.RIGHT);
/* 576:    */      }
/* 577:577 */      else if ((i == 0) || (i == 1) || (i == 2))
/* 578:    */      {
/* 579:579 */        cFormat.setAlignment(Alignment.LEFT);
/* 580:    */      }
/* 581:    */      else
/* 582:    */      {
/* 583:583 */        cFormat.setAlignment(Alignment.CENTRE);
/* 584:    */      }
/* 585:585 */      if (i == 2)
/* 586:    */      {
/* 587:587 */        cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 588:    */      }
/* 589:589 */      if (i == 4)
/* 590:    */      {
/* 591:591 */        cFormat.setWrap(true);
/* 592:    */      }
/* 593:593 */      label = new Label(x[i], y[i], text[i], cFormat);
/* 594:594 */      sheet.addCell(label);
/* 595:    */    }
/* 596:    */  }
/* 597:    */  
/* 598:    */  private void setTableHeader(WritableSheet sheet, ManufactureOrder produce)
/* 599:    */    throws RowsExceededException, WriteException
/* 600:    */  {
/* 601:601 */    WritableFont font = null;
/* 602:602 */    WritableCellFormat cFormat = null;
/* 603:603 */    Label label = null;
/* 604:    */    
/* 605:605 */    String[] text = 
/* 606:606 */      { "编 号", "图 片", "描  述", "数量", "包 装", "备注" };
/* 607:607 */    int[] indexs = 
/* 608:608 */      { 1, 2, 3, 5, 6, 7 };
/* 609:    */    
/* 610:610 */    for (int i = 0; i < text.length; i++)
/* 611:    */    {
/* 612:612 */      font = new WritableFont(WritableFont.createFont("宋体"), 12, 
/* 613:613 */        WritableFont.NO_BOLD, false);
/* 614:614 */      cFormat = new WritableCellFormat(font);
/* 615:615 */      cFormat.setAlignment(Alignment.CENTRE);
/* 616:616 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 617:617 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 618:618 */      label = new Label(indexs[i], 4, text[i], cFormat);
/* 619:619 */      sheet.addCell(label);
/* 620:    */    }
/* 621:    */  }
/* 622:    */  
/* 623:    */  private void setExcelFormat(WritableSheet sheet) throws WriteException
/* 624:    */  {
/* 625:625 */    sheet.getSettings().setBottomMargin(0.2D);
/* 626:626 */    sheet.getSettings().setTopMargin(0.3D);
/* 627:627 */    sheet.getSettings().setLeftMargin(0.1D);
/* 628:628 */    sheet.getSettings().setRightMargin(0.0D);
/* 629:    */    
/* 630:630 */    sheet.setColumnView(0, 3);
/* 631:631 */    sheet.setColumnView(1, 11);
/* 632:632 */    sheet.setColumnView(2, 15);
/* 633:633 */    sheet.setColumnView(3, 26);
/* 634:634 */    sheet.setColumnView(4, 12);
/* 635:635 */    sheet.setColumnView(5, 8);
/* 636:636 */    sheet.setColumnView(6, 15);
/* 637:637 */    sheet.setColumnView(7, 13);
/* 638:    */    
/* 639:639 */    sheet.setRowView(0, 315);
/* 640:640 */    sheet.setRowView(1, 608);
/* 641:641 */    sheet.setRowView(2, 570);
/* 642:642 */    sheet.setRowView(3, 180);
/* 643:643 */    sheet.setRowView(4, 450);
/* 644:    */    
/* 645:645 */    sheet.mergeCells(4, 1, 5, 2);
/* 646:646 */    sheet.mergeCells(3, 4, 4, 4);
/* 647:    */  }
/* 648:    */  
/* 650:    */  private void setTableData(PMF pmf, WritableSheet sheet, ManufactureOrder produce, List<Production> productions)
/* 651:    */    throws WriteException, IOException
/* 652:    */  {
/* 653:653 */    WritableFont font = null;
/* 654:654 */    WritableCellFormat cFormat = null;
/* 655:655 */    Label label = null;
/* 656:656 */    BufferedImage image = null;
/* 657:    */    
/* 658:658 */    double width = TextUtil.convertToPix(13.5D, 0);
/* 659:659 */    WritableImage ri = null;
/* 660:    */    
/* 661:661 */    Map<Long, Files> pics = getProductFile(pmf, productions);
/* 662:662 */    Map<Long, Product> product = getProduct(pmf, productions);
/* 663:    */    
/* 664:664 */    for (int i = 0; i < productions.size(); i++)
/* 665:    */    {
/* 666:666 */      sheet.setRowView(5 + i, 1500);
/* 667:    */      
/* 668:668 */      sheet.mergeCells(3, 5 + i, 4, 5 + i);
/* 669:    */      
/* 670:670 */      font = new WritableFont(WritableFont.TIMES, 12, 
/* 671:671 */        WritableFont.NO_BOLD, false);
/* 672:672 */      cFormat = new WritableCellFormat(font);
/* 673:673 */      cFormat.setAlignment(Alignment.CENTRE);
/* 674:674 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 675:675 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 676:676 */      cFormat.setWrap(true);
/* 677:677 */      label = new Label(1, 5 + i, 
/* 678:678 */        ((Production)productions.get(i)).getNumber(), cFormat);
/* 679:679 */      sheet.addCell(label);
/* 680:    */      
/* 681:681 */      int h = 0;
/* 682:682 */      int h1 = TextUtil.countRowHeight(
/* 683:683 */        ((Production)productions.get(i)).getDescription(), 26, 
/* 684:684 */        false);
/* 685:685 */      int h2 = TextUtil.countRowHeight(
/* 686:686 */        ((Production)productions.get(i)).getRemark(), 13, false);
/* 687:687 */      if (h1 > h2) {
/* 688:688 */        h = h1;
/* 689:    */      } else
/* 690:690 */        h = h2;
/* 691:691 */      if (h <= 1500)
/* 692:692 */        h = 1500;
/* 693:693 */      sheet.setRowView(5 + i, h);
/* 694:694 */      font = new WritableFont(WritableFont.TIMES, 12, 
/* 695:695 */        WritableFont.NO_BOLD, false);
/* 696:696 */      cFormat = new WritableCellFormat(font);
/* 697:697 */      cFormat.setAlignment(Alignment.CENTRE);
/* 698:698 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 699:699 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 700:700 */      cFormat.setWrap(true);
/* 701:701 */      label = new Label(3, 5 + i, 
/* 702:702 */        ((Production)productions.get(i)).getDescription(), cFormat);
/* 703:703 */      sheet.addCell(label);
/* 704:    */      
/* 705:705 */      cFormat = new WritableCellFormat();
/* 706:706 */      cFormat.setAlignment(Alignment.CENTRE);
/* 707:707 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 708:708 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 709:709 */      label = new Label(2, 5 + i, "", cFormat);
/* 710:710 */      sheet.addCell(label);
/* 711:    */      
/* 712:712 */      if ((((Production)productions.get(i)).getProductId() != null) && 
/* 713:713 */        (pics.get(((Production)productions.get(i))
/* 714:714 */        .getProductId()) != null)) {
/* 715:715 */        if (((Files)pics.get(((Production)productions.get(i))
/* 716:716 */          .getProductId())).getPngpath() != null)
/* 717:    */        {
/* 718:718 */          File f = new File(((Files)pics.get(
/* 719:719 */            ((Production)productions.get(i)).getProductId())).getPngpath());
/* 720:720 */          if (f.exists())
/* 721:    */          {
/* 722:722 */            image = ImageIO.read(f);
/* 723:723 */            double height = image.getHeight() / 
/* 724:724 */              image.getWidth() * width;
/* 725:725 */            if (height < TextUtil.convertToPix(h, 1))
/* 726:    */            {
///* 727:727 */              ri = new WritableImage(2.05D, 5 + 
///* 728:728 */                i + 
///* 729:729 */                TextUtil.countHeightPositionStartPersent(h, 
///* 730:730 */                13.5D, 0), 0.9D, 
///* 731:731 */                TextUtil.countHeightPercentage(
///* 732:732 */                image.getHeight() / 
///* 733:733 */                image.getWidth(), 
///* 734:    */                
///* 735:735 */                new int[] { 15 }, 0.9D, 
///* 736:736 */                new int[] { h }), 
///* 737:    */                
///* 738:738 */                f);
/* 739:    */            }
/* 740:    */            else
/* 741:    */            {
/* 742:742 */              ri = new WritableImage(2.05D, 5 + i + 0.05D, 
/* 743:743 */                TextUtil.countWidthPercentage(
/* 744:744 */                image.getHeight() / 
/* 745:745 */                image.getWidth(), 
/* 746:    */                
/* 747:747 */                new int[] { h }, 0.9D, 
/* 748:748 */                new int[] { 15 }), 0.9D, f);
/* 749:    */            }
/* 750:750 */            sheet.addImage(ri);
/* 751:    */          }
/* 752:    */        }
/* 753:    */      }
/* 754:754 */      font = new WritableFont(WritableFont.TIMES, 12, 
/* 755:755 */        WritableFont.NO_BOLD, false);
/* 756:756 */      cFormat = new WritableCellFormat(font);
/* 757:757 */      cFormat.setAlignment(Alignment.CENTRE);
/* 758:758 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 759:759 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 760:760 */      label = new Label(5, 5 + i, 
/* 761:761 */        ((Production)productions.get(i)).getAmount(), cFormat);
/* 762:762 */      sheet.addCell(label);
/* 763:    */      
/* 764:764 */      font = new WritableFont(WritableFont.TIMES, 12, 
/* 765:765 */        WritableFont.NO_BOLD, false);
/* 766:766 */      cFormat = new WritableCellFormat(font);
/* 767:767 */      cFormat.setAlignment(Alignment.CENTRE);
/* 768:768 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 769:769 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 770:770 */      cFormat.setWrap(true);
/* 771:771 */      label = new Label(6, 5 + i, 
/* 772:772 */        ((Production)productions.get(i)).getPacking(), cFormat);
/* 773:773 */      sheet.addCell(label);
/* 774:    */      
/* 775:775 */      font = new WritableFont(WritableFont.TIMES, 12, 
/* 776:776 */        WritableFont.NO_BOLD, false);
/* 777:777 */      cFormat = new WritableCellFormat(font);
/* 778:778 */      cFormat.setAlignment(Alignment.CENTRE);
/* 779:779 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/* 780:780 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/* 781:781 */      cFormat.setWrap(true);
/* 782:782 */      label = new Label(7, 5 + i, 
/* 783:783 */        ((Production)productions.get(i)).getRemark(), cFormat);
/* 784:784 */      sheet.addCell(label);
/* 785:    */    }
/* 786:    */  }
/* 787:    */  
/* 789:    */  private Map<Long, Files> getProductFile(PMF pmf, List<Production> productions)
/* 790:    */  {
/* 791:791 */    Map<Long, Files> result = new HashMap();
/* 792:    */    
/* 793:793 */    StringBuffer hql = new StringBuffer();
/* 794:794 */    hql.append("from Files where RelatedObject in (");
/* 795:795 */    for (Production production : productions)
/* 796:    */    {
/* 797:797 */      hql.append(production.getProductId()).append(",");
/* 798:    */    }
/* 799:799 */    hql.deleteCharAt(hql.length() - 1);
/* 800:800 */    hql.append(") and Type=").append("2");
/* 801:    */    
/* 802:802 */    List<Files> files = pmf.list(hql.toString());
/* 803:803 */    for (Files file : files)
/* 804:    */    {
/* 805:805 */      result.put(file.getRelatedObject(), file);
/* 806:    */    }
/* 807:807 */    return result;
/* 808:    */  }
/* 809:    */  
/* 810:    */  private Map<Long, Product> getProduct(PMF pmf, List<Production> productions)
/* 811:    */  {
/* 812:812 */    Map<Long, Product> result = new HashMap();
/* 813:    */    
/* 814:814 */    StringBuffer hql = new StringBuffer();
/* 815:815 */    hql.append("from Product where Id in (");
/* 816:816 */    for (Production production : productions)
/* 817:    */    {
/* 818:818 */      hql.append(production.getProductId()).append(",");
/* 819:    */    }
/* 820:820 */    hql.deleteCharAt(hql.length() - 1);
/* 821:821 */    hql.append(")");
/* 822:822 */    List<Product> files = pmf.list(hql.toString());
/* 823:823 */    for (Product file : files)
/* 824:    */    {
/* 825:825 */      result.put(file.getId(), file);
/* 826:    */    }
/* 827:827 */    return result;
/* 828:    */  }
/* 829:    */  
/* 830:    */  public ResponseEntity queryProductionList(RequestEntity req)
/* 831:    */    throws Exception
/* 832:    */  {
/* 833:833 */    StringBuffer sql = new StringBuffer();
/* 834:834 */    sql.append("select * from VW_PRODUCE_PRODUCT_FILES where order_id=:id");
/* 835:835 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 836:836 */      new String[] { "productId", "number", "pic", "description", "amount", "packing", 
/* 837:837 */      "remark", "orderId" }, null, null);
/* 838:    */  }
/* 839:    */  
/* 840:    */  public List<Production> getProductions(PMF pmf, Long orderid)
/* 841:    */  {
/* 842:842 */    StringBuffer sb = new StringBuffer();
/* 843:843 */    sb.append("from Production where OrderId=").append(orderid);
/* 844:844 */    return pmf.list(sb.toString());
/* 845:    */  }
/* 846:    */  
/* 847:    */  public void deleteProductions(PMF pmf, Long orderid)
/* 848:    */  {
/* 849:849 */    List<Production> productions = getProductions(pmf, orderid);
/* 850:850 */    for (Production p : productions)
/* 851:    */    {
/* 852:852 */      pmf.remove(p);
/* 853:    */    }
/* 854:    */  }
/* 855:    */  
/* 856:    */  public ResponseEntity audit(RequestEntity req)
/* 857:    */  {
/* 858:858 */    PMF pmf = RemoteUtil.getPMF(req);
/* 859:859 */    ResponseEntity resp = new ResponseEntity();
/* 860:    */    try
/* 861:    */    {
/* 862:862 */      Map val = (Map)req.getData().get(0);
/* 863:863 */      String id = val.get("id").toString();
/* 864:864 */      ManufactureOrder produce = (ManufactureOrder)pmf.get(
/* 865:865 */        ManufactureOrder.class, new Long(id));
/* 866:866 */      if (produce.getIsAudit())
/* 867:    */      {
/* 868:868 */        resp.setType(0);
/* 869:869 */        resp.setMessage("该生产单已经审核!");
/* 870:870 */        return resp;
/* 871:    */      }
/* 872:872 */      if (req.getUser().getReserved().equals("0"))
/* 873:    */      {
/* 874:874 */        produce.setAudit(req.getUser().getRealname());
/* 875:875 */        produce.setIsAudit(true);
/* 876:876 */        pmf.update(produce);
/* 877:    */      }
/* 878:    */      else
/* 879:    */      {
/* 880:880 */        resp.setType(0);
/* 881:881 */        resp.setMessage("您无权审核生产单");
/* 882:    */      }
/* 883:    */    }
/* 884:    */    catch (Exception e)
/* 885:    */    {
/* 886:886 */      resp.setType(0);
/* 887:887 */      resp.setMessage("审核失败，错误信息：" + e.getMessage());
/* 888:    */    }
/* 889:889 */    return resp;
/* 890:    */  }
/* 891:    */  
/* 892:    */  private void deleteProduceFile(PMF pmf, Long id, SysUser user)
/* 893:    */  {
/* 894:894 */    StringBuffer hql = new StringBuffer();
/* 895:895 */    hql.append("from Files where Type=4 and RelatedObject=").append(id);
/* 896:896 */    List<Files> list = pmf.list(hql.toString());
/* 897:897 */    for (Files f : list)
/* 898:    */    {
/* 899:899 */      CommonService.deleteFiles(pmf, f.getId(), user);
/* 900:    */    }
/* 901:    */  }
/* 902:    */  
/* 904:    */  public boolean createProduceFile(PMF pmf, ManufactureOrder produce, List<Production> list, SysUser user)
/* 905:    */  {
/* 906:906 */    CommonService.delete(pmf, produce.getId(), new Integer("4").intValue(), 
/* 907:907 */      user);
/* 908:    */    try
/* 909:    */    {
/* 910:910 */      Files file = exportExcel(pmf, produce, list);
/* 911:911 */      if (file == null)
/* 912:    */      {
/* 913:913 */        return false;
/* 914:    */      }
/* 915:915 */      pmf.save(file);
/* 916:916 */      OperationLogService.addLog(pmf, user, file, 1, "生成生产单excel文件");
/* 917:917 */      return true;
/* 918:    */    }
/* 919:    */    catch (Exception localException) {}
/* 920:    */    
/* 922:922 */    return false;
/* 923:    */  }
/* 924:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ProduceService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */