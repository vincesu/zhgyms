/*  1:   */package com.vincesu.framework.service;
/*  2:   */
/*  3:   */import com.vincesu.framework.entity.SysParam;
/*  4:   */import com.vincesu.framework.remote.RemoteUtil;
/*  5:   */import com.vincesu.framework.remote.RequestEntity;
/*  6:   */import com.vincesu.framework.remote.ResponseEntity;
/*  7:   */import com.vincesu.framework.util.BeanUtil;
/*  8:   */import com.vincesu.framework.util.QueryUtil;
/*  9:   */import com.vincesu.persistence.PMF;
/* 10:   */import java.util.List;
/* 11:   */import java.util.Map;
/* 12:   */
/* 15:   */public class SysParamService
/* 16:   */{
/* 17:   */  public ResponseEntity getParamList(RequestEntity req)
/* 18:   */    throws Exception
/* 19:   */  {
/* 20:20 */    return QueryUtil.queryBySQLAsJqgrid(req, 
/* 21:21 */      "select * from sys_param where id like '%:id%' and code like '%:code%' and value like '%:value%' and available=':available' and code<>'ndmbb'", 
/* 22:22 */      new Class[] { SysParam.class }, 
/* 23:23 */      new String[] { "id", "code", "value", "available", "description" }, null, null);
/* 24:   */  }
/* 25:   */  
/* 26:   */  public ResponseEntity getParameters(RequestEntity req) throws Exception
/* 27:   */  {
/* 28:28 */    ResponseEntity resp = QueryUtil.queryBySQL(req, 
/* 29:29 */      "select * from sys_param where id=:id and code like '%:code%' ", 
/* 30:30 */      new Class[] { SysParam.class }, 
/* 31:31 */      null, null);
/* 32:32 */    return resp;
/* 33:   */  }
/* 34:   */  
/* 35:   */  public ResponseEntity restoreParameter(RequestEntity req) throws Exception
/* 36:   */  {
/* 37:37 */    PMF pmf = RemoteUtil.getPMF(req);
/* 38:38 */    SysParam sp = (SysParam)pmf.get(SysParam.class, new Long(((Map)req.getData().get(0)).get("id").toString()));
/* 39:39 */    BeanUtil.copyProperty((Map)req.getData().get(0), sp);
/* 40:40 */    pmf.restore(sp);
/* 41:41 */    return new ResponseEntity();
/* 42:   */  }
/* 43:   */  
/* 44:   */  public ResponseEntity removeParameter(RequestEntity req)
/* 45:   */  {
/* 46:46 */    PMF pmf = RemoteUtil.getPMF(req);
/* 47:47 */    SysParam sp = (SysParam)pmf.get(SysParam.class, new Long(((Map)req.getData().get(0)).get("id").toString()));
/* 48:48 */    BeanUtil.copyProperty((Map)req.getData().get(0), sp);
/* 49:49 */    pmf.remove(sp);
/* 50:50 */    return new ResponseEntity();
/* 51:   */  }
/* 52:   */  
/* 53:   */  public static List<SysParam> queryParameters(PMF pmf, String code, int count)
/* 54:   */  {
/* 55:55 */    String hql = "from SysParam where Code='" + code + "' ";
/* 56:56 */    List<SysParam> result = pmf.list(hql, null, 0, count);
/* 57:57 */    for (SysParam s : result)
/* 58:   */    {
/* 59:59 */      if (s.getCode() == null)
/* 60:60 */        s.setCode("");
/* 61:61 */      if (s.getValue() == null)
/* 62:62 */        s.setValue("");
/* 63:63 */      if (s.getDescription() == null)
/* 64:64 */        s.setDescription("");
/* 65:   */    }
/* 66:66 */    return pmf.list(hql, null, 0, count);
/* 67:   */  }
/* 68:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.service.SysParamService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */