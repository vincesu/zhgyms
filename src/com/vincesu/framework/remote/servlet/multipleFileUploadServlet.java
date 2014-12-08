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
/*  11:    */import com.vincesu.persistence.PMF;
/*  12:    */import com.vincesu.persistence.SessionFactoryHelper;
/*  13:    */import java.io.IOException;
/*  14:    */import java.io.PrintWriter;
/*  15:    */import java.sql.SQLException;
/*  16:    */import java.util.ArrayList;
/*  17:    */import java.util.HashMap;
/*  18:    */import java.util.Iterator;
/*  19:    */import java.util.List;
/*  20:    */import java.util.Map;
/*  21:    */import javax.servlet.ServletException;
/*  22:    */import javax.servlet.http.HttpServlet;
/*  23:    */import javax.servlet.http.HttpServletRequest;
/*  24:    */import javax.servlet.http.HttpServletResponse;
/*  25:    */import org.hibernate.Session;
/*  26:    */import org.hibernate.Transaction;
/*  27:    */
/*  35:    */public class multipleFileUploadServlet
/*  36:    */  extends HttpServlet
/*  37:    */{
/*  38:    */  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/*  39:    */    throws ServletException, IOException
/*  40:    */  {
/*  41: 41 */    java.io.File file = null;
/*  42: 42 */    List<java.io.File> files = null;
/*  43: 43 */    Session session = SessionFactoryHelper.getSession();
/*  44: 44 */    Transaction trans = null;
/*  45:    */    Iterator localIterator;
/*  46:    */    java.io.File f;
/*  47: 47 */    try { trans = session.beginTransaction();
/*  48:    */      
/*  49: 49 */      String serviceid = req.getParameter("serviceid");
/*  50: 50 */      String filenames = "";
/*  51:    */      
/*  52: 52 */      List<Map> data = RemoteUtil.setData(req.getParameterMap());
/*  53:    */      
/*  55: 55 */      SmartUpload su = new SmartUpload();
/*  56:    */      
/*  57: 57 */      su.initialize(getServletConfig(), req, resp);
/*  58:    */      
/*  59: 59 */      su.setMaxFileSize(31457280L);
/*  60: 60 */      su.setTotalMaxFileSize(314572800L);
/*  61:    */      
/*  62: 62 */      su.setDeniedFilesList("exe,bat,jsp,htm,html,EXE,BAT,JSP,HTM,HTML");
/*  63: 63 */      su.upload();
/*  64:    */      
/*  73: 73 */      SequenceSimulator sequenceSimulator = null;
/*  74:    */      
/*  75: 75 */      files = new ArrayList();
/*  76:    */      
/*  77: 77 */      PMF pmf = new PMF(SessionFactoryHelper.getSession());
/*  78:    */      
/*  79: 79 */      for (int i = 0; i < su.getFiles().getCount(); i++)
/*  80:    */      {
/*  81: 81 */        if (!su.getFiles().getFile(i).getFieldName().startsWith("filename"))
/*  82:    */        {
/*  83: 83 */          sequenceSimulator = new SequenceSimulator();
/*  84: 84 */          pmf.save(sequenceSimulator);
/*  85: 85 */          file = new java.io.File(System.getProperty("user.home"), "temp-" + sequenceSimulator.getId() + "-file");
/*  86: 86 */          su.getFiles().getFile(i).saveAs(file.getAbsolutePath(), 2);
/*  87: 87 */          files.add(file);
/*  88:    */        }
/*  89: 89 */        if (su.getFiles().getFile(i).getFieldName().startsWith("filename|"))
/*  90:    */        {
/*  91: 91 */          filenames = su.getFiles().getFile(i).getFieldName().substring(9);
/*  92:    */        }
/*  93:    */      }
/*  94:    */      
/*  95: 95 */      if (filenames.equals(""))
/*  96:    */      {
/*  97: 97 */        resp.setContentType("text/html;charset=UTF-8");
/*  98: 98 */        resp.getWriter().print("error0");
					return;
/* 100:    */      }
/* 101:    */      
/* 103:103 */      String[] fileNames = filenames.split("\\|");
/* 104:    */      
/* 105:105 */      if (fileNames.length != files.size())
/* 106:    */      {
/* 107:107 */        resp.setContentType("text/html;charset=UTF-8");
/* 108:108 */        resp.getWriter().print("error1");
return;
/* 110:    */      }
/* 111:    */      
/* 112:112 */      RequestEntity reqEntity = new RequestEntity(req.getSession());
/* 113:113 */      Map m = null;
/* 114:114 */      if (data.size() == 0)
/* 115:    */      {
/* 116:116 */        m = new HashMap();
/* 117:117 */        data.add(m);
/* 118:    */      }
/* 119:    */      else
/* 120:    */      {
/* 121:121 */        m = (Map)data.get(0);
/* 122:    */      }
/* 123:    */      
/* 124:124 */      m.put("files", files);
/* 125:    */      
/* 128:128 */      m.put("filenames", fileNames);
/* 129:129 */      reqEntity.setData(data);
/* 130:130 */      ResponseEntity respEntity = RemoteService.execute(serviceid, reqEntity);
/* 131:    */      
/* 132:132 */      if (respEntity.isRollback()) {
/* 133:133 */        trans.rollback();
/* 134:    */      } else {
/* 135:135 */        trans.commit();
/* 136:    */      }
/* 137:    */      
/* 138:138 */      resp.setContentType("text/html;charset=UTF-8");
/* 139:139 */      resp.getWriter().print(respEntity.getMessage());
/* 141:    */    }
/* 142:    */    catch (SmartUploadException e)
/* 143:    */    {
/* 145:145 */      e.printStackTrace();
/* 146:146 */      if (trans != null) {
/* 147:147 */        trans.rollback();
/* 148:    */      }
/* 149:    */    } catch (SQLException e) {
/* 151:151 */      e.printStackTrace();
/* 152:152 */      if (trans != null)
/* 153:153 */        trans.rollback();
/* 154:    */    } catch (Exception e) {
/* 156:156 */      e.printStackTrace();
/* 157:157 */      if (trans != null) {
/* 158:158 */        trans.rollback();
/* 159:    */      }
/* 160:    */    } finally {
/* 162:162 */      if ((files != null) && (files.size() > 0))
/* 163:    */      {
/* 164:164 */        for (java.io.File f1 : files) {
/* 165:165 */          f1.delete();
/* 166:    */        }
/* 167:    */      }
/* 168:    */    }
/* 169:    */  }
/* 170:    */  
/* 172:    */  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/* 173:    */    throws ServletException, IOException
/* 174:    */  {
/* 175:175 */    doPost(req, resp);
/* 176:    */  }
/* 177:    */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.servlet.multipleFileUploadServlet
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */