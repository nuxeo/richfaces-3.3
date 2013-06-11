
/*------------------------------------------------*/

function fun(linkId,formName,target,parameters){
	var form = document.forms[formName];
	if(form){
		var hiddenFiled = form.elements[form.name+":_idcl"];
		hiddenField.value=linkId;
		if(target){
			form.target=target;
		}
		for(var param in parameters){
			if(form.elements[param]){
				form.elements[param].value = parameters[param];
			} else {
				var input = document.createElement("input");
				input.type="hidden";
				input.name=param;
				input.value=parameters[param];
				form.appendChild(input);
			}
		}
		form.submit();
	} else {
		alert("Form "+formName+" not found in document");
	}
};