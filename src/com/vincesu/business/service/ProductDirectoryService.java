/*  1:   */package com.vincesu.business.service;
/*  2:   */
/*  3:   */import com.vincesu.business.entity.Files;
/*  4:   */import com.vincesu.framework.remote.RemoteUtil;
/*  5:   */import com.vincesu.framework.remote.RequestEntity;
/*  6:   */import com.vincesu.framework.remote.ResponseEntity;
/*  7:   */import com.vincesu.framework.util.QueryUtil;
/*  8:   */import com.vincesu.persistence.PMF;
/*  9:   */import java.util.List;
/* 10:   */import java.util.Map;
/* 11:   */
/* 12:   */public class ProductDirectoryService
/* 13:   */{
/* 14:   */  public ResponseEntity queryList(RequestEntity req)
/* 15:   */    throws Exception
/* 16:   */  {
/* 17:17 */    StringBuffer sql = new StringBuffer();
/* 18:18 */    sql.append("select b.id,b.name,b.date,b.path,b.pdfpath from files b ");
/* 19:19 */    sql.append("where b.type=3 ");
/* 20:20 */    sql.append(" and b.name like '%:name%' ");
/* 21:21 */    sql.append(" order by id desc");
/* 22:22 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/* 23:23 */      new String[] { "id", "name", "date", "path", "pdfpath" }, 
/* 24:24 */      new String[] { "id", "name", "date", "path", "pdfpath" }, null, null);
/* 25:   */  }
/* 26:   */  
/* 27:   */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/* 28:   */  {
/* 29:29 */    StringBuffer sql = new StringBuffer();
/* 30:30 */    sql.append("select b.id,b.name,b.date,b.path,b.pdfpath from files b ");
/* 31:31 */    sql.append("where b.type=3 ");
/* 32:32 */    sql.append(" and b.id=:id ");
/* 33:33 */    return QueryUtil.queryBySQL(req, sql.toString(), new String[] { "id", "name", "date", "path", "pdfpath" }, null, null);
/* 34:   */  }
/* 35:   */  
/* 36:   */  public ResponseEntity queryObjects(RequestEntity req) throws Exception
/* 37:   */  {
/* 38:38 */    StringBuffer sql = new StringBuffer();
/* 39:39 */    sql.append("select b.id,b.name,b.date,b.path,b.pdfpath from files b ");
/* 40:40 */    sql.append("where b.type=3 ");
/* 41:41 */    sql.append(" and b.name like '%:name%' ");
/* 42:42 */    return QueryUtil.queryBySQL(req, sql.toString(), new String[] { "id", "name", "date", "path", "pdfpath" }, null, null);
/* 43:   */  }
/* 44:   */  
/* 45:   */  public ResponseEntity add(RequestEntity req)
/* 46:   */  {
/* 47:47 */    PMF pmf = RemoteUtil.getPMF(req);
/* 48:48 */    ResponseEntity resp = new ResponseEntity();
/* 49:   */    try
/* 50:   */    {
/* 51:51 */      Map val = (Map)req.getData().get(0);
/* 52:52 */      String fileId = val.get("id").toString();
/* 53:53 */      String name = val.get("name").toString();
/* 54:54 */      Files file = (Files)pmf.get(Files.class, new Long(fileId));
/* 55:55 */      file.setType(new Integer("3"));
/* 56:56 */      file.setName(name);
/* 57:57 */      if (val.get("relatedDescription") != null)
/* 58:58 */        file.setRelatedDescription(val.get("relatedDescription").toString());
/* 59:59 */      pmf.update(file);
/* 60:   */    }
/* 61:   */    catch (Exception e)
/* 62:   */    {
/* 63:63 */      resp.setType(0);
/* 64:64 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/* 65:   */    }
/* 66:66 */    return resp;
/* 67:   */  }
/* 68:   */  
/* 69:   */  public ResponseEntity remove(RequestEntity req)
/* 70:   */  {
/* 71:71 */    PMF pmf = RemoteUtil.getPMF(req);
/* 72:72 */    ResponseEntity resp = new ResponseEntity();
/* 73:   */    try
/* 74:   */    {
/* 75:75 */      Map val = (Map)req.getData().get(0);
/* 76:76 */      String id = val.get("id").toString();
/* 77:77 */      Files file = (Files)pmf.get(Files.class, new Long(id));
/* 78:78 */      CommonService.deleteFiles(pmf, file.getId(), req.getUser());
/* 79:   */    }
/* 80:   */    catch (Exception e)
/* 81:   */    {
/* 82:82 */      resp.setType(0);
/* 83:83 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/* 84:   */    }
/* 85:85 */    return resp;
/* 86:   */  }
/* 87:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.ProductDirectoryService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */