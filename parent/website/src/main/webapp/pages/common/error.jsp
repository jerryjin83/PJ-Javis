<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>

<link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<style>
</style>
<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$.support.transition = true;
	function selectMenu(event) {
		var li = event.target.parentNode;
		var lis = $(".navbar-nav>li");
		for (var i = 0; i < lis.length; i++) {
			if (li.outerHTML == lis[i].outerHTML) {
				lis[i].setAttribute("class", "active");
				window.location.href = $(lis[i]).find("a")[0]
						.getAttribute("menu-url");
			} else {
				lis[i].removeAttribute("class");
			}
		}
	}
	function doLogin(){
		$("#loginForm").submit();
	}
	function clearError(){
		var error = $("#error")[0];
		error.parentNode.removeChild(error);
	}
	$(document).ready(function() {
		var lis = $(".navbar-nav>li");
		for (var i = 0; i < lis.length; i++) {
			lis[i].onclick = selectMenu;
		}
		$(".navbar-brand")[0].onclick = function() {
			window.location.href = "<%=request.getContextPath()%>/pages/welcome.html";
		};
	});
</script>
</head>
<body role="document">
	<nav class="navbar navbar-inverse ">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">选课管理系统</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="navbar">
				<ul class="nav navbar-nav">
					<c:if test="${isSignin }">
						<li><a menu-url="pages/chooseCourses.html" href="#">在线选课</a></li>
						<li><a menu-url="pages/studentManagement.html" href="#">学生信息管理</a></li>
						<li><a menu-url="pages/teacherManagement.html" href="#">教师信息管理</a></li>
						<li><a menu-url="courseManagement/list.htm" href="#">课程管理</a></li>
					</c:if>
				</ul>

				<c:choose>
					<c:when test="${isSignin }">
						<p class="navbar-text navbar-right">
							欢迎，<a href="#" class="navbar-link">XXX</a> <a href="#"
								style="margin-left: 10px;" class="navbar-link">退出</a>
						</p>
					</c:when>
					<c:when test="${isSignin==null}">
						<form id="loginForm"
							action="<%=request.getContextPath()%>/system/login.do"
							method="POST" class="navbar-form navbar-right">
							<span class="navbar-tex">欢迎&nbsp;</span>
							<div class="form-group">
								<input type="text" name="username" id="username"
									class="form-control" placeholder="请输入用户名">
							</div>
							<div class="form-group">
								<input type="password" name="password" id="password"
									class="form-control" placeholder="请输入密码">
							</div>
							<a href="#" onclick="javascript:doLogin();"
								style="margin-left: 10px;" class="navbar-link">登录</a>
						</form>
					</c:when>
				</c:choose>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="container" role="main">
		<c:choose>
		<c:when test="${errorMessage!=null}">
			<div class="alert alert-danger" role="alert" id="error">
				<div class="row">
					<div class="col-md-11">${errorMessage }</div>
					<div class="col-md-1">
						<a href="javascript:void(0)" onclick="javascript:clearError();">关闭</a>
					</div>
				</div>
			</div>
		</c:when>
		<c:when test="${errorMessage==null}">
		<div class="alert alert-danger" role="alert" id="error">
				<div class="row">
					<div class="col-md-11">未知错误，请联系管理员!</div>
					<div class="col-md-1">
						<a href="javascript:void(0)" onclick="javascript:clearError();">关闭</a>
					</div>
				</div>
			</div>
		</c:when>
		</c:choose>
		<center>
			<a href="<%=request.getContextPath() %>/"> 点击此处返回主页 </a>
		</center>
	</div>
</body>
</html>
