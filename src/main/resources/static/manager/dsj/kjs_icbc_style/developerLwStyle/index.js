/* 为null判断 */
function isEmpty(val){
	if(val || val=="null" || val=="undefined"){
		return false;
	}else{
		return true;
	}
}
//简单的身份证校验
function iscard(s) { 
	var re = /^\d{17}([0,9]|X|x)$/; 
		if (re.test(s)) { 
			return true; //校验通过
		} else{ 
			return false; 
		} 
}
/* 金额判断 */
function isMoney(s){ 
	var regu = "^[0-9]+[\.]?[0-9]{0,2}$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) { 
		return true; //校验通过
	}
}
//使用正则表达式去掉多余的.与0 
function subZeroAndDot(s){
		if(s.indexOf(".")>0){
			console.log("有小数点");
			s = s.replace(/0+?$/,"").replace(/[.]$/,"");  
		}	
		return s;
}



