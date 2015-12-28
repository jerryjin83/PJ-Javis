<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		document.title="课程管理";
		$('#delete')[0].onclick = function() {
			$('#confirm').modal('show')
		};
		$('#add')[0].onclick = function() {

			$('#addModal').modal('show')
		};
		$('#edit')[0].onclick = function() {

			$('#editModal').modal('show')
		};
	});
</script>

<h1>课程管理</h1>
<div>
	<form class="form-inline">
		<div class="form-group">
			<label for="exampleInputName2">课程名称: </label> <input type="text"
				class="form-control" id="courseName" placeholder="请输入课程名称">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail2">讲师: </label> <input type="email"
				class="form-control" id="teacher" placeholder="请输入讲师姓名">
		</div>
		<button type="button" class="btn btn-default">查询</button>
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
	<table class="table table-hover">
		<tr>
			<th></th>
			<th>课程编号</th>
			<th>课程名称</th>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>讲师</th>
			<th>已报名人数</th>
			<th>操作</th>
		</tr>
		<tr>
			<td>
				<div class="radio">
					<label> <input type="radio" name="course">
					</label>
				</div>
			</td>
			<td>CX01</td>
			<td>C语言</td>
			<td>2015-12-25</td>
			<td>2016-4-1</td>
			<td>谭浩强</td>
			<td>1/200</td>
			<td><button type="button" class="btn btn-default">查看已选名单</button></td>
		</tr>
		<tr>
			<td>
				<div class="radio">
					<label> <input type="radio" name="course">
					</label>
				</div>
			</td>
			<td>GS01</td>
			<td>高等数学（1）</td>
			<td>2015-12-25</td>
			<td>2016-4-1</td>
			<td>张捷</td>
			<td>12/200</td>
			<td><button type="button" class="btn btn-default">查看已选名单</button></td>
		</tr>
	</table>
	<nav>
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
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
				<form>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="courseName">课程名称: </label> <input type="text"
								class="form-control" id="courseName" placeholder="请输入课程名称">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="startDate">开始日期: </label> <input type="text"
								class="form-control" id="startDate" placeholder="请输入开始日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="endDate">结束日期: </label> <input type="text"
								class="form-control" id="endDate" placeholder="请输入结束日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="total">人数: </label> <input type="text"
								class="form-control" id="total" placeholder="请输入人数">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="teacher">讲师: </label> <input type="text"
								class="form-control" id="teacher" placeholder="请选择讲师">
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">确认</button>
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
				<form>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="courseName">课程名称: </label> <input type="text"
								class="form-control" value="C语言" disabled id="courseName"
								placeholder="请输入课程名称">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="startDate">开始日期: </label> <input type="text"
								class="form-control" id="startDate" placeholder="请输入开始日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="endDate">结束日期: </label> <input type="text"
								class="form-control" id="endDate" placeholder="请输入结束日期">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="total">人数: </label> <input type="text"
								class="form-control" id="total" placeholder="请输入人数">
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-8">
							<label for="teacher">讲师: </label> <input type="text"
								class="form-control" id="teacher" placeholder="请选择讲师">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">确认</button>
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
				<button type="button" class="btn btn-primary">确认</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<jsp:include page="/pages/common/footer.jsp"></jsp:include>