/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.business.entity.SemifinishedProduct;
/*   5:    */import com.vincesu.framework.remote.RemoteUtil;
/*   6:    */import com.vincesu.framework.remote.RequestEntity;
/*   7:    */import com.vincesu.framework.remote.ResponseEntity;
/*   8:    */import com.vincesu.framework.util.BeanUtil;
/*   9:    */import com.vincesu.framework.util.QueryUtil;
/*  10:    */import com.vincesu.persistence.PMF;
/*  11:    */import java.util.List;
/*  12:    */import java.util.Map;
/*  13:    */
/*  14:    */public class SemifinishedProductService
/*  15:    */{
/*  16:    */  public ResponseEntity queryList(RequestEntity req)
/*  17:    */    throws Exception
/*  18:    */  {
/*  19: 19 */    StringBuffer sql = new StringBuffer();
/*  20: 20 */    sql.append("select b.id fid,b.path,b.webpath,a.id,a.name,a.size from semifinished_product a,files b ");
/*  21: 21 */    sql.append("where a.id=b.related_object and b.type=1 ");
/*  22: 22 */    sql.append(" and a.name like '%:name%' ");
/*  23: 23 */    sql.append(" and a.size like '%:size%' ");
/*  24: 24 */    sql.append(" order by a.id desc");
/*  25: 25 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  26: 26 */      new String[] { "fid", "downpath", "path", "id", "name", "size" }, 
/*  27: 27 */      new String[] { "fid", "downpath", "path", "id", "name", "size" }, null, null);
/*  28:    */  }
/*  29:    */  
/*  30:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  31:    */  {
/*  32: 32 */    StringBuffer sql = new StringBuffer();
/*  33: 33 */    sql.append("select a.id,a.name,a.size,b.webpath,b.id fileid from semifinished_product a,files b ");
/*  34: 34 */    sql.append("where a.id=b.related_object and b.type=1 ");
/*  35: 35 */    sql.append(" and a.id=:id ");
/*  36: 36 */    return QueryUtil.queryBySQL(req, sql.toString(), new String[] { "id", "name", "size", "path", "fileid" }, null, null);
/*  37:    */  }
/*  38:    */  
/*  39:    */  public ResponseEntity add(RequestEntity req)
/*  40:    */  {
/*  41: 41 */    PMF pmf = RemoteUtil.getPMF(req);
/*  42: 42 */    ResponseEntity resp = new ResponseEntity();
/*  43:    */    try
/*  44:    */    {
/*  45: 45 */      Map val = (Map)req.getData().get(0);
/*  46: 46 */      SemifinishedProduct sp = new SemifinishedProduct();
/*  47: 47 */      BeanUtil.copyProperty(val, sp);
/*  48: 48 */      pmf.save(sp);
/*  49: 49 */      String fileId = val.get("fileid").toString();
/*  50: 50 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/*  51: 51 */      file.setType(new Integer("1"));
/*  52: 52 */      file.setRelatedObject(sp.getId());
/*  53: 53 */      file.setDescription(sp.getName());
/*  54: 54 */      pmf.update(file);
/*  55:    */    }
/*  56:    */    catch (Exception e)
/*  57:    */    {
/*  58: 58 */      resp.setType(0);
/*  59: 59 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  60:    */    }
/*  61: 61 */    return resp;
/*  62:    */  }
/*  63:    */  
/*  64:    */  public ResponseEntity remove(RequestEntity req)
/*  65:    */  {
/*  66: 66 */    PMF pmf = RemoteUtil.getPMF(req);
/*  67: 67 */    ResponseEntity resp = new ResponseEntity();
/*  68:    */    try
/*  69:    */    {
/*  70: 70 */      Map val = (Map)req.getData().get(0);
/*  71: 71 */      String id = val.get("id").toString();
/*  72: 72 */      CommonService.delete(pmf, new Long(id), new Integer("1").intValue(), req.getUser());
/*  73: 73 */      SemifinishedProduct sp = (SemifinishedProduct)pmf.get(SemifinishedProduct.class, new Long(id));
/*  74: 74 */      pmf.remove(sp);
/*  75:    */    }
/*  76:    */    catch (Exception e)
/*  77:    */    {
/*  78: 78 */      resp.setType(0);
/*  79: 79 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/*  80:    */    }
/*  81: 81 */    return resp;
/*  82:    */  }
/*  83:    */  
/*  84:    */  public ResponseEntity update(RequestEntity req)
/*  85:    */  {
/*  86: 86 */    PMF pmf = RemoteUtil.getPMF(req);
/*  87: 87 */    ResponseEntity resp = new ResponseEntity();
/*  88:    */    try
/*  89:    */    {
/*  90: 90 */      Map val = (Map)req.getData().get(0);
/*  91: 91 */      String id = val.get("id").toString();
/*  92: 92 */      SemifinishedProduct sp = (SemifinishedProduct)pmf.get(SemifinishedProduct.class, new Long(id));
/*  93: 93 */      BeanUtil.copyProperty(val, sp);
/*  94: 94 */      pmf.update(sp);
/*  95:    */      
/*  96: 96 */      String fileId = val.get("fileid").toString();
/*  97: 97 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/*  98: 98 */      file.setType(new Integer("1"));
/*  99: 99 */      file.setRelatedObject(sp.getId());
/* 100:100 */      pmf.update(file);
/* 101:101 */      CommonService.deleteFilesByRelatedObject(pmf, sp.getId(), file.getId(), 
/* 102:102 */        "1", req.getUser());
/* 103:    */      
/* 104:104 */      pmf.save(file);
/* 105:    */    }
/* 106:    */    catch (Exception e)
/* 107:    */    {
/* 108:108 */      resp.setType(0);
/* 109:109 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 110:    */    }
/* 111:111 */    return resp;
/* 112:    */  }
/* 113:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.SemifinishedProductService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */