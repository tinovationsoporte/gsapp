$(document).ready(function()  {
	prepararMayusculas();
});

function prepararMayusculas(){
	$('input').keyup(function() {
        this.value = this.value.toUpperCase();
    });
}
