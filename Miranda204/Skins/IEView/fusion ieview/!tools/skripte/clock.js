// Version 0.1.1.1:   User Einstellungen in die Config.js verschoben
// Version 0.1.1.0:   1. Stable


var uhrauswahlpath = ToolPfad+'clocks/'+uhrauswahl;   // Hier kann man die Flash-Uhr wählen und den Pfad dorthin anpassen, falls keine Uhr gewünscht: LEER LASSEN


document.write('<div id="uhrdiv" style=\"');
if ((sichtbarkeitinnen!="100") || (sichtbarkeitaussen!="100")) {
  document.write('filter:Alpha(opacity='+sichtbarkeitinnen+', finishopacity='+sichtbarkeitaussen+', style=2)');
}
document.write('overflow:hidden; position:absolute; top:0; visibility:visible; z-index:150;\">'
    + '<object id="uhrobject" classid="CLSID:D27CDB6E-AE6D-11cf-96B8-444553540000" Width="'+uhrbreite+'" height="'+uhrhoehe+'" border="0" codebase="http://active.macromedia.com/flash2/cabs/swflash.cab#version=4,0,0,0">'
    + '<param name="movie" value="'+uhrauswahlpath+'">' //Hier kann man die Flash-Uhr wählen und den Pfad anpassen
    + '<PARAM NAME=quality VALUE=high>'
    + '<PARAM NAME=wmode VALUE=transparent>'
    + '</object>'
    + '</div>');
uhrdivposi();

function uhrdivposi() {
  var scrollPos;
  var bildbreite;
  var bildhoehe;
  if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
    scrollPos  = document.documentElement.scrollTop;
    bildbreite = document.documentElement.offsetWidth;
    bildhoehe  = document.documentElement.offsetHeight;
  }
  else 
    if (typeof document.body != 'undefined') {
      scrollPos  = document.all.body.scrollTop;
      bildbreite = document.all.body.offsetWidth;
      bildhoehe  = document.all.body.offsetHeight;
    }
  
  with (document.getElementById("uhrdiv").style) {
    width  = uhrbreite;
    height = uhrhoehe;
    top = scrollPos+(bildhoehe - uhrhoehe)/100*uhrpositionh;
    left= (bildbreite - uhrbreite - 18)/100*uhrpositionv;
  }
   
  window.setTimeout("uhrdivposi();",10);
}
