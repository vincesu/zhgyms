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
/* 24:   */public class filePreviewServlet
/* 25:   */  extends HttpServlet
/* 26:   */{
/* 27:   */  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
/* 28:   */    throws ServletException, IOException
/* 29:   */  {
/* 30:30 */    String serviceid = req.getParameter("serviceid");
/* 31:31 */    String params = req.getParameter("params");
/* 32:32 */    String filename = req.getParameter("filename");
/* 33:33 */    if (filename == null) {
/* 34:34 */      filename = "filePreview";
/* 35:   */    }
/* 36:   */    
/* 39:39 */    OutputStream outStream = resp.getOutputStream();
/* 40:   */    
/* 46:   */    try
/* 47:   */    {
/* 48:48 */      RequestEntity reqEntity = new RequestEntity(req.getSession());
/* 49:49 */      List<Map> data = new ArrayList();
/* 50:50 */      Map m = new HashMap();
/* 51:51 */      m.put("filestream", outStream);
/* 52:52 */      m.put("params", params);
/* 53:53 */      data.add(m);
/* 54:54 */      reqEntity.setData(data);
/* 55:55 */      ResponseEntity localResponseEntity = RemoteService.execute(serviceid, reqEntity);
/* 56:   */    }
/* 57:   */    catch (Exception e) {
/* 58:58 */      e.printStackTrace();
/* 59:   */    }
/* 60:   */    finally
/* 61:   */    {
/* 62:62 */      if (outStream != null) {
/* 63:63 */        outStream.close();
/* 64:   */      }
/* 65:   */    }
/* 66:   */  }
/* 67:   */  
/* 68:   */  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/* 69:   */  {
/* 70:70 */    doPost(req, resp);
/* 71:   */  }
/* 72:   */}


/* Location:           C:\Users\Administrator\Desktop\code\WEB-INF\classes\
 * Qualified Name:     com.vincesu.framework.remote.servlet.filePreviewServlet
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */