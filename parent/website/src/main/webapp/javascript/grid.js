/**
 * 基于Bootstrap样式的表格
 * 使用案例：
 * 			window.grid = new Grid({
 *			id:"courses",	// grid编号
 *			url:"${context}/course/list.do",
 *			getCriteria:function(){
 *				return {"teacher":$("#teacher")[0].value,name:$("#name")[0].value}
 *			},
 *			selectMode:{mode:"single",field:"id",fieldName:""},// 选择模式，支持single, multiple 
 *			renderTo:"courses", // 渲染到指定ID的DOM组件中，建议是DIV
 *			page : {total:10,totalPage:1,pageNumber:1,pageSize:10,start:1,result:[{number:"XXX",name:"随便",startDate:"2015-1-1",endDate:"2016-1-1","teacher":"张三",id:"1"}]},//数据实体
 *			selectMode:{mode:'single',field:"id",fieldName:""},
 *			structure:[
 *			           {field:"number",fieldName:"课程编号"},
 *			           {field:"name",fieldName:"课程名称"},
 *			           {field:"startDate",fieldName:"开始日期"},
 *			           {field:"endDate",fieldName:"结束日期"},
 *			           {field:"teacher",fieldName:"讲师"},
 *			           {field:"count",fieldName:"已报名人数",formatter:function(count,row){
 *			        	   var row = grid.getRow(row);
 *			        	   return row.count+"/"+row.total;
 *			           }},
 *			           {field:"id",fieldName:"操作",formatter:function(id,row){
 *			        	  return viewAllStudent(id);
 *			           }}
 *			           ] // 表格结构
 *		});
 *		grid.show();
 *
 *提供接口:
 *		grid.getSelectedRows(); //获取所有已经选中的行
 *		grid.show();			//显示表格
 */
var Grid = function(config){
	
	this.init(config);
	return {
		config:this.config,
		show:this.show,
		getRow:this.getRow,
		goTo:this.goTo,
		getSelectedRows:this.getSelectedRows,
		_generageHeader : this.generageHeader,
		_generateRows : this.generateRows,
		_generatePagination : this.generatePagination,
		_initEvent:this.initEvent 
	};
};
Grid.prototype.init = function(config){
	this.config = config;
};
Grid.prototype.show = function(){
	var config = this.config;
	
	var html = "<table class='table table-hover'>{caption}{head}{body}</table>";
	html = html.replace("{head}",this._generageHeader());
	if(config.page.result.length>0)
		html = html.replace("{body}",this._generateRows());
	else
		html = html.replace("{body}","");
	if(typeof config.caption!="undefined")
		html = html.replace("{caption}",config.caption);
	else
		html = html.replace("{caption}","");
	if(config.page.result.length==0){
		html+="<div>无记录</div>"
	}else{
		html+=this._generatePagination();
	}
	document.getElementById(config.renderTo).innerHTML = html;
	this._initEvent();
};

Grid.prototype.generageHeader = function(){
	var selectMode = this.config.selectMode;
	var structure = this.config.structure;
	var head = "<tr>";
	if(typeof selectMode!="undefined"){
		head+="<th>"+selectMode.fieldName+"</th>";
	}
	for(var i=0;i<structure.length;i++){ 
		var struct = structure[i];
		head += "<th>"+struct.fieldName+"</th>";
	}
	head+="</tr>";
	return head;
};

Grid.prototype.generateRows = function(){
	var page = this.config.page;
	var selectMode = this.config.selectMode;
	var structure = this.config.structure;
	var body = "";
	for(var i=0;i<page.result.length;i++){
		body += "<tr>";
		if(typeof selectMode!="undefined"){
			if(selectMode.mode=="single")
				body+="<td><input type='radio' name='"+this.config.id+"_select' value='"+page.result[selectMode.field]+"' id='"+this.config.id+"_select"+i+"'></td>";
			if(selectMode.mode=="multiple")
				body+="<td><input type='radio' name='"+this.config.id+"_select' value='"+page.result[selectMode.field]+"' id='"+this.config.id+"_select"+i+"'></td>";
		}
		for(var j=0;j<structure.length;j++){
			var struct = structure[j];
			if(typeof struct.formatter=="undefined"){ 
				body += "<td>"+page.result[i][struct.field]+"</td>";
			}else{
				body +="<td>"+struct.formatter(page.result[i][struct.field],i)+"</td>";
			}
		}
		body+="</tr>";
	}
	
	return body;
};
Grid.prototype.generatePagination = function(){
	var page = this.config.page;
	var pagination = "<nav><ul class='pagination'>";
	var currentPage = page.pageNumber;
	var total = page.total;
	var totalPage = page.totalPage;
	var pageSize = page.pageSize;
	var start = page.start;
	if(totalPage>1 && currentPage>1){
		pagination+='<li><a href="javascript:void(0)" pa="pre" aria-label="Previous"> <spanaria-hidden="true">&laquo;</span></a></li>';
	}
	var len = totalPage-start>=(pageSize-1)?(start+pageSize-1):totalPage
	for(var i=start;i<=len;i++){
		pagination+='<li '+(i==currentPage?'class="active"':'')+'><a href="javascript:void(0)" pa="'+i+'" >'+i+'</a></li>';
	}
	if(totalPage>1 && currentPage<totalPage){
		pagination+='<li><a href="javascript:void(0)" pa="next" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>'
	}
	pagination+="</ul></nav>";
	return pagination;
};

Grid.prototype.goTo = function(pageNumber){
	var data = {};
	if(typeof this.config.getCriteria=="function"){
		data = this.config.getCriteria();
	}
	data.pageNumber = pageNumber;
	data.pageSize = this.config.page.pageSize;
	var params = "";
	for(var key in data){
		params+=(key+"="+data[key])+"&";
	}
	window.location.href=this.config.url+"?" + params;
};

Grid.prototype.getRow = function(rowNumber){
	return this.config.page.result[rowNumber];
};

Grid.prototype.getSelectedRows = function(){
	var sr = []; 
	var selects = $("input[id^="+this.config.id+"_select]");
	for(var i=0;i<selects.length;i++){
		if(selects[i].checked==true){ 
			sr.push(this.getRow(i));
		}
	}
	return sr;
};
Grid.prototype.initEvent = function(){
	var pageLinks = $(".pagination>li>a");
	for(var i=0;i<pageLinks.length;i++){
		var pa = pageLinks[i].getAttribute("pa");
		if(pa=='pre'){
			var _self = this;
			pageLinks[i].onclick = function(event){
				_self.goTo(_self.config.page.pageNumber-1);
			}
		}else if(pa=='next'){
			var _self = this;
			pageLinks[i].onclick = function(event){
				_self.goTo(_self.config.page.pageNumber+1);
			}
		}else{
			var _self = this;
			if(parseInt(pa)==this.config.page.pageNumber){
				continue;
			}
			pageLinks[i].onclick = function(event){
				var pageNumber = event.target.getAttribute("pa");
				_self.goTo(pageNumber);
			}
		}
	}
};
