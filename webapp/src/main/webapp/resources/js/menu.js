
// Resalta el elemento del menu seleccionado


function marcarElementoActivo(elemento){
	$( ".modulos ul li" ).each(function() {
		$(this).removeClass("moduloActivo");
		
	});
	$( elemento ).addClass( "moduloActivo" );
}

function quitarElementoActivo(){
	$( ".modulos ul li" ).each(function() {
		$(this).removeClass("moduloActivo");		
	});	
}

/*
$('.modulos ul li a').click(function(){
  $('li').removeClass('moduloActivo');
  $(this).parent().addClass('moduloActivo');
});
*/