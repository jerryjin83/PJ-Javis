/**
*	You can use this plug-in to very easy to add validation. 
*	var validationConfig = {namespace:'v',selectMethod:dojo.query,parseOnload:true};
*/
(function(){
	onload = window.onload;
	if(onload!=undefined){
		onload();
	}
	var jsonObjReg = /^\{.*\}$/;
	var createValidation = function(validationNamespace){
		validationEls = validationConfig.selectMethod("*["+validationNamespace+"=true]");
		var config = {};
		var fields = [];
		for(var i=0;i<validationEls.length;i++){
			var fieldConfig = {};
			var el = validationEls[i];
			
			var id = el.getAttribute(validationNamespace+"-id");
			if(id!=undefined){
				fieldConfig.id = id;
			}else{
				if(el["id"]==undefined){
					throw new Error("Id is necessary for validation element.");
				}
				fieldConfig.id = el["id"];
			}
			var method = el.getAttribute(validationNamespace+"-method");
			if(method!=undefined){
				fieldConfig.method = method;
				if(Validation.prototype._defaultRule[fieldConfig.method]==undefined){
					eval("fieldConfig.method =" + fieldConfig.method);
				}
			}
			var regex = el.getAttribute(validationNamespace+"-regex");
			if(regex!=undefined){
				fieldConfig.regex = regex;
			}
			var max = el.getAttribute(validationNamespace+"-max");
			if(max!=undefined){
				fieldConfig.max = Number(max);
			}
			var min = el.getAttribute(validationNamespace+"-min");
			if(min!=undefined){
				fieldConfig.min = Number(min);
			}
			var required = el.getAttribute(validationNamespace+"-required");
			if(required!=undefined){
				if(jsonObjReg.test(required)){
					eval("fieldConfig.required ="+ required);
				}else{
					fieldConfig.required = Boolean(required);
				}
			}
			var equalTo = el.getAttribute(validationNamespace+"-equalTo");
			if(equalTo !=undefined){
				if(jsonObjReg.test(equalTo)){
					eval("fieldConfig.equalTo ="+ equalTo);
				}else{
					fieldConfig.equalTo = equalTo;
				}
			}
			
			var notEqualTo = el.getAttribute(validationNamespace+"-notEqualTo");
			if(notEqualTo !=undefined){
				if(jsonObjReg.test(notEqualTo)){
					eval("fieldConfig.notEqualTo = "+notEqualTo);
				}else{
					fieldConfig.notEqualTo = notEqualTo;
				}
			}
			
			var onChange = el.getAttribute(validationNamespace+"-onChange");
			if(onChange!=undefined){
				if(typeof(onChange)=="string"){
					eval("fieldConfig.onChange ="+ onChange);
				}else{
					fieldConfig.onChange = Boolean(onChange);
				}
			}
			
			var onBlur = el.getAttribute(validationNamespace+"-onBlur");
			if(onBlur!=undefined){
				if(typeof(onBlur)=="string"){
					eval("fieldConfig.onBlur ="+ onBlur);
				}else{
					fieldConfig.onBlur = Boolean(onBlur);
				}
			}
			
			var onKeyup = el.getAttribute(validationNamespace+"-onKeyup");
			if(onKeyup!=undefined){
				if(typeof(onBlur)=="string"){
					eval("fieldConfig.onKeyup ="+ onKeyup);
				}else{
					fieldConfig.onKeyup = Boolean(onKeyup);
				}
			}
			
			var message = el.getAttribute(validationNamespace+"-message");
			if(message!=undefined){
					fieldConfig.message = message;
			}
			
			var showMessage = el.getAttribute(validationNamespace+"-showMessage");
			if(showMessage!=undefined){
					eval("fieldConfig.showMessage = "+showMessage);
			}
			
			var clearMessage = el.getAttribute(validationNamespace+"-clearMessage");
			if(clearMessage!=undefined){
					eval("fieldConfig.clearMessage = "+clearMessage);
			}
			
			var widgetType = el.getAttribute(validationNamespace+"-widgetType");
			if(widgetType!=undefined){
				fieldConfig.widgetType = widgetType;
			}
			var associate = el.getAttribute(validationNamespace+"-associate");
			if(associate!=undefined){
				fieldConfig.associate = associate;
			}
			fields.push(fieldConfig);
		}
		config.fields = fields;
		window[validationNamespace] = new Validation(config);
	};
	window.onload = function(){
		var validationNamespace = "validation";
		try{
			if(typeof(validationConfig)!="undefined"){
				if(validationConfig.parseOnload){
					if(validationConfig.namespace!=undefined){
						if(typeof(validationConfig.namespace)!="string"){
							for(var i=0;i<validationConfig.namespace.length;i++){
								validationNamespace = validationConfig.namespace[i];
								createValidation(validationNamespace);
							}
						}else{
							validationNamespace = validationConfig.namespace;
							createValidation(validationNamespace);
						}
					}else{
						createValidation(validationNamespace);
					}
				}
			}
		}catch(e){
			try{
				console.log(e);
			}catch(e){
				
			}
		}
	};
})();