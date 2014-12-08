/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.framework.entity.SysUser;
/*   5:    */import com.vincesu.framework.exception.LoginInTimeOutException;
/*   6:    */import com.vincesu.framework.remote.RemoteUtil;
/*   7:    */import com.vincesu.framework.remote.RequestEntity;
/*   8:    */import com.vincesu.framework.remote.ResponseEntity;
/*   9:    */import com.vincesu.framework.util.BeanUtil;
/*  10:    */import com.vincesu.framework.util.ImageUtil;
/*  11:    */import com.vincesu.framework.util.OfficeUtil;
/*  12:    */import com.vincesu.framework.util.PathUtil;
/*  13:    */import com.vincesu.framework.util.QueryUtil;
/*  14:    */import com.vincesu.framework.util.SystemUtil;
/*  15:    */import com.vincesu.framework.util.TextUtil;
/*  16:    */import com.vincesu.persistence.PMF;
/*  17:    */import java.io.DataInputStream;
/*  18:    */import java.io.File;
/*  19:    */import java.io.FileInputStream;
/*  20:    */import java.io.IOException;
/*  21:    */import java.io.OutputStream;
/*  22:    */import java.io.PrintStream;
/*  23:    */import java.util.Date;
/*  24:    */import java.util.List;
/*  25:    */import java.util.Map;
/*  26:    */import java.util.Random;
/*  27:    */
/*  32:    */public class CommonService
/*  33:    */{
/*  34: 34 */  protected static Random random = new Random(System.currentTimeMillis());
/*  35:    */  
/*  36:    */  public ResponseEntity uploadFile(RequestEntity req)
/*  37:    */  {
/*  38: 38 */    PMF pmf = RemoteUtil.getPMF(req);
/*  39: 39 */    ResponseEntity resp = new ResponseEntity();
/*  40:    */    
/*  41:    */    try
/*  42:    */    {
/*  43: 43 */      Map data = (Map)req.getData().get(0);
/*  44:    */      
/*  45: 45 */      if ((data.get("filename") != null) && (
/*  46: 46 */        (PathUtil.getExtensionName(data.get("filename").toString().toLowerCase()).equals("docx")) || 
/*  47: 47 */        (PathUtil.getExtensionName(data.get("filename").toString()).toLowerCase().equals("xlsx")))) {
/*  48: 48 */        resp.setType(0);
/*  49: 49 */        resp.setMessage("docx和xlsx格式（office2007之后版本）的文件，请先另存为97-2003版本格式（doc或xlsx）再上传！");
/*  50: 50 */        return resp;
/*  51:    */      }
/*  52:    */      
/*  54: 54 */      File targetFile = null;
/*  55: 55 */      int temp = random.nextInt(1000);
/*  56: 56 */      File targetFilePath = new File(SystemUtil.getLocation() + "/resource/" + temp);
/*  57: 57 */      String path = "resource/" + temp;
/*  58: 58 */      if (!targetFilePath.exists())
/*  59: 59 */        targetFilePath.mkdirs();
/*  60: 60 */      int i = 0;
/*  61: 61 */      Random filenamerandow = new Random(System.currentTimeMillis());
/*  62: 62 */      String filename = null;
/*  63:    */      do
/*  64:    */      {
/*  65: 65 */        int randomint = filenamerandow.nextInt(10000);
/*  66: 66 */        targetFile = new File(targetFilePath, Integer.toString(randomint) + "." + 
/*  67: 67 */          PathUtil.getExtensionName(data.get("filename").toString().toLowerCase()));
/*  68:    */        
/*  69: 69 */        if (!targetFile.exists())
/*  70:    */        {
/*  71: 71 */          if (pmf.count("select * from files where path='" + targetFile.getAbsolutePath() + "'") == 0L) {
/*  72: 72 */            path = path + "/" + randomint + "." + PathUtil.getExtensionName(data.get("filename").toString().toLowerCase());
/*  73: 73 */            filename = Integer.toString(randomint);
/*  74: 74 */            break;
/*  75:    */          }
/*  76:    */        }
/*  77: 77 */        i++;
/*  78: 78 */      } while (i != 99999);
/*  79:    */      
					if(i>=99999)
					{
/*  80: 80 */      resp.setType(0);
/*  81: 81 */      resp.setMessage("保存文件有误，随机文件夹" + targetFilePath.getAbsolutePath() + "已经存满99999个文件");
/*  82: 82 */      return resp;
					}
/*  83:    */      
/*  87: 87 */      //FileInputStream fis = (FileInputStream)data.get("filestream");;
/*  88:    */      FileInputStream fis = (FileInputStream)data.get("filestream");
/*  88:    */      
/*  89: 89 */      PathUtil.saveFile(fis, targetFile);
/*  90:    */      
/*  91: 91 */      Files file = new Files();
/*  92: 92 */      file.setPath(targetFile.getAbsolutePath());
/*  93: 93 */      file.setWebpath(path);
/*  94:    */      
/*  95: 95 */      if ((PathUtil.getExtensionName(data.get("filename").toString()).equals("doc")) || 
/*  96: 96 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("xls")) || 
/*  97: 97 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("docx")) || 
/*  98: 98 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("xlsx")) || 
/*  99: 99 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("DOC")) || 
/* 100:100 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("XLS")) || 
/* 101:101 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("DOCX")) || 
/* 102:102 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("XLSX")))
/* 103:    */      {
/* 104:104 */        File pdffile = new File(targetFile.getParentFile().getAbsolutePath(), filename + ".pdf");
/* 105:105 */        i = 1;
/* 106:106 */        while (pdffile.exists()) {
/* 107:107 */          pdffile = new File(targetFile.getParentFile().getAbsolutePath(), filename + i + ".pdf");
/* 108:108 */          i++;
/* 109:    */        }
/* 110:110 */        if (OfficeUtil.office2PDF(targetFile, pdffile) != 0) {
/* 111:111 */          resp.setType(0);
/* 112:112 */          resp.setMessage("转换pdf出错，请重新启动服务后重试！");
/* 113:113 */          return resp;
/* 114:    */        }
/* 115:115 */        file.setPdfpath(pdffile.getAbsolutePath());
/* 117:    */      }
/* 118:118 */      else if ((PathUtil.getExtensionName(data.get("filename").toString()).equals("pdf")) || 
/* 119:119 */        (PathUtil.getExtensionName(data.get("filename").toString()).equals("PDF")))
/* 120:    */      {
/* 121:121 */        file.setPdfpath(targetFile.getAbsolutePath());
/* 122:    */      }
/* 123:    */      
/* 124:124 */      file.setDate(new Date());
/* 125:125 */      file.setOperator(req.getUser().getUsername());
/* 126:126 */      file.setName(data.get("filename").toString());
/* 127:    */      
/* 129:129 */      if ((PathUtil.getExtensionName(targetFile.getName().toLowerCase()).equals("jpg")) || 
/* 130:130 */        (PathUtil.getExtensionName(targetFile.getName().toLowerCase()).equals("jpeg"))) {
/* 131:    */        try
/* 132:    */        {
/* 133:133 */          File pngPic = new File(targetFile.getAbsolutePath().substring(0, targetFile.getAbsolutePath().length() - 4) + ".png");
/* 134:134 */          ImageUtil.convert(targetFile, pngPic, "png");
/* 135:135 */          file.setPngpath(pngPic.getAbsolutePath());
/* 136:    */        } catch (Exception e) {
/* 137:137 */          resp.setType(0);
/* 138:138 */          resp.setMessage("图片转换png格式失败，请自行转换png图片上传");
/* 139:139 */          return resp;
/* 140:    */        }
/* 141:    */      }
/* 142:    */      
/* 143:143 */      pmf.save(file);
/* 144:144 */      OperationLogService.addLog(pmf, req.getUser(), file, 
/* 145:145 */        1, "上传文件");
/* 146:    */      
/* 147:147 */      resp.setType(1);
/* 148:148 */      resp.setMessage(file.getId().toString() + "&" + path);
/* 149:    */    }
/* 155:    */    catch (Exception e)
/* 156:    */    {
/* 157:157 */      System.out.println(e);
/* 158:158 */      System.out.println(e.getMessage());
/* 159:159 */      resp.setType(0);
/* 160:160 */      resp.setMessage("保存文件出错");
/* 161:    */    }
/* 162:162 */    return resp;
/* 163:    */  }
/* 164:    */  
/* 165:    */  public static void deleteFiles(PMF pmf, Long id, SysUser user)
/* 166:    */  {
/* 167:167 */    Files file = (Files)pmf.get(Files.class, id);
/* 168:168 */    File f = null;
/* 169:169 */    if ((file.getPath() != null) && (!file.getPath().equals(""))) {
/* 170:170 */      f = new File(file.getPath());
/* 171:171 */      f.delete();
/* 172:    */    }
/* 173:173 */    if ((file.getPdfpath() != null) && (!file.getPdfpath().equals(""))) {
/* 174:174 */      f = new File(file.getPdfpath());
/* 175:175 */      f.delete();
/* 176:    */    }
/* 177:177 */    pmf.remove(file);
/* 178:178 */    OperationLogService.addLog(pmf, user, file, 
/* 179:179 */      2, "删除文件");
/* 180:    */  }
/* 181:    */  
/* 182:    */  public static void deleteFilesByRelatedObject(PMF pmf, Long objectId, Long fileId, String type, SysUser user)
/* 183:    */  {
/* 184:184 */    File file = null;
/* 185:185 */    StringBuffer sql = new StringBuffer();
/* 186:186 */    sql.append("select * from files where related_object=").append(objectId).append(" and Type=").append(type);
/* 187:187 */    List<Files> data = pmf.get(sql.toString(), new Class[] { Files.class });
/* 188:188 */    for (Files f : data)
/* 189:    */    {
/* 190:190 */      if (!f.getId().equals(fileId))
/* 191:    */      {
/* 192:192 */        if ((f.getPath() != null) && (!f.getPath().equals(""))) {
/* 193:193 */          file = new File(f.getPath());
/* 194:194 */          file.delete();
/* 195:    */        }
/* 196:196 */        if ((f.getPdfpath() != null) && (!f.getPdfpath().equals("")))
/* 197:    */        {
/* 198:198 */          file = new File(f.getPdfpath());
/* 199:199 */          file.delete();
/* 200:    */        }
/* 201:    */      }
/* 202:202 */      pmf.remove(f);
/* 203:203 */      OperationLogService.addLog(pmf, user, f, 
/* 204:204 */        2, "删除系统相关文件");
/* 205:    */    }
/* 206:    */  }
/* 207:    */  
/* 227:    */  public static ResponseEntity downloadFile(RequestEntity req)
/* 228:    */    throws IOException
/* 229:    */  {
/* 230:230 */    PMF pmf = RemoteUtil.getPMF(req);
/* 231:231 */    ResponseEntity resp = new ResponseEntity();
/* 232:    */    
/* 233:233 */    DataInputStream dis = null;
/* 234:    */    try
/* 235:    */    {
/* 236:236 */      Map val = (Map)req.getData().get(0);
/* 237:237 */      String fileid = val.get("params").toString();
/* 238:238 */      Files file = (Files)pmf.get(Files.class, new Long(fileid));
/* 239:239 */      OutputStream os = (OutputStream)val.get("filestream");
/* 240:    */      
/* 242:242 */      long totalsize = 0L;
/* 243:243 */      File f = new File(file.getPath());
/* 244:244 */      long filelength = f.length();
/* 245:245 */      byte[] b = new byte[1024];
/* 246:246 */      dis = new DataInputStream(new FileInputStream(f));
/* 247:    */      
/* 248:248 */      while (totalsize < filelength) {
/* 249:249 */        totalsize += 1024L;
/* 250:250 */        if (totalsize > filelength) {
/* 251:251 */          byte[] leftpart = new byte[1024 - (int)(totalsize - filelength)];
/* 252:252 */          dis.readFully(leftpart);
/* 253:253 */          os.write(leftpart);
/* 254:    */        }
/* 255:    */        else {
/* 256:256 */          dis.readFully(b);
/* 257:257 */          os.write(b);
/* 259:    */        }
/* 260:    */        
/* 262:    */      }
/* 263:    */      
/* 266:    */    }
/* 267:    */    catch (Exception e)
/* 268:    */    {
/* 271:271 */      System.out.println(e.getMessage());
/* 273:    */    }
/* 274:    */    finally
/* 275:    */    {
/* 277:277 */      if (dis != null)
/* 278:278 */        dis.close();
/* 279:    */    }
/* 280:280 */    return resp;
/* 281:    */  }
/* 282:    */  
/* 283:    */  public static ResponseEntity previewFile(RequestEntity req) throws IOException
/* 284:    */  {
/* 285:285 */    PMF pmf = RemoteUtil.getPMF(req);
/* 286:286 */    ResponseEntity resp = new ResponseEntity();
/* 287:    */    
/* 288:288 */    DataInputStream dis = null;
/* 289:    */    try
/* 290:    */    {
/* 291:291 */      Map val = (Map)req.getData().get(0);
/* 292:292 */      String fileid = val.get("params").toString();
/* 293:293 */      Files file = (Files)pmf.get(Files.class, new Long(fileid));
/* 294:294 */      OutputStream os = (OutputStream)val.get("filestream");
/* 295:    */      
/* 297:297 */      long totalsize = 0L;
/* 298:298 */      File f = null;
/* 299:299 */      if ((PathUtil.getExtensionName(file.getPath()).toLowerCase().equals("jpg")) || 
/* 300:300 */        (PathUtil.getExtensionName(file.getPath()).toLowerCase().equals("png"))) {
/* 301:301 */        f = new File(file.getPath());
/* 302:    */      } else
/* 303:303 */        f = new File(file.getPdfpath());
/* 304:304 */      long filelength = f.length();
/* 305:305 */      byte[] b = new byte[1024];
/* 306:306 */      dis = new DataInputStream(new FileInputStream(f));
/* 307:    */      
/* 308:308 */      while (totalsize < filelength) {
/* 309:309 */        totalsize += 1024L;
/* 310:310 */        if (totalsize > filelength) {
/* 311:311 */          byte[] leftpart = new byte[1024 - (int)(totalsize - filelength)];
/* 312:312 */          dis.readFully(leftpart);
/* 313:313 */          os.write(leftpart);
/* 314:    */        }
/* 315:    */        else {
/* 316:316 */          dis.readFully(b);
/* 317:317 */          os.write(b);
/* 318:    */        }
/* 319:    */        
/* 320:    */      }
/* 321:    */    }
/* 322:    */    catch (Exception e)
/* 323:    */    {
/* 324:324 */      System.out.println(e.getMessage());
/* 325:    */    }
/* 326:    */    finally
/* 327:    */    {
/* 328:328 */      if (dis != null)
/* 329:329 */        dis.close();
/* 330:    */    }
/* 331:331 */    return resp;
/* 332:    */  }
/* 333:    */  
/* 334:    */  public ResponseEntity queryFiles(RequestEntity req) throws Exception
/* 335:    */  {
/* 336:336 */    StringBuffer sql = new StringBuffer();
/* 337:337 */    sql.append("select b.id,b.name,b.description,b.path,b.pdfpath,b.type,b.related_description,b.related_object,b.date,b.operator,b.is_primary,b.reserved,b.webpath from files b ");
/* 338:338 */    sql.append("where 1=1 and b.type=:type and b.related_description like ':relatedDescription%'  ");
/* 339:339 */    sql.append(" and b.name like '%:name%' ");
/* 340:340 */    sql.append(" order by id desc, date desc");
/* 341:341 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 342:342 */      new String[] { "id", "name", "description", "path", "pdfpath", "type", "related_description", "related_object", "date", "operator", "is_primary", "reserved", "webpath" }, 
/* 343:343 */      new String[] { "id", "name", "description", "path", "pdfpath", "type", "related_description", "related_object", "date", "operator", "is_primary", "reserved", "webpath" }, 
/* 344:344 */      null, null);
/* 345:    */  }
/* 346:    */  
/* 347:    */  public ResponseEntity queryFile(RequestEntity req) throws Exception
/* 348:    */  {
/* 349:349 */    StringBuffer sql = new StringBuffer();
/* 350:350 */    sql.append("select b.id,b.name,b.description,b.path,b.pdfpath,b.type,b.related_description,b.related_object,b.date,b.operator,b.is_primary,b.reserved,b.webpath from files b ");
/* 351:351 */    sql.append("where 1=1 and b.type=:type and b.related_description=':relatedDescription' ");
/* 352:352 */    sql.append(" and b.id=:id ");
/* 353:353 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 354:354 */      new String[] { "id", "name", "description", "path", "pdfpath", "type", "related_description", "related_object", "date", "operator", "is_primary", "reserved", "webpath" }, 
/* 355:355 */      null, null);
/* 356:    */  }
/* 357:    */  
/* 358:    */  public ResponseEntity querySearchFiles(RequestEntity req) throws Exception
/* 359:    */  {
/* 360:360 */    StringBuffer sql = new StringBuffer();
/* 361:361 */    sql.append("select b.id,b.name,b.description,b.path,b.pdfpath,b.type,b.related_description,b.related_object,b.date,b.operator,b.is_primary,b.reserved,b.webpath from files b ");
/* 362:362 */    sql.append("where b.type=:type ");
/* 363:363 */    sql.append(" and b.name like '%:name%' order by b.id desc ");
/* 364:364 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 365:365 */      new String[] { "id", "name", "description", "path", "pdfpath", "type", "relatedDescription", "relatedObject", "date", "operator", "isPrimary", "reserved", "webpath" }, 
/* 366:366 */      new String[] { "date" }, 
/* 367:367 */      new String[] { "yyyy-MM-dd" });
/* 368:    */  }
/* 369:    */  
/* 370:    */  public ResponseEntity add(RequestEntity req)
/* 371:    */  {
/* 372:372 */    PMF pmf = RemoteUtil.getPMF(req);
/* 373:373 */    ResponseEntity resp = new ResponseEntity();
/* 374:    */    try
/* 375:    */    {
/* 376:376 */      Map val = (Map)req.getData().get(0);
/* 377:377 */      String fileId = val.get("id").toString();
/* 378:378 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 379:379 */      if ((val.get("date") == null) || (val.get("date").toString().equals(""))) {
/* 380:380 */        BeanUtil.copyProperty(val, file, false);
/* 381:    */      } else
/* 382:382 */        BeanUtil.copyProperty(val, file, new String[] { "yyyy-MM-dd" }, false);
/* 383:383 */      pmf.update(file);
/* 384:    */    }
/* 385:    */    catch (Exception e)
/* 386:    */    {
/* 387:387 */      resp.setType(0);
/* 388:388 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 389:    */    }
/* 390:390 */    return resp;
/* 391:    */  }
/* 392:    */  
/* 393:    */  public ResponseEntity remove(RequestEntity req)
/* 394:    */  {
/* 395:395 */    PMF pmf = RemoteUtil.getPMF(req);
/* 396:396 */    ResponseEntity resp = new ResponseEntity();
/* 397:    */    try
/* 398:    */    {
/* 399:399 */      Map val = (Map)req.getData().get(0);
/* 400:400 */      String id = val.get("id").toString();
/* 401:401 */      Files file = (Files)pmf.get(Files.class, new Long(id));
/* 402:402 */      deleteFiles(pmf, file.getId(), req.getUser());
/* 403:    */    }
/* 404:    */    catch (Exception e)
/* 405:    */    {
/* 406:406 */      resp.setType(0);
/* 407:407 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 408:    */    }
/* 409:409 */    return resp;
/* 410:    */  }
/* 411:    */  
/* 413:    */  public void delete(Long id) {}
/* 414:    */  
/* 416:    */  public static List<Files> getLatestFilesList(PMF pmf, int type, int count)
/* 417:    */  {
/* 418:418 */    List<Files> result = null;
/* 419:419 */    StringBuffer hql = new StringBuffer();
/* 420:420 */    hql.append("from Files where Type=").append(type).append(" order by Id desc ");
/* 421:421 */    result = pmf.list(hql.toString(), null, 0, count);
/* 422:422 */    String ename = null;
/* 423:423 */    for (Files f : result)
/* 424:    */    {
/* 425:425 */      ename = PathUtil.getExtensionName(f.getPath());
/* 426:426 */      if ((f.getDescription() == null) || (f.getDescription().equals(""))) {
/* 427:427 */        f.setDescription(PathUtil.getFileNameNoEx(f.getName()) + "." + ename);
/* 428:    */      } else
/* 429:429 */        f.setDescription(PathUtil.getFileNameNoEx(f.getDescription()) + "." + ename);
/* 430:    */    }
/* 431:431 */    return result;
/* 432:    */  }
/* 433:    */  
/* 434:    */  public static List<Files> getLatestFilesListWithFormatTitle(PMF pmf, int type, int count, int charLength)
/* 435:    */  {
/* 436:436 */    List<Files> files = getLatestFilesList(pmf, 12, 6);
/* 437:437 */    TextUtil tu = null;
/* 438:438 */    int i = 1;
/* 439:439 */    for (Files f : files) {
/* 440:440 */      if (f.getName() != null) {
/* 441:441 */        tu = new TextUtil(f.getName());
/* 442:    */        
/* 443:443 */        if (tu.getChCharacter() * 2 + tu.getEnCharacter() + tu.getNumberCharacter() + tu.getOtherCharacter() + tu.getSpaceCharacter() > charLength) {
/* 444:444 */          f.setRelatedDescription(f.getName());
/* 445:445 */          tu = new TextUtil(f.getName().substring(0, f.getName().length() - i));
/* 446:    */          
/* 447:447 */          while ((tu.getChCharacter() * 2 + tu.getEnCharacter() + tu.getNumberCharacter() + tu.getOtherCharacter() + tu.getSpaceCharacter() > charLength) && (i > 0) && (i <= f.getName().length())) {
/* 448:448 */            i++;
/* 449:449 */            tu = new TextUtil(f.getName().substring(0, f.getName().length() - i));
/* 450:    */          }
/* 451:451 */          f.setName(f.getName().substring(0, f.getName().length() - i - 3) + "...");
/* 452:    */        }
/* 453:    */      }
/* 454:    */    }
/* 455:455 */    return files;
/* 456:    */  }
/* 457:    */  
/* 463:    */  public ResponseEntity uploadMultipleFiles(RequestEntity req)
/* 464:    */    throws IOException
/* 465:    */  {
/* 466:466 */    PMF pmf = RemoteUtil.getPMF(req);
/* 467:467 */    ResponseEntity resp = new ResponseEntity();
/* 468:468 */    StringBuffer result = new StringBuffer();
/* 469:469 */    FileInputStream fis = null;
/* 470:    */    
/* 471:    */    try
/* 472:    */    {
/* 473:473 */      Map data = (Map)req.getData().get(0);
/* 474:474 */      List<File> files = (List)data.get("files");
/* 475:475 */      String[] filenames = (String[])data.get("filenames");
/* 476:    */      ResponseEntity localResponseEntity1;
/* 477:477 */      if (files.size() != filenames.length)
/* 478:    */      {
/* 479:479 */        resp.setType(0);
/* 480:480 */        resp.setMessage("error1");
/* 481:481 */        localResponseEntity1 = resp;return localResponseEntity1;
/* 482:    */      }
/* 483:    */      
/* 484:484 */      File targetFile = null;File targetFileJpg = null;File targetFilePng = null;
/* 485:485 */      String exname = null;
/* 486:    */      
/* 488:488 */      for (int j = 0; j < files.size(); j++)
/* 489:    */      {
/* 492:492 */        int temp = random.nextInt(100);
/* 493:493 */        File targetFilePath = new File(SystemUtil.getLocation() + "/resource/" + temp);
/* 494:494 */        String path = "resource/" + temp;
/* 495:495 */        if (!targetFilePath.exists())
/* 496:496 */          targetFilePath.mkdirs();
/* 497:497 */        int i = 0;
/* 498:498 */        Random filenamerandow = new Random(5L);
/* 499:    */        
/* 502:    */        do
/* 503:    */        {
/* 504:504 */          exname = PathUtil.getExtensionName(filenames[j]);
/* 505:505 */          int randomint = filenamerandow.nextInt(10000);
/* 506:506 */          targetFile = new File(targetFilePath, Integer.toString(randomint) + "." + exname);
/* 507:    */          
/* 509:509 */          if (!targetFile.exists())
/* 510:    */          {
/* 511:511 */            path = path + "/" + randomint + "." + exname;
/* 512:512 */            break;
/* 513:    */          }
/* 514:514 */          i++;
/* 515:515 */        } while (i != 99999);
/* 516:    */        
if(i>=99999) 
{
/* 517:517 */        resp.setType(0);
/* 518:518 */        resp.setMessage("保存文件有误，随机文件夹" + targetFilePath.getAbsolutePath() + "已经存满99999个文件");
/* 519:519 */        localResponseEntity1 = resp;
					return localResponseEntity1;
}
/* 520:    */        
/* 523:523 */        fis = new FileInputStream((File)files.get(j));
/* 524:524 */        PathUtil.saveFile(fis, targetFile);
/* 525:525 */        if (fis != null) {
/* 526:526 */          fis.close();
/* 527:    */        }
/* 528:    */        
/* 529:529 */        Files file = new Files();
/* 530:530 */        file.setPath(targetFile.getAbsolutePath());
/* 531:531 */        file.setWebpath(path);
/* 532:    */        
/* 533:533 */        file.setDate(new Date());
/* 534:534 */        file.setOperator(req.getUser().getUsername());
/* 535:535 */        file.setName(filenames[j]);
/* 536:    */        
/* 538:538 */        if ((PathUtil.getExtensionName(targetFile.getName().toLowerCase()).equals("jpg")) || 
/* 539:539 */          (PathUtil.getExtensionName(targetFile.getName().toLowerCase()).equals("jpeg")))
/* 540:    */        {
/* 541:541 */          File pngPic = new File(targetFile.getAbsolutePath().substring(0, targetFile.getAbsolutePath().length() - 4) + ".png");
/* 542:542 */          ImageUtil.convert(targetFile, pngPic, "png");
/* 543:543 */          file.setPngpath(pngPic.getAbsolutePath());
/* 544:    */        }
/* 545:    */        
/* 546:546 */        pmf.save(file);
/* 547:547 */        OperationLogService.addLog(pmf, req.getUser(), file, 
/* 548:548 */          1, "批量上传文件");
/* 549:    */        
/* 551:551 */        result.append(file.getId()).append("|");
/* 552:    */      }
/* 553:    */      
/* 557:557 */      result.deleteCharAt(result.length() - 1);
/* 558:558 */      resp.setMessage(result.toString());
/* 559:    */    }
/* 567:    */    catch (Exception e)
/* 568:    */    {
/* 569:569 */      System.out.println(e);
/* 570:570 */      System.out.println(e.getMessage());
/* 571:571 */      resp.setType(0);
/* 572:    */      
/* 573:573 */      resp.setMessage("error3");
/* 575:    */    }
/* 576:    */    finally
/* 577:    */    {
/* 578:578 */      if (fis != null)
/* 579:579 */        fis.close();
/* 580:    */    }
/* 581:581 */    return resp;
/* 582:    */  }
/* 583:    */  
/* 584:    */  public static void delete(PMF pmf, Long objectId, int type, SysUser user)
/* 585:    */  {
/* 586:586 */    StringBuffer hql = new StringBuffer();
/* 587:587 */    File file = null;
/* 588:588 */    hql.append("from Files where RelatedObject=").append(objectId).append(" and Type=").append(type);
/* 589:589 */    List<Files> list = pmf.list(hql.toString());
/* 590:590 */    for (Files f : list)
/* 591:    */    {
/* 592:592 */      if ((f.getPath() != null) && (!f.getPath().equals(""))) {
/* 593:593 */        file = new File(f.getPath());
/* 594:594 */        file.delete();
/* 595:    */      }
/* 596:596 */      if ((f.getPdfpath() != null) && (!f.getPdfpath().equals("")))
/* 597:    */      {
/* 598:598 */        file = new File(f.getPdfpath());
/* 599:599 */        file.delete();
/* 600:    */      }
/* 601:601 */      pmf.remove(f);
/* 602:602 */      OperationLogService.addLog(pmf, user, file, 
/* 603:603 */        2, "删除系统相关文件");
/* 604:    */    }
/* 605:    */  }
/* 606:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.CommonService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */