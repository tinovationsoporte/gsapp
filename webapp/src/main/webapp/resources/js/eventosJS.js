function detenerAccionDefault(event, codigoTecla){
	var elemento;
	if(event.keyCode == 13) {
		alert('Dentro'+'Keycode: '+event.keyCode);
		elemento = document.getElementById("#{m1p1s12_codigoTecla}");
		elemento.value = event.keyCode;
		alert("VALOR JSF: "+'#{+codigoTecla+.clientId}');
		onchange(); 
		return false;
	}
}

function soloNumerosConDecimal(event, obj) {
	 var charCode = (event.which) ? event.which : event.keyCode;
	        if (event.which == 0)//common keys
	            return true;
	        var value = obj.value;

	       if (charCode == 46 && value.indexOf('.') != -1)
	            return false;
	        else if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
	            return false;
	        return true;    }


function cambiarTipo(obj){
	   if(obj.getAttribute('type') == 'password')
	   	obj.setAttribute('type', 'text');
	   	else
	   		obj.setAttribute('type', 'password');
}