<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
	var validationConfig = {namespace:['nv','uv'],selectMethod:$,parseOnload:true};
	$(document).ready(function() {
		document.title="课程管理";
		$('#delete')[0].onclick = function() {
			$('#confirm').modal('show')
		};
		$('#add')[0].onclick = function() {
			$('#addModal').modal('show')
		};
		$('#edit')[0].onclick = function() {
			var form = $("#updateCourse")[0];
			var selectRow = grid.getSelectedRows()[0];
			if(selectRow==null){
				alert("请选择课程再编辑!");
				return;
			}
			form.id.value = selectRow.id;
			form.total.value=selectRow.total;
			form.name.value=selectRow.name;
			form.number.value=selectRow.number;
			form.startDate.value = selectRow.startDate;
			form.endDate.value = selectRow.endDate;
			form.teacher.value = selectRow.teacherId;
			$('#editModal').modal('show')
		};
		window.grid = new Grid({
			id:"courses",
			url:"${context}/course/list.do",
			getCriteria:function(){
				return {"teacher":$("#teacher")[0].value,name:$("#name")[0].value}
			},
			renderTo:"courses",
			page : ${page},
			selectMode:{mode:'single',field:"id",fieldName:""},
			structure:[
			           {field:"number",fieldName:"课程编号"},
			           {field:"name",fieldName:"课程名称"},
			           {field:"startDate",fieldName:"开始日期"},
			           {field:"endDate",fieldName:"结束日期"},
			           {field:"teacher",fieldName:"讲师"},
			           {field:"count",fieldName:"已报名人数",formatter:function(count,row){
			        	   var row = grid.getRow(row);
			        	   return row.count+"/"+row.total;
			           }},
			           {field:"id",fieldName:"操作",formatter:function(id,row){
			        	  return viewAllStudent(id);
			           }}
			           ]
		});
		grid.show();
	});
	function removeCourse(){
		var selectedRows = grid.getSelectedRows();
		$.ajax({url:"${context}/course/delete.do",data:{id:selectedRows[0].id},success:function(result){
			if(result.success){
				alert("删除成功");
				window.location.href="${context}/course/list.do";
			}else{
				alert("删除失败,原因："+result.message);
			}
		}});
	}
	function insert(){
		var form = $("#newCourse")[0];
		if(nv.validate()){
			$.ajax({
				url:"${context}/course/insert.do",
				data:{
					name:form.name.value,
					number:form.number.value,
					teacher:form.teacher.value,
					total:form.total.value,
					startDate:form.startDate.value,
					endDate:form.endDate.value,
				},
				success:function(result){
					if(result.success){
						alert("新增成功");
						window.location.href="${context}/course/list.do";
					}else{
						alert("新增失败,原因："+result.message);
					}
				}
				
			});
		}
	}
	function validateTeacher(){}
	function update(){
		var form = $("#updateCourse")[0];
		if(uv.validate()){
			$.ajax({
				url:"${context}/course/update.do",
				data:{
					id:form.id.value,
					teacher:form.teacher.value,
					total:form.total.value,
					startDate:form.startDate.value,
					endDate:form.endDate.value,
				},
				success:function(result){
					if(result.success){
						alert("更新成功");
						window.location.href="${context}/course/list.do";
					}else{
						alert("更新失败,原因："+result.message);
					}
				}
				
			});
		}
	}
	function viewAllStudent(id){
		return "<button class='btn btn-default'>查看详情</button>"
	}
	
</script>

<h1>课程管理</h1>
<div>
	<form class="form-inline" action="${context }/course/list.do" method="POST">
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
	<button type="button" id="add" class="btn btn-primary"
		data-toggle="modal">新增</button>
	<button type="button" id="edit" class="btn btn-primary"
		data-toggle="modal">编辑</button>
	<button type="button" id="delete" class="btn btn-primary"
		data-toggle="modal">删除</button>
</div>
<br />
<div>
	<div id="courses"></div>
</div>

<!-- 新增课程 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="addLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">新增课程</h4>
			</div>
			<div class="modal-body">
				<h3>新增课程</h3>
				<form id="newCourse" >
					<div class="row">
						<div class="form-group col-md-11">
							<label for="courseName">课程编号: </label> <input type="text"
								class="form-control" id="ncNumber" name="number"  nv="true"  nv-onKeyup="true" placeholder="请输入课程编号">
						</div> 
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="courseName">课程名称: </label> <input type="text"
								class="form-control" id="ncName" name="name" nv="true"  nv-onKeyup="true" placeholder="请输入课程名称">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="startDate">开始日期: </label> <input type="text"
								class="form-control" id="ncStartDate"" nv="true" nv-method="date"  name="startDate" nv-onKeyup="true" placeholder="请输入开始日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="endDate">结束日期: </label> <input type="text"
								class="form-control" id="ncEndDate"" nv="true" nv-method="date" name="endDate" nv-onKeyup="true" placeholder="请输入结束日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="total">人数: </label> <input type="text"
								class="form-control" id="ncTotal"" nv="true"  nv-method="number" nv-max="999" nv-onKeyup="true" nv-min="1" name="total" placeholder="请输入人数">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="teacher">讲师: </label> <select id="ncTeacher" class="form-control" nv-onChange="true" nv="true" nv-message="请选择讲师" nv-method="validateSelect" name="teacher">
							<option value="-1">--请选择--</option>
							<c:forEach items="${teachers}" var="teacher">
							<option value="${teacher.id }">${teacher.fullName }</option>
						 	</c:forEach>
						</select>
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="insert()">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 编辑课程 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="editLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">编辑课程</h4>
			</div>
			<div class="modal-body">
				<h3>编辑课程</h3>
				<form id="updateCourse">
					<div class="row">
						<div class="form-group col-md-11">
							<label for="courseName">课程编号: </label> <input type="text"
								class="form-control" id="ucNumber" name="number" disabled  placeholder="请输入课程编号">
								<input type="hidden" name="id"/>
						</div>
					</div>
				<div class="row">
					<div class="form-group col-md-11">
						<label for="name">课程名称: </label> <input type="text"
							class="form-control" id="ucName" value="" name="name" disabled 
							placeholder="请输入课程名称">
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-11">
						<label for="startDate">开始日期: </label> <input type="text"
							class="form-control" id="ucStartDate" name="startDate" uv="true" uv-method="date" uv-onKeyup="true" placeholder="请输入开始日期">
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-11">
						<label for="endDate">结束日期: </label> <input type="text"
							class="form-control" id="ucEndDate" name="endDate" uv="true" uv-method="date" uv-onKeyup="true" placeholder="请输入结束日期">
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-11">
						<label for="total">人数: </label> <input type="text"
							class="form-control" id="ucTotal" name="total" uv="true" uv-method="number" uv-onKeyup="true" uv-min="1" uv-max="999" placeholder="请输入人数">
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-8">
						<label for="teacher">讲师: </label> 
						<select class="form-control" id="ucTeacher"  uv-method="validateSelect" uv-onChange="true" uv-message="请选择讲师" name="teacher" uv="true">
							<option value="-1">--请选择--</option>
							<c:forEach items="${teachers}"  var="teacher">
							<option value="${teacher.id }">${teacher.fullName }</option>
						 	</c:forEach>
						</select>
					</div>
				</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="update();">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 确认删除弹出 -->
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
				<p>是否要删除该记录?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="javascript:removeCourse();">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<jsp:include page="/pages/common/footer.jsp"></jsp:include>