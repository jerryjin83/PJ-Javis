/**
* 	@author Jerry
* 	@version 1.0
* 
* 	Validation tool. 
*	var validation = new Validation({
*		formId:"", // optional
*		fields:[{ 
*			id:"", // mandatory
*			method:"email"|"number"|"regex"|"zipcode"|"mobile"other callback
*			regex:xxx // if method is regex, regex is necessary.
*			max:15, // if method is number
*			min:1,	// if method is number
*			required:true | {required:true,message:"Required"},
*			equalTo:"field2" | {id:"field2",message:"A must the same as B",widgetType:"dom"|"dojo"}, // this field should be equal to field2
*			notEqualTo:"field2"|{id:"field2",message:"Field2 and Field2 could not be the same value.",widgetType:"dom"|"dojo"},  // this field should not be equal to field2
*			onChange:true, // default is false
*			onBlur:true, // default is false
*			onKeyup:true, // default is false
*			message:"", //if it's invalid, then show the message
*			showMessage:function(){}, // customized
*			clearMessage:function(){}, // customized
*			widgetType:"dom"|"dojo",
*			associate:"id1,id2",// when validate this filed, all associated the field would also be validated
*		}],
*		onFormSubmit:true, // default is true
*	});
*	validation.validate();
*/
var Validation = function(config){
	var id = Math.round(Math.random()*1000000000);
	config.id = id;
	this._initialize(config);
	return {
		id:id,
		config:config,
		_self:this,
		_prototype:this.prototype,
		validate :this.validate,
		replaceMessage:this.replaceMessage,
		clearAll:this.clearAll
	};
};
Validation.prototype._defaultRule = {
	email:{regex:"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message:"Please input a valid email"},
	number:{regex:"^\\d+\\.*\\d*$",message:"Please input a valid number"},
	zipcode:{regex:"",message:"Please input a valid zipcode."},
	mobile:{regex:"",message:"Please input a valid mobile."},
	date:{regex:"^[0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}$",message:"Please input a valid date."},
	min:{message:"The value you input must be great than {0}" },
	max:{message:"The value you input must be less than {0}"},
	required:{message:"This field is required"},
	equalTo:{message:"{0} must be equal to {1}"},
	notEqualTo:{message:"{0} must be different with {1}"},
};
Validation.prototype._defaultFieldConfig = {
	widgetType:"dom",
	required:true,
	onKeyup:false,
	onChange:false,
	onBlur:false,
};
Validation.prototype._defaultConfig = {
	onFormSubmit : true,
	required:true
};
Validation.prototype.validate = function(){
	var self;
	self = this._self?this._self:this;
	var fields = self.config.fields;
	var canSubmit = true;
	for(var i=0;i<fields.length;i++){
		var result =  self._validateField(fields[i],self.config);
		canSubmit = canSubmit?result:canSubmit;
	}
	return canSubmit;
};

Validation.prototype.replaceMessage = function(id,message){
	var self;
	self = this._self?this._self:this;
	var fields = self.config.fields;
	for(var i=0;i<fields.length;i++){
		if(fields[i].id==id){
			fields[i].message = message;
		}
	}
};

Validation.prototype._initialize = function(config){
	if(config==undefined){
		throw new Error("Constructor parameter is necessary.");
	}
	this.config = config;
	for(var p in Validation.prototype._defaultConfig){
		if(config[p]==undefined){
			config[p]=Validation.prototype._defaultConfig[p];
		}
	}
	for(var i =0;i<this.config.fields.length;i++){
		var field = this.config.fields[i];
		for(var p in Validation.prototype._defaultFieldConfig){
			if(field[p]==undefined){
				field[p] = Validation.prototype._defaultFieldConfig[p];
			}
		}
	}
	this.config.rule = Validation.prototype._defaultRule;
	// override the default rule configuration
	if(Validation.prototype.defaultRule!=undefined){
		for(var p in Validation.prototype.defaultRule){
			this.config.rule[p] = Validation.prototype.defaultRule[p];
		}
	}
	Validation.prototype.showMessage = this.config.showMessage==undefined?this._showMessage:this.config.showMessage==undefined;
	if(!Validation.prototype.configs){
		Validation.prototype.configs = {};
	}
	Validation.prototype.configs[config.id] = this.config;
	this._registerFormEvent();
	this._registerAllFieldEvents();
};
Validation.prototype.$ = function(id){
	return document.getElementById(id);
};
Validation.prototype._getWidgetById = function(field){
	switch (field.widgetType){
		case "dom":
			return document.getElementById(field.id);
		case "dojo":
			return dijit.byId(field.id);
		default:
			return;
	}
};
Validation.prototype._getValue = function(field){
	switch (field.widgetType){
		case "dom":
			return document.getElementById(field.id).value;
		case "dojo":
			var domObj = dojo.byId(field.id);
			return domObj.tagName=='INPUT'?domObj.value:dijit.byId(field.id).value;
		default:
			return;
	}
};
Validation.prototype._registerFormEvent = function(){
	if(this.config.onFormSubmit){
		if(this.config.formId==undefined){
			return;
		}
		var form = this.$(this.config.formId);
		if(form ==undefined){
			throw new Error("Form " + this.config.formId +" not found.");
		}
		form.onsubmit = this.validate;
	}
};
Validation.prototype._registerFieldEvent = function(field){
	var obj = this._getWidgetById(field);
	switch (field.widgetType){
		case "dom":
			if(field.onKeyup){
				obj.onkeyup = this._validate;
			}
			if(field.onChange){
				obj.onchange = this._validate;
			}
			if(field.onBlur){
				obj.onblur = this._validate;
			}
			break;
		case "dojo":
			if(field.onChange){
				dojo.connect(obj,"onChange",this._validate);
			}
			if(field.onBlur){
				dojo.connect(obj,"onBlur",this._validate);
			}
			if(field.onKeyup){
				dojo.connect(obj,"onKeyUp",this._validate);
			}
			break;
		default:
			return;
	}
};
Validation.prototype._registerAllFieldEvents = function(){
	for(var i=0;i<this.config.fields.length;i++){
		var field = this.config.fields[i];
		this._registerFieldEvent(field);
	}
};
Validation.prototype._getFieldAndConfig = function(id){
	for(var cfid in this.configs){
		for(var i=0;i<this.configs[cfid].fields.length;i++){
			var field = this.configs[cfid].fields[i];
			if(field.id==id){
				return {field:field,config:this.configs[cfid]};
			}
		}
	}
	throw new Error("Field["+id+"] not found.");
};
Validation.prototype._validate = function(){
	var fieldId = this.id;
	var _prototype = Validation.prototype;
	var result = _prototype._getFieldAndConfig(fieldId);
	_prototype._validateField(result.field,result.config);
	if(result.field.associate!=undefined){
		var associateFields = result.field.associate.split(",");
		if(associateFields!=undefined){
			for(var i=0;i<associateFields.length;i++){
				var aresult = _prototype._getFieldAndConfig(associateFields[i]);
				_prototype._validateField(aresult.field,aresult.config);
			}
		}
	}
};
Validation.prototype._validateField = function(field,config){
	if(field.id==undefined){
		throw new Error("Field id is necessary");
	}
	var fieldValue = this._getValue(field);
	var isValid = true;
	if(isValid && field.required!=undefined){
		if(typeof(field.required)=="boolean" && field.required){
			isValid =this.isRequired(fieldValue);
			if(!isValid){
				this.showMessage(field,config.rule.required.message);
			}else{
				this.clear(field);
			}
		}else if(typeof(field.required)=="object" && (field.required.required=(field.required.required!=undefined)?field.required.required:this.config.required)){
			isValid =this.isRequired(fieldValue);
			if(!isValid){
				this.showMessage(field,field.required.message?field.required.message:config.rule.required.message);
			}else{
				this.clear(field);
			}
		}
	}
	if(isValid && field.equalTo!=undefined){
		if(typeof(field.equalTo)=="string"){
			isValid = this.isEqualTo(fieldValue,field,field.equalTo);
			if(!isValid){
				this.showMessage(field,config.rule.equalTo.message,[field.id,field.equalTo]);
			}else{
				this.clear(field);
			}
		}else if(typeof(field.equalTo)=="object"){
			isValid = this.isEqualTo(fieldValue,field,field.equalTo);
			if(!isValid){
				this.showMessage(field,field.equalTo.message?field.equalTo.message:config.rule.equalTo.message,[field.id,field.equalTo.field]);
			}else{
				this.clear(field);
			}
		}
		
	}
	if(isValid && field.notEqualTo!=undefined){
		if(typeof(field.notEqualTo)=="string"){
			isValid = this.isNotEqualTo(fieldValue,field,field.notEqualTo);
			if(!isValid){
				this.showMessage(field,config.rule.notEqualTo.message,[field.id,field.notEqualTo]);
			}else{
				this.clear(field);
			}
		}else if(typeof(field.notEqualTo)=="object"){
			isValid = this.isNotEqualTo(fieldValue,field,field.notEqualTo);
			if(!isValid){
				this.showMessage(field,field.notEqualTo.message?field.notEqualTo.message:config.rule.notEqualTo.message,[field.id,field.notEqualTo.field]);
			}else{
				this.clear(field);
			}
		}
	}
	if( isValid && field.method!=undefined){
		if(typeof(field.method)=="string"){
			switch(field.method){
				case "email":
					isValid = this.isEmail(fieldValue);
					break;
				case "number":
					isValid = this.isNumber(fieldValue);
					if(isValid && field.min!=undefined){
						isValid = parseInt(fieldValue)>=field.min;
						if(!isValid){
							this.showMessage(field,field.message?field.message:config.rule.min.message,[field.min]);
							return isValid;
						}
					}
					if(isValid && field.max!=undefined){
						isValid = parseInt(fieldValue)<=field.max;
						if(!isValid){
							this.showMessage(field,field.message?field.message:config.rule.max.message,[field.max]);
							return isValid;
						}
					}
					break;
				case "date":
					isValid = this.isDate(fieldValue,field.date);
					break;
				case "regex":
					isValid = this.isRegexMatched(fieldValue,field.regex);
					break;
				case "mobile":
					isValid = this.isMobile(fieldValue);
					break;
				case "zipcode":
					isValid = this.isZipcode(fieldValue);
					break;
			}
			if(!isValid){
				this.showMessage(field,field.message?field.message:config.rule[field.method].message);
			}else{
				this.clear(field);
			}
		}else if(typeof(field.method)=="function"){
			if(!(isValid=field.method(fieldValue))){
				this.showMessage(field,field.message?field.message:"");
			}else{
				this.clear(field);
			}
		}else{
			throw new Error("Field method must be a string or function.");
		}
	}
	return isValid;
};
// validate the fields is satisfied or not
Validation.prototype.equalTo=function(){
	
};
Validation.prototype.isEmail=function(fieldValue){
	return this.isRegexMatched(fieldValue,this._defaultRule.email.regex);
};
Validation.prototype.isNumber=function(fieldValue){
	return this.isRegexMatched(fieldValue,this._defaultRule.number.regex);
};
Validation.prototype.isMobile = function(fieldValue){
	return this.isRegexMatched(fieldValue,this._defaultRule.mobile.regex);
};
Validation.prototype.isZipcode = function(fieldValue){
	return this.isRegexMatched(fieldValue,this._defaultRule.zipcode.regex);
};
Validation.prototype.isDate=function(fieldValue){
	return this.isRegexMatched(fieldValue,this._defaultRule.date.regex);
};
Validation.prototype.isRegexMatched=function(fieldValue,regex){
	return new RegExp(regex).test(fieldValue);
};
Validation.prototype.isRequired=function(fieldValue){
	return fieldValue.trim()!="";
};
Validation.prototype.isEqualTo=function(fieldValue,field1,field2){
	var nv = null;
	if(typeof(field2)=="string"){
		nv = this.$(field2).value;
	}else if(typeof(field2)=="object"){
		if(field2.widgetType==undefined){
			field2.widgetType = field1.widgetType;
		}
		nv = this._getValue(field2);
	}else{
		throw new Error("equalTo is not a valid string or object.");
	}
	return fieldValue==nv;
};
Validation.prototype.isNotEqualTo=function(fieldValue,field1,field2){
	var nv = null;
	if(typeof(field2)=="string"){
		nv = this.$(field2).value;
	}else if(typeof(field2)=="object"){
		if(field2.widgetType==undefined){
			field2.widgetType = field1.widgetType;
		}
		nv = this._getValue(field2);
	}else{
		throw new Error("equalTo is not a valid string or object.");
	}
	return fieldValue!=nv;
};
Validation.prototype._generateErrorLableId = function(fieldId){
	return [fieldId,"error_label"].join("_");
};
Validation.prototype._generateErrorMsgId = function(fieldId){
	return [fieldId,"error_msg"].join("_");
};
Validation.prototype._showMessage =function(field,message,values){
	if(field.showMessage!=undefined){
		field.showMessage(field,message,values);
		return;
	}
	var msg = message;
	if(values){
		for(var i=0;i<values.length;i++){
			msg = msg.replace("{"+i+"}",values[i]);
		}
	}
	var fieldId = field.id;
	var f = null;
	if(field.widgetType=="dojo"){
		if(dojo.byId(fieldId).getAttribute("class").indexOf("dijitInputInner")!=-1){
			f = dojo.byId("widget_"+fieldId);
		}else{
			f = dojo.byId(fieldId);
		}
	}else{
		f = this.$(fieldId);
	}
	var oldClass = f.getAttribute("old-class");
	if(oldClass==undefined){
		oldClass = f.getAttribute("class")?f.getAttribute("class"):"";
		f.setAttribute("old-class",oldClass);
		f.setAttribute("class",oldClass+" validation-error-input");
	}
	var oldBorderColor = f.getAttribute("old-borderColor");
	if(oldBorderColor==undefined){
		oldBorderColor = f.style.borderColor;
		f.setAttribute("old-borderColor",oldBorderColor);
		f.style.borderColor="red";
	}
	// show error label
	var labelId = this._generateErrorLableId(fieldId);
	var label = this.$(labelId);
	if(label!=undefined){
		label.style.display = "none";
		label.style.display = "inline-block";
	}else{
		label = document.createElement("span");
		label.setAttribute("class","validation-error-label");
		label.setAttribute("id",labelId);
		label.innerHTML = "&nbsp;";
		f.parentNode.insertBefore(label,f.nextSibling);
	}
	
	
	
	if(msg.trim()!=""){
		var divId = this._generateErrorMsgId(fieldId);
		var div = this.$(divId);
		if(div!=undefined){
			if(div.style.display=="none"){
				div.innerHTML = msg;
			}else{
				var rect = label.getBoundingClientRect();
				var x = rect.left;
				var y = rect.top +25;
				div.innerHTML = msg;
				div.setAttribute("style","display:inline-block;top:"+y+"px;left:"+x+"px;");
			}
			
		}
		
		label.onmouseover = function(){
			var rect = label.getBoundingClientRect();
			var x = rect.left;
			var y = rect.top +25;
			if(div==undefined){
				div = document.createElement("div");
				div.setAttribute("class","validation-error-msg");
				div.setAttribute("id",divId);
				div.innerHTML = msg;
				div.setAttribute("style","display:inline-block;top:"+y+"px;left:"+x+"px;");
				div.setAttribute("z-index",1);
				f.parentNode.insertBefore(div,label.nextSibling);
			}else{
				div.innerHTML = msg;
				div.setAttribute("style","display:inline-block;top:"+y+"px;left:"+x+"px;");
			}
		};
		
		label.onmouseout = function(){
			if(div){
				div.innerHTML = msg;
				div.setAttribute("style","display:none");
			}
		};
	}
};
Validation.prototype.clearAll = function(){
	var self;
	self = this._self?this._self:this;
	var fields = self.config.fields;
		for(var i=0;i<fields.length;i++){
			self.clear(fields[i]);
		}
};
Validation.prototype.clear =function(field){
	if(field.clearMessage){
		field.clearMessage(field);
		return;
	}
	var fieldId = field.id;
	var f = null;
	if(field.widgetType=="dojo"){
		if(dojo.byId(fieldId).getAttribute("class").indexOf("dijitInputInner")!=-1){
			f = dojo.byId("widget_"+fieldId);
		}else{
			f = dojo.byId(fieldId);
		}
	}else{
		f = this.$(fieldId);
	}
	if(f.getAttribute("old-class")!=undefined){
			f.setAttribute("class",f.getAttribute("old-class"));
			f.removeAttribute("old-class");
	}
	if(f.getAttribute("old-borderColor")!=undefined){
		f.style.borderColor = f.getAttribute("old-borderColor");
		f.removeAttribute("old-borderColor");
	}
	var labelId = this._generateErrorLableId(fieldId);
	var label = this.$(labelId);
	if(label!=undefined){
		label.style.display = "none";
	}
	var divId = this._generateErrorMsgId(fieldId);
	var div = this.$(divId);
	if(div!=undefined){
		div.style.display="none";
		div.innerHTML = "";
	}
};