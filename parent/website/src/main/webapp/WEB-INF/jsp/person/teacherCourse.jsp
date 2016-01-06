<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/pages/common/header.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
		document.title="我的课程";
		window.grid = new Grid({
			id:"courses",
			url:"${context}/person/getCourse.do",
			renderTo:"courses",
			page : {pageSize:10},
			structure:[
			           {field:"number",fieldName:"课程编号"},
			           {field:"name",fieldName:"课程名称"},
			           {field:"startDate",fieldName:"开始日期"},
			           {field:"endDate",fieldName:"结束日期"},
			           {field:"classroom",fieldName:"上课地点"},
			           {field:"startTime",fieldName:"开始时间"},
			           {field:"endTime",fieldName:"结束时间"},
			           {field:"count",fieldName:"已报名人数",formatter:function(count,row){
			        	   var row = grid.getRow(row);
			        	   return row.count+"/"+row.total;
			           }},
			           {field:"id",fieldName:"操作",formatter:function(id,row){
			        	   if(grid.getRow(row).count==0){
			        		   return "-";
			        	   }
			        		   
			        	   return "<button onclick='viewMyStudent(\""+id+"\")' class='btn btn-default'>我的学生</button>";
			           }}
			           ]
		});
		grid.show();
});
function printPage(){
	doPrint({
		id:"myCourse"
	});
}
function updateScore(id,courseId){
	var score = $("#"+id+"_score")[0].value;
	$.ajax({
		url:"${context}/person/updateScore.do",
		data:{courseId:courseId,score:score,studentId:id},
		success:function(result){
			if(result.success){
				alert("设置成功");
				$("#container_"+id)[0].parentNode.innerHTML = score;
			}else{
				alert(result.message);
			}
		}
	});
}
function viewMyStudent(courseid){
	window.myStudents = new Grid({
		id:"myStudents",
		url:"${context}/person/getMyStudents.do",
		renderTo:"myStudents",
		page : {},
		getCriteria:function(){
			return {id:courseid}
 		},
		structure:[
		           {field:"fullName",fieldName:"姓名"},
		           {field:"department",fieldName:"学院"},
		           {field:"major",fieldName:"专业"},
		           {field:"id",fieldName:"操作",formatter:function(id,row){
		        	   var p = myStudents.getRow(row);
		        	   var html = "";
		        	   if(typeof p.score=='undefined' || p.score==0){
		        		   html = '<div id="container_'+id+'" class="form-inline form-group">'+
		        		    '<input type="text" class="form-control" style="width:80px;" value="'+myStudents.getRow(row).score+'" maxLength=2 id="'+id+'_score"><button onclick="updateScore(\''+id+'\',\''+courseid+'\')" style="margin-left:10px" class="btn btn-default">提交分数</button>'+
		        		  	'</div>';
		        	   }else{
		        		   html =p.score;
		        	   }
		        	   return html;
		           }}
		           ]
	});
	myStudents.show();
	$('#myStudent').modal('show');
}
</script>
<div id="myCourse">
<div>
<button type="button" print-visible="false" id="add" class="btn btn-primary"
		onclick="printPage();" style="float:right">打印</button>
		<br/>
</div>
<h1>我的课程</h1>
<div>
	<div id="courses"></div>
</div>
</div>

<div class="modal fade" id="myStudent" tabindex="-1" role="dialog"
	aria-labelledby="myStudentLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">我的学生</h4>
			</div>
			<div class="modal-body">
				<div id="myStudents"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<jsp:include page="/pages/common/footer.jsp"></jsp:include>