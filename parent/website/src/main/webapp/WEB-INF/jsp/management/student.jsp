<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
	var validationConfig = {namespace:['nv','uv'],selectMethod:$,parseOnload:true};
	$(document).ready(function() {
		document.title="学生信息管理";
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
				alert("请选择某位学生再编辑!");
				return;
			}
			form.id.value = selectRow.id;
			form.username.value=selectRow.username;
			form.fullName.value=selectRow.fullName;
			form.gander.value=selectRow.gander;
			form.birthdate.value = selectRow.birthdate;
			form.department.value = selectRow.department;
			form.major.value = selectRow.major;
			$('#editModal').modal('show')
		};
		window.grid = new Grid({
			id:"students",
			url:"${context}/management/student.do",
			getCriteria:function(){
				return {"name":$("#name")[0].value}
			},
			renderTo:"students",
			page : ${page},
			selectMode:{mode:'single',field:"id",fieldName:""},
			structure:[
			           {field:"username",fieldName:"用户名"},
			           {field:"fullName",fieldName:"姓名"},
			           {field:"gander",fieldName:"性别"},
			           {field:"birthdate",fieldName:"生日"},
			           {field:"department",fieldName:"学院"},
			           {field:"major",fieldName:"专业"},
			          ]
		});
		grid.show();
		$('#ncBirthdate').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			format:'yyyy-mm-dd'
	    });
		$('#ucBirthdate').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			format:'yyyy-mm-dd'
	    });
	});
	function removeCourse(){
		var selectedRows = grid.getSelectedRows();
		$.ajax({url:"${context}/management/delete.do",data:{id:selectedRows[0].id},success:function(result){
			if(result.success){
				alert("删除成功");
				window.location.href="${context}/management/student.do";
			}else{
				alert("删除失败,原因："+result.message);
			}
		}});
	}
	function insert(){
		var form = $("#newStudent")[0];
		if(nv.validate()){
			$.ajax({
				url:"${context}/management/insert.do",
				data:{
					username:form.username.value,
					fullName:form.fullName.value,
					gander:form.gander.value,
					birthdate:form.birthdate.value,
					type:form.type.value,
					department:form.department.value,
					major:form.major.value,
				},
				success:function(result){
					if(result.success){
						alert("新增成功");
						window.location.href="${context}/management/student.do";
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
				url:"${context}/management/update.do",
				data:{
					id:form.id.value,
					gander:form.gander.value,
					birthdate:form.birthdate.value,
					department:form.department.value,
					major:form.major.value,
				},
				success:function(result){
					if(result.success){
						alert("更新成功");
						window.location.href="${context}/management/student.do";
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

<h1>学生信息管理</h1>
<div>
	<form class="form-inline" action="${context}/management/student.do" method="POST">
		<div class="form-group">
			<label for="exampleInputName2">学生姓名: </label> <input type="text"
				class="form-control" id="name" name="name" value="${name }" placeholder="请输入学生姓名">
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
	<div id="students"></div>
</div>

<!-- 新增学生 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="addLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">新增学生</h4>
			</div>
			<div class="modal-body">
				<h3>新增学生</h3>
				<form id="newStudent" >
					<input type="hidden" name="type" value="3"/>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="username">用户名: </label> <input type="text"
								class="form-control" id="ncID" name="username"  nv="true"  nv-onKeyup="true" placeholder="请输入用户名">
						</div> 
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="fullName">姓名: </label> <input type="text"
								class="form-control" id="ncName" name="fullName" nv="true"  nv-onKeyup="true" placeholder="请输入姓名">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="gander">性别: </label> <select id="ncGander" class="form-control" nv-onChange="true" nv="true" nv-message="请选择性别" nv-method="validateSelect" name="gander">
							<option value="-1">--请选择--</option>
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="birthdate">生日: </label> <input type="text"
								class="form-control" id="ncBirthdate" nv="true" nv-method="date"  name="birthdate" nv-onKeyup="true" placeholder="请输入生日">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="department">学院: </label> <input type="text"
								class="form-control" id="ncDepartment" name="department" nv="true"  nv-onKeyup="true" placeholder="请输入所在学院">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="major">专业: </label> <input type="text"
								class="form-control" id="ncMajor" name="major" nv="true"  nv-onKeyup="true" placeholder="请输入所在专业">
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

<!-- 编辑学生信息 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="editLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">编辑学生信息</h4>
			</div>
			<div class="modal-body">
				<h3>编辑学生信息</h3>
				<form id="updateCourse">
					<input type="hidden" name="id">
					<div class="row">
						<div class="form-group col-md-11">
							<label for="username">用户名: </label> <input type="text" disabled
								class="form-control" id="ucID" name="username"  uv="true"  uv-onKeyup="true" placeholder="请输入用户名">
						</div> 
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="fullName">姓名: </label> <input type="text" disabled
								class="form-control" id="ucName" name="fullName" uv="true"  uv-onKeyup="true" placeholder="请输入姓名">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="gander">性别: </label> <select id="ucGander" class="form-control" uv-onChange="true" uv="true" uv-message="请选择性别" nv-method="validateSelect" name="gander">
							<option value="-1">--请选择--</option>
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="birthdate">生日: </label> <input type="text"
								class="form-control" id="ucBirthdate" uv="true" uv-method="date"  name="birthdate" uv-onKeyup="true" placeholder="请输入生日">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="department">学院: </label> <input type="text"
								class="form-control" id="ucDepartment" name="department" uv="true"  uv-onKeyup="true" placeholder="请输入所在学院">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-11">
							<label for="major">专业: </label> <input type="text"
								class="form-control" id="ucMajor" name="major" uv="true"  uv-onKeyup="true" placeholder="请输入所在专业">
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