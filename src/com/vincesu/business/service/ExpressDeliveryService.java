/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.ExpressDelivery;
/*   4:    */import com.vincesu.framework.remote.RemoteUtil;
/*   5:    */import com.vincesu.framework.remote.RequestEntity;
/*   6:    */import com.vincesu.framework.remote.ResponseEntity;
/*   7:    */import com.vincesu.framework.util.BeanUtil;
/*   8:    */import com.vincesu.framework.util.QueryUtil;
/*   9:    */import com.vincesu.persistence.PMF;
/*  10:    */import java.util.List;
/*  11:    */import java.util.Map;
/*  12:    */
/*  13:    */public class ExpressDeliveryService
/*  14:    */{
/*  15:    */  public ResponseEntity queryList(RequestEntity req)
/*  16:    */    throws Exception
/*  17:    */  {
/*  18: 18 */    StringBuffer sql = new StringBuffer();
/*  19: 19 */    sql.append("select a.* from express_delivery a ");
/*  20: 20 */    sql.append(" where a.company like '%:company%' ");
/*  21: 21 */    sql.append(" order by a.id desc ");
/*  22: 22 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  23: 23 */      new String[] { "id", "company", "contact", "phone" }, 
/*  24: 24 */      new String[] { "id", "company", "contact", "phone" }, null, null);
/*  25:    */  }
/*  26:    */  
/*  27:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  28:    */  {
/*  29: 29 */    StringBuffer sql = new StringBuffer();
/*  30: 30 */    sql.append("select a.* from express_delivery a ");
/*  31: 31 */    sql.append(" where a.id=:id ");
/*  32: 32 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  33: 33 */      new String[] { "id", "company", "contact", "phone" }, null, null);
/*  34:    */  }
/*  35:    */  
/*  36:    */  public ResponseEntity add(RequestEntity req)
/*  37:    */  {
/*  38: 38 */    PMF pmf = RemoteUtil.getPMF(req);
/*  39: 39 */    ResponseEntity resp = new ResponseEntity();
/*  40:    */    try
/*  41:    */    {
/*  42: 42 */      Map val = (Map)req.getData().get(0);
/*  43: 43 */      ExpressDelivery ed = new ExpressDelivery();
/*  44: 44 */      BeanUtil.copyProperty(val, ed);
/*  45: 45 */      pmf.save(ed);
/*  46:    */    }
/*  47:    */    catch (Exception e)
/*  48:    */    {
/*  49: 49 */      resp.setType(0);
/*  50: 50 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  51:    */    }
/*  52: 52 */    return resp;
/*  53:    */  }
/*  54:    */  
/*  55:    */  public ResponseEntity remove(RequestEntity req)
/*  56:    */  {
/*  57: 57 */    PMF pmf = RemoteUtil.getPMF(req);
/*  58: 58 */    ResponseEntity resp = new ResponseEntity();
/*  59:    */    try
/*  60:    */    {
/*  61: 61 */      Map val = (Map)req.getData().get(0);
/*  62: 62 */      String id = val.get("id").toString();
/*  63: 63 */      ExpressDelivery ed = (ExpressDelivery)pmf.get(ExpressDelivery.class, new Long(id));
/*  64: 64 */      pmf.remove(ed);
/*  65:    */    }
/*  66:    */    catch (Exception e)
/*  67:    */    {
/*  68: 68 */      resp.setType(0);
/*  69: 69 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/*  70:    */    }
/*  71: 71 */    return resp;
/*  72:    */  }
/*  73:    */  
/*  74:    */  public ResponseEntity update(RequestEntity req)
/*  75:    */  {
/*  76: 76 */    PMF pmf = RemoteUtil.getPMF(req);
/*  77: 77 */    ResponseEntity resp = new ResponseEntity();
/*  78:    */    try
/*  79:    */    {
/*  80: 80 */      Map val = (Map)req.getData().get(0);
/*  81: 81 */      String id = val.get("id").toString();
/*  82: 82 */      ExpressDelivery ed = (ExpressDelivery)pmf.get(ExpressDelivery.class, new Long(id));
/*  83: 83 */      BeanUtil.copyProperty(val, ed);
/*  84: 84 */      pmf.update(ed);
/*  85:    */    }
/*  86:    */    catch (Exception e)
/*  87:    */    {
/*  88: 88 */      resp.setType(0);
/*  89: 89 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/*  90:    */    }
/*  91: 91 */    return resp;
/*  92:    */  }
/*  93:    */  
/*  94:    */  public static List<ExpressDelivery> getExpressDeliveryList(PMF pmf, int count)
/*  95:    */  {
/*  96: 96 */    List<ExpressDelivery> result = null;
/*  97: 97 */    StringBuffer hql = new StringBuffer();
/*  98: 98 */    hql.append("from ExpressDelivery order by Id desc ");
/*  99: 99 */    result = pmf.list(hql.toString(), null, 0, count);
/* 100:100 */    return result;
/* 101:    */  }
/* 102:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ExpressDeliveryService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */