document.onkeydown = onTecla;
function onTecla(e){
	var num = e?e.keyCode:event.keyCode;
	if(num==117){//116 F5 120 f9
		simulateClickTicket1();
	}
	if(num==121){//116 F5 120 f9
		simulateClickTicket2();
	}
}
function simulateClickTicket1() {
  var evt = document.createEvent("MouseEvents");
  evt.initMouseEvent("click", true, true, window,
    0, 0, 0, 0, 0, false, false, false, false, 0, null);
  var cb = document.getElementById("m1p1s2_btnTicket"); 
  var canceled = !cb.dispatchEvent(evt);
  if(canceled) {
	  
    // A handler called preventDefault
    //alert("ticket");
  } else {
    // None of the handlers called preventDefault
    //alert("no entro evento");
  }
}
function simulateClickTicket2() {
	  var evt = document.createEvent("MouseEvents");
	  evt.initMouseEvent("click", true, true, window,
	    0, 0, 0, 0, 0, false, false, false, false, 0, null);
	  var cb = document.getElementById("m1p1s2_btnRecibo"); 
	  var canceled = !cb.dispatchEvent(evt);
	  if(canceled) {
		  //alert("recibo");
	    // A handler called preventDefault
	    //alert("entro evento");
	  } else {
	    // None of the handlers called preventDefault
	    //alert("no entro evento");
	  }
	}


function imprimir(){	
	window.print();
}