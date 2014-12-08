<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.jspsmart.upload.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
文件名：do_upload.jsp
作 者：纵横软件制作中心雨亦奇(zhsoft88@sohu.com)
--%>
<html>
<head>
<title>文件上传处理页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body>
<%
// 新建一个SmartUpload对象
SmartUpload su = new SmartUpload();
// 上传初始化
su.initialize(pageContext);
// 设定上传限制
// 1.限制每个上传文件的最大长度。
// su.setMaxFileSize(10000);
// 2.限制总上传数据的长度。
// su.setTotalMaxFileSize(20000);
// 3.设定允许上传的文件（通过扩展名限制）,仅允许doc,txt文件。
// su.setAllowedFilesList("doc,txt");
// 4.设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,
//jsp,htm,html扩展名的文件和没有扩展名的文件。
// su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
// 上传文件
su.upload();
// 将上传文件全部保存到指定目录
//int count = su.save("c:/upload");
//out.println(count+"个文件上传成功！<br/>");

// 利用Request对象获取参数之值
out.println("TEST="+su.getRequest().getParameter("filename2")
+"<BR><BR>"); 
out.println("TEST="+su.getRequest().getParameter("filename")
		+"<BR><BR>"); 

%>