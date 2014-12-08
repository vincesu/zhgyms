/*  1:   */package com.vincesu.framework.remote.servlet;
/*  2:   */
/*  3:   */import com.vincesu.framework.remote.RemoteService;
/*  4:   */import com.vincesu.framework.remote.RequestEntity;
/*  5:   */import com.vincesu.framework.remote.ResponseEntity;
/*  6:   */import java.io.IOException;
/*  7:   */import java.io.OutputStream;
/*  8:   */import java.util.ArrayList;
/*  9:   */import java.util.HashMap;
/* 10:   */import java.util.List;
/* 11:   */import java.util.Map;
/* 12:   */import javax.servlet.ServletException;
/* 13:   */import javax.servlet.http.HttpServlet;
/* 14:   */import javax.servlet.http.HttpServletRequest;
/* 15:   */import javax.servlet.http.HttpServletResponse;
/* 16:   */
/* 24:   */public class fileDownloadServlet
/* 25:   */  extends HttpServlet
/* 26:   */{
/* 27:   */  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/* 28:   */    throws ServletException, IOException
/* 29:   */  {
/* 30:30 */    String serviceid = req.getParameter("serviceid");
/* 31:31 */    String filename = req.getParameter("filename");
/* 32:32 */    String params = req.getParameter("params");
/* 33:   */    
/* 34:34 */    resp.reset();
/* 35:35 */    resp.setContentType("text/plan; charset=UTF-8");
/* 36:36 */    resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
/* 37:37 */    OutputStream outStream = resp.getOutputStream();
/* 38:   */    try
/* 39:   */    {
/* 40:40 */      RequestEntity reqEntity = new RequestEntity(req.getSession());
/* 41:41 */      List<Map> data = new ArrayList();
/* 42:42 */      Map m = new HashMap();
/* 43:43 */      m.put("filestream", outStream);
/* 44:44 */      m.put("params", params);
/* 45:45 */      data.add(m);
/* 46:46 */      reqEntity.setData(data);
/* 47:47 */      ResponseEntity localResponseEntity = RemoteService.execute(serviceid, reqEntity);
/* 48:   */    }
/* 49:   */    catch (Exception e)
/* 50:   */    {
/* 51:51 */      e.printStackTrace();
/* 52:   */    }
/* 53:   */    finally
/* 54:   */    {
/* 55:55 */      if (outStream != null)
/* 56:   */      {
/* 57:57 */        outStream.flush();
/* 58:58 */        outStream.close();
/* 59:   */      }
/* 60:   */    }
/* 61:   */  }
/* 62:   */  
/* 63:   */  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/* 64:   */    throws ServletException, IOException
/* 65:   */  {
/* 66:66 */    doPost(req, resp);
/* 67:   */  }
/* 68:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.servlet.fileDownloadServlet
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */