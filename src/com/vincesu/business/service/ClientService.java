/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Client;
/*   4:    */import com.vincesu.business.entity.Trackprogress;
/*   5:    */import com.vincesu.business.model.ContractModel;
/*   6:    */import com.vincesu.framework.entity.SysUser;
/*   7:    */import com.vincesu.framework.remote.RemoteUtil;
/*   8:    */import com.vincesu.framework.remote.RequestEntity;
/*   9:    */import com.vincesu.framework.remote.ResponseEntity;
/*  10:    */import com.vincesu.framework.util.BeanUtil;
/*  11:    */import com.vincesu.framework.util.EncodingUtil;
/*  12:    */import com.vincesu.framework.util.QueryUtil;
/*  13:    */import com.vincesu.framework.util.TimeUtil;
/*  14:    */import com.vincesu.persistence.PMF;
/*  15:    */import java.io.FileInputStream;
/*  16:    */import java.io.IOException;
/*  17:    */import java.io.OutputStream;
/*  18:    */import java.util.Date;
/*  19:    */import java.util.List;
/*  20:    */import java.util.Map;
/*  21:    */import java.util.zip.ZipOutputStream;
/*  22:    */import org.apache.tools.zip.ZipEntry;
/*  23:    */
/*  25:    */public class ClientService
/*  26:    */{
/*  27:    */  public ResponseEntity queryList(RequestEntity req)
/*  28:    */    throws Exception
/*  29:    */  {
/*  30: 30 */    Map val = (Map)req.getData().get(0);
/*  31: 31 */    StringBuffer sql = new StringBuffer();
/*  32: 32 */    sql.append("select ");
/*  33: 33 */    sql.append("a.id, ");
/*  34: 34 */    sql.append("a.number, ");
/*  35: 35 */    sql.append("a.contact, ");
/*  36: 36 */    sql.append("a.unit, ");
/*  37: 37 */    sql.append("a.email, ");
/*  38: 38 */    sql.append("a.phone, ");
/*  39: 39 */    sql.append("a.web, ");
/*  40: 40 */    sql.append("a.address, ");
/*  41: 41 */    sql.append("a.nationality, ");
/*  42: 42 */    sql.append("a.remark, ");
/*  43: 43 */    sql.append("a.reserved, ");
/*  44: 44 */    sql.append("a.saleman, ");
/*  45: 45 */    sql.append("a.clientfrom, ");
/*  46: 46 */    sql.append("a.jointime, ");
/*  47: 47 */    sql.append("a.potential, ");
/*  48: 48 */    sql.append("a.havequote, ");
/*  49: 49 */    sql.append("a.mailingsample, ");
/*  50: 50 */    sql.append("a.potentialreserved1, ");
/*  51: 51 */    sql.append("a.potentialreserved2, ");
/*  52: 52 */    sql.append("a.potentialreserved3, ");
/*  53: 53 */    sql.append("b.realname from client a,sys_user b ");
/*  54: 54 */    sql.append(" where b.uid=a.saleman and a.reserved like '")
/*  55: 55 */      .append(req.getUser().getReserved()).append("%' ");
/*  56: 56 */    sql.append(" and a.number='-1' ");
/*  57: 57 */    sql.append("and b.realname like '%:saleman%' ");
/*  58: 58 */    if ((val.get("clientfrom") != null) && (!val.get("clientfrom").toString().trim().equals("")))
/*  59:    */    {
/*  60: 60 */      if ((val.get("clientfrom").toString().trim().equals("-1")) || 
/*  61: 61 */        (val.get("clientfrom").toString().trim().equals("其他")))
/*  62:    */      {
/*  63: 63 */        sql.append("and a.clientfrom not in (SELECT field_value FROM sys_encoding WHERE field_name='clientfrom') ");
/*  64:    */      }
/*  65:    */      else
/*  66:    */      {
/*  67: 67 */        sql.append("and a.clientfrom = ':clientfrom' ");
/*  68:    */      }
/*  69:    */      
/*  70:    */    }
/*  71:    */    else {
/*  72: 72 */      sql.append("and a.clientfrom = ':clientfrom' ");
/*  73:    */    }
/*  74: 74 */    sql.append("and a.potential = ':potential' ");
/*  75: 75 */    sql.append("and contact like '%:contact%' ");
/*  76: 76 */    sql.append("and unit like '%:unit%' ");
/*  77: 77 */    sql.append(" order by a.id desc");
/*  78: 78 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  79: 79 */      new String[] { "id", "number", "contact", "unit", "email", "phone", "web", 
/*  80: 80 */      "address", "nationality", "remark", "reserved", "saleman", 
/*  81: 81 */      "clientfrom", "jointime", "potential", "havequote", 
/*  82: 82 */      "mailingsample", "potentialreserved1", "potentialreserved2", 
/*  83: 83 */      "potentialreserved3", "saleman" }, 
/*  84: 84 */      new String[] { "id", "number", "contact", "unit", "email", "phone", "web", 
/*  85: 85 */      "address", "nationality", "remark", "reserved", "saleman", 
/*  86: 86 */      "clientfrom", "jointime", "potential", "havequote", 
/*  87: 87 */      "mailingsample", "potentialreserved1", "potentialreserved2", 
/*  88: 88 */      "potentialreserved3", "saleman" }, null, null);
/*  89:    */  }
/*  90:    */  
/*  91:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  92:    */  {
/*  93: 93 */    StringBuffer sql = new StringBuffer();
/*  94: 94 */    sql.append("select ");
/*  95: 95 */    sql.append("a.id, ");
/*  96: 96 */    sql.append("a.number, ");
/*  97: 97 */    sql.append("a.contact, ");
/*  98: 98 */    sql.append("a.unit, ");
/*  99: 99 */    sql.append("a.email, ");
/* 100:100 */    sql.append("a.phone, ");
/* 101:101 */    sql.append("a.web, ");
/* 102:102 */    sql.append("a.address, ");
/* 103:103 */    sql.append("a.nationality, ");
/* 104:104 */    sql.append("a.remark, ");
/* 105:105 */    sql.append("a.reserved, ");
/* 106:106 */    sql.append("a.saleman, ");
/* 107:107 */    sql.append("a.clientfrom, ");
/* 108:108 */    sql.append("a.jointime, ");
/* 109:109 */    sql.append("a.potential, ");
/* 110:110 */    sql.append("a.havequote, ");
/* 111:111 */    sql.append("a.mailingsample, ");
/* 112:112 */    sql.append("a.potentialreserved1, ");
/* 113:113 */    sql.append("a.potentialreserved2, ");
/* 114:114 */    sql.append("a.potentialreserved3, ");
/* 115:115 */    sql.append("b.realname salemanname from client a left join sys_user b on a.saleman=b.uid ");
/* 116:116 */    sql.append(" where a.id=:id ");
/* 117:117 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 118:118 */      new String[] { "id", "number", "contact", "unit", "email", "phone", "web", 
/* 119:119 */      "address", "nationality", "remark", "reserved", "saleman", 
/* 120:120 */      "clientfrom", "jointime", "potential", "havequote", 
/* 121:121 */      "mailingsample", "potentialreserved1", "potentialreserved2", 
/* 122:122 */      "potentialreserved3", "saleman" }, null, null);
/* 123:    */  }
/* 124:    */  
/* 125:    */  public ResponseEntity queryCompanyList(RequestEntity req) throws Exception
/* 126:    */  {
/* 127:127 */    StringBuffer sql = new StringBuffer();
/* 128:128 */    sql.append("select ");
/* 129:129 */    sql.append("a.id, ");
/* 130:130 */    sql.append("a.number, ");
/* 131:131 */    sql.append("a.contact, ");
/* 132:132 */    sql.append("a.unit, ");
/* 133:133 */    sql.append("a.email, ");
/* 134:134 */    sql.append("a.phone, ");
/* 135:135 */    sql.append("a.web, ");
/* 136:136 */    sql.append("a.address, ");
/* 137:137 */    sql.append("a.nationality, ");
/* 138:138 */    sql.append("a.remark, ");
/* 139:139 */    sql.append("a.reserved, ");
/* 140:140 */    sql.append("a.saleman, ");
/* 141:141 */    sql.append("a.clientfrom, ");
/* 142:142 */    sql.append("a.jointime, ");
/* 143:143 */    sql.append("a.potential, ");
/* 144:144 */    sql.append("a.havequote, ");
/* 145:145 */    sql.append("a.mailingsample, ");
/* 146:146 */    sql.append("a.potentialreserved1, ");
/* 147:147 */    sql.append("a.potentialreserved2, ");
/* 148:148 */    sql.append("a.potentialreserved3 ");
/* 149:149 */    sql.append(" from client a ");
/* 150:150 */    sql.append(" where a.unit=':unit' ");
/* 151:151 */    sql.append("and contact like '%:contact%' ");
/* 152:152 */    sql.append("and a.id<>:id ");
/* 153:153 */    sql.append(" order by a.id desc");
/* 154:154 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 155:155 */      new String[] { "id", "number", "contact", "unit", "email", "phone", "web", 
/* 156:156 */      "address", "nationality", "remark", "reserved", "saleman", 
/* 157:157 */      "clientfrom", "jointime", "potential", "havequote", 
/* 158:158 */      "mailingsample", "potentialreserved1", "potentialreserved2", 
/* 159:159 */      "potentialreserved3", "saleman" }, 
/* 160:160 */      new String[] { "id", "number", "contact", "unit", "email", "phone", "web", 
/* 161:161 */      "address", "nationality", "remark", "reserved", "saleman", 
/* 162:162 */      "clientfrom", "jointime", "potential", "havequote", 
/* 163:163 */      "mailingsample", "potentialreserved1", "potentialreserved2", 
/* 164:164 */      "potentialreserved3", "saleman" }, null, null);
/* 165:    */  }
/* 166:    */  
/* 167:    */  public ResponseEntity add(RequestEntity req)
/* 168:    */  {
/* 169:169 */    PMF pmf = RemoteUtil.getPMF(req);
/* 170:170 */    ResponseEntity resp = new ResponseEntity();
/* 171:    */    try
/* 172:    */    {
/* 173:173 */      Map val = (Map)req.getData().get(0);
/* 174:174 */      Client client = new Client();
/* 175:175 */      BeanUtil.copyProperty(val, client);
/* 176:    */      
/* 177:177 */      if (client.getClientfrom().equals("其他"))
/* 178:    */      {
/* 179:179 */        if ((val.get("clientfromother") != null) && 
/* 180:180 */          (!val.get("clientfromother").toString().equals("其他")) && 
/* 181:181 */          (!val.get("clientfromother").toString().equals("")))
/* 182:    */        {
/* 183:183 */          client.setClientfrom(val.get("clientfromother").toString());
/* 184:184 */          EncodingUtil.update(pmf, "clientfrom", 
/* 185:185 */            client.getClientfrom(), client.getClientfrom(), 1);
/* 186:    */        }
/* 187:    */        else
/* 188:    */        {
/* 189:189 */          client.setClientfrom("其他");
/* 190:    */        }
/* 191:    */      }
/* 192:192 */      if (!setSaleman(pmf, val, client, req))
/* 193:    */      {
/* 194:194 */        resp.setType(0);
/* 195:195 */        resp.setMessage("添加失败，无法设置业务员！");
/* 196:196 */        return resp;
/* 197:    */      }
/* 198:198 */      client.setJointime(TimeUtil.toString(new Date(), "yyyyMMdd"));
/* 199:    */      
/* 200:200 */      if (client.getNumber().equals("-1"))
/* 201:    */      {
/* 202:202 */        long count = pmf.count("select * from client where unit='" + 
/* 203:203 */          client.getUnit() + "'");
/* 204:204 */        if (count > 0L)
/* 205:    */        {
/* 206:206 */          resp.setType(0);
/* 207:207 */          resp.setMessage("添加失败，错误信息：此单位已经添加");
/* 208:208 */          return resp;
/* 209:    */        }
/* 210:    */      }
/* 211:211 */      client.setPotential("1");
/* 212:212 */      pmf.save(client);
/* 213:213 */      Trackprogress tp = new Trackprogress();
/* 214:214 */      tp.setClientid(client.getId());
/* 215:215 */      tp.setTracktime(new Date());
/* 216:216 */      tp.setProgress("系统添加客户");
/* 217:217 */      pmf.save(tp);
/* 219:    */    }
/* 220:    */    catch (Exception e)
/* 221:    */    {
/* 222:222 */      resp.setType(0);
/* 223:223 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 224:    */    }
/* 225:225 */    return resp;
/* 226:    */  }
/* 227:    */  
/* 228:    */  public ResponseEntity remove(RequestEntity req)
/* 229:    */  {
/* 230:230 */    PMF pmf = RemoteUtil.getPMF(req);
/* 231:231 */    ResponseEntity resp = new ResponseEntity();
/* 232:    */    try
/* 233:    */    {
/* 234:234 */      Map val = (Map)req.getData().get(0);
/* 235:235 */      String id = val.get("id").toString();
/* 236:236 */      Client client = (Client)pmf.get(Client.class, new Long(id));
/* 237:237 */      if (client.getReserved().startsWith(req.getUser().getReserved()))
/* 238:    */      {
/* 240:240 */        if (pmf.count("select * from contract where buyer_id=" + client.getId()) > 0L)
/* 241:    */        {
/* 242:242 */          resp.setType(0);
/* 243:243 */          resp.setMessage("此客户已存在相关合同信息，不可删除！");
/* 244:    */        }
/* 245:245 */        pmf.remove(client);
/* 246:    */      }
/* 247:    */      else
/* 248:    */      {
/* 249:249 */        resp.setType(0);
/* 250:250 */        resp.setMessage("这不是您的客户，您无权删除此客户信息");
/* 251:    */      }
/* 252:    */    }
/* 253:    */    catch (Exception e)
/* 254:    */    {
/* 255:255 */      resp.setType(0);
/* 256:256 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 257:    */    }
/* 258:258 */    return resp;
/* 259:    */  }
/* 260:    */  
/* 261:    */  public ResponseEntity update(RequestEntity req)
/* 262:    */  {
/* 263:263 */    PMF pmf = RemoteUtil.getPMF(req);
/* 264:264 */    ResponseEntity resp = new ResponseEntity();
/* 265:    */    try
/* 266:    */    {
/* 267:267 */      Map val = (Map)req.getData().get(0);
/* 268:268 */      String id = val.get("id").toString();
/* 269:269 */      Client client = (Client)pmf.get(Client.class, new Long(id));
/* 270:270 */      String jointime = client.getJointime();
/* 271:271 */      if (client.getReserved().startsWith(req.getUser().getReserved()))
/* 272:    */      {
/* 273:273 */        BeanUtil.copyProperty(val, client);
/* 274:274 */        if ((client.getClientfrom().equals("其他")) && 
/* 275:275 */          (val.get("clientfromother") != null) && 
/* 276:276 */          (!val.get("clientfromother").toString().equals("其他")))
/* 277:    */        {
/* 278:278 */          client.setClientfrom(val.get("clientfromother").toString());
/* 279:279 */          EncodingUtil.update(pmf, "clientfrom", 
/* 280:280 */            client.getClientfrom(), client.getClientfrom(), 1);
/* 281:    */        }
/* 282:282 */        if (!setSaleman(pmf, val, client, req))
/* 283:    */        {
/* 284:284 */          resp.setType(0);
/* 285:285 */          resp.setMessage("添加失败，无法设置业务员！");
/* 286:286 */          return resp;
/* 287:    */        }
/* 288:288 */        client.setJointime(jointime);
/* 289:289 */        pmf.update(client);
/* 290:    */      }
/* 291:    */      else
/* 292:    */      {
/* 293:293 */        resp.setType(0);
/* 294:294 */        resp.setMessage("这不是您的客户，您无权修改此客户信息");
/* 295:    */      }
/* 296:    */    }
/* 297:    */    catch (Exception e)
/* 298:    */    {
/* 299:299 */      resp.setType(0);
/* 300:300 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 301:    */    }
/* 302:302 */    return resp;
/* 303:    */  }
/* 304:    */  
/* 305:    */  private boolean setSaleman(PMF pmf, Map val, Client client, RequestEntity req)
/* 306:    */    throws Exception
/* 307:    */  {
/* 308:308 */    if (val.get("salemanflag") != null)
/* 309:    */    {
/* 310:310 */      if (val.get("salemanflag").toString().equals("0"))
/* 311:    */      {
/* 312:312 */        if (val.get("salemanname") != null)
/* 313:    */        {
/* 314:314 */          List<SysUser> users = pmf
/* 315:315 */            .list("from SysUser where Realname='" + 
/* 316:316 */            val.get("salemanname") + "'");
/* 317:317 */          if ((users == null) || (users.size() != 1))
/* 318:    */          {
/* 319:319 */            return false;
/* 320:    */          }
/* 321:321 */          SysUser oldSalesman = (SysUser)pmf.get(SysUser.class, 
/* 322:322 */            client.getSaleman());
/* 323:323 */          client.setSaleman(((SysUser)users.get(0)).getId());
/* 324:324 */          client.setReserved(((SysUser)users.get(0)).getReserved());
/* 325:325 */          if (oldSalesman != null)
/* 326:    */          {
/* 327:327 */            ContractModel.chanageSalesman(pmf, client, oldSalesman, 
/* 328:328 */              (SysUser)users.get(0));
/* 329:    */          }
/* 330:    */        }
/* 331:    */        else
/* 332:    */        {
/* 333:333 */          return false;
/* 334:    */        }
/* 335:    */      }
/* 336:    */      else
/* 337:    */      {
/* 338:338 */        client.setSaleman(req.getUser().getId());
/* 339:339 */        client.setReserved(req.getUser().getReserved());
/* 340:    */      }
/* 341:    */    }
/* 342:    */    else
/* 343:    */    {
/* 344:344 */      client.setSaleman(req.getUser().getId());
/* 345:345 */      client.setReserved(req.getUser().getReserved());
/* 346:    */    }
/* 347:347 */    return true;
/* 348:    */  }
/* 349:    */  
/* 350:    */  public ResponseEntity backup(RequestEntity req) throws IOException
/* 351:    */  {
/* 352:352 */    PMF pmf = RemoteUtil.getPMF(req);
/* 353:353 */    ResponseEntity resp = new ResponseEntity();
/* 354:354 */    Map val = (Map)req.getData().get(0);
/* 355:355 */    OutputStream os = (OutputStream)val.get("filestream");
/* 356:    */    
/* 357:357 */    ZipOutputStream zos = new ZipOutputStream(os);
/* 358:    */    
/* 359:359 */    ZipEntry entry = new ZipEntry("test1.txt");
/* 360:    */    
/* 361:361 */    FileInputStream in = new FileInputStream(
/* 362:362 */      "f:/0008PLDKQQ20140306100306.txt");
/* 363:363 */    int nNumber = 0;
/* 364:364 */    byte[] buffer = new byte[512];
/* 365:365 */    while ((nNumber = in.read(buffer)) != -1)
/* 366:    */    {
/* 367:367 */      zos.write(buffer, 0, nNumber);
/* 368:    */    }
/* 369:    */    
/* 370:370 */    in.close();
/* 371:    */    
/* 372:372 */    entry = new ZipEntry("test2/test2.txt");
/* 373:    */    
/* 374:374 */    in = new FileInputStream("f:/0008PLDKQQ20140306100306.txt");
/* 375:375 */    nNumber = 0;
/* 376:376 */    buffer = new byte[512];
/* 377:377 */    while ((nNumber = in.read(buffer)) != -1)
/* 378:    */    {
/* 379:379 */      zos.write(buffer, 0, nNumber);
/* 380:    */    }
/* 381:    */    
/* 382:382 */    in.close();
/* 383:    */    
/* 384:384 */    zos.putNextEntry(entry);
/* 385:385 */    return resp;
/* 386:    */  }
/* 387:    */  
/* 388:    */  public ResponseEntity changeClientType(RequestEntity req) throws IOException
/* 389:    */  {
/* 390:390 */    PMF pmf = RemoteUtil.getPMF(req);
/* 391:391 */    ResponseEntity resp = new ResponseEntity();
/* 392:    */    try
/* 393:    */    {
/* 394:394 */      Map val = (Map)req.getData().get(0);
/* 395:395 */      String id = val.get("id").toString();
/* 396:396 */      Client client = (Client)pmf.get(Client.class, new Long(id));
/* 397:    */      
/* 398:398 */      if (client.getPotential() != null)
/* 399:    */      {
/* 400:400 */        if (client.getPotential().equals("0"))
/* 401:    */        {
/* 402:402 */          client.setPotential("1");
/* 403:    */        }
/* 404:404 */        else if (client.getPotential().equals("1"))
/* 405:    */        {
/* 406:406 */          client.setPotential("0");
/* 407:    */        }
/* 408:    */      }
/* 409:409 */      pmf.update(client);
/* 410:    */    }
/* 411:    */    catch (Exception e)
/* 412:    */    {
/* 413:413 */      resp.setType(0);
/* 414:414 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 415:    */    }
/* 416:416 */    return resp;
/* 417:    */  }
/* 418:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ClientService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */