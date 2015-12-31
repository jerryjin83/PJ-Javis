/**
 * 通用打印功能
 * 使用方法：
 * 		doPrint({id:"我要打印的内容"});//如果不指定ID，则会打印完整页面
 * 配合自定义DOM属性
 * 		print-visible true|false //是否打印
 * 		print-style  			//自定义打印样式
 */
var PRINT_TEMPLATE = '<!DOCTYPE html><html><head>${styles}</head><body role="document"><div class="container" role="main">${printContent}</div></body></html>';
function doPrint(config){
  if(config==undefined || config.id==undefined){
	  window.print();
	  return;
  }
  
  // create a iFrame
  var iframe = document.createElement('IFRAME');
  var doc = null;
  iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
  document.body.appendChild(iframe);
  doc = iframe.contentWindow.document;
  
  // generate the whole style sheets
  var styles = getStyleSheets(doc);
  if(config.styles!=undefined){
	  styles+=('<style>'+config.styles+'</style>');
  }
  if(config.links!=undefined){
	  for(var i=0;i<config.links.length;i++){
		  styles+=('<link rel="stylesheet" type="text\/css" href="'+config.links[i]+'"\/>');
	  }
  }
  var wholeHtml = PRINT_TEMPLATE.replace("${styles}",styles);
  
  // generate the print contents
  var el = document.getElementById(config.id);
  var tagName = el.tagName;
  var html = el.innerHTML;
  
  var elAttrs = getElementAttributes(el);
  html = ('<'+tagName+' '+elAttrs+'>' + html + '</'+tagName+'>');
  wholeHtml = wholeHtml.replace("${printContent}",html);
  
  // remove all elements which print-visible is false
  var pv = $("[print-visible]");
  for(var i=0;i<pv.length;i++){
     var printVisible = pv[i].getAttribute("print-visible");
     if(printVisible=='false'){
    	wholeHtml = wholeHtml.replace(pv[i].outerHTML, "");
     }
  }
  
  // write HTML to the iFrame
  doc.write(wholeHtml);
  doc.close();
  //replace the print-style to style
  var ps = $(doc).find("[print-style]");
  for(var i=0;i<ps.length;i++){
     var printStyle = ps[i].getAttribute("print-style");
       ps[i].setAttribute("style",printStyle);
  }
 
  iframe.contentWindow.focus();
  // print iFrame
  iframe.contentWindow.print();
}
function isIE() { //ie?  
	if (!!window.ActiveXObject || "ActiveXObject" in window)  
		return true;  
	else  
		return false;  
}  
function getElementAttributes(el){
	 var elAttrs = el.attributes;
	  var elAttrStr = "";
	  for(var i=0;i<elAttrs.length;i++){
		  elAttrStr+=' '+elAttrs[i].nodeName+'="'+elAttrs[i].nodeValue+'" ';
	  }
	  return elAttrStr;
}
function replaceAll(fullCharacters,replacedCharacters,newCharacters){
	while(fullCharacters.indexOf(replacedCharacters)!=-1){
		fullCharacters = fullCharacters.replace(replacedCharacters,newCharacters);
	}
	return fullCharacters;
}
function getStyleSheets(doc) {
    var sheets = document.styleSheets;
    var styles = "";
    for (var i in sheets) {
      if(sheets[i].href==null){
        var rules = sheets[i].rules || sheets[i].cssRules;
        var style = "";
        for (var r in rules) {
          if(rules[r].cssText!=undefined){
            style+=rules[r].cssText;
          }  
        }
        if(style!=""){
        	styles+=("<style>"+style+"<\/style>");
        }
      }else{
    	  var linkHref='<link rel="stylesheet" type="text\/css" href="'+sheets[i].href+'"\/>';
    	  styles+=(linkHref);
      }
    }
    return styles;
}