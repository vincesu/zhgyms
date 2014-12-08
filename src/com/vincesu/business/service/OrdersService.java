/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ContractNumberSequence;
/*   4:    */import com.vincesu.business.entity.Files;
/*   5:    */import com.vincesu.business.entity.Orders;
/*   6:    */import com.vincesu.framework.entity.SysUser;
/*   7:    */import com.vincesu.framework.remote.RemoteUtil;
/*   8:    */import com.vincesu.framework.remote.RequestEntity;
/*   9:    */import com.vincesu.framework.remote.ResponseEntity;
/*  10:    */import com.vincesu.framework.util.BeanUtil;
/*  11:    */import com.vincesu.framework.util.QueryUtil;
/*  12:    */import com.vincesu.persistence.PMF;
/*  13:    */import java.io.PrintStream;
/*  14:    */import java.util.List;
/*  15:    */import java.util.Map;
/*  16:    */
/*  17:    */public class OrdersService
/*  18:    */{
/*  19:    */  public ResponseEntity queryList(RequestEntity req)
/*  20:    */    throws Exception
/*  21:    */  {
/*  22: 22 */    StringBuffer sql = new StringBuffer();
/*  23: 23 */    sql.append("select a.*,b.contact contact,b.phone phone,b.address address from orders a left join client b on b.unit=a.buyer and b.number='-1' ");
/*  24: 24 */    sql.append(" where (a.reserved like '").append(req.getUser().getReserved()).append("%' ");
/*  25: 25 */    sql.append(" or a.reserved like 'lock").append(req.getUser().getReserved()).append("%' ) ");
/*  26: 26 */    sql.append(" and a.buyer like '%:buyer%' ");
/*  27: 27 */    sql.append(" and a.name like '%:name%' ");
/*  28: 28 */    sql.append(" and a.contract_number like '%:contractNumber%' ");
/*  29: 29 */    sql.append(" order by a.id desc");
/*  30: 30 */    System.out.println(sql);
/*  31: 31 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  32: 32 */      new String[] { "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "contact", "phone", "address" }, 
/*  33: 33 */      new String[] { "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "contact", "phone", "address" }, null, null);
/*  34:    */  }
/*  35:    */  
/*  36:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  37:    */  {
/*  38: 38 */    StringBuffer sql = new StringBuffer();
/*  39: 39 */    sql.append("select a.* from orders a ");
/*  40: 40 */    sql.append("where a.id=:id ");
/*  41: 41 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  42: 42 */      new String[] { "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved" }, null, null);
/*  43:    */  }
/*  44:    */  
/*  45:    */  public ResponseEntity queryRelatedObject(RequestEntity req) throws Exception
/*  46:    */  {
/*  47: 47 */    StringBuffer sql = new StringBuffer();
/*  48: 48 */    sql.append("select a.*,b.id fileid,b.name,b.path,b.pdfpath from orders a,files b ");
/*  49: 49 */    sql.append("where 1=1 and a.id=b.related_object and a.id=:id order by b.id desc ");
/*  50: 50 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  51: 51 */      new String[] { "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "fileid", "filename", "path", "pdfpath" }, null, null);
/*  52:    */  }
/*  53:    */  
/*  54:    */  public ResponseEntity add(RequestEntity req)
/*  55:    */  {
/*  56: 56 */    PMF pmf = RemoteUtil.getPMF(req);
/*  57: 57 */    ResponseEntity resp = new ResponseEntity();
/*  58:    */    try
/*  59:    */    {
/*  60: 60 */      Map val = (Map)req.getData().get(0);
/*  61: 61 */      Orders order = new Orders();
/*  62: 62 */      BeanUtil.copyProperty(val, order, new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/*  63: 63 */      order.setSalesman(req.getUser().getUsername());
/*  64: 64 */      order.setReserved(req.getUser().getReserved());
/*  65: 65 */      if ((order.getContractNumber() == null) || (order.getContractNumber().equals("")))
/*  66:    */      {
/*  67: 67 */        ContractNumberSequence cns = new ContractNumberSequence();
/*  68: 68 */        pmf.save(cns);
/*  69: 69 */        order.setContractNumber("PO/" + cns.getId());
/*  70:    */      }
/*  71: 71 */      pmf.save(order);
/*  72:    */    }
/*  73:    */    catch (Exception e)
/*  74:    */    {
/*  75: 75 */      resp.setType(0);
/*  76: 76 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  77:    */    }
/*  78: 78 */    return resp;
/*  79:    */  }
/*  80:    */  
/*  81:    */  public ResponseEntity remove(RequestEntity req)
/*  82:    */  {
/*  83: 83 */    PMF pmf = RemoteUtil.getPMF(req);
/*  84: 84 */    ResponseEntity resp = new ResponseEntity();
/*  85:    */    try
/*  86:    */    {
/*  87: 87 */      Map val = (Map)req.getData().get(0);
/*  88: 88 */      String id = val.get("id").toString();
/*  89: 89 */      Orders order = (Orders)pmf.get(Orders.class, new Long(id));
/*  90: 90 */      CommonService.delete(pmf, order.getId(), 6, req.getUser());
/*  91: 91 */      if (req.getUser().getReserved().equals("0"))
/*  92:    */      {
/*  93: 93 */        pmf.remove(order);
/*  94:    */      }
/*  95: 95 */      else if (order.getReserved().startsWith("lock"))
/*  96:    */      {
/*  97: 97 */        resp.setType(0);
/*  98: 98 */        resp.setMessage("订单已提交，无法删除，请联系管理员。");
/*  99:    */      }
/* 100:100 */      else if (order.getReserved().startsWith(req.getUser().getReserved()))
/* 101:    */      {
/* 102:102 */        pmf.remove(order);
/* 103:    */      }
/* 104:    */      else
/* 105:    */      {
/* 106:106 */        resp.setType(0);
/* 107:107 */        resp.setMessage("该订单不是您的订单，您无权操作。");
/* 108:    */      }
/* 109:    */    }
/* 110:    */    catch (Exception e)
/* 111:    */    {
/* 112:112 */      resp.setType(0);
/* 113:113 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 114:    */    }
/* 115:115 */    return resp;
/* 116:    */  }
/* 117:    */  
/* 118:    */  public ResponseEntity update(RequestEntity req)
/* 119:    */  {
/* 120:120 */    PMF pmf = RemoteUtil.getPMF(req);
/* 121:121 */    ResponseEntity resp = new ResponseEntity();
/* 122:    */    try
/* 123:    */    {
/* 124:124 */      Map val = (Map)req.getData().get(0);
/* 125:125 */      String id = val.get("id").toString();
/* 126:126 */      Orders order = (Orders)pmf.get(Orders.class, new Long(id));
/* 127:127 */      if (req.getUser().getReserved().equals("0"))
/* 128:    */      {
/* 129:129 */        BeanUtil.copyProperty(val, order, new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/* 130:130 */        pmf.update(order);
/* 131:    */      }
/* 132:132 */      else if (order.getReserved().startsWith("lock"))
/* 133:    */      {
/* 134:134 */        resp.setType(0);
/* 135:135 */        resp.setMessage("订单已提交，无法修改，请联系管理员。");
/* 136:    */      }
/* 137:137 */      else if (order.getReserved().startsWith(req.getUser().getReserved()))
/* 138:    */      {
/* 139:139 */        BeanUtil.copyProperty(val, order, new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/* 140:140 */        pmf.update(order);
/* 141:    */      }
/* 142:    */      else
/* 143:    */      {
/* 144:144 */        resp.setType(0);
/* 145:145 */        resp.setMessage("该订单不是您的订单，您无权操作。");
/* 146:    */      }
/* 147:    */      
/* 148:    */    }
/* 149:    */    catch (Exception e)
/* 150:    */    {
/* 151:151 */      resp.setType(0);
/* 152:152 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 153:    */    }
/* 154:154 */    return resp;
/* 155:    */  }
/* 156:    */  
/* 157:    */  public ResponseEntity queryRelatedFilesList(RequestEntity req) throws Exception
/* 158:    */  {
/* 159:159 */    StringBuffer sql = new StringBuffer();
/* 160:160 */    sql.append("select b.type tpye,b.name filename,a.*,b.id fileid,b.path from orders a,files b ");
/* 161:161 */    sql.append(" where a.id=b.related_object and b.type>=6 and b.type<=11 ");
/* 162:162 */    sql.append(" and a.buyer like '%:buyer%' ");
/* 163:163 */    sql.append(" and a.name like '%:name%' ");
/* 164:164 */    sql.append(" and b.type = :type ");
/* 165:165 */    sql.append(" and a.contract_number like '%:contractNumber%' ");
/* 166:166 */    sql.append(" order by b.id desc");
/* 167:167 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 168:168 */      new String[] { "type", "filename", "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "fileid", "path" }, 
/* 169:169 */      new String[] { "type", "filename", "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "fileid", "path" }, null, null);
/* 170:    */  }
/* 171:    */  
/* 172:    */  public ResponseEntity queryRelatedFilesWithOrderId(RequestEntity req) throws Exception
/* 173:    */  {
/* 174:174 */    StringBuffer sql = new StringBuffer();
/* 175:175 */    sql.append("select b.name filename,b.id fileid,b.path,b.pdfpath,b.related_description,a.reserved from files b,orders a ");
/* 176:176 */    sql.append(" where b.type=6 and a.id=b.related_object and b.related_object = :id order by b.id desc ");
/* 177:177 */    return QueryUtil.queryBySQL(req, sql.toString(), new String[] { "filename", "id", "path", "pdfpath", "rd", "reserved" }, 
/* 178:178 */      null, null);
/* 179:    */  }
/* 180:    */  
/* 181:    */  public ResponseEntity queryRelatedFiles(RequestEntity req) throws Exception
/* 182:    */  {
/* 183:183 */    StringBuffer sql = new StringBuffer();
/* 184:184 */    sql.append("select a.*,b.id fileid,b.path from orders a,files b ");
/* 185:185 */    sql.append(" where a.id=b.related_object and b.type>=6 and b.type<=11 ");
/* 186:186 */    sql.append(" and b.id='%:fileid%' order by b.id desc ");
/* 187:    */    
/* 188:188 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 189:189 */      new String[] { "id", "buyer", "name", "contractNumber", "sum", "orderDate", "deliveryDate", "salesman", "reserved", "fileid", "path" }, null, null);
/* 190:    */  }
/* 191:    */  
/* 192:    */  public ResponseEntity addRelatedFile(RequestEntity req)
/* 193:    */  {
/* 194:194 */    PMF pmf = RemoteUtil.getPMF(req);
/* 195:195 */    ResponseEntity resp = new ResponseEntity();
/* 196:    */    try
/* 197:    */    {
/* 198:198 */      Map val = (Map)req.getData().get(0);
/* 199:199 */      Orders order = (Orders)pmf.get(Orders.class, new Long(val.get("orderid").toString()));
/* 200:200 */      if (req.getUser().getReserved().equals("0"))
/* 201:    */      {
/* 202:202 */        String fileId = val.get("id").toString();
/* 203:203 */        String rd = val.get("relatedDescription").toString();
/* 204:204 */        String orderid = val.get("orderid").toString();
/* 205:205 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 206:206 */        file.setType(Integer.valueOf(6));
/* 207:207 */        file.setRelatedDescription(rd);
/* 208:208 */        file.setRelatedObject(new Long(orderid));
/* 209:209 */        pmf.update(file);
/* 210:    */      }
/* 211:211 */      else if (order.getReserved().startsWith("lock"))
/* 212:    */      {
/* 213:213 */        resp.setType(0);
/* 214:214 */        resp.setMessage("订单已提交，无法修改，请联系管理员。");
/* 215:    */      }
/* 216:216 */      else if (order.getReserved().startsWith(req.getUser().getReserved()))
/* 217:    */      {
/* 218:218 */        String fileId = val.get("id").toString();
/* 219:219 */        String rd = val.get("relatedDescription").toString();
/* 220:220 */        String orderid = val.get("orderid").toString();
/* 221:221 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 222:222 */        file.setType(Integer.valueOf(6));
/* 223:223 */        file.setRelatedDescription(rd);
/* 224:224 */        file.setRelatedObject(new Long(orderid));
/* 225:225 */        pmf.update(file);
/* 226:    */      }
/* 227:    */      else
/* 228:    */      {
/* 229:229 */        resp.setType(0);
/* 230:230 */        resp.setMessage("该订单不是您的订单，您无权操作。");
/* 231:    */      }
/* 232:    */    }
/* 233:    */    catch (Exception e)
/* 234:    */    {
/* 235:235 */      resp.setType(0);
/* 236:236 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 237:    */    }
/* 238:238 */    return resp;
/* 239:    */  }
/* 240:    */  
/* 241:    */  public ResponseEntity removeRelatedObject(RequestEntity req)
/* 242:    */  {
/* 243:243 */    PMF pmf = RemoteUtil.getPMF(req);
/* 244:244 */    ResponseEntity resp = new ResponseEntity();
/* 245:    */    try
/* 246:    */    {
/* 247:247 */      Map val = (Map)req.getData().get(0);
/* 248:248 */      Files file = (Files)pmf.get(Files.class, new Long(val.get("fileid").toString()));
/* 249:249 */      Orders order = (Orders)pmf.get(Orders.class, file.getRelatedObject());
/* 250:    */      
/* 251:251 */      if (req.getUser().getReserved().equals("0"))
/* 252:    */      {
/* 253:253 */        String id = val.get("fileid").toString();
/* 254:254 */        CommonService.deleteFiles(pmf, new Long(id), req.getUser());
/* 255:    */      }
/* 256:256 */      else if (order.getReserved().startsWith("lock"))
/* 257:    */      {
/* 258:258 */        resp.setType(0);
/* 259:259 */        resp.setMessage("订单已提交，无法修改，请联系管理员。");
/* 260:    */      }
/* 261:261 */      else if (order.getReserved().startsWith(req.getUser().getReserved()))
/* 262:    */      {
/* 263:263 */        String id = val.get("fileid").toString();
/* 264:264 */        CommonService.deleteFiles(pmf, new Long(id), req.getUser());
/* 265:    */      }
/* 266:    */      else
/* 267:    */      {
/* 268:268 */        resp.setType(0);
/* 269:269 */        resp.setMessage("该订单不是您的订单，您无权操作。");
/* 270:    */      }
/* 271:    */    }
/* 272:    */    catch (Exception e)
/* 273:    */    {
/* 274:274 */      resp.setType(0);
/* 275:275 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 276:    */    }
/* 277:277 */    return resp;
/* 278:    */  }
/* 279:    */  
/* 280:    */  public ResponseEntity lockOrder(RequestEntity req)
/* 281:    */  {
/* 282:282 */    PMF pmf = RemoteUtil.getPMF(req);
/* 283:283 */    ResponseEntity resp = new ResponseEntity();
/* 284:    */    try
/* 285:    */    {
/* 286:286 */      Map val = (Map)req.getData().get(0);
/* 287:287 */      String id = val.get("id").toString();
/* 288:288 */      Orders order = (Orders)pmf.get(Orders.class, new Long(id));
/* 289:289 */      if (order.getReserved().startsWith("lock"))
/* 290:    */      {
/* 291:291 */        resp.setType(0);
/* 292:292 */        resp.setMessage("该订单已经提交。");
/* 293:    */      }
/* 294:294 */      else if (order.getReserved().startsWith(req.getUser().getReserved()))
/* 295:    */      {
/* 296:296 */        order.setReserved("lock" + order.getReserved());
/* 297:297 */        pmf.update(order);
/* 298:    */      }
/* 299:    */      else
/* 300:    */      {
/* 301:301 */        resp.setType(0);
/* 302:302 */        resp.setMessage("该订单不是您的订单，您无权操作。");
/* 303:    */      }
/* 304:    */      
/* 305:    */    }
/* 306:    */    catch (Exception e)
/* 307:    */    {
/* 308:308 */      resp.setType(0);
/* 309:309 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 310:    */    }
/* 311:311 */    return resp;
/* 312:    */  }
/* 313:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.OrdersService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */