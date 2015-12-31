<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
var courseId;
$(document).ready(function() {
		document.title="在线选课";
		window.grid = new Grid({
			id:"courses",
			url:"${context}/person/pickupCourse.do",
			getCriteria:function(){
				return {"teacher":$("#teacher")[0].value,name:$("#name")[0].value}
			},
			renderTo:"courses",
			page : ${page},
			structure:[
			           {field:"number",fieldName:"课程编号"},
			           {field:"name",fieldName:"课程名称"},
			           {field:"startDate",fieldName:"开始日期"},
			           {field:"endDate",fieldName:"结束日期"},
			           {field:"teacher",fieldName:"讲师"},
			           {field:"count",fieldName:"已报名人数",formatter:function(count,row){
			        	   var row = grid.getRow(row);
			        	   return count+"/"+row.total;
			           }},
			           {field:"id",fieldName:"操作",formatter:function(id,row){
			        	  var row =  grid.getRow(row);
			        	  if(row.pickedup!=true){
			        	   		return "<button class='btn btn-default' onclick='courseId=\""+id+"\";$(\"#confirm\").modal(\"show\");'>选择</button>";
			        	  }else{
			        		  return "已选择";
			        	  }
			           }},
			           ]
		});
		grid.show();
});
function pickup(){
	$.ajax({
		url:'${context}/person/pickup.do',
		data:{id:courseId},
		success:function(result){
			if(result.success){
				alert("选择成功");
				window.location.href="${context}/person/pickupCourse.do";
			}else{
				alert("选择失败,原因："+result.message);
				$("#confirm").modal("hide");
			}
		}
	});
}
</script>
<h1>在线选课</h1>
<div>
	<form class="form-inline" action="${context}/person/pickupCourse.do" method="POST">
		<div class="form-group">
			<label for="exampleInputName2">课程名称: </label> <input type="text"
				class="form-control" id="name" name="name" value="${name }" placeholder="请输入课程名称">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail2">讲师: </label> <input type="text"
				class="form-control" id="teacher" name="teacher" value="${teacher }" placeholder="请输入讲师姓名">
		</div>
		<button type="submit" class="btn btn-default">查询</button>
	</form>
</div>
<hr />
<div>
	<div id="courses"></div>
</div>
<div class="modal fade" id="confirm" tabindex="-1" role="dialog"
	aria-labelledby="confirmLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
				<p>是否要选择该课?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="javascript:pickup();">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<jsp:include page="/pages/common/footer.jsp"></jsp:include>