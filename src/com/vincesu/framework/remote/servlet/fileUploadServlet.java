/*   1:    */package com.vincesu.framework.remote.servlet;
/*   2:    */
/*   3:    */import com.jspsmart.upload.Files;
/*   4:    */import com.jspsmart.upload.SmartUpload;
/*   5:    */import com.jspsmart.upload.SmartUploadException;
/*   6:    */import com.vincesu.business.entity.SequenceSimulator;
/*   7:    */import com.vincesu.framework.remote.RemoteService;
/*   8:    */import com.vincesu.framework.remote.RemoteUtil;
/*   9:    */import com.vincesu.framework.remote.RequestEntity;
/*  10:    */import com.vincesu.framework.remote.ResponseEntity;
/*  11:    */import com.vincesu.framework.util.PathUtil;
/*  12:    */import com.vincesu.persistence.PMF;
/*  13:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  14:    */import java.io.FileInputStream;
/*  15:    */import java.io.IOException;
/*  16:    */import java.io.PrintWriter;
/*  17:    */import java.sql.SQLException;
/*  18:    */import java.util.HashMap;
/*  19:    */import java.util.List;
/*  20:    */import java.util.Map;
/*  21:    */import javax.servlet.ServletException;
/*  22:    */import javax.servlet.http.HttpServlet;
/*  23:    */import javax.servlet.http.HttpServletRequest;
/*  24:    */import javax.servlet.http.HttpServletResponse;
/*  25:    */import org.hibernate.Session;
/*  26:    */import org.hibernate.Transaction;
/*  27:    */
/*  36:    */public class fileUploadServlet
/*  37:    */  extends HttpServlet
/*  38:    */{
/*  39:    */  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*  40:    */    throws ServletException, IOException
/*  41:    */  {
/*  42: 42 */    FileInputStream fis = null;
/*  43: 43 */    java.io.File file = null;
/*  44: 44 */    Session session = SessionFactoryHelper.getSession();
/*  45: 45 */    Transaction trans = null;
/*  46:    */    try
/*  47:    */    {
/*  48: 48 */      trans = session.beginTransaction();
/*  49:    */      
/*  50: 50 */      String serviceid = req.getParameter("serviceid");
/*  51:    */      
/*  52: 52 */      String callbackSuc = req.getParameter("callbackfunc");
/*  53: 53 */      String callbackErr = req.getParameter("errorfunc");
/*  54: 54 */      String filename = new String(req.getParameter("filename").getBytes("ISO-8859-1"), "UTF-8");
/*  55:    */      
/*  56: 56 */      StringBuffer sb = new StringBuffer();
/*  57:    */      
/*  58: 58 */      List<Map> data = RemoteUtil.setData(req.getParameterMap());
/*  59:    */      
/*  61: 61 */      SmartUpload su = new SmartUpload();
/*  62:    */      
/*  63: 63 */      su.initialize(getServletConfig(), req, resp);
/*  64:    */      
/*  65: 65 */      su.setMaxFileSize(31457280L);
/*  66: 66 */      su.setTotalMaxFileSize(314572800L);
/*  67:    */      
/*  68: 68 */      su.setDeniedFilesList("exe,bat,jsp,htm,html,EXE,BAT,JSP,HTM,HTML");
/*  69:    */      try {
/*  70: 70 */        su.upload();
/*  72:    */      }
/*  73:    */      catch (Throwable e)
/*  74:    */      {
/*  75: 75 */        sb.append("<script>").append("window.top.window[\"").append(callbackErr).append("\"]('").append("上传文件出错，错误原因，文件格式有误或文件大小超过限制。上传文件大小为：单个文件30m，所有文件300m。禁止上传文件格式为（不区分大小写）：exe,bat,jsp,htm,html。当前文件格式为：").append(PathUtil.getExtensionName(filename)).append("。');").append("</script>");
/*  76: 76 */        resp.setContentType("text/html;charset=UTF-8");
/*  77: 77 */        resp.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
/*  78: 78 */        resp.getWriter().print("<html>");
/*  79: 79 */        resp.getWriter().print("<head></head>");
/*  80: 80 */        resp.getWriter().print("<body>");
/*  81: 81 */        resp.getWriter().print(sb.toString());
/*  82: 82 */        resp.getWriter().print("</body>");
/*  83: 83 */        resp.getWriter().print("</html>");
/*  84:    */        
/* 164:164 */        if (fis != null)
/* 165:165 */          fis.close();
/* 166:166 */        if ((file != null) && (file.exists())) {
/* 167:167 */          file.delete();
/* 168:    */        }
/* 169: 84 */        return;
/* 170:    */      }
/* 171:    */      
/* 172: 87 */      SequenceSimulator sequenceSimulator = new SequenceSimulator();
/* 173: 88 */      PMF pmf = new PMF(SessionFactoryHelper.getSession());
/* 174: 89 */      pmf.save(sequenceSimulator);
/* 175:    */      
/* 176: 91 */      file = new java.io.File(System.getProperty("user.home"), "temp-" + sequenceSimulator.getId() + "-file");
/* 177: 92 */      su.getFiles().getFile(0).saveAs(file.getAbsolutePath(), 2);
/* 178:    */      
/* 181: 96 */      RequestEntity reqEntity = new RequestEntity(req.getSession());
/* 182: 97 */      Map m = null;
/* 183: 98 */      if (data.size() == 0)
/* 184:    */      {
/* 185:100 */        m = new HashMap();
/* 186:101 */        data.add(m);
/* 187:    */      }
/* 188:    */      else
/* 189:    */      {
/* 190:105 */        m = (Map)data.get(0);
/* 191:    */      }
/* 192:107 */      fis = new FileInputStream(file);
/* 193:108 */      m.put("filesize", Integer.valueOf(su.getFiles().getFile(0).getSize()));
/* 194:109 */      m.put("filestream", fis);
/* 195:110 */      m.put("filename", filename);
/* 196:111 */      reqEntity.setData(data);
/* 197:112 */      ResponseEntity respEntity = RemoteService.execute(serviceid, reqEntity);
/* 198:113 */      if (respEntity.isRollback()) {
/* 199:114 */        trans.rollback();
/* 200:    */      } else {
/* 201:116 */        trans.commit();
/* 202:    */      }
/* 203:    */      
/* 204:119 */      if (respEntity.getType() == 0)
/* 205:    */      {
/* 208:123 */        sb.append("<script>").append("window.top.window[\"").append(callbackErr).append("\"]('").append(respEntity.getMessage()).append("');").append("</script>");
/* 210:    */      }
/* 211:    */      else
/* 212:    */      {
/* 214:129 */        sb.append("<script>").append("window.top.window[\"").append(callbackSuc).append("\"]('").append(respEntity.getMessage()).append("');").append("</script>");
/* 215:    */      }
/* 216:    */      
/* 218:133 */      if (sb != null)
/* 219:    */      {
/* 220:135 */        resp.setContentType("text/html;charset=UTF-8");
/* 221:136 */        resp.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
/* 222:137 */        resp.getWriter().print("<html>");
/* 223:138 */        resp.getWriter().print("<head></head>");
/* 224:139 */        resp.getWriter().print("<body>");
/* 225:140 */        resp.getWriter().print(sb.toString());
/* 226:141 */        resp.getWriter().print("</body>");
/* 227:142 */        resp.getWriter().print("</html>");
/* 228:    */      }
/* 229:    */    }
/* 230:    */    catch (SmartUploadException e)
/* 231:    */    {
/* 232:147 */      e.printStackTrace();
/* 233:148 */      if (trans != null) {
/* 234:149 */        trans.rollback();
/* 235:    */      }
/* 236:    */    }
/* 237:    */    catch (SQLException e) {
/* 238:153 */      e.printStackTrace();
/* 239:154 */      if (trans != null) {
/* 240:155 */        trans.rollback();
/* 241:    */      }
/* 242:    */    } catch (Exception e) {
/* 243:158 */      e.printStackTrace();
/* 244:159 */      if (trans != null) {
/* 245:160 */        trans.rollback();
/* 246:    */      }
/* 247:    */    }
/* 248:    */    finally {
/* 249:164 */      if (fis != null)
/* 250:165 */        fis.close();
/* 251:166 */      if ((file != null) && (file.exists())) {
/* 252:167 */        file.delete();
/* 253:    */      }
/* 254:    */    }
/* 255:    */  }
/* 256:    */  
/* 257:    */  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/* 258:    */    throws ServletException, IOException
/* 259:    */  {
/* 260:175 */    doPost(req, resp);
/* 261:    */  }
/* 262:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.servlet.fileUploadServlet
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */