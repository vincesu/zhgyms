<%@page import="com.vincesu.persistence.PMF"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.vincesu.business.service.ProduceService" %>
<%@ page import="com.vincesu.business.service.CommonService" %>
<%@ page import="com.vincesu.business.service.NoticeService" %>
<%@ page import="com.vincesu.business.service.ExpressDeliveryService" %>
<%@ page import="com.vincesu.framework.service.SysParamService" %>
<%@ page import="com.vincesu.framework.util.SystemConfig" %>
<%@ page import="com.vincesu.business.entity.ManufactureOrder" %>
<%@ page import="com.vincesu.business.entity.Files" %>
<%@ page import="com.vincesu.business.entity.Notice" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>温州中昊工艺品有限公司</title>
<script type="text/javascript" src="js/framework/jquery-1.8.0.js"></script>
<script type="text/javascript" src="js/framework/include.js"></script>
<script type="text/javascript" src="js/homepage.js"></script>
<script>
function imagezoom(new_w, new_h){
    var img = document.createElement('img');
    img.src = this.src;
    var src_w = img.width;
    var src_h = img.height;
   
    var zoom_w = 0, zoom_h = 0;
    if(src_w > new_w || src_h > new_h){
        var scale_w = new_w / src_w;
        var scale_h = new_h / src_h;
        var b = scale_w < scale_h;
           
        if(b){
            zoom_w = src_w * scale_w;
            zoom_h = src_h * scale_w;
        }else{
            zoom_w = src_w * scale_h;
            zoom_h = src_h * scale_h;
        }
    }else{
        zoom_w = src_w;
        zoom_h = src_h;
    }
       
    this.style.marginLeft = Math.abs(new_w-zoom_w)/2+'px';
    this.style.marginTop = Math.abs(new_h-zoom_h)/2+'px';
   
    this.width = zoom_w;
    this.height = zoom_h;
}
</script>
<link href="css/framework/main.css" rel="stylesheet" type="text/css" />
<link href="css/home/homepage.css" rel="stylesheet" type="text/css" />
</head>
<%
	PMF pmf = new PMF();

	request.setAttribute("produce",ProduceService.getLatestProduceList(pmf));
	
	request.setAttribute("producttestreport",CommonService.getLatestFilesListWithFormatTitle(pmf,12,6,28));
	
	request.setAttribute("productpic",CommonService.getLatestFilesList(pmf,Integer.parseInt(SystemConfig.ProductFileType),6));
	
	request.setAttribute("notices",NoticeService.getLatestNoticeList(pmf));
	
	request.setAttribute("productdir",CommonService.getLatestFilesList(pmf,3,5));
	
	request.setAttribute("annualTargets",SysParamService.queryParameters(pmf,"ndmbb",1).get(0).getValue().split("\n"));
	
	request.setAttribute("tradedata",CommonService.getLatestFilesList(pmf,13,6));
	
	request.setAttribute("epressdelivery",ExpressDeliveryService.getExpressDeliveryList(pmf,6));
	
%>
<body>
	<div id="header" class="header">
		<img src="images/header-bg.png">
	</div>
	<div id="menu">
		<div id="menumain">
<!-- 			<span class="item" style="margin-top:12px;" id="currentDate" class="Wdate" >当前日期：2013-03-01</span> -->
<!-- 			<span id="hiddendate" class="item" style="display:none;"></span> -->
<!-- 			<img class="item" id="popupDate" onClick="WdatePicker({el:'hiddendate'})" src="js/framework/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;margin-top:9px;display:none;" /> -->
			<span class="item">
				<a href="index.html" class="hidebg"><span class="hidebg">进入系统</span> </a>
			</span>
			<div class="cleaner"></div>
		</div>
	</div>
	<div id="contentPanel">
		<div id="content-main">
			<div class="content-wrapper">
			
				<div style="width:46%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>最新生产单</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${produce}" var="produceItem">
							<div class="information_box">
								<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
								<span style="float:left;margin:0px 5px;"><c:out value="${produceItem.number}" /></span>
								<span style="float:left;margin:0px 5px;">[<fmt:formatDate value="${produceItem.deliveryTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/>]</span>
								<!-- <span style="float:left;margin:0px 5px;">[<fmt:formatDate value="${produceItem.deliveryTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/>]</span>-->
								<span style="float:left;margin:0px 5px;">[<c:out value="${produceItem.saleman}" />]</span>
								<span style="float:left;margin:0px 5px;"><c:out value="${produceItem.destination}" /></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent('previewFiles','<c:out value="${produceItem.fileId}" />');">预览打印</a></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${produceItem.number}" />'+'.xls','<c:out value="${produceItem.fileId}" />');">下载</a></span>
								<div class="cleaner"></div>
							</div>
						</c:forEach>
						<div class="cleaner"></div>
						
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<div style="width:36%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>最新产品图片</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<div>
							<table>
								<c:forEach items="${productpic}" var="pp" varStatus="vs">
									<c:if test="${vs.index%3==0}">
										<tr height="120px">
									</c:if>
									<td>
									<a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${pp.description}" />','<c:out value="${pp.id}" />');">
										<div style="width:100px;height:100px;overflow:hidden;">
									        <img src="<c:out value="${pp.webpath}" />" onload="imagezoom.call(this, 100, 100);" />
									    </div>
<!-- 										<img height="100px" -->
<%-- 											src="<c:out value="${pp.webpath}" />" /> --%>
									</a>
									</td>
									<c:if test="${vs.index%3==2}">
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</div>

						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<div style="width:18%;padding-top:20px;" class="content-div">
					<div id="cal"></div>
					<div id="currentdate" style="margin:10px 0px 0px 0px;TEXT-ALIGN: center;">当前日期：8888-88-88</div>
					<div id="currenttime" style="margin:10px 0px 0px 0px;TEXT-ALIGN: center;">当前时间：88:88:88</div>
					<div id="currentweek" style="margin:10px 0px 0px 0px;TEXT-ALIGN: center;">星期一</div>
				</div>
				
				<div class="cleaner"></div>
				
				<div class="content-div" style="width:30%">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>年度目标榜</h2>
							</div>
<!-- 							<div style="float: right; margin-top: 5px;"> -->
<!-- 								<h3> -->
<!-- 									<a href="###">更多</a> -->
<!-- 								</h3> -->
<!-- 							</div> -->
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${annualTargets}" var="at" >
						<div class="information_box"><c:out value="${at}" ></c:out></div>
						</c:forEach>
<!-- 						<div class="information_box"> -->
<!-- 							<a href='###'>infot</a><span>[yyy-mm-dd]</span> -->
<!-- 						</div> -->
<!-- 						<div class="information_box"> -->
<!-- 							<a href='###'>infot</a><span>[yyy-mm-dd]</span> -->
<!-- 						</div> -->

						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<div class="content-div" style="width:30%">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>通知</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${notices}" var="notice" >
						<div class="information_box">
							<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
							<a href="javascript:showNotice('<c:out value="${notice.title}"></c:out>','<c:out value="${notice.id}"></c:out>','<c:out value="${notice.fileid}"></c:out>');">${notice.title}</a>
							<span>[<fmt:formatDate value="${notice.date}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/>]</span>
							<input type="hidden" id="<c:out value="notice-${notice.id}"></c:out>" value="<c:out value="${notice.content}"></c:out>">
						</div>
						</c:forEach>
						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				<!-- 
				<div style="width:30%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>外贸资料模版</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${tradedata}" var="tdata">
							<div class="information_box">
								<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
								<span style="float:left;"><c:out value="${tdata.name}" /></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent('previewFiles','<c:out value="${tdata.id}" />');">打印预览</a></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${tdata.description}" />','<c:out value="${tdata.id}" />');">下载</a></span>
								<div class="cleaner"></div>
							</div>
						</c:forEach>
						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				-->
				 
				<div class="content-div" style="width:40%">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>产品目录</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<div class="information_box">
							<input type="text" id="pdsearchname" name="name" /><button id="pdsearchbutton">搜索</button>
						</div>
						<div id="productDirItems">
							<c:forEach items="${productdir}" var="pd" >
								
								<div class="information_box">
									<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
									<span style="float:left;margin:0px 5px;"><a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${pd.description}" />','<c:out value="${pd.id}" />');">${pd.name}[<fmt:formatDate value="${pd.date}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/>]</a></span>
									<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent('previewFiles','<c:out value="${pd.id}" />');">预览打印</a></span>
									<div class="cleaner"></div>
								</div>
							</c:forEach>
						</div>

						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>

				<div class="cleaner"></div>
				
				<div style="width:35%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>产品测试报告</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${producttestreport}" var="ptr">
							<div class="information_box">
								<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
								<span style="float:left;"><a title="<c:out value="${ptr.relatedDescription}" />"><c:out value="${ptr.name}" /></a></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent('previewFiles','<c:out value="${ptr.id}" />');">预览打印</a></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${ptr.description}" />','<c:out value="${ptr.id}" />');">下载</a></span>
								<div class="cleaner"></div>
							</div>
						</c:forEach>
<!-- 						<div class="information_box"> -->
<!-- 							<a href='###'>infot</a><span>[yyy-mm-dd]</span> -->
<!-- 						</div> -->
<!-- 						<div class="information_box"> -->
<!-- 							<a href='###'>infot</a><span>[yyy-mm-dd]</span> -->
<!-- 						</div> -->

						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<div style="width:35%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>快递人员名单</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${epressdelivery}" var="ed">
							<div class="information_box">
								<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
								<span style="float:left;margin:0px 5px;">[<c:out value="${ed.company}" />]</span>
								<span style="float:left;margin:0px 5px;"><c:out value="${ed.contact}" /></span>
								<span style="float:left;margin:0px 10px;">[<c:out value="${ed.phone}" />]</span>
								<div class="cleaner"></div>
							</div>
						</c:forEach>
						<div class="cleaner"></div>
						
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<!--  -->
				<div style="width:30%;" class="content-div">
					<div id="informations" class="sidebar_box myborder a">
					<div class="myborder b">
					<div class="sidebar_box_height myborder c">
					<div style="padding:10px;">
						<div class="information_box bottomborder">
							<div style="float: left;">
								<h2>外贸资料模版</h2>
							</div>
							<div style="float: right; margin-top: 5px;">
								<h3>
									<a href="index.html">更多</a>
								</h3>
							</div>
							<div style="clear: both;"></div>
						</div>
						<c:forEach items="${tradedata}" var="tdata">
							<div class="information_box">
								<span style="float:left;"><img style="margin:6px 4px 0px 0px;" src="css/framework/images/tag.png"></span>
								<span style="float:left;"><c:out value="${tdata.name}" /></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new PreviewComponent().createPreviewComponent('previewFiles','<c:out value="${tdata.id}" />');">打印预览</a></span>
								<span style="float:right;margin-right:7px;"><a href="javascript:new DownloadComponent().createDownloadComponent('homepageForm','downloadFiles','<c:out value="${tdata.description}" />','<c:out value="${tdata.id}" />');">下载</a></span>
								<div class="cleaner"></div>
							</div>
						</c:forEach>
						<div class="cleaner"></div>
					</div>
					</div>
					</div>
					</div>
				</div>
				
				<div class="cleaner"></div>
				
			</div>
		</div>
	</div>
<form id="homepageForm"></form>
</body>
</html>
