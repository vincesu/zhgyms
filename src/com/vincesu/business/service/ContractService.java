/*    1:     */package com.vincesu.business.service;
/*    2:     */
/*    3:     */import com.vincesu.business.entity.Client;
/*    4:     */import com.vincesu.business.entity.ContractDetail;
/*    5:     */import com.vincesu.business.entity.ContractNumberSequence;
/*    6:     */import com.vincesu.business.entity.Files;
/*    7:     */import com.vincesu.business.entity.FinancialStatements;
/*    8:     */import com.vincesu.business.entity.ManufactureOrder;
/*    9:     */import com.vincesu.business.entity.Product;
/*   10:     */import com.vincesu.business.entity.SalesProgram;
/*   11:     */import com.vincesu.business.model.ContractModel;
/*   12:     */import com.vincesu.framework.entity.SysUser;
/*   13:     */import com.vincesu.framework.exception.LoginInTimeOutException;
/*   14:     */import com.vincesu.framework.remote.RemoteUtil;
/*   15:     */import com.vincesu.framework.remote.RequestEntity;
/*   16:     */import com.vincesu.framework.remote.ResponseEntity;
/*   17:     */import com.vincesu.framework.util.BeanUtil;
/*   18:     */import com.vincesu.framework.util.OfficeUtil;
/*   19:     */import com.vincesu.framework.util.QueryUtil;
/*   20:     */import com.vincesu.framework.util.SystemUtil;
/*   21:     */import com.vincesu.framework.util.TextUtil;
/*   22:     */import com.vincesu.framework.util.TimeUtil;
/*   23:     */import com.vincesu.persistence.PMF;
/*   24:     */import java.awt.image.BufferedImage;
/*   25:     */import java.io.File;
/*   26:     */import java.io.FileOutputStream;
/*   27:     */import java.io.IOException;
/*   28:     */import java.io.OutputStream;
/*   29:     */import java.math.BigDecimal;
/*   30:     */import java.util.ArrayList;
/*   31:     */import java.util.Date;
/*   32:     */import java.util.HashMap;
/*   33:     */import java.util.List;
/*   34:     */import java.util.Map;
/*   35:     */import java.util.Map.Entry;
/*   36:     */import javax.imageio.ImageIO;
/*   37:     */import jxl.SheetSettings;
/*   38:     */import jxl.Workbook;
/*   39:     */import jxl.format.Alignment;
/*   40:     */import jxl.format.Border;
/*   41:     */import jxl.format.BorderLineStyle;
/*   42:     */import jxl.format.VerticalAlignment;
/*   43:     */import jxl.write.Label;
/*   44:     */import jxl.write.WritableCellFormat;
/*   45:     */import jxl.write.WritableFont;
/*   46:     */import jxl.write.WritableFont.FontName;
/*   47:     */import jxl.write.WritableImage;
/*   48:     */import jxl.write.WritableSheet;
/*   49:     */import jxl.write.WritableWorkbook;
/*   50:     */import jxl.write.WriteException;
/*   51:     */import jxl.write.biff.RowsExceededException;
/*   52:     */
/*   53:     */public class ContractService
/*   54:     */{
/*   55:     */  public ResponseEntity queryList(RequestEntity req) throws Exception
/*   56:     */  {
/*   57:  57 */    StringBuffer sql = new StringBuffer();
/*   58:  58 */    sql.append("select a.order_date,a.delivery_date,a.id,a.number,c.unit,c.nationality,c.clientfrom,c.address,a.`name`,ifnull(e.amount,0) amount,a.lead_time,");
/*   59:  59 */    sql.append("a.shipping_trem,a.payment,a.remark,a.currency_symbol,a.complete, ");
/*   60:  60 */    sql.append("(case when a.reserved like 'locked%' then '已审核' when a.reserved like 'lock%' then '审核中' when a.reserved like 'finish%' then '完成' else '未提交' end ),a.makepoint,");
/*   61:  61 */    sql.append("d.realname,a.reserved,b.id fileid,b.pdfpath path,b.name filename,a.email email,a.tel tel,a.fax fax,a.`createc` `createc` from ");
/*   62:  62 */    sql.append("contract a LEFT JOIN files b ON (a.id=b.`related_object` AND b.`type`=24 AND b.`related_description`='合同') ");
/*   63:  63 */    sql.append(" left join VW_CONTRACT_AMOUNT e on ( a.id=e.id ), ");
/*   64:  64 */    sql.append("client c,sys_user d ");
/*   65:  65 */    sql.append("where a.buyer_id=c.id and a.saleman=d.uid and a.buyer_id is not null ");
/*   66:  66 */    sql.append(" and a.number like '%:number%' ");
/*   67:  67 */    sql.append(" and d.realname like '%:salesman%' ");
/*   68:  68 */    sql.append(" and a.order_date >= ':bt' ");
/*   69:  69 */    sql.append(" and a.order_date <= ':et' ");
/*   70:  70 */    sql.append(" and c.unit like '%:client%' ");
/*   71:  71 */    sql.append(" and a.reserved REGEXP ':status' ");
/*   72:     */    
/*   73:  73 */    sql.append(" and (c.reserved like '")
/*   74:  74 */      .append(req.getUser().getReserved()).append("%' ) ");
/*   75:     */    
/*   76:  76 */    sql.append(" order by a.id desc");
/*   77:  77 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*   78:  78 */      new String[] { "orderDate", "deliveryDate", "id", "number", "clientname", 
/*   79:  79 */      "nationality", "clientfrom", "address", "name", "amount", 
/*   80:  80 */      "leadTime", "shippingTrem", "payment", "remark", 
/*   81:  81 */      "currencySymbol", "complete", "status", "saleman", "reserved", 
/*   82:  82 */      "makepoint", "fileid", "path", "filename", "email", "tel", 
/*   83:  83 */      "fax", "createc" }, 
/*   84:  84 */      new String[] { "orderDate", "deliveryDate", "id", "number", "clientname", 
/*   85:  85 */      "nationality", "clientfrom", "address", "name", "amount", 
/*   86:  86 */      "leadTime", "shippingTrem", "payment", "remark", 
/*   87:  87 */      "currencySymbol", "complete", "status", "saleman", "reserved", 
/*   88:  88 */      "makepoint", "fileid", "path", "filename", "email", "tel", 
/*   89:  89 */      "fax", "createc" }, null, null);
/*   90:     */  }
/*   91:     */  
/*   92:     */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*   93:     */  {
/*   94:  94 */    StringBuffer sql = new StringBuffer();
/*   95:  95 */    sql.append("select a.id,c.name,c.address,a.number,a.order_date,a.delivery_date,a.saleman,a.reserved,");
/*   96:  96 */    sql.append("b.id fileid,b.pdfpath path,b.name filename,a.lead_time,a.makepoint,a.complete from contract a,files b,client c ");
/*   97:  97 */    sql.append("where a.id=b.related_object and b.type=24 and a.buyer_id=c.id ");
/*   98:  98 */    sql.append(" and a.id=:id ");
/*   99:  99 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  100: 100 */      new String[] { "id", "clientname", "address", "number", "orderDate", "deliveryDate", 
/*  101: 101 */      "saleman", "reserved", "fileid", "path", "filename", 
/*  102: 102 */      "leadTime", "makepoint", "complete" }, null, null);
/*  103:     */  }
/*  104:     */  
/*  105:     */  public ResponseEntity queryContractTotal(RequestEntity req)
/*  106:     */    throws Exception
/*  107:     */  {
/*  108: 108 */    StringBuffer sql = new StringBuffer();
/*  109: 109 */    sql.append("SELECT a.`currency_symbol`,SUM(e.amount_number) FROM contract a,files b,client c,sys_user d,VW_CONTRACT_AMOUNT e ");
/*  110: 110 */    sql.append(" where 1=1 and a.id=e.id and a.id=b.related_object and b.type=24 and a.buyer_id=c.id and a.saleman=d.uid and b.related_description='合同' and a.buyer_id is not null ");
/*  111: 111 */    sql.append(" and a.number like '%:number%' ");
/*  112: 112 */    sql.append(" and d.realname like '%:salesman%' ");
/*  113: 113 */    sql.append(" and a.order_date >= ':bt' ");
/*  114: 114 */    sql.append(" and a.order_date <= ':et' ");
/*  115: 115 */    sql.append(" and a.reserved REGEXP ':status' ");
/*  116: 116 */    sql.append(" and (c.reserved like '")
/*  117: 117 */      .append(req.getUser().getReserved()).append("%' ) ");
/*  118:     */    
/*  119: 119 */    sql.append(" GROUP BY a.`currency_symbol` ");
/*  120: 120 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  121: 121 */      new String[] { "currencySymbol", "amount" }, null, null);
/*  122:     */  }
/*  123:     */  
/*  124:     */  public ResponseEntity restoreContract(RequestEntity req)
/*  125:     */  {
/*  126: 126 */    PMF pmf = RemoteUtil.getPMF(req);
/*  127: 127 */    ResponseEntity resp = new ResponseEntity();
/*  128: 128 */    com.vincesu.business.entity.Contract contract = null;
/*  129: 129 */    com.vincesu.business.entity.Contract oldContract = null;
/*  130: 130 */    FinancialStatements fs = null;
/*  131:     */    try
/*  132:     */    {
/*  133: 133 */      List<ContractDetail> list = new ArrayList();
/*  134: 134 */      Map val = (Map)req.getData().get(0);
/*  135:     */      
/*  136: 136 */      if ((val.get("id") == null) || 
/*  137: 137 */        (val.get("id").toString().equals("")))
/*  138:     */      {
/*  139: 139 */        contract = new com.vincesu.business.entity.Contract();
/*  140:     */        
/*  141: 141 */        if (pmf.count("select * from contract where number='" + val.get("number") + "'") > 0L)
/*  142:     */        {
/*  143: 143 */          resp.setErrorCode("001");
/*  144: 144 */          resp.setType(0);
/*  145: 145 */          ContractNumberSequence cns = new ContractNumberSequence();
/*  146: 146 */          pmf.save(cns);
/*  147: 147 */          resp.setMessage("PI/" + cns.getId().toString());
/*  148: 148 */          return resp;
/*  149:     */        }
/*  150: 150 */        fs = new FinancialStatements();
/*  151:     */      }
/*  152:     */      else
/*  153:     */      {
/*  154: 154 */        contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, 
/*  155: 155 */          new Long(val.get("id").toString()));
/*  156:     */        
/*  157: 157 */        if (checkContractStatus(contract) == 1)
/*  158:     */        {
/*  159: 159 */          resp.setType(0);
/*  160: 160 */          resp.setMessage("保存失败，此合同已提交，不能再次修改");
/*  161: 161 */          return resp;
/*  162:     */        }
/*  163: 163 */        if (checkContractStatus(contract) == 2)
/*  164:     */        {
/*  165: 165 */          resp.setType(0);
/*  166: 166 */          resp.setMessage("保存失败，此合同已审核，不能再次修改");
/*  167: 167 */          return resp;
/*  168:     */        }
/*  169:     */        
/*  170: 170 */        if (!ContractModel.removeLockStatus(contract.getReserved()).equals(req.getUser().getReserved()))
/*  171:     */        {
/*  172: 172 */          resp.setType(0);
/*  173: 173 */          resp.setMessage("保存失败，您无权限修改合同");
/*  174: 174 */          return resp;
/*  175:     */        }
/*  176:     */        
/*  177: 177 */        oldContract = new com.vincesu.business.entity.Contract();
/*  178: 178 */        BeanUtil.copyProperty(contract, oldContract);
/*  179:     */      }
/*  180:     */      
/*  181: 181 */      BeanUtil.copyProperty(val, contract, 
/*  182: 182 */        new String[] { "yyyy-MM-dd", "yyyy-MM-dd" });
/*  183: 183 */      contract.setSaleman(req.getUser().getId());
/*  184: 184 */      contract.setReserved(req.getUser().getReserved());
/*  185: 185 */      contract.setIsAudit(false);
/*  186:     */      
/*  187: 187 */      if ((val.get("id") == null) || 
/*  188: 188 */        (val.get("id").toString().equals("")))
/*  189:     */      {
/*  190: 190 */        ContractNumberSequence cns = new ContractNumberSequence();
/*  191: 191 */        pmf.save(cns);
/*  192:     */        
/*  195: 195 */        pmf.save(contract);
/*  196: 196 */        fs.setId(null);
/*  197: 197 */        fs.setContractId(contract.getId());
/*  198: 198 */        fs.setUserid(req.getUser().getId());
/*  199: 199 */        fs.setStatus(Integer.valueOf(1));
/*  200: 200 */        pmf.save(fs);
/*  201: 201 */        OperationLogService.addLog(pmf, req.getUser(), contract, 1, 
/*  202: 202 */          "添加合同");
/*  203:     */      }
/*  204:     */      else
/*  205:     */      {
/*  206: 206 */        if (!contract.getNumber().equals(oldContract.getNumber()))
/*  207:     */        {
/*  208: 208 */          List<ManufactureOrder> produces = pmf
/*  209: 209 */            .list("from ManufactureOrder where Number='" + 
/*  210: 210 */            oldContract.getNumber() + "'");
/*  211: 211 */          for (ManufactureOrder produce : produces)
/*  212:     */          {
/*  213: 213 */            produce.setNumber(contract.getNumber());
/*  214: 214 */            pmf.update(produce);
/*  215: 215 */            ProduceService ps = new ProduceService();
/*  216: 216 */            ps.createProduceFile(pmf, produce, 
/*  217: 217 */              ps.getProductions(pmf, produce.getId()), 
/*  218: 218 */              req.getUser());
/*  219:     */          }
/*  220:     */        }
/*  221:     */        
/*  225: 225 */        OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/*  226: 226 */          "修改合同");
/*  227:     */      }
/*  228:     */      
/*  229: 229 */      deleteContractDetail(pmf, contract.getId(), req.getUser());
/*  230: 230 */      pmf.flush();
/*  231: 231 */      for (int i = 1; i < req.getData().size(); i++)
/*  232:     */      {
/*  233: 233 */        val = (Map)req.getData().get(i);
/*  234: 234 */        ContractDetail contractDetail = new ContractDetail();
/*  235: 235 */        BeanUtil.copyProperty(val, contractDetail);
/*  236: 236 */        contractDetail.setContractId(contract.getId());
/*  237:     */        
/*  238: 238 */        if ((contractDetail.getQty() != null) && 
/*  239: 239 */          (contractDetail.getUnit() != null))
/*  240:     */        {
/*  241: 241 */          BigDecimal bd = new BigDecimal(new Double(contractDetail
/*  242: 242 */            .getQty().intValue()).doubleValue() * 
/*  243: 243 */            contractDetail.getUnit().doubleValue());
/*  244: 244 */          bd = bd.setScale(2, 4);
/*  245: 245 */          contractDetail.setAmount(Double.valueOf(bd.doubleValue()));
/*  246:     */        }
/*  247:     */        
/*  248: 248 */        pmf.save(contractDetail);
/*  249: 249 */        list.add(contractDetail);
/*  250:     */      }
/*  251: 251 */      pmf.flush();
/*  252:     */      
/*  253: 253 */      val = (Map)req.getData().get(0);
/*  254: 254 */      if ((val.get("ccf") == null) || 
/*  255: 255 */        (val.get("ccf").toString().equals("none")))
/*  256:     */      {
/*  257: 257 */        deleteContractFile(pmf, contract.getId(), req.getUser());
/*  258: 258 */        Files file = null;
/*  259: 259 */        if (((Map)req.getData().get(0)).get("language") == null)
/*  260:     */        {
/*  261: 261 */          file = exportExcel(pmf, contract, list, true);
/*  264:     */        }
/*  265: 265 */        else if (((Map)req.getData().get(0)).get("language").equals("1"))
/*  266:     */        {
/*  267: 267 */          file = exportExcel(pmf, contract, list, false);
/*  268:     */        }
/*  269:     */        else
/*  270:     */        {
/*  271: 271 */          file = exportExcel(pmf, contract, list, true);
/*  272:     */        }
/*  273: 273 */        if (file == null)
/*  274:     */        {
/*  275: 275 */          resp.setType(0);
/*  276: 276 */          resp.setMessage("生成合同失败");
/*  277: 277 */          return resp;
/*  278:     */        }
/*  279: 279 */        pmf.save(file);
/*  280: 280 */        OperationLogService.addLog(pmf, req.getUser(), file, 1, 
/*  281: 281 */          "生成合同excel文件");
/*  282:     */        
/*  283: 283 */        contract.setCreatec(Integer.valueOf(1));
/*  284: 284 */        pmf.update(contract);
/*  287:     */      }
/*  288: 288 */      else if (val.get("ccf").toString().equals("constant"))
/*  289:     */      {
/*  290: 290 */        contract.setCreatec(Integer.valueOf(2));
/*  291: 291 */        pmf.update(contract);
/*  292:     */      }
/*  293:     */      else
/*  294:     */      {
/*  295: 295 */        Files f = (Files)pmf.get(Files.class, 
/*  296: 296 */          new Long(val.get("ccf").toString()));
/*  297: 297 */        if (f != null)
/*  298:     */        {
/*  299: 299 */          deleteContractFile(pmf, contract.getId(), req.getUser());
/*  300: 300 */          f.setRelatedObject(contract.getId());
/*  301: 301 */          f.setRelatedDescription("合同");
/*  302: 302 */          f.setType(new Integer("24"));
/*  303: 303 */          pmf.update(f);
/*  304: 304 */          contract.setCreatec(Integer.valueOf(2));
/*  305: 305 */          pmf.update(contract);
/*  306:     */        }
/*  307:     */      }
/*  308:     */      
/*  309: 309 */      pmf.flush();
/*  310: 310 */      calculationIntegrity(pmf, contract);
/*  311:     */    }
/*  312:     */    catch (LoginInTimeOutException e)
/*  313:     */    {
/*  314: 314 */      resp.setType(0);
/*  315: 315 */      resp.setMessage("登陆超时，请重新登陆！");
/*  316: 316 */      return resp;
/*  317:     */    }
/*  318:     */    catch (Exception e)
/*  319:     */    {
/*  320: 320 */      resp.setType(0);
/*  321: 321 */      resp.setMessage("保存失败，错误信息：" + e.getMessage());
/*  322: 322 */      return resp;
/*  323:     */    }
/*  324: 324 */    resp.setMessage(contract.getId().toString());
/*  325: 325 */    return resp;
/*  326:     */  }
/*  327:     */  
/*  328:     */  public void deleteContractDetail(PMF pmf, Long contractId, SysUser user)
/*  329:     */  {
/*  330: 330 */    StringBuffer hql = new StringBuffer();
/*  331: 331 */    hql.append("from ContractDetail where ContractId=").append(contractId);
/*  332: 332 */    List<ContractDetail> list = pmf.list(hql.toString());
/*  333: 333 */    for (ContractDetail cd : list)
/*  334:     */    {
/*  335:     */      try
/*  336:     */      {
/*  337: 337 */        pmf.remove(cd);
/*  338:     */      }
/*  339:     */      catch (Exception localException) {}
/*  340:     */    }
/*  341:     */  }
/*  342:     */  
/*  346:     */  private Files exportExcel(PMF pmf, com.vincesu.business.entity.Contract contract, List<ContractDetail> contractDetails, boolean isEnglish)
/*  347:     */    throws IOException, WriteException
/*  348:     */  {
/*  349: 349 */    Files file = new Files();
/*  350:     */    
/*  351: 351 */    File targetPath = new File(SystemUtil.getLocation() + 
/*  352: 352 */      "/resource/contract/");
/*  353: 353 */    if (!targetPath.exists())
/*  354: 354 */      targetPath.mkdirs();
/*  355: 355 */    File targetFilePath = new File(targetPath, contract.getId() + ".xls");
/*  356: 356 */    File targetPDFFilePath = new File(targetPath, contract.getId() + ".pdf");
/*  357: 357 */    if (targetFilePath.exists())
/*  358:     */    {
/*  359: 359 */      targetFilePath.delete();
/*  360:     */    }
/*  361: 361 */    OutputStream os = new FileOutputStream(targetFilePath);
/*  362: 362 */    WritableWorkbook workbook = Workbook.createWorkbook(os);
/*  363: 363 */    WritableSheet sheet = workbook.createSheet("contract", 0);
/*  364:     */    
/*  365:     */    try
/*  366:     */    {
/*  367: 367 */      setExcelData(pmf, sheet, contract, contractDetails, isEnglish);
/*  368:     */    }
/*  369:     */    catch (Exception e)
/*  370:     */    {
/*  371: 371 */      return null;
/*  372:     */    }
/*  373:     */    finally
/*  374:     */    {
/*  375: 375 */      if (workbook != null)
/*  376:     */      {
/*  377: 377 */        workbook.write();
/*  378: 378 */        workbook.close();
/*  379:     */      }
/*  380: 380 */      if (os != null)
/*  381:     */      {
/*  382: 382 */        os.close();
/*  383:     */      }
/*  384:     */    }
/*  385:     */    try
/*  386:     */    {
/*  387: 387 */      OfficeUtil.office2PDF(targetFilePath, targetPDFFilePath);
/*  388:     */    }
/*  389:     */    catch (Exception e)
/*  390:     */    {
/*  391: 391 */      return null;
/*  392:     */    }
/*  393:     */    
/*  394: 394 */    file.setPath(targetFilePath.getAbsolutePath());
/*  395: 395 */    file.setName("合同-" + contract.getNumber() + ".xls");
/*  396: 396 */    file.setType(new Integer("24"));
/*  397: 397 */    file.setRelatedObject(contract.getId());
/*  398: 398 */    file.setPdfpath(targetPDFFilePath.getAbsolutePath());
/*  399: 399 */    file.setRelatedDescription("合同");
/*  400:     */    
/*  401: 401 */    return file;
/*  402:     */  }
/*  403:     */  
/*  405:     */  private void setExcelData(PMF pmf, WritableSheet sheet, com.vincesu.business.entity.Contract contract, List<ContractDetail> contractDetails, boolean isEnglish)
/*  406:     */    throws Exception
/*  407:     */  {
/*  408: 408 */    setExcelFormat(sheet);
/*  409: 409 */    setHeaderText(pmf, sheet, contract, isEnglish);
/*  410: 410 */    setTableHeader(sheet, contract, isEnglish);
/*  411: 411 */    setTableData(pmf, sheet, contract, contractDetails, isEnglish);
/*  412: 412 */    setFooter(sheet, contract, contractDetails, isEnglish);
/*  413: 413 */    setPic(pmf, sheet, contract, contractDetails, isEnglish);
/*  414:     */  }
/*  415:     */  
/*  417:     */  private Map<Long, Files> getProductFile(PMF pmf, List<ContractDetail> contractDetails)
/*  418:     */  {
/*  419: 419 */    int count = 0;
/*  420: 420 */    Map<Long, Files> result = new HashMap();
/*  421:     */    
/*  422: 422 */    if (contractDetails.size() == 0)
/*  423:     */    {
/*  424: 424 */      return result;
/*  425:     */    }
/*  426: 426 */    StringBuffer hql = new StringBuffer();
/*  427: 427 */    hql.append("from Files where RelatedObject in (");
/*  428: 428 */    for (ContractDetail cd : contractDetails)
/*  429:     */    {
/*  430: 430 */      if (cd.getProductId() != null)
/*  431:     */      {
/*  432: 432 */        hql.append(cd.getProductId()).append(",");
/*  433: 433 */        count++;
/*  434:     */      }
/*  435:     */    }
/*  436: 436 */    if (count == 0)
/*  437: 437 */      return result;
/*  438: 438 */    hql.deleteCharAt(hql.length() - 1);
/*  439: 439 */    hql.append(") and Type=").append("2");
/*  440:     */    
/*  441: 441 */    List<Files> files = pmf.list(hql.toString());
/*  442: 442 */    for (Files file : files)
/*  443:     */    {
/*  444: 444 */      result.put(file.getRelatedObject(), file);
/*  445:     */    }
/*  446: 446 */    return result;
/*  447:     */  }
/*  448:     */  
/*  450:     */  private void setPic(PMF pmf, WritableSheet sheet, com.vincesu.business.entity.Contract contract, List<ContractDetail> contractDetails, boolean isEnglish)
/*  451:     */    throws WriteException, IOException
/*  452:     */  {
/*  453: 453 */    String picture = null;
/*  454: 454 */    WritableFont font = null;
/*  455: 455 */    WritableCellFormat cFormat = null;
/*  456: 456 */    BufferedImage image = null;
/*  457: 457 */    Label label = null;
/*  458: 458 */    Map<Long, Files> pics = getProductFile(pmf, contractDetails);
/*  459: 459 */    Map<Long, Product> products = getProductList(pmf, contractDetails);
/*  460: 460 */    WritableImage ri = null;
/*  461: 461 */    int baseRowsCount = 19;
/*  462: 462 */    WritableFont.FontName fontName = WritableFont.createFont("Calibri");
/*  463:     */    
/*  464: 464 */    if (isEnglish)
/*  465:     */    {
/*  466: 466 */      picture = "Picture:";
/*  467:     */    }
/*  468:     */    else
/*  469:     */    {
/*  470: 470 */      picture = "图片:";
/*  471:     */    }
/*  472:     */    
/*  473: 473 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  474: 474 */    cFormat = new WritableCellFormat(font);
/*  475: 475 */    cFormat.setAlignment(Alignment.LEFT);
/*  476: 476 */    cFormat.setVerticalAlignment(VerticalAlignment.TOP);
/*  477:     */    
/*  478: 478 */    label = new Label(1, baseRowsCount + contractDetails.size(), picture, 
/*  479: 479 */      cFormat);
/*  480: 480 */    sheet.addCell(label);
/*  481:     */    
/*  482: 482 */    for (int i = 0; i < contractDetails.size(); i++)
/*  483:     */    {
/*  484: 484 */      sheet.setRowView(baseRowsCount + contractDetails.size() + (
/*  485: 485 */        i - i % 2), 2730);
/*  486: 486 */      sheet.setRowView(baseRowsCount + contractDetails.size() + (
/*  487: 487 */        i - i % 2) + 1, 400);
/*  488:     */      
/*  489: 489 */      if (pics.get(((ContractDetail)contractDetails.get(i))
/*  490: 490 */        .getProductId()) != null) {
/*  491: 491 */        if (((Files)pics.get(
/*  492: 492 */          ((ContractDetail)contractDetails.get(i)).getProductId())).getPngpath() != null)
/*  493:     */        {
/*  494: 494 */          File f = new File(
/*  495: 495 */            ((Files)pics.get(
/*  496: 496 */            ((ContractDetail)contractDetails.get(i)).getProductId())).getPngpath());
/*  497: 497 */          if (f.exists())
/*  498:     */          {
/*  499: 499 */            image = ImageIO.read(f);
/*  500: 500 */            if (i % 2 == 0)
/*  501:     */            {
/*  502: 502 */              ri = new WritableImage(2 + i % 2 * 2, baseRowsCount + 
/*  503: 503 */                contractDetails.size() + (i - i % 2), 
/*  504: 504 */                TextUtil.countWidthPercentage(image.getHeight() / 
/*  505: 505 */                image.getWidth(), 
/*  506: 506 */                new int[] { 2730 }, 1.0D, 
/*  507: 507 */                new int[] { 26, 8 }), 1.0D, f);
/*  508:     */            }
/*  509:     */            else
/*  510: 510 */              ri = new WritableImage(2 + i % 2 * 2, baseRowsCount + 
/*  511: 511 */                contractDetails.size() + (i - i % 2), 
/*  512: 512 */                TextUtil.countWidthPercentage(image.getHeight() / 
/*  513: 513 */                image.getWidth(), 
/*  514: 514 */                new int[] { 2730 }, 1.0D, 
/*  515: 515 */                new int[] { 21, 10 }), 1.0D, f);
/*  516: 516 */            sheet.addImage(ri);
/*  517:     */          }
/*  518:     */        }
/*  519:     */      }
/*  520: 520 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  521: 521 */      cFormat = new WritableCellFormat(font);
/*  522: 522 */      cFormat.setAlignment(Alignment.CENTRE);
/*  523: 523 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  524:     */      
/*  525: 525 */      if (products.get(((ContractDetail)contractDetails.get(i))
/*  526: 526 */        .getProductId()) != null)
/*  527:     */      {
/*  528: 528 */        label = new Label(2 + i % 2 * 2, baseRowsCount + 
/*  529: 529 */          contractDetails.size() + (i - i % 2) + 1, 
/*  530: 530 */          ((ContractDetail)contractDetails.get(i)).getNumber(), 
/*  531: 531 */          cFormat);
/*  532: 532 */        sheet.addCell(label);
/*  533:     */      }
/*  534:     */    }
/*  535:     */  }
/*  536:     */  
/*  538:     */  private void setFooter(WritableSheet sheet, com.vincesu.business.entity.Contract contract, List<ContractDetail> contractDetails, boolean isEnglish)
/*  539:     */    throws RowsExceededException, WriteException
/*  540:     */  {
/*  541: 541 */    int baseRowsCount = 15;
/*  542: 542 */    int baseRowsCount2 = 18;
/*  543: 543 */    WritableFont font = null;
/*  544: 544 */    WritableCellFormat cFormat = null;
/*  545: 545 */    Label label = null;
/*  546: 546 */    String payment = null;
/*  547: 547 */    String note = null;
/*  548: 548 */    WritableFont.FontName fontName = WritableFont.createFont("Calibri");
/*  549:     */    
/*  550: 550 */    sheet.mergeCells(2, baseRowsCount + contractDetails.size(), 7, 
/*  551: 551 */      baseRowsCount + contractDetails.size());
/*  552: 552 */    sheet.mergeCells(2, baseRowsCount + contractDetails.size() + 2, 7, 
/*  553: 553 */      baseRowsCount + contractDetails.size() + 2);
/*  554: 554 */    if (isEnglish)
/*  555:     */    {
/*  556: 556 */      payment = "Payment:";
/*  557: 557 */      note = "Note:";
/*  558:     */    }
/*  559:     */    else
/*  560:     */    {
/*  561: 561 */      payment = "付款方式:";
/*  562: 562 */      note = "备注:";
/*  563:     */    }
/*  564:     */    
/*  565: 565 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  566: 566 */    cFormat = new WritableCellFormat(font);
/*  567: 567 */    cFormat.setAlignment(Alignment.LEFT);
/*  568: 568 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  569:     */    
/*  570: 570 */    label = new Label(1, baseRowsCount + contractDetails.size(), payment, 
/*  571: 571 */      cFormat);
/*  572:     */    
/*  573: 573 */    sheet.addCell(label);
/*  574:     */    
/*  575: 575 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  576: 576 */    cFormat = new WritableCellFormat(font);
/*  577: 577 */    cFormat.setAlignment(Alignment.LEFT);
/*  578: 578 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  579:     */    
/*  580: 580 */    label = new Label(2, baseRowsCount + contractDetails.size(), 
/*  581: 581 */      contract.getLeadTime(), cFormat);
/*  582:     */    
/*  583: 583 */    sheet.addCell(label);
/*  584:     */    
/*  585: 585 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  586: 586 */    cFormat = new WritableCellFormat(font);
/*  587: 587 */    cFormat.setAlignment(Alignment.LEFT);
/*  588: 588 */    cFormat.setVerticalAlignment(VerticalAlignment.TOP);
/*  589:     */    
/*  590: 590 */    label = new Label(1, baseRowsCount + contractDetails.size() + 2, note, 
/*  591: 591 */      cFormat);
/*  592: 592 */    sheet.addCell(label);
/*  593:     */    
/*  594: 594 */    int c = TextUtil.countRowHeight(contract.getRemark(), 91, isEnglish);
/*  595: 595 */    sheet.setRowView(baseRowsCount + contractDetails.size() + 2, c);
/*  596: 596 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  597: 597 */    cFormat = new WritableCellFormat(font);
/*  598: 598 */    cFormat.setWrap(true);
/*  599: 599 */    cFormat.setAlignment(Alignment.LEFT);
/*  600: 600 */    cFormat.setVerticalAlignment(VerticalAlignment.TOP);
/*  601:     */    
/*  602: 602 */    label = new Label(2, baseRowsCount + contractDetails.size() + 2, 
/*  603: 603 */      contract.getRemark(), cFormat);
/*  604: 604 */    sheet.addCell(label);
/*  605:     */  }
/*  606:     */  
/*  608:     */  private void setTableData(PMF pmf, WritableSheet sheet, com.vincesu.business.entity.Contract contract, List<ContractDetail> contractDetails, boolean isEnglish)
/*  609:     */    throws WriteException
/*  610:     */  {
/*  611: 611 */    WritableFont font = null;
/*  612: 612 */    WritableCellFormat cFormat = null;
/*  613: 613 */    Label label = null;
/*  614:     */    
/*  615: 615 */    Map<Long, Product> products = getProductList(pmf, contractDetails);
/*  616:     */    
/*  617: 617 */    WritableFont.FontName fontName = WritableFont.createFont("Calibri");
/*  618:     */    
/*  619: 619 */    String currencySymbol = contract.getCurrencySymbol();
/*  620:     */    
/*  621: 621 */    String total = null;
/*  622: 622 */    if (isEnglish)
/*  623:     */    {
/*  624: 624 */      total = "total";
/*  625:     */    }
/*  626:     */    else
/*  627:     */    {
/*  628: 628 */      total = "总计";
/*  629:     */    }
/*  630:     */    
/*  631: 631 */    int sumQty = 0;
/*  632: 632 */    double sumAmount = 0.0D;
/*  633:     */    
/*  634: 634 */    for (int i = 0; i < contractDetails.size(); i++)
/*  635:     */    {
/*  636: 636 */      sheet.setRowView(13 + i, 600);
/*  637:     */      
/*  638: 638 */      sheet.mergeCells(2, 13 + i, 4, 13 + i);
/*  639:     */      
/*  640: 640 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  641: 641 */      cFormat = new WritableCellFormat(font);
/*  642: 642 */      cFormat.setAlignment(Alignment.CENTRE);
/*  643: 643 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  644: 644 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  645: 645 */      cFormat.setWrap(true);
/*  646: 646 */      if (((ContractDetail)contractDetails.get(i)).getNumber() != null)
/*  647:     */      {
/*  648: 648 */        label = new Label(1, 13 + i, 
/*  649: 649 */          ((ContractDetail)contractDetails.get(i)).getNumber(), 
/*  650: 650 */          cFormat);
/*  651:     */      }
/*  652:     */      else
/*  653: 653 */        label = new Label(1, 13 + i, "", cFormat);
/*  654: 654 */      sheet.addCell(label);
/*  655: 655 */      int c = 0;
/*  656: 656 */      int c1 = 0;
/*  657: 657 */      if (((ContractDetail)contractDetails.get(i)).getDescription() != null)
/*  658: 658 */        c = TextUtil.countRowHeight(
/*  659: 659 */          ((ContractDetail)contractDetails.get(i)).getDescription(), 55, isEnglish);
/*  660: 660 */      if (((ContractDetail)contractDetails.get(i)).getNumber() != null)
/*  661: 661 */        c1 = TextUtil.countRowHeight(
/*  662: 662 */          ((ContractDetail)contractDetails.get(i)).getNumber(), 
/*  663: 663 */          12, isEnglish);
/*  664: 664 */      if (c > c1)
/*  665:     */      {
/*  666: 666 */        sheet.setRowView(13 + i, c);
/*  667:     */      }
/*  668:     */      else
/*  669: 669 */        sheet.setRowView(13 + i, c1);
/*  670: 670 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  671: 671 */      cFormat = new WritableCellFormat(font);
/*  672: 672 */      cFormat.setWrap(true);
/*  673: 673 */      cFormat.setAlignment(Alignment.CENTRE);
/*  674: 674 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  675: 675 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  676: 676 */      label = new Label(2, 13 + i, 
/*  677: 677 */        ((ContractDetail)contractDetails.get(i)).getDescription(), 
/*  678: 678 */        cFormat);
/*  679: 679 */      sheet.addCell(label);
/*  680:     */      
/*  681: 681 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  682: 682 */      cFormat = new WritableCellFormat(font);
/*  683: 683 */      cFormat.setAlignment(Alignment.CENTRE);
/*  684: 684 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  685: 685 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  686: 686 */      if (((ContractDetail)contractDetails.get(i)).getQty() != null)
/*  687:     */      {
/*  688: 688 */        label = new Label(5, 13 + i, 
/*  689: 689 */          ((ContractDetail)contractDetails.get(i)).getQty()
/*  690: 690 */          .toString(), cFormat);
/*  691:     */      }
/*  692:     */      else
/*  693: 693 */        label = new Label(5, 13 + i, "", cFormat);
/*  694: 694 */      sheet.addCell(label);
/*  695:     */      
/*  696: 696 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  697: 697 */      cFormat = new WritableCellFormat(font);
/*  698: 698 */      cFormat.setAlignment(Alignment.CENTRE);
/*  699: 699 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  700: 700 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  701: 701 */      if (((ContractDetail)contractDetails.get(i)).getUnit() != null)
/*  702:     */      {
/*  703: 703 */        label = new Label(6, 13 + i, currencySymbol + 
/*  704: 704 */          ((ContractDetail)contractDetails.get(i)).getUnit(), 
/*  705: 705 */          cFormat);
/*  706:     */      }
/*  707:     */      else
/*  708: 708 */        label = new Label(6, 13 + i, currencySymbol + "0", cFormat);
/*  709: 709 */      sheet.addCell(label);
/*  710:     */      
/*  711: 711 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  712: 712 */      cFormat = new WritableCellFormat(font);
/*  713: 713 */      cFormat.setAlignment(Alignment.CENTRE);
/*  714: 714 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  715: 715 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  716: 716 */      if (((ContractDetail)contractDetails.get(i)).getAmount() != null)
/*  717:     */      {
/*  718: 718 */        label = new Label(7, 13 + i, 
/*  719: 719 */          currencySymbol + 
/*  720: 720 */          ((ContractDetail)contractDetails.get(i))
/*  721: 721 */          .getAmount(), cFormat);
/*  722:     */      }
/*  723:     */      else
/*  724: 724 */        label = new Label(7, 13 + i, currencySymbol + "0", cFormat);
/*  725: 725 */      sheet.addCell(label);
/*  726:     */      
/*  727: 727 */      if (((ContractDetail)contractDetails.get(i)).getQty() != null)
/*  728:     */      {
/*  729: 729 */        sumQty = sumQty + ((ContractDetail)contractDetails.get(i)).getQty().intValue(); }
/*  730: 730 */      if (((ContractDetail)contractDetails.get(i)).getAmount() != null)
/*  731:     */      {
/*  733: 733 */        sumAmount = sumAmount + ((ContractDetail)contractDetails.get(i)).getAmount().doubleValue();
/*  734:     */      }
/*  735:     */    }
/*  736:     */    
/*  737: 737 */    sheet.mergeCells(1, 13 + contractDetails.size(), 4, 
/*  738: 738 */      13 + contractDetails.size());
/*  739:     */    
/*  740: 740 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  741: 741 */    cFormat = new WritableCellFormat(font);
/*  742: 742 */    cFormat.setAlignment(Alignment.CENTRE);
/*  743: 743 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  744: 744 */    cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  745: 745 */    label = new Label(1, 13 + contractDetails.size(), total, cFormat);
/*  746: 746 */    sheet.addCell(label);
/*  747:     */    
/*  748: 748 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  749: 749 */    cFormat = new WritableCellFormat(font);
/*  750: 750 */    cFormat.setAlignment(Alignment.CENTRE);
/*  751: 751 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  752: 752 */    cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  753: 753 */    label = new Label(5, 13 + contractDetails.size(), 
/*  754: 754 */      Integer.toString(sumQty), cFormat);
/*  755: 755 */    sheet.addCell(label);
/*  756:     */    
/*  757: 757 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  758: 758 */    cFormat = new WritableCellFormat(font);
/*  759: 759 */    cFormat.setAlignment(Alignment.CENTRE);
/*  760: 760 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  761: 761 */    cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  762: 762 */    label = new Label(6, 13 + contractDetails.size(), "", cFormat);
/*  763: 763 */    sheet.addCell(label);
/*  764:     */    
/*  765: 765 */    BigDecimal bd = new BigDecimal(sumAmount);
/*  766: 766 */    bd = bd.setScale(2, 6);
/*  767:     */    
/*  768: 768 */    font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  769: 769 */    cFormat = new WritableCellFormat(font);
/*  770: 770 */    cFormat.setAlignment(Alignment.CENTRE);
/*  771: 771 */    cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  772: 772 */    cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  773: 773 */    label = new Label(7, 13 + contractDetails.size(), currencySymbol + 
/*  774: 774 */      bd.doubleValue(), cFormat);
/*  775: 775 */    sheet.addCell(label);
/*  776:     */  }
/*  777:     */  
/*  779:     */  private Map<Long, Product> getProductList(PMF pmf, List<ContractDetail> contractDetails)
/*  780:     */  {
/*  781: 781 */    int count = 0;
/*  782: 782 */    Map<Long, Product> result = new HashMap();
/*  783:     */    
/*  784: 784 */    StringBuffer hql = new StringBuffer();
/*  785: 785 */    hql.append("from Product where Id in (");
/*  786: 786 */    for (ContractDetail cd : contractDetails)
/*  787:     */    {
/*  788: 788 */      if (cd.getProductId() != null)
/*  789:     */      {
/*  790: 790 */        hql.append(cd.getProductId()).append(",");
/*  791: 791 */        count++;
/*  792:     */      }
/*  793:     */    }
/*  794: 794 */    if (count == 0)
/*  795: 795 */      return result;
/*  796: 796 */    hql.deleteCharAt(hql.length() - 1);
/*  797: 797 */    hql.append(")");
/*  798:     */    
/*  799: 799 */    List<Product> products = pmf.list(hql.toString());
/*  800: 800 */    for (Product p : products)
/*  801:     */    {
/*  802: 802 */      result.put(p.getId(), p);
/*  803:     */    }
/*  804: 804 */    return result;
/*  805:     */  }
/*  806:     */  
/*  807:     */  private void setTableHeader(WritableSheet sheet, com.vincesu.business.entity.Contract contract, boolean isEnglish)
/*  808:     */    throws WriteException
/*  809:     */  {
/*  810: 810 */    WritableFont font = null;
/*  811: 811 */    WritableCellFormat cFormat = null;
/*  812: 812 */    Label label = null;
/*  813:     */    
/*  814: 814 */    String[] text = null;
/*  815: 815 */    int[] indexs = 
/*  816: 816 */      { 1, 2, 5, 6, 7 };
/*  817:     */    
/*  818: 818 */    WritableFont.FontName fontName = null;
/*  819:     */    
/*  820: 820 */    sheet.mergeCells(2, 12, 4, 12);
/*  821:     */    
/*  822: 822 */    if (isEnglish)
/*  823:     */    {
/*  824: 824 */      text = 
/*  825: 825 */        new String[] { "Item #", "Decription", "QTY/pc", "Unit $", "Amount" };
/*  826: 826 */      fontName = WritableFont.createFont("Calibri");
/*  827:     */    }
/*  828:     */    else
/*  829:     */    {
/*  830: 830 */      text = 
/*  831: 831 */        new String[] { "编号", "描述", "数量/pc", "单价￥", "总价" };
/*  832: 832 */      fontName = WritableFont.createFont("宋体");
/*  833:     */    }
/*  834:     */    
/*  835: 835 */    for (int i = 0; i < text.length; i++)
/*  836:     */    {
/*  837: 837 */      font = new WritableFont(fontName, 12, WritableFont.NO_BOLD, false);
/*  838: 838 */      cFormat = new WritableCellFormat(font);
/*  839: 839 */      cFormat.setAlignment(Alignment.CENTRE);
/*  840: 840 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  841: 841 */      cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  842: 842 */      label = new Label(indexs[i], 12, text[i], cFormat);
/*  843: 843 */      sheet.addCell(label);
/*  844:     */    }
/*  845:     */    
/*  846: 846 */    sheet.setRowView(12, 450);
/*  847:     */  }
/*  848:     */  
/*  849:     */  private void setHeaderText(PMF pmf, WritableSheet sheet, com.vincesu.business.entity.Contract contract, boolean isEnglish)
/*  850:     */    throws Exception
/*  851:     */  {
/*  852: 852 */    WritableFont font = null;
/*  853: 853 */    WritableCellFormat cFormat = null;
/*  854: 854 */    Label label = null;
/*  855: 855 */    String[] text = null;
/*  856: 856 */    int[] x = 
/*  857: 857 */      { 1, 1, 3, 6, 6, 6, 6, 1, 7, 7, 7, 7, 2, 2 };
/*  858: 858 */    int[] y = 
/*  859: 859 */      { 1, 2, 3, 1, 2, 4, 5, 8, 1, 2, 4, 5, 8, 9 };
/*  860: 860 */    int[] fontsizes = 
/*  861: 861 */      { 14, 10, 14, 12, 12, 11, 11, 12, 12, 12, 11, 11, 12, 12 };
/*  862: 862 */    WritableFont.FontName fontnames = null;
/*  863:     */    
/*  864: 864 */    Client client = (Client)pmf.get(Client.class, contract.getBuyerId());
/*  865: 865 */    if (client == null)
/*  866:     */    {
/*  867: 867 */      throw new Exception("查无此客户信息");
/*  868:     */    }
/*  869: 869 */    if (isEnglish)
/*  870:     */    {
/*  871: 871 */      text = 
/*  872: 872 */        new String[] {
/*  873: 873 */        "Wenzhou zhonghao crafts & gifts co.,ltd", 
/*  874: 874 */        "No.5 Zone,Demonstration Industrial Park\nLonggang Town, Wenzhou City, China\nTel:  " + 
/*  875: 875 */        contract.getTel() + 
/*  876: 876 */        "\nFax:  " + 
/*  877: 877 */        contract.getFax() + "\n" + contract.getEmail(), 
/*  878: 878 */        "PROFORMA  INVOICE", "No.:", "Date:", "Shipping term", 
/*  879: 879 */        "Delivery time", "Consignee:", contract.getNumber(), 
/*  880: 880 */        TimeUtil.toString(contract.getOrderDate(), "dd-MM-yyyy"), 
/*  881: 881 */        contract.getShippingTrem(), contract.getDeliveryDate(), 
/*  882: 882 */        client.getUnit(), client.getAddress() };
/*  883:     */      
/*  884: 884 */      fontnames = WritableFont.createFont("Calibri");
/*  885:     */    }
/*  886:     */    else
/*  887:     */    {
/*  888: 888 */      text = 
/*  889: 889 */        new String[] {
/*  890: 890 */        "温州中昊工艺品有限公司", 
/*  891: 891 */        "温州市苍南县龙港镇示范工业园区五区七栋\nTel:  " + contract.getTel() + 
/*  892: 892 */        "\nFax:  " + contract.getFax() + "\n" + 
/*  893: 893 */        contract.getEmail(), "形式发票", "合同号:", "日期:", 
/*  894: 894 */        "运输方式", "生产时间", "收货人: ", contract.getNumber(), 
/*  895: 895 */        TimeUtil.toString(contract.getOrderDate(), "yyyy-MM-dd"), 
/*  896: 896 */        contract.getShippingTrem(), contract.getDeliveryDate(), 
/*  897: 897 */        client.getUnit(), client.getAddress() };
/*  898:     */      
/*  899: 899 */      fontnames = WritableFont.createFont("宋体");
/*  900:     */    }
/*  901:     */    
/*  902: 902 */    int c = contract.getNumber().length() % 10 == 0 ? contract.getNumber()
/*  903: 903 */      .length() / 10 : contract.getNumber().length() / 10 + 1;
/*  904: 904 */    if (c == 0)
/*  905: 905 */      c = 1;
/*  906: 906 */    sheet.setRowView(1, (27 + (c - 1) * 20) * 15);
/*  907: 907 */    if (isEnglish)
/*  908:     */    {
/*  909: 909 */      c = contract.getShippingTrem().length() % 10 == 0 ? contract
/*  910: 910 */        .getShippingTrem().length() / 10 : contract
/*  911: 911 */        .getShippingTrem().length() / 10 + 1;
/*  912: 912 */      if (c == 0) {
/*  913: 913 */        c = 1;
/*  914:     */      }
/*  915:     */    }
/*  916:     */    else {
/*  917: 917 */      c = contract.getShippingTrem().length() % 5 == 0 ? contract
/*  918: 918 */        .getShippingTrem().length() / 5 : contract
/*  919: 919 */        .getShippingTrem().length() / 5 + 1;
/*  920: 920 */      if (c == 0)
/*  921: 921 */        c = 1;
/*  922:     */    }
/*  923: 923 */    sheet.setRowView(4, (27 + (c - 1) * 20) * 15);
/*  924:     */    
/*  925: 925 */    for (int i = 0; i < text.length; i++)
/*  926:     */    {
/*  927: 927 */      if (i == 0)
/*  928:     */      {
/*  929: 929 */        font = new WritableFont(fontnames, fontsizes[i], 
/*  930: 930 */          WritableFont.BOLD, false);
/*  931:     */      }
/*  932:     */      else
/*  933:     */      {
/*  934: 934 */        font = new WritableFont(fontnames, fontsizes[i], 
/*  935: 935 */          WritableFont.NO_BOLD, false);
/*  936:     */      }
/*  937: 937 */      if (i == text.length - 1)
/*  938:     */      {
/*  939: 939 */        c = TextUtil.countRowHeight(text[i], 91, isEnglish);
/*  940: 940 */        sheet.setRowView(9, c);
/*  941:     */      }
/*  942:     */      
/*  943: 943 */      cFormat = new WritableCellFormat(font);
/*  944:     */      
/*  945: 945 */      if (i == 8)
/*  946:     */      {
/*  947: 947 */        cFormat.setWrap(true);
/*  948:     */      }
/*  949:     */      
/*  950: 950 */      if (i == text.length - 4)
/*  951:     */      {
/*  952: 952 */        cFormat.setWrap(true);
/*  953:     */      }
/*  954:     */      
/*  955: 955 */      if ((i == 0) || (i == 1) || (i == 7) || (i == 12) || (i == 13))
/*  956:     */      {
/*  957: 957 */        cFormat.setAlignment(Alignment.LEFT);
/*  958:     */      }
/*  959:     */      else
/*  960:     */      {
/*  961: 961 */        cFormat.setAlignment(Alignment.CENTRE);
/*  962:     */      }
/*  963: 963 */      if ((i == 5) || (i == 6) || (i == 10) || (i == 11) || (i == 2))
/*  964:     */      {
/*  965: 965 */        cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
/*  967:     */      }
/*  968: 968 */      else if (i == 7)
/*  969:     */      {
/*  970: 970 */        cFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
/*  971: 971 */        cFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
/*  973:     */      }
/*  974: 974 */      else if (i == 12)
/*  975:     */      {
/*  976: 976 */        cFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
/*  977: 977 */        cFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
/*  979:     */      }
/*  980: 980 */      else if (i == 13)
/*  981:     */      {
/*  982: 982 */        cFormat.setBorder(Border.RIGHT, 
/*  983: 983 */          BorderLineStyle.THIN);
/*  984: 984 */        cFormat.setBorder(Border.BOTTOM, 
/*  985: 985 */          BorderLineStyle.THIN);
/*  986: 986 */        cFormat.setWrap(true);
/*  987:     */      }
/*  988:     */      
/*  989: 989 */      cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
/*  990: 990 */      label = new Label(x[i], y[i], text[i], cFormat);
/*  991: 991 */      sheet.addCell(label);
/*  992:     */    }
/*  993:     */    
/*  994: 994 */    cFormat = new WritableCellFormat(font);
/*  995: 995 */    cFormat.setWrap(true);
/*  996: 996 */    cFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
/*  997: 997 */    cFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
/*  998: 998 */    label = new Label(1, 9, "", cFormat);
/*  999: 999 */    sheet.addCell(label);
/* 1000:     */  }
/* 1001:     */  
/* 1002:     */  private void setExcelFormat(WritableSheet sheet) throws WriteException
/* 1003:     */  {
/* 1004:1004 */    sheet.getSettings().setBottomMargin(0.2D);
/* 1005:1005 */    sheet.getSettings().setTopMargin(0.2D);
/* 1006:1006 */    sheet.getSettings().setLeftMargin(0.1D);
/* 1007:1007 */    sheet.getSettings().setRightMargin(0.0D);
/* 1008:     */    
/* 1009:1009 */    sheet.setColumnView(0, 1);
/* 1010:1010 */    sheet.setColumnView(1, 12);
/* 1011:1011 */    sheet.setColumnView(2, 26);
/* 1012:1012 */    sheet.setColumnView(3, 8);
/* 1013:1013 */    sheet.setColumnView(4, 21);
/* 1014:1014 */    sheet.setColumnView(5, 10);
/* 1015:1015 */    sheet.setColumnView(6, 13);
/* 1016:1016 */    sheet.setColumnView(7, 13);
/* 1017:     */    
/* 1018:1018 */    sheet.setRowView(0, 315);
/* 1019:1019 */    sheet.setRowView(1, 405);
/* 1020:1020 */    sheet.setRowView(2, 330);
/* 1021:1021 */    sheet.setRowView(3, 120);
/* 1022:1022 */    sheet.setRowView(4, 315);
/* 1023:1023 */    sheet.setRowView(5, 315);
/* 1024:1024 */    sheet.setRowView(6, 300);
/* 1025:1025 */    sheet.setRowView(7, 240);
/* 1026:1026 */    sheet.setRowView(8, 510);
/* 1027:1027 */    sheet.setRowView(9, 510);
/* 1028:1028 */    sheet.setRowView(10, 180);
/* 1029:1029 */    sheet.setRowView(11, 180);
/* 1030:     */    
/* 1031:1031 */    sheet.mergeCells(1, 1, 4, 1);
/* 1032:1032 */    sheet.mergeCells(1, 2, 2, 6);
/* 1033:1033 */    sheet.mergeCells(3, 3, 4, 6);
/* 1034:1034 */    sheet.mergeCells(2, 8, 7, 8);
/* 1035:1035 */    sheet.mergeCells(2, 9, 7, 9);
/* 1036:     */  }
/* 1037:     */  
/* 1038:     */  public ResponseEntity queryContractDetail(RequestEntity req)
/* 1039:     */    throws Exception
/* 1040:     */  {
/* 1041:1041 */    StringBuffer hql = new StringBuffer();
/* 1042:1042 */    hql.append("select * from VW_CONTRACT_PRODUCT_FILES where contract_id=:id ");
/* 1043:1043 */    return QueryUtil.queryBySQL(req, hql.toString(), 
/* 1044:     */    
/* 1045:1045 */      new String[] { "id", "contractId", "productId", "description", "qty", 
/* 1046:1046 */      "unit", "amount", "remark", "reserved", "number", 
/* 1047:1047 */      "fileid", "webpath" }, null, null);
/* 1048:     */  }
/* 1049:     */  
/* 1050:     */  public int checkContractStatus(com.vincesu.business.entity.Contract contract)
/* 1051:     */  {
/* 1052:1052 */    if (contract.getReserved().startsWith("locked"))
/* 1053:1053 */      return 2;
/* 1054:1054 */    if (contract.getReserved().startsWith("lock"))
/* 1055:1055 */      return 1;
/* 1056:1056 */    if (contract.getReserved().startsWith("finish"))
/* 1057:     */    {
/* 1058:1058 */      return 3;
/* 1059:     */    }
/* 1060:1060 */    return 0;
/* 1061:     */  }
/* 1062:     */  
/* 1063:     */  public String getContractPermission(String v)
/* 1064:     */  {
/* 1065:1065 */    if (v.startsWith("locked"))
/* 1066:1066 */      return v.substring(6);
/* 1067:1067 */    if (v.startsWith("lock"))
/* 1068:1068 */      return v.substring(4);
/* 1069:1069 */    if (v.startsWith("finish"))
/* 1070:     */    {
/* 1071:1071 */      return v.substring(6);
/* 1072:     */    }
/* 1073:1073 */    return v;
/* 1074:     */  }
/* 1075:     */  
/* 1076:     */  public ResponseEntity remove(RequestEntity req)
/* 1077:     */  {
/* 1078:1078 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1079:1079 */    ResponseEntity resp = new ResponseEntity();
/* 1080:     */    try
/* 1081:     */    {
/* 1082:1082 */      Map val = (Map)req.getData().get(0);
/* 1083:1083 */      com.vincesu.business.entity.Contract contract = new com.vincesu.business.entity.Contract();
/* 1084:1084 */      contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1085:1085 */        .get("id").toString()));
/* 1086:1086 */      if (contract == null)
/* 1087:     */      {
/* 1088:1088 */        resp.setType(0);
/* 1089:1089 */        resp.setMessage("删除失败，没有此合同编号");
/* 1090:1090 */        return resp;
/* 1091:     */      }
/* 1092:1092 */      if (checkContractStatus(contract) > 0)
/* 1093:     */      {
/* 1094:1094 */        resp.setType(0);
/* 1095:1095 */        resp.setMessage("删除失败，此合同已审核或完成，不能删除");
/* 1096:1096 */        return resp;
/* 1097:     */      }
/* 1098:     */      
/* 1099:1099 */      if (!req.getUser().getReserved().equals("0"))
/* 1100:     */      {
/* 1101:1101 */        if (!ContractModel.removeLockStatus(contract.getReserved()).startsWith(req.getUser().getReserved()))
/* 1102:     */        {
/* 1103:1103 */          resp.setType(0);
/* 1104:1104 */          resp.setMessage("删除失败，您无权限删除合同");
/* 1105:1105 */          return resp;
/* 1106:     */        }
/* 1107:     */      }
/* 1108:1108 */      pmf.remove(contract);
/* 1109:1109 */      OperationLogService.addLog(pmf, req.getUser(), contract, 2, "删除合同");
/* 1110:1110 */      deleteContractDetail(pmf, contract.getId(), req.getUser());
/* 1111:1111 */      CommonService.delete(pmf, contract.getId(), 
/* 1112:1112 */        new Integer("24").intValue(), req.getUser());
/* 1113:     */    }
/* 1114:     */    catch (Exception e)
/* 1115:     */    {
/* 1116:1116 */      resp.setType(0);
/* 1117:1117 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 1118:     */    }
/* 1119:1119 */    return resp;
/* 1120:     */  }
/* 1121:     */  
/* 1122:     */  public ResponseEntity ApplicationReview(RequestEntity req)
/* 1123:     */  {
/* 1124:1124 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1125:1125 */    ResponseEntity resp = new ResponseEntity();
/* 1126:     */    try
/* 1127:     */    {
/* 1128:1128 */      Map val = (Map)req.getData().get(0);
/* 1129:1129 */      com.vincesu.business.entity.Contract contract = new com.vincesu.business.entity.Contract();
/* 1130:1130 */      contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1131:1131 */        .get("id").toString()));
/* 1132:1132 */      if (contract == null)
/* 1133:     */      {
/* 1134:1134 */        resp.setType(0);
/* 1135:1135 */        resp.setMessage("申请审核失败，没有此合同编号");
/* 1136:1136 */        return resp;
/* 1137:     */      }
/* 1138:1138 */      if (checkContractStatus(contract) == 2)
/* 1139:     */      {
/* 1140:1140 */        resp.setType(0);
/* 1141:1141 */        resp.setMessage("此合同已审核通过");
/* 1142:1142 */        return resp;
/* 1143:     */      }
/* 1144:1144 */      if (checkContractStatus(contract) == 3)
/* 1145:     */      {
/* 1146:1146 */        resp.setType(0);
/* 1147:1147 */        resp.setMessage("此合同完成");
/* 1148:1148 */        return resp;
/* 1149:     */      }
/* 1150:1150 */      if (checkContractStatus(contract) == 1)
/* 1151:     */      {
/* 1152:1152 */        if (contract.getReserved().equals("0"))
/* 1153:     */        {
/* 1154:1154 */          String r = contract.getReserved().substring(4);
/* 1155:1155 */          contract.setReserved(r);
/* 1156:1156 */          pmf.update(contract);
/* 1157:1157 */          resp.setMessage("撤销提交成功");
/* 1158:1158 */          return resp;
/* 1159:     */        }
/* 1160:1160 */        String r = contract.getReserved().substring(4);
/* 1161:1161 */        if (r.startsWith(req.getUser().getReserved()))
/* 1162:     */        {
/* 1163:1163 */          contract.setReserved(r);
/* 1164:1164 */          pmf.update(contract);
/* 1165:1165 */          resp.setMessage("撤销提交成功");
/* 1166:1166 */          OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1167:1167 */            "撤销提交合同");
/* 1168:1168 */          return resp;
/* 1169:     */        }
/* 1170:1170 */        resp.setMessage("撤销提交失败，这不是您的合同");
/* 1171:1171 */        return resp;
/* 1172:     */      }
/* 1173:     */      
/* 1174:1174 */      if (contract.getReserved().equals("0"))
/* 1175:     */      {
/* 1176:1176 */        contract.setReserved("lock" + contract.getReserved());
/* 1177:1177 */        pmf.update(contract);
/* 1178:1178 */        resp.setMessage("提交成功");
/* 1179:1179 */        OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1180:1180 */          "提交合同");
/* 1181:1181 */        return resp;
/* 1182:     */      }
/* 1183:1183 */      if (contract.getReserved().startsWith(req.getUser().getReserved()))
/* 1184:     */      {
/* 1185:1185 */        contract.setReserved("lock" + contract.getReserved());
/* 1186:1186 */        pmf.update(contract);
/* 1187:1187 */        resp.setMessage("提交成功");
/* 1188:1188 */        OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1189:1189 */          "提交合同");
/* 1190:1190 */        return resp;
/* 1191:     */      }
/* 1192:     */      
/* 1193:1193 */      resp.setType(0);
/* 1194:1194 */      resp.setMessage("这不是您提交的合同，您无法申请审核。");
/* 1195:1195 */      return resp;
/* 1196:     */    }
/* 1197:     */    catch (Exception e)
/* 1198:     */    {
/* 1199:1199 */      resp.setType(0);
/* 1200:1200 */      resp.setMessage("申请审核失败，错误信息：" + e.getMessage());
/* 1201:     */    }
/* 1202:1202 */    return resp;
/* 1203:     */  }
/* 1204:     */  
/* 1205:     */  public ResponseEntity Review(RequestEntity req)
/* 1206:     */  {
/* 1207:1207 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1208:1208 */    ResponseEntity resp = new ResponseEntity();
/* 1209:     */    try
/* 1210:     */    {
/* 1211:1211 */      Map val = (Map)req.getData().get(0);
/* 1212:1212 */      com.vincesu.business.entity.Contract contract = new com.vincesu.business.entity.Contract();
/* 1213:1213 */      contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1214:1214 */        .get("id").toString()));
/* 1215:1215 */      Client client = (Client)pmf.get(Client.class, contract.getBuyerId());
/* 1216:1216 */      if (contract == null)
/* 1217:     */      {
/* 1218:1218 */        resp.setType(0);
/* 1219:1219 */        resp.setMessage("审核失败，没有此合同编号");
/* 1220:1220 */        return resp;
/* 1221:     */      }
/* 1222:1222 */      if (checkContractStatus(contract) == 0)
/* 1223:     */      {
/* 1224:1224 */        resp.setType(0);
/* 1225:1225 */        resp.setMessage("审核出错，请先申请审核");
/* 1226:1226 */        return resp;
/* 1227:     */      }
/* 1228:1228 */      if (checkContractStatus(contract) == 2)
/* 1229:     */      {
/* 1230:1230 */        if (req.getUser().getReserved().equals("0"))
/* 1231:     */        {
/* 1232:1232 */          contract.setReserved(contract.getReserved().substring(6));
/* 1233:1233 */          contract.setAudit(req.getUser().getId());
/* 1234:1234 */          if (client != null)
/* 1235:1235 */            client.setPotential("0");
/* 1236:1236 */          pmf.update(contract);
/* 1237:1237 */          resp.setMessage("退回提交成功");
/* 1238:1238 */          OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1239:1239 */            "合同审核-退回提交");
/* 1240:     */        }
/* 1241:     */        else
/* 1242:     */        {
/* 1243:1243 */          resp.setType(0);
/* 1244:1244 */          resp.setMessage("您无权做审核操作");
/* 1245:1245 */          return resp;
/* 1246:     */        }
/* 1247:     */      }
/* 1248:     */      else
/* 1249:     */      {
/* 1250:1250 */        if (checkContractStatus(contract) == 3)
/* 1251:     */        {
/* 1252:1252 */          resp.setType(0);
/* 1253:1253 */          resp.setMessage("此合同已完成");
/* 1254:1254 */          return resp;
/* 1255:     */        }
/* 1256:     */        
/* 1257:1257 */        if (req.getUser().getReserved().equals("0"))
/* 1258:     */        {
/* 1259:1259 */          contract.setReserved("locked" + 
/* 1260:1260 */            contract.getReserved().substring(4));
/* 1261:1261 */          contract.setAudit(req.getUser().getId());
/* 1262:1262 */          if (client != null)
/* 1263:1263 */            client.setPotential("0");
/* 1264:1264 */          pmf.update(contract);
/* 1265:1265 */          resp.setMessage("审核成功");
/* 1266:1266 */          OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1267:1267 */            "合同审核-成功");
/* 1268:     */        }
/* 1269:     */        else
/* 1270:     */        {
/* 1271:1271 */          resp.setType(0);
/* 1272:1272 */          resp.setMessage("您无权审核");
/* 1273:1273 */          return resp;
/* 1274:     */        }
/* 1275:     */      }
/* 1276:     */    }
/* 1277:     */    catch (Exception e)
/* 1278:     */    {
/* 1279:1279 */      resp.setType(0);
/* 1280:1280 */      resp.setMessage("审核失败，错误信息：" + e.getMessage());
/* 1281:     */    }
/* 1282:1282 */    return resp;
/* 1283:     */  }
/* 1284:     */  
/* 1285:     */  public ResponseEntity finish(RequestEntity req)
/* 1286:     */  {
/* 1287:1287 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1288:1288 */    ResponseEntity resp = new ResponseEntity();
/* 1289:     */    try
/* 1290:     */    {
/* 1291:1291 */      Map val = (Map)req.getData().get(0);
/* 1292:1292 */      com.vincesu.business.entity.Contract contract = new com.vincesu.business.entity.Contract();
/* 1293:1293 */      contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1294:1294 */        .get("id").toString()));
/* 1295:1295 */      if (contract == null)
/* 1296:     */      {
/* 1297:1297 */        resp.setType(0);
/* 1298:1298 */        resp.setMessage("审核失败，没有此合同编号");
/* 1299:1299 */        return resp;
/* 1300:     */      }
/* 1301:1301 */      if (checkContractStatus(contract) < 2)
/* 1302:     */      {
/* 1303:1303 */        resp.setType(0);
/* 1304:1304 */        resp.setMessage("不能完结此合同，请确定此合同已经通过审核");
/* 1305:1305 */        return resp;
/* 1306:     */      }
/* 1307:1307 */      if (checkContractStatus(contract) == 3)
/* 1308:     */      {
/* 1309:1309 */        if (req.getUser().getReserved().equals("0"))
/* 1310:     */        {
/* 1311:1311 */          contract.setReserved(contract.getReserved().replaceFirst(
/* 1312:1312 */            "finish", "locked"));
/* 1313:1313 */          contract.setAudit(req.getUser().getId());
/* 1314:1314 */          pmf.update(contract);
/* 1315:     */        }
/* 1316:     */        
/* 1318:     */      }
/* 1319:1319 */      else if (req.getUser().getReserved().equals("0"))
/* 1320:     */      {
/* 1321:1321 */        contract.setReserved(contract.getReserved().replaceFirst(
/* 1322:1322 */          "locked", "finish"));
/* 1323:1323 */        contract.setAudit(req.getUser().getId());
/* 1324:1324 */        pmf.update(contract);
/* 1325:1325 */        List<ContractDetail> contractDetails = getContractDetail(
/* 1326:1326 */          pmf, contract);
/* 1327:1327 */        for (ContractDetail cd : contractDetails)
/* 1328:     */        {
/* 1329:1329 */          SalesProgram sp = new SalesProgram();
/* 1330:1330 */          sp.setDate(new Date());
/* 1331:1331 */          sp.setPermissions(
/* 1332:1332 */            ContractModel.removeLockStatus(contract.getReserved()));
/* 1333:1333 */          if (cd.getUnit() == null)
/* 1334:     */          {
/* 1335:1335 */            sp.setPrice(null);
/* 1336:     */          }
/* 1337:     */          else
/* 1338:1338 */            sp.setPrice(contract.getCurrencySymbol() + 
/* 1339:1339 */              cd.getUnit().toString());
/* 1340:1340 */          sp.setProductid(cd.getProductId());
/* 1341:1341 */          if (cd.getQty() == null)
/* 1342:     */          {
/* 1343:1343 */            sp.setQuantity(null);
/* 1344:     */          }
/* 1345:     */          else
/* 1346:1346 */            sp.setQuantity(cd.getQty().toString());
/* 1347:1347 */          Client client = (Client)pmf.get(Client.class, 
/* 1348:1348 */            contract.getBuyerId());
/* 1349:1349 */          sp.setReserved(client.getUnit());
/* 1350:1350 */          SysUser saleman = (SysUser)pmf.get(SysUser.class, 
/* 1351:1351 */            contract.getSaleman());
/* 1352:1352 */          sp.setSalesman(saleman.getRealname());
/* 1353:1353 */          pmf.save(sp);
/* 1354:     */        }
/* 1355:     */      }
/* 1356:     */      else
/* 1357:     */      {
/* 1358:1358 */        resp.setType(0);
/* 1359:1359 */        resp.setMessage("您无权审核");
/* 1360:1360 */        return resp;
/* 1361:     */      }
/* 1362:     */      
/* 1363:     */    }
/* 1364:     */    catch (Exception e)
/* 1365:     */    {
/* 1366:1366 */      resp.setType(0);
/* 1367:1367 */      resp.setMessage("审核失败，错误信息：" + e.getMessage());
/* 1368:     */    }
/* 1369:1369 */    return resp;
/* 1370:     */  }
/* 1371:     */  
/* 1372:     */  public ResponseEntity UnApplicationReview(RequestEntity req)
/* 1373:     */  {
/* 1374:1374 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1375:1375 */    ResponseEntity resp = new ResponseEntity();
/* 1376:     */    try
/* 1377:     */    {
/* 1378:1378 */      Map val = (Map)req.getData().get(0);
/* 1379:1379 */      com.vincesu.business.entity.Contract contract = new com.vincesu.business.entity.Contract();
/* 1380:1380 */      contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1381:1381 */        .get("id").toString()));
/* 1382:1382 */      if (contract == null)
/* 1383:     */      {
/* 1384:1384 */        resp.setType(0);
/* 1385:1385 */        resp.setMessage("操作失败，没有此合同编号");
/* 1386:1386 */        return resp;
/* 1387:     */      }
/* 1388:1388 */      if (checkContractStatus(contract) == 0)
/* 1389:     */      {
/* 1390:1390 */        resp.setType(0);
/* 1391:1391 */        resp.setMessage("操作出错，此合同已处于可修改状态");
/* 1392:1392 */        return resp;
/* 1393:     */      }
/* 1394:1394 */      if (checkContractStatus(contract) >= 3)
/* 1395:     */      {
/* 1396:1396 */        resp.setType(0);
/* 1397:1397 */        resp.setMessage("此合同已完成，不能退回修改");
/* 1398:1398 */        return resp;
/* 1399:     */      }
/* 1400:     */      
/* 1401:1401 */      if (req.getUser().getReserved().equals("0"))
/* 1402:     */      {
/* 1403:1403 */        contract.setReserved(contract.getReserved().replaceFirst(
/* 1404:1404 */          "locked", ""));
/* 1405:1405 */        contract.setReserved(contract.getReserved().replaceFirst(
/* 1406:1406 */          "lock", ""));
/* 1407:1407 */        contract.setAudit(req.getUser().getId());
/* 1408:1408 */        pmf.update(contract);
/* 1409:1409 */        OperationLogService.addLog(pmf, req.getUser(), contract, 3, 
/* 1410:1410 */          "合同退回修改");
/* 1411:     */      }
/* 1412:     */      else
/* 1413:     */      {
/* 1414:1414 */        resp.setType(0);
/* 1415:1415 */        resp.setMessage("您无权操作");
/* 1416:1416 */        return resp;
/* 1417:     */      }
/* 1418:     */      
/* 1419:     */    }
/* 1420:     */    catch (Exception e)
/* 1421:     */    {
/* 1422:1422 */      resp.setType(0);
/* 1423:1423 */      resp.setMessage("操作失败，错误信息：" + e.getMessage());
/* 1424:     */    }
/* 1425:1425 */    return resp;
/* 1426:     */  }
/* 1427:     */  
/* 1428:     */  public ResponseEntity queryRelatedFilesList(RequestEntity req)
/* 1429:     */    throws Exception
/* 1430:     */  {
/* 1431:1431 */    StringBuffer sql = new StringBuffer();
/* 1432:1432 */    sql.append("select a.* from vw_contract_related_file a ");
/* 1433:1433 */    sql.append(" where ");
/* 1434:1434 */    sql.append(" and a.buyer like '%:buyer%' ");
/* 1435:1435 */    sql.append(" and a.name like '%:name%' ");
/* 1436:1436 */    sql.append(" and a.type = :type ");
/* 1437:1437 */    sql.append(" and a.contract_number like '%:contractNumber%' ");
/* 1438:1438 */    sql.append(" order by a.id desc");
/* 1439:1439 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 1440:1440 */      new String[] { "type", "filename", "id", "buyer", "name", "contractNumber", "sum", 
/* 1441:1441 */      "orderDate", "deliveryDate", "salesman", "reserved", "fileid", 
/* 1442:1442 */      "path" }, 
/* 1443:1443 */      new String[] { "type", "filename", "id", "buyer", "name", "contractNumber", "sum", 
/* 1444:1444 */      "orderDate", "deliveryDate", "salesman", "reserved", "fileid", 
/* 1445:1445 */      "path" }, null, null);
/* 1446:     */  }
/* 1447:     */  
/* 1448:     */  public ResponseEntity queryRelatedFilesWithOrderId(RequestEntity req)
/* 1449:     */    throws Exception
/* 1450:     */  {
/* 1451:1451 */    StringBuffer sql = new StringBuffer();
/* 1452:1452 */    sql.append("select b.name filename,b.id fileid,b.path,b.pdfpath,b.related_description,a.reserved from files b,orders a ");
/* 1453:1453 */    sql.append(" where b.type=6 and a.id=b.related_object and b.related_object = :id order by b.id desc ");
/* 1454:1454 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 1455:1455 */      new String[] { "filename", "id", "path", "pdfpath", "rd", "reserved" }, null, null);
/* 1456:     */  }
/* 1457:     */  
/* 1458:     */  public ResponseEntity queryRelatedFiles(RequestEntity req) throws Exception
/* 1459:     */  {
/* 1460:1460 */    StringBuffer sql = new StringBuffer();
/* 1461:     */    
/* 1462:1462 */    sql.append("select b.fileid fileid,b.path,b.name,b.related_description,b.reserved from vw_contract_related_file b ");
/* 1463:1463 */    sql.append(" where  b.contractid=:id ");
/* 1464:1464 */    sql.append(" order by b.fileid ");
/* 1465:     */    
/* 1466:1466 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/* 1467:1467 */      new String[] { "id", "path", "filename", "rd", "reserved" }, null, null);
/* 1468:     */  }
/* 1469:     */  
/* 1470:     */  public ResponseEntity addRelatedFile(RequestEntity req)
/* 1471:     */  {
/* 1472:1472 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1473:1473 */    ResponseEntity resp = new ResponseEntity();
/* 1474:     */    try
/* 1475:     */    {
/* 1476:1476 */      Map val = (Map)req.getData().get(0);
/* 1477:1477 */      com.vincesu.business.entity.Contract contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, new Long(val
/* 1478:1478 */        .get("orderid").toString()));
/* 1479:1479 */      if (req.getUser().getReserved().equals("0"))
/* 1480:     */      {
/* 1481:1481 */        String fileId = val.get("id").toString();
/* 1482:1482 */        String rd = val.get("relatedDescription").toString();
/* 1483:1483 */        String contractid = val.get("orderid").toString();
/* 1484:1484 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 1485:1485 */        file.setType(Integer.valueOf(Integer.parseInt("24")));
/* 1486:1486 */        file.setRelatedDescription(rd);
/* 1487:1487 */        file.setRelatedObject(new Long(contractid));
/* 1488:1488 */        pmf.update(file);
/* 1489:1489 */        pmf.flush();
/* 1490:1490 */        calculationIntegrity(pmf, contract);
/* 1491:1491 */        pmf.update(contract);
/* 1492:1492 */        OperationLogService.addLog(pmf, 
/* 1493:1493 */          "添加合同相关文件，合同编号为：" + contract.getNumber() + "文件名:" + 
/* 1494:1494 */          file.getName() + "文件路径为:" + file.getPath(), 
/* 1495:1495 */          req.getUser().getRealname(), req.getUser().getId());
/* 1498:     */      }
/* 1499:1499 */      else if (getContractPermission(contract.getReserved()).startsWith(req.getUser().getReserved()))
/* 1500:     */      {
/* 1501:1501 */        String fileId = val.get("id").toString();
/* 1502:1502 */        String rd = val.get("relatedDescription").toString();
/* 1503:1503 */        String orderid = val.get("orderid").toString();
/* 1504:1504 */        Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 1505:1505 */        file.setType(Integer.valueOf(Integer.parseInt("24")));
/* 1506:1506 */        file.setRelatedDescription(rd);
/* 1507:1507 */        file.setRelatedObject(new Long(orderid));
/* 1508:1508 */        pmf.update(file);
/* 1509:1509 */        pmf.flush();
/* 1510:1510 */        calculationIntegrity(pmf, contract);
/* 1511:1511 */        pmf.update(contract);
/* 1512:1512 */        OperationLogService.addLog(pmf, "添加合同相关文件，合同编号为：" + 
/* 1513:1513 */          contract.getNumber() + "文件名:" + file.getName() + 
/* 1514:1514 */          "文件路径为:" + file.getPath(), req.getUser()
/* 1515:1515 */          .getRealname(), req.getUser().getId());
/* 1516:     */      }
/* 1517:     */      else
/* 1518:     */      {
/* 1519:1519 */        resp.setType(0);
/* 1520:1520 */        resp.setMessage("该合同不是您的合同，您无权操作。");
/* 1521:     */      }
/* 1522:     */    }
/* 1523:     */    catch (Exception e)
/* 1524:     */    {
/* 1525:1525 */      resp.setType(0);
/* 1526:1526 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 1527:     */    }
/* 1528:1528 */    return resp;
/* 1529:     */  }
/* 1530:     */  
/* 1531:     */  public int calculationIntegrity(PMF pmf, com.vincesu.business.entity.Contract contract)
/* 1532:     */  {
/* 1533:1533 */    Map<String, Boolean> flagMap = new HashMap();
/* 1534:1534 */    Map<String, Boolean> countMap = new HashMap();
/* 1535:1535 */    Map<String, Boolean> completeMap = new HashMap();
/* 1536:1536 */    String completeStr = null;
/* 1537:1537 */    int result = 0;int apart = 0;int base = 60;
/* 1538:1538 */    boolean completeFlag = true;
/* 1539:1539 */    if (contract.getPayment() != null)
/* 1540:     */    {
/* 1541:1541 */      List<Files> fileList = pmf.list("from Files where Type=24 and RelatedObject=" + contract.getId());
/* 1542:1542 */      for (Files file : fileList)
/* 1543:     */      {
/* 1544:1544 */        if (file.getRelatedDescription() != null)
/* 1545:     */        {
/* 1546:1546 */          flagMap.put(file.getRelatedDescription(), Boolean.valueOf(true));
/* 1547:     */        }
/* 1548:     */      }
/* 1549:     */      
/* 1550:1550 */      if ((contract.getPayment().equals("退税")) || 
/* 1551:1551 */        (contract.getPayment().equals("买单")))
/* 1552:     */      {
/* 1554:1554 */        completeMap.put("合同", Boolean.valueOf(false));
/* 1555:1555 */        completeMap.put("生产单", Boolean.valueOf(false));
/* 1556:1556 */        completeMap.put("报关资料", Boolean.valueOf(false));
/* 1557:1557 */        completeMap.put("提单", Boolean.valueOf(false));
/* 1558:     */        
/* 1559:1559 */        completeStr = "-合同-生产单-报关资料-提单-";
/* 1560:     */        
/* 1561:1561 */        for (Map.Entry<String, Boolean> entry : flagMap.entrySet())
/* 1562:     */        {
/* 1563:1563 */          if (countMap.get(entry.getKey()) == null)
/* 1564:     */          {
/* 1565:1565 */            if (completeStr.indexOf("-" + ((String)entry.getKey()).trim() + "-") >= 0)
/* 1566:     */            {
/* 1567:1567 */              completeMap.put((String)entry.getKey(), Boolean.valueOf(true));
/* 1568:     */            }
/* 1569:     */            else
/* 1570:     */            {
/* 1571:1571 */              apart += 10;
/* 1572:     */            }
/* 1573:     */          }
/* 1574:     */        }
/* 1575:     */      }
/* 1576:1576 */      else if (contract.getPayment().equals("内销"))
/* 1577:     */      {
/* 1578:1578 */        completeMap.put("合同", Boolean.valueOf(false));
/* 1579:1579 */        completeMap.put("生产单", Boolean.valueOf(false));
/* 1580:     */        
/* 1581:1581 */        completeStr = "-合同-生产单-";
/* 1582:     */        
/* 1583:1583 */        for (Map.Entry<String, Boolean> entry : flagMap.entrySet())
/* 1584:     */        {
/* 1585:1585 */          if (countMap.get(entry.getKey()) == null)
/* 1586:     */          {
/* 1587:1587 */            if (completeStr.indexOf("-" + ((String)entry.getKey()).trim() + "-") >= 0)
/* 1588:     */            {
/* 1589:1589 */              completeMap.put((String)entry.getKey(), Boolean.valueOf(true));
/* 1590:     */            }
/* 1591:     */            else
/* 1592:     */            {
/* 1593:1593 */              apart += 10;
/* 1594:     */            }
/* 1595:     */          }
/* 1596:     */        }
/* 1597:     */      }
/* 1598:     */      
/* 1599:1599 */      for (Map.Entry<String, Boolean> entry : completeMap.entrySet())
/* 1600:     */      {
/* 1601:1601 */        if (!((Boolean)completeMap.get(entry.getKey())).booleanValue())
/* 1602:     */        {
/* 1603:1603 */          completeFlag = false;
/* 1604:1604 */          break;
/* 1605:     */        }
/* 1606:     */      }
/* 1607:     */      
/* 1608:1608 */      if (completeFlag)
/* 1609:     */      {
/* 1610:1610 */        result = base + apart;
/* 1611:1611 */        if (result > 100) {
/* 1612:1612 */          result = 100;
/* 1613:     */        }
/* 1614:     */      }
/* 1615:     */      else {
/* 1616:1616 */        result = 0;
/* 1617:     */      }
/* 1618:     */    }
/* 1619:1619 */    contract.setComplete(Integer.toString(result) + "%");
/* 1620:1620 */    return result;
/* 1621:     */  }
/* 1622:     */  
/* 1623:     */  public ResponseEntity removeRelatedObject(RequestEntity req)
/* 1624:     */  {
/* 1625:1625 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1626:1626 */    ResponseEntity resp = new ResponseEntity();
/* 1627:     */    try
/* 1628:     */    {
/* 1629:1629 */      Map val = (Map)req.getData().get(0);
/* 1630:1630 */      Files file = (Files)pmf.get(Files.class, new Long(val
/* 1631:1631 */        .get("fileid").toString()));
/* 1632:1632 */      com.vincesu.business.entity.Contract contract = (com.vincesu.business.entity.Contract)pmf.get(com.vincesu.business.entity.Contract.class, 
/* 1633:1633 */        file.getRelatedObject());
/* 1634:     */      
/* 1635:1635 */      if (req.getUser().getReserved().equals("0"))
/* 1636:     */      {
/* 1637:1637 */        String id = val.get("fileid").toString();
/* 1638:1638 */        CommonService.deleteFiles(pmf, new Long(id), req.getUser());
/* 1639:1639 */        calculationIntegrity(pmf, contract);
/* 1640:1640 */        pmf.update(contract);
/* 1642:     */      }
/* 1643:1643 */      else if (contract.getReserved().startsWith("lock"))
/* 1644:     */      {
/* 1645:1645 */        resp.setType(0);
/* 1646:1646 */        resp.setMessage("合同已提交，无法修改，请联系管理员。");
/* 1648:     */      }
/* 1649:1649 */      else if (contract.getReserved().startsWith("finish"))
/* 1650:     */      {
/* 1651:1651 */        resp.setType(0);
/* 1652:1652 */        resp.setMessage("合同已提交，无法修改，请联系管理员。");
/* 1655:     */      }
/* 1656:1656 */      else if (getContractPermission(contract.getReserved()).startsWith(req.getUser().getReserved()))
/* 1657:     */      {
/* 1658:1658 */        String id = val.get("fileid").toString();
/* 1659:1659 */        CommonService.deleteFiles(pmf, new Long(id), 
/* 1660:1660 */          req.getUser());
/* 1661:1661 */        calculationIntegrity(pmf, contract);
/* 1662:1662 */        pmf.update(contract);
/* 1663:     */      }
/* 1664:     */      else
/* 1665:     */      {
/* 1666:1666 */        resp.setType(0);
/* 1667:1667 */        resp.setMessage("该订单不是您的合同，您无权操作。");
/* 1668:     */      }
/* 1669:     */    }
/* 1670:     */    catch (Exception e)
/* 1671:     */    {
/* 1672:1672 */      resp.setType(0);
/* 1673:1673 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 1674:     */    }
/* 1675:1675 */    return resp;
/* 1676:     */  }
/* 1677:     */  
/* 1678:     */  public ResponseEntity getCurrentContractNumber(RequestEntity req)
/* 1679:     */  {
/* 1680:1680 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1681:1681 */    ResponseEntity resp = new ResponseEntity();
/* 1682:     */    
/* 1683:     */    try
/* 1684:     */    {
/* 1685:1685 */      List list = pmf
/* 1686:1686 */        .get("select CONCAT('PI/',max(id)) maxnumber from contract_number_sequence");
/* 1687:1687 */      if (list.size() > 0)
/* 1688:     */      {
/* 1689:1689 */        resp.setMessage(list.get(0).toString());
/* 1690:     */      }
/* 1691:     */    }
/* 1692:     */    catch (Exception e)
/* 1693:     */    {
/* 1694:1694 */      resp.setType(0);
/* 1695:1695 */      resp.setMessage("获取合同编号失败，错误信息：" + e.getMessage());
/* 1696:     */    }
/* 1697:1697 */    return resp;
/* 1698:     */  }
/* 1699:     */  
/* 1700:     */  private List<ContractDetail> getContractDetail(PMF pmf, com.vincesu.business.entity.Contract contract)
/* 1701:     */  {
/* 1702:1702 */    StringBuffer hql = new StringBuffer();
/* 1703:1703 */    hql.append("from ContractDetail where ContractId=").append(
/* 1704:1704 */      contract.getId());
/* 1705:1705 */    List<ContractDetail> result = pmf.list(hql.toString());
/* 1706:1706 */    return result;
/* 1707:     */  }
/* 1708:     */  
/* 1709:     */  public ResponseEntity queryContractDefault(RequestEntity req)
/* 1710:     */    throws Exception
/* 1711:     */  {
/* 1712:1712 */    return 
/* 1713:1713 */      QueryUtil.queryBySQL(
/* 1714:1714 */      req, 
/* 1715:1715 */      "select email,payment_en,payment_cn,shippingtrem,phone,fax,contract_note,contract_note_cn from sys_user where uid=" + 
/* 1716:1716 */      req.getUser().getId(), 
/* 1717:1717 */      new String[] { "email", "paymentEn", "paymentCn", "shippingtrem", 
/* 1718:1718 */      "phone", "fax", "contractNote", 
/* 1719:1719 */      "contractNoteCn" }, null, null);
/* 1720:     */  }
/* 1721:     */  
/* 1722:     */  private void deleteContractFile(PMF pmf, Long id, SysUser user)
/* 1723:     */  {
/* 1724:1724 */    StringBuffer hql = new StringBuffer();
/* 1725:1725 */    hql.append(
/* 1726:1726 */      "from Files where Type=24 and RelatedDescription='合同' and RelatedObject=")
/* 1727:1727 */      .append(id);
/* 1728:1728 */    List<Files> list = pmf.list(hql.toString());
/* 1729:1729 */    for (Files f : list)
/* 1730:     */    {
/* 1731:1731 */      CommonService.deleteFiles(pmf, f.getId(), user);
/* 1732:     */    }
/* 1733:     */  }
/* 1734:     */  
/* 1735:     */  public ResponseEntity calAllContract(RequestEntity req)
/* 1736:     */  {
/* 1737:1737 */    ResponseEntity resp = new ResponseEntity();
/* 1738:1738 */    PMF pmf = RemoteUtil.getPMF(req);
/* 1739:     */    try
/* 1740:     */    {
/* 1741:1741 */      List<com.vincesu.business.entity.Contract> contracts = pmf.list("from Contract");
/* 1742:1742 */      for (com.vincesu.business.entity.Contract contract : contracts)
/* 1743:     */      {
/* 1744:1744 */        calculationIntegrity(pmf, contract);
/* 1745:1745 */        pmf.update(contract);
/* 1746:     */      }
/* 1747:     */    }
/* 1748:     */    catch (Exception e)
/* 1749:     */    {
/* 1750:1750 */      resp.setType(0);
/* 1751:1751 */      resp.setMessage("保存失败，错误信息：" + e.getMessage());
/* 1752:1752 */      return resp;
/* 1753:     */    }
/* 1754:1754 */    return resp;
/* 1755:     */  }
/* 1756:     */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ContractService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */