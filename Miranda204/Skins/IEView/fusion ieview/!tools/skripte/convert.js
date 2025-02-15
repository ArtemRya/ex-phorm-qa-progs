// Version 0.3.0.8:   Popupbildvorschau f�r Videos gefixt
// Version 0.3.0.7:   Variable "alleurlsalsbild" durch die Variable "vorschaupopup" ersetzt; 
// Version 0.3.0.6:   seitenumwandlung als Funktionsparameter hinzugef�gt 
// Version 0.3.0.5:   User Einstellungen in die Config.js verschoben
// Version 0.3.0.4:   Bild- und SWF-Bereiche ans die neue Messageanalyse angepasst
// Version 0.3.0.3:   Variable "vorschaubuttonposi" in "vorschaubuttonstatus" umbenannt und Wertebereich ver�ndert
// Version 0.3.0.2:   Ver�nderung der Auswahl bei "alleurlsalsbild"
// Version 0.3.0.1:   Umarbeiteung der Messageanalyse
// Version 0.2.2.5:   Funktionen in globalfunctions.js ausgelagert;      Class Link-Foto + Class Link-Foto2 ersetzt durch Class Imagebox + Imagebox2
// Version 0.2.2.4:   F�r Videos nun 2 Buttons: 1. Popupbereich �ffnen - 2. Videobereich �ffnen/schlie�en
// Version 0.2.2.3:   Einstellung f�r Popupvorschau in die CSS gewandert; Abfrage nach SWF Umwandlung (ja/nein) eingebracht (ACHTUNG: Anpassung in der Basicfile.js n�tig)
// Version 0.2.2.2:   Problem mit RESET des Seitenverh�ltnises gefixt
// Version 0.2.2.1:   OnMouseDown ersetzt OnClick; Funktionsexistierung von VIDEOPLAY bei Video-Links
// Version 0.2.2.0:   2. Stable
// Version 0.2.1.4:   2 weitere Videoanbieter
// Version 0.2.1.3:   Standardcursor und Linkcursor per CSS ver�nderbar
// Version 0.2.1.2:   Icons vor und hinter dem Link werden nun per CSS ver�nderbar
// Version 0.2.1.1:   Iconsver�nderung per CSS eingef�gt
// Version 0.2.1.0:   1. Stable

var imagespfad   = ToolPfad+'icons/'   // Hier wird der Pfad zu den Video-, Bild- und SWF-Bereichesicons ermittelt
var seitenverhaeltnis = proportion;
document.write('<div id="mausbereich" width="1" height="1" style=\"overflow:hidden; position:absolute; z-index:175;\"></div>');

function linkconvert(text,bildumwandlung,videoumwandlung,swfumwandlung,seitenumwandlung) {
 einstellungspruefung_convert(imagespfad,1,'imagespfad','config.js','\"DeinPfad/\"');   
 einstellungspruefung_convert(vorschaubuttonstatus,2,'vorschaubuttonstatus','config.js','Icons vor und hinter dem Link mit 1=an und 0=aus\n\nReihenfolge: Bildlink, URLBildlink, Videolink und SWFlink'); 
 einstellungspruefung_convert(vorschaupopup,3,'vorschaupopup','config.js','Vorschaupopupbilder mit 1=an und 0=aus\n\nReihenfolge: Bildlink und URLBildlink');  
  
 text = text.replace(/<br>/g,' yyx ');       //1. Anweisung der if-Anweisung entfernt alle Umbr�che
  
  // die eigentliche Umwandlung
  hrefsplit = text.split('<a class=\"link\" target=\"_self\"');
  ausgabe = hrefsplit[0];
  for (j=1; j<hrefsplit.length;j++) {
    teiltext = hrefsplit[j].split("</a>");
    gefunden="undefiniert";
    link = teiltext[0].split(">")[1];
      
    //sucht nach Video-Links
    if ((videoumwandlung == 'yes') && (gefunden=="undefiniert") && (window.videoplay)) {
      //(window.videoplay) schaut nach ob die Function VIDEOPLAY existiert, wenn nicht wird diese IF-Anweisung �bersprungen, daraus folgt: ALLE Videolinkseiten werden als normale Seiten behandelt
      var VideoAnbieter = new Array();
      VideoAnbieter[0] = "(http\\:\\/\\/)?(\\w+\\.)*youtube\\.com/watch\\?[\\w\\=\\&\\-]*v\\=([\\w\\-]*)";
      VideoAnbieter[1] = "(http\\:\\/\\/)?video.google(\\.\\w+)+/(\\w+)\\?docid\\=([\\-\\d]+)";
      VideoAnbieter[2] = "(http\\:\\/\\/)?rutube.ru\\/tracks\\/\\d+\\.*html\\?[\\w\\=\\&\\*v=\\w\\-]";
      VideoAnbieter[3] = "(http\\:\\/\\/)?(\\w+\\.)*myvideo\\.de\\/watch\\/(\\d+)[\\?\\w\\=\\&\\-]*";
      VideoAnbieter[4] = "(http\\:\\/\\/)?video.golem.de\\/?[$ a-zA-Z0-9_ -]";
      VideoAnbieter[5] = "(http\\:\\/\\/)?(\w+\\.)*clipfish\\.de(\\/\\w+\\.\\w+\\/\\w+\\/\\d+\\/\\w+\\/\\d+)";
      VideoAnbieter[6] = "(http\\:\\/\\/)?(\w+\\.)*metacafe\\.com/watch\\/(\\w+)\\/?[a-zA-Z0-9_-]";
      for (index=0; index<VideoAnbieter.length; index++) {
        if ( teiltext[0].search(VideoAnbieter[index]) != -1 ) {
          bildlink = screenbildersteller(link);
          kurzerlink = linkverkuerzer(link)
          gefunden="video";
        }
      }
    }

    //sucht nach Bild-Links
    if ((bildumwandlung == 'yes') && (gefunden=="undefiniert")) {
      BildEndungen = new Array(".bmp",".gif",".jpg",".png",".xbm",".jpeg");
      for (index=0; index<BildEndungen.length; index++) {
        if (((link.substr(link.length-4,4)).toLowerCase()==BildEndungen[index]) || ((link.substr(link.length-5,5)).toLowerCase()==BildEndungen[index])) {
          screenshotlink = link;
          kurzerlink = linkverkuerzer(link);
          switch (vorschaupopup.charAt(0)) {
            case "0":
              bildlink = "";
            break;
            case "1":
              bildlink = link;
            break;
          }
          gefunden="bild";
        }
      }
    }
  
    //sucht nach SWF-Links
    if ((swfumwandlung == 'yes') && (gefunden=="undefiniert")) {
      FlashEndungen = new Array(".swf","admin");
      for (index=0; index<FlashEndungen.length; index++) {
        if (((link.substr(link.length-4,4)).toLowerCase()==FlashEndungen[index]) || ((link.substr(link.length-6,6)).toLowerCase()==FlashEndungen[index])) {
          screenshotlink = link;
          kurzerlink = linkverkuerzer(link);
          bildlink = link;
          gefunden="swf";
        }
      }
    }
    
    //Seiten anzeigen
    if ((seitenumwandlung == 'yes') && (gefunden=="undefiniert")) {
      switch (vorschaupopup.charAt(1)) {
        case "0":
          bildlink = "";
        break;
        case "1":
          bildlink = screenbildersteller(link);
        break;
      }
      kurzerlink = linkverkuerzer(link)
      gefunden="urlbild";  
    }
    
    //keine Umwandlung
    if (gefunden=="undefiniert") {
      link = "<a "+teiltext[0]+"</a>";
    }
    
    var name = Math.floor(Math.random()*9999) //erzeuge id
    switch (gefunden) {
      case "bild":
        teiltext0ersatz = "";
        if (vorschaubuttonstatus.charAt(0)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'picture_go.png" class="icons" onmouseover="src=\''+imagespfad+'picture_go_d.png\';" onmouseout="src=\''+imagespfad+'picture_go.png\';" onmousedown="picturearea(document.getElementById(\''+name+'div\'),this.alt)" title="Bildbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+'<a href="'+link+'" class="Link-Text" onmousemove="mausdrauf(this.alt);" onmouseout="this.className=\'Link-Text\';mausrunter();" onmouseover="this.className=\'Link-Text2\';mausdrauf(this.alt);" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'+kurzerlink+'</a>';
        if (vorschaubuttonstatus.charAt(1)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'picture_go.png" class="icons" onmouseover="src=\''+imagespfad+'picture_go_d.png\';" onmouseout="src=\''+imagespfad+'picture_go.png\';" onmousedown="picturearea(document.getElementById(\''+name+'div\'),this.alt)" title="Bildbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+' '+'<div id="'+name+'div" style="display:none" class="imagebox"></div>'; 
      break;
      case "urlbild":
        teiltext0ersatz = "";
        if (vorschaubuttonstatus.charAt(2)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'favicon.png" class="icons" onmouseover="src=\''+imagespfad+'favicon_d.png\';" onmouseout="src=\''+imagespfad+'favicon.png\';" onmousedown="picturearea(document.getElementById(\''+name+'div\'),this.alt)" title="Bildbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+'<a href="'+link+'" class="Link-Text" onmousemove="mausdrauf(this.alt);" onmouseout="this.className=\'Link-Text\';mausrunter();" onmouseover="this.className=\'Link-Text2\';mausdrauf(this.alt);" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'+kurzerlink+'</a>';
        if (vorschaubuttonstatus.charAt(3)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'favicon.png" class="icons" onmouseover="src=\''+imagespfad+'favicon_d.png\';" onmouseout="src=\''+imagespfad+'favicon.png\';" onmousedown="picturearea(document.getElementById(\''+name+'div\'),this.alt)" title="Bildbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+' '+'<div id="'+name+'div" style="display:none" class="imagebox"></div>';
      break;
      case "video":
        var Video_Bild = "./VideoPl"
        var Video_Bild_Endung = ".png"
        teiltext0ersatz = "";
        if (vorschaubuttonstatus.charAt(4)==1) {
          teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'\\'+Video_Bild+'2'+Video_Bild_Endung+'" class="icons" onMouseOver="src=\''+imagespfad+''+Video_Bild+'2_d'+Video_Bild_Endung+'\'" onMouseOut="src=\''+imagespfad+''+Video_Bild+'2'+Video_Bild_Endung+'\'" onmousedown="videoarea(document.getElementById(\''+name+'div\'),this.alt,1)" title="Videofenster &ouml;ffnen" alt="'+link+'[trenn]'+name+'">';
          teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'\\'+Video_Bild+'1'+Video_Bild_Endung+'" class="icons" onMouseOver="src=\''+imagespfad+''+Video_Bild+'1_d'+Video_Bild_Endung+'\'" onMouseOut="src=\''+imagespfad+''+Video_Bild+'1'+Video_Bild_Endung+'\'" onmousedown="videoarea(document.getElementById(\''+name+'div\'),this.alt,0)" title="Videobereich anzeigen/ausblenden" alt="'+link+'[trenn]'+name+'">';
          teiltext0ersatz = teiltext0ersatz+' ';  // Unterbindet oft die Bildtrennung  
        }
        teiltext0ersatz = teiltext0ersatz+'<a href='+link+' class="Link-Text" onmousemove="mausdrauf(this.alt);" onmouseout="this.className=\'Link-Text\';mausrunter();" onmouseover="this.className=\'Link-Text2\';mausdrauf(this.alt);" alt="'+link+'[trenn]'+bildlink+'[trenn]'+name+'">'+link+'</a>';
        if (vorschaubuttonstatus.charAt(5)==1) {
          teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'\\'+Video_Bild+'2'+Video_Bild_Endung+'" class="icons" onMouseOver="src=\''+imagespfad+''+Video_Bild+'2_d'+Video_Bild_Endung+'\'" onMouseOut="src=\''+imagespfad+''+Video_Bild+'2'+Video_Bild_Endung+'\'" onmousedown="videoarea(document.getElementById(\''+name+'div\'),this.alt,1)" title="Videofenster &ouml;ffnen" alt="'+link+'[trenn]'+name+'">';
          teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'\\'+Video_Bild+'1'+Video_Bild_Endung+'" class="icons" onMouseOver="src=\''+imagespfad+''+Video_Bild+'1_d'+Video_Bild_Endung+'\'" onMouseOut="src=\''+imagespfad+''+Video_Bild+'1'+Video_Bild_Endung+'\'" onmousedown="videoarea(document.getElementById(\''+name+'div\'),this.alt,0)" title="Videobereich anzeigen/ausblenden" alt="'+link+'[trenn]'+name+'">';
          teiltext0ersatz = teiltext0ersatz+' ';  // Unterbindet oft die Bildtrennung  
        }
        teiltext0ersatz = teiltext0ersatz+' '+'<div id="'+name+'div" class="videobox"></div>';
      break;
      case "swf":
        teiltext0ersatz = "";
        if (vorschaubuttonstatus.charAt(6)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'show.png" class="icons" onmouseover="src=\''+imagespfad+'show_d.png\';" onmouseout="src=\''+imagespfad+'show.png\'" onmousedown="swfarea(document.getElementById(\''+name+'div\'),this.alt)" title="Animationsbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+'<a href="'+link+'" class="Link-Text" onmouseout="this.className=\'Link-Text\'" onmouseover="this.className=\'Link-Text2\';">'+kurzerlink+'</a>';
        if (vorschaubuttonstatus.charAt(7)==1) { teiltext0ersatz = teiltext0ersatz+'<img src="'+imagespfad+'show.png" class="icons" onmouseover="src=\''+imagespfad+'show_d.png\';" onmouseout="src=\''+imagespfad+'show.png\'" onmousedown="swfarea(document.getElementById(\''+name+'div\'),this.alt)" title="Animationsbereich anzeigen/ausblenden" alt="'+link+'[trenn]'+name+'">'; }
        teiltext0ersatz = teiltext0ersatz+' '+'<div id="'+name+'div" class="swfbox"></div>';
      break;
      default:
        teiltext0ersatz = link;
      break;
    }
    ausgabe = ausgabe + teiltext0ersatz + teiltext[1];
  }
  ausgabe = ausgabe.replace(/ yyx /g,'<br>'); //letzte Anweisung der If-Anweisung erstellt wieder die Umbr�che
  return ausgabe;
}