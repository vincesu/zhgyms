/*   1:    */package com.vincesu.business.service;
/*   2:    */
/*   3:    */import com.vincesu.business.entity.Files;
/*   4:    */import com.vincesu.business.entity.Notice;
/*   5:    */import com.vincesu.framework.entity.SysUser;
/*   6:    */import com.vincesu.framework.remote.RemoteUtil;
/*   7:    */import com.vincesu.framework.remote.RequestEntity;
/*   8:    */import com.vincesu.framework.remote.ResponseEntity;
/*   9:    */import com.vincesu.framework.util.BeanUtil;
/*  10:    */import com.vincesu.framework.util.QueryUtil;
/*  11:    */import com.vincesu.persistence.PMF;
/*  12:    */import java.util.Date;
/*  13:    */import java.util.List;
/*  14:    */import java.util.Map;
/*  15:    */
/*  16:    */public class NoticeService
/*  17:    */{
/*  18:    */  public ResponseEntity queryList(RequestEntity req)
/*  19:    */    throws Exception
/*  20:    */  {
/*  21: 21 */    StringBuffer sql = new StringBuffer();
/*  22: 22 */    sql.append("select a.id,a.title,a.author,a.date,b.`id` fileid,b.`pdfpath` from notice a left join files b on b.type=25 and a.id=b.related_object ");
/*  23: 23 */    sql.append(" where a.title like '%:title%' ");
/*  24: 24 */    sql.append(" order by a.date desc ");
/*  25: 25 */    return QueryUtil.queryBySQLAsJqgrid(req, sql.toString(), 
/*  26: 26 */      new String[] { "id", "title", "author", "date", "fileid", "filepath" }, 
/*  27: 27 */      new String[] { "id", "title", "author", "date", "fileid", "filepath" }, null, null);
/*  28:    */  }
/*  29:    */  
/*  30:    */  public ResponseEntity queryObject(RequestEntity req) throws Exception
/*  31:    */  {
/*  32: 32 */    StringBuffer sql = new StringBuffer();
/*  33: 33 */    sql.append("select a.* from notice a ");
/*  34: 34 */    sql.append(" where a.id=:id ");
/*  35: 35 */    return QueryUtil.queryBySQL(req, sql.toString(), 
/*  36: 36 */      new String[] { "id", "title", "author", "content", "date" }, null, null);
/*  37:    */  }
/*  38:    */  
/*  39:    */  public ResponseEntity add(RequestEntity req)
/*  40:    */  {
/*  41: 41 */    PMF pmf = RemoteUtil.getPMF(req);
/*  42: 42 */    ResponseEntity resp = new ResponseEntity();
/*  43:    */    try
/*  44:    */    {
/*  45: 45 */      Map val = (Map)req.getData().get(0);
/*  46: 46 */      Notice ed = new Notice();
/*  47: 47 */      BeanUtil.copyProperty(val, ed);
/*  48: 48 */      ed.setAuthor(req.getUser().getUsername());
/*  49: 49 */      ed.setDate(new Date());
/*  50: 50 */      pmf.save(ed);
/*  51: 51 */      if ((val.get("fileid") != null) && (!val.get("fileid").toString().equals(""))) {
/*  52: 52 */        Files f = (Files)pmf.get(Files.class, new Long(val.get("fileid").toString()));
/*  53: 53 */        if (f != null) {
/*  54: 54 */          f.setRelatedObject(ed.getId());
/*  55: 55 */          f.setType(new Integer("25"));
/*  56: 56 */          pmf.update(f);
/*  57:    */        }
/*  58:    */      }
/*  59:    */    }
/*  60:    */    catch (Exception e)
/*  61:    */    {
/*  62: 62 */      resp.setType(0);
/*  63: 63 */      resp.setMessage("添加失败，错误信息：" + e.getMessage());
/*  64:    */    }
/*  65: 65 */    return resp;
/*  66:    */  }
/*  67:    */  
/*  68:    */  public ResponseEntity remove(RequestEntity req)
/*  69:    */  {
/*  70: 70 */    PMF pmf = RemoteUtil.getPMF(req);
/*  71: 71 */    ResponseEntity resp = new ResponseEntity();
/*  72:    */    try
/*  73:    */    {
/*  74: 74 */      Map val = (Map)req.getData().get(0);
/*  75: 75 */      String id = val.get("id").toString();
/*  76: 76 */      Notice ed = (Notice)pmf.get(Notice.class, new Long(id));
/*  77: 77 */      pmf.remove(ed);
/*  78:    */    }
/*  79:    */    catch (Exception e)
/*  80:    */    {
/*  81: 81 */      resp.setType(0);
/*  82: 82 */      resp.setMessage("删除失败，错误信息：" + e.getMessage());
/*  83:    */    }
/*  84: 84 */    return resp;
/*  85:    */  }
/*  86:    */  
/*  87:    */  public ResponseEntity update(RequestEntity req)
/*  88:    */  {
/*  89: 89 */    PMF pmf = RemoteUtil.getPMF(req);
/*  90: 90 */    ResponseEntity resp = new ResponseEntity();
/*  91:    */    try
/*  92:    */    {
/*  93: 93 */      Map val = (Map)req.getData().get(0);
/*  94: 94 */      String id = val.get("id").toString();
/*  95: 95 */      Notice ed = (Notice)pmf.get(Notice.class, new Long(id));
/*  96: 96 */      BeanUtil.copyProperty(val, ed);
/*  97: 97 */      pmf.update(ed);
/*  98:    */    }
/*  99:    */    catch (Exception e)
/* 100:    */    {
/* 101:101 */      resp.setType(0);
/* 102:102 */      resp.setMessage("更新失败，错误信息：" + e.getMessage());
/* 103:    */    }
/* 104:104 */    return resp;
/* 105:    */  }
/* 106:    */  
/* 107:    */  public static List<Notice> getLatestNoticeList(PMF pmf)
/* 108:    */  {
/* 109:109 */    Object[] objs = null;
/* 110:110 */    List<Notice> result = pmf.list("from Notice p order by p.Id desc", null, 0, 6);
/* 111:111 */    List<Object[]> r = pmf.get("select p.id pid,f.id fid from notice p left join files f on p.id=f.related_object and f.type=25 order by p.id desc ");
/* 112:112 */    for (int i = 0; (i < 6) && (i < r.size()); i++) {
/* 113:113 */      objs = (Object[])r.get(i);
/* 114:114 */      if ((objs[1] != null) && (!objs[1].toString().equals(""))) {
/* 115:115 */        ((Notice)result.get(i)).setFileid(new Long(objs[1].toString()));
/* 116:    */      }
/* 117:    */    }
/* 118:    */    
/* 119:119 */    return result;
/* 120:    */  }
/* 121:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.business.service.NoticeService
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */