var Grid = function(config){
	
	this.init(config);
	return {
		config:this.config,
		show:this.show,
		getRow:this.getRow,
		getSelectedRows:this.getSelectedRows,
		_generageHeader : this.generageHeader,
		_generateRows : this.generateRows,
		_initEvent:this.initEvent
	};
};
Grid.prototype.init = function(config){
	this.config = config;
};
Grid.prototype.show = function(){
	var config = this.config;
	
	var html = "<table class='table table-hover'>{caption}{head}{body}</table>";
	html = html.replace("{head}",this._generageHeader(config.structure));
	if(config.page.result.length>0)
		html = html.replace("{body}",this._generateRows(config.page,config.structure));
	else
		html = html.replace("{body}","");
	if(typeof config.caption!="undefined")
		html = html.replace("{caption}",config.caption);
	else
		html = html.replace("{caption}","");
	if(config.page.result.length==0){
		html+="<div>无记录</div>"
	}
	document.getElementById(config.renderTo).innerHTML = html;
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
				body +="<td>"+struct.formatter(page.result[struct.field],i)+"</td>";
			}
		}
		body+="</tr>";
	}
	
	return body;
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
