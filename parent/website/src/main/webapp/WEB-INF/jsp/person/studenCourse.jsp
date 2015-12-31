<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
		document.title="我的课程";
		window.grid = new Grid({
			id:"courses",
			url:"${context}/person/myCourse.do",
			renderTo:"courses",
			page : ${page},
			structure:[
			           {field:"number",fieldName:"课程编号"},
			           {field:"name",fieldName:"课程名称"},
			           {field:"startDate",fieldName:"开始日期"},
			           {field:"endDate",fieldName:"结束日期"},
			           {field:"teacher",fieldName:"讲师"},
			           {field:"score",fieldName:"分数"}
			           ]
		});
		grid.show();
});
</script>
<h1>我的课程</h1>
<div>
	<div id="courses"></div>
</div>
<jsp:include page="/pages/common/footer.jsp"></jsp:include>