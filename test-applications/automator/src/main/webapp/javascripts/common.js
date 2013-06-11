function checkValidatorMessage(){	
	var mes = document.getElementById('mainForm:comboBoxSubview:mess');
	var inp = document.getElementById('mainForm:comboBoxSubview:hiddenValidatorInput');	
	if ((mes != null)&&(inp != null)) {
		if (mes.textContent.indexOf('converter') == -1) inp.value = mes.textContent;
	}
}

function checkConverterMessage(){	
	var mes = document.getElementById('mainForm:comboBoxSubview:mess');
	var inp = document.getElementById('mainForm:comboBoxSubview:hiddenConverterInput');	
	if ((mes != null)&&(inp != null)) {
		if (mes.textContent.indexOf('validator') == -1) inp.value = mes.textContent;
	}
}