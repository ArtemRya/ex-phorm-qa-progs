// Version 0.1.1.10:   Problem mit Darstellung wenn tzername=no gewählt wurde behoben
// Version 0.1.1.9:   Pfadermittlung zu den Animationen und den Bildern anhand einer Variablen in der Config.js
// Version 0.1.1.8:   Pfade zu den Animationen und Grafiken werden anhand einer Variablen in der Config.js ermittelt
// Version 0.1.1.7:   Ausrichtung für CENTER, LEFT und RIGHT entfernt, dafür Class tzerbildbereich eingeführt, die die Positionsvergabe in der CSS realisieren lässt
// Version 0.1.1.6:   CloseButton wird nun beim Überfahren grün;   Mauszeiger verändert sich beim Überfahren
// Version 0.1.1.5:   Class tzerlink2 hinzugefügt für Mouseovereffekte;   Im Stykle vom tZersDiv-DIV sind margin-top: 2px; und margin-left: 2px; von der CSS her eingewandert
// Version 0.1.1.4:   Funktion getavatar ausgelagert
// Version 0.1.1.3:   Closebild ist mit in das tZerBild-Verzeichnis gewandert
// Version 0.1.1.2:   Eigene Cursor können in der CSS gewählt werden
// Version 0.1.1.1:   Variabler Pfad zu den Bildern und den Animationen 
// Version 0.1.1.0:   1. Stable

var tzeranimpfad = ToolPfad+'tZersFiles/animationen/';   // Hier den Pfad zu den tZers-Images eintragen
var tzerbildpfad = ToolPfad+'tZersFiles/grafiken/';      // Hier den Pfad zu den tZers-Animationen eintragen

//-------------------------------------------------------------------------------------
// hier wird ein DIV-Tag namens "tZersDiv" mit eingebetteten Object-Child erzeugt, das durch die Funktion "neubestimmen()"
// immer im Sichtbereich, auch beim Scrollen, gehalten und mit der Funktion "ausblenden()" nach einigen Sekunden versteckt wird
//-------------------------------------------------------------------------------------
<!--
// hier kann die Größe und ggf der Name des Closebuttons - der parallel zur ivt-Datei liegt - verändert werden 
document.write('<img src="'+tzerbildpfad+'close.png" class="icons" onmouseover="src=\''+tzerbildpfad+'/close_d.png\';" onmouseout="src=\''+tzerbildpfad+'/close.png\';" style=\"height:32px; width:32px; position:absolute; visibility:hidden; z-index:30000;\" id="closeButton" onclick="ausblenden()" title="close" alt="Close">');
document.write('<div id="tZersDiv" align=\"right\" style=\" margin-top: 2px; margin-left: 2px; overflow:hidden; position:absolute; top:0; visibility:hidden; z-index:200;\"></div>');
TimeoutClear = 0;  

//-------------------------------------------------------------------------------------
// Der DIV-Tag wird nach einigen Sekunden ausgeblendet
//-------------------------------------------------------------------------------------
function ausblenden() {
  document.getElementById("tZersDiv").style.visibility = "hidden";
  document.getElementById("closeButton").style.visibility = "hidden";
  document.getElementById("tZersDiv").innerHTML = "";
}

//-------------------------------------------------------------------------------------
// Der DIV-Tag wird beim Scrollen nach ein paar Millisekunden auf die neue Position innerhalb des Blickberechs gesetzt
//-------------------------------------------------------------------------------------
function neubestimmen() {
  //-------------------------------------------------------------------------------------
  // tZersHoehenAnpassung verhindert beim tZers-Empfang das vertikale Scrollen ins Unendliche; muss ggf. mit einem größeren Wert angepasst werden.
  // tZersBreitenAnpassung passt die Breite des Ausgabefensters an, dadurch kann das horizontale Scrollen verhindert werden, muss ggf. mit einem größeren Wert angepasst werden.
  //-------------------------------------------------------------------------------------
  tZersHoehenAnpassung  = 4;
  tZersBreitenAnpassung = 21;
  // Je nach <!DOCTYPE> in der .ivt-Datei wird eine andere IF-Anweisung durchgeführt.  
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
      if(document.getElementById){
        scrollPos  = document.getElementById("Body").scrollTop;
        bildbreite = document.getElementById("Body").offsetWidth;
        bildhoehe  = document.getElementById("Body").offsetHeight;
      }
      else {
        scrollPos  = document.all.body.scrollTop;
        bildbreite = document.all.body.offsetWidth;
        bildhoehe  = document.all.body.offsetHeight;
      }
    }
  document.getElementById("closeButton").style.top = scrollPos+7;
  document.getElementById("closeButton").style.left = bildbreite - tZersBreitenAnpassung - document.getElementById("closeButton").style.width.substr(0,2) - 3;
  
  document.getElementById("tZersDiv").style.height = bildhoehe - tZersHoehenAnpassung;
  document.getElementById("tZersDiv").style.width  = bildbreite - tZersBreitenAnpassung;
  document.getElementById("tZersDiv").style.left = 0;
  document.getElementById("tZersDiv").style.top = scrollPos;
  
  if (document.getElementById("tZersDiv").innerHTML != "") { //Diese Abfrage ist nötig, da es sonst nach Ende des tZers eine Fehlermeldung gibt
    tzerzoom(100); //das berechnet den Zoom immer wieder neu
  }

}

function tzerzoom(grundwert) {
  document.getElementById("tZersObject").Zoom(0); //das setzt den Zoom auf 0
  switch (swfname) {
    // Jeder tZer kann seinen eigenen Zoom mit (wert = grundwert - XX;) - der nicht auf Null sinken darf - bekommen; es gilt: je größer XX, um so größer die Animation
    case "gangsta":   case 'gangsterSheep': wert = grundwert - 40; break;
    case "canthearu": case 'cant_hear':     wert = grundwert - 40; break;
    case "skratch":   case 'scratch':       wert = grundwert - 40; break;
    case "boo":                             wert = grundwert - 10; break;
    case "kisses":                          wert = grundwert - 25; break;
    case "chillout":  case 'rastamab':      wert = grundwert - 15; break;
    case "akitaka":   case 'sappuko':       wert = grundwert - 40; break;
    case "laugh":                           wert = grundwert - 50; break;
    case "duh":       case 'dahh':          wert = grundwert - 00; break;
    case "beback":    case 'beBack':        wert = grundwert - 15; break;
    case "likeu":     case 'iLikeU':        wert = grundwert - 40; break;
    case "sorry":                           wert = grundwert - 58; break;
  }
  document.getElementById("tZersObject").Zoom(wert);
}

function tzersabspielen(swflink) {
  setInterval("neubestimmen()", 1);
  document.getElementById("tZersDiv").innerHTML =
  '<object id="tZersObject" classid="CLSID:D27CDB6E-AE6D-11cf-96B8-444553540000" Width=100% height=100% border=0 codebase="http://active.macromedia.com/flash2/cabs/swflash.cab#version=4,0,0,0">'
  +'<param name="movie" value="'+swflink+'">'
  +'<PARAM NAME=wmode VALUE=transparent>'
  +'</object>';
  window.clearTimeout(TimeoutClear);
  TimeoutClear = window.setTimeout("ausblenden();",15000);
  document.getElementById("tZersObject").StopPlay();
  document.getElementById("tZersDiv").style.visibility = "visible";
  document.getElementById("closeButton").style.visibility = "visible";
  document.getElementById("tZersObject").Play();
}

//-------------------------------------------------------------------------------------
// hier wird die Ausgabe vorbereitet und je nach Wert angezeigt
// der Parameter "durchreichwert" wird einfach nur an verschiedene Erweiterungen weitergereicht
//-------------------------------------------------------------------------------------
function tZersOutput (eingangsmeldung,chatpartner,tzersoderbild,zeigetzersname,bekommensenden) {
  einstellungspruefung_tzerausgabe(tzeranimpfad,1,'tzeranimpfad','config.js','\"DeinPfad/\"'); 
  einstellungspruefung_tzerausgabe(tzerbildpfad,1,'tzerbildpfad','config.js','\"DeinPfad/\"'); 

  link = eingangsmeldung;
  link = link.split("swf");
  if (link[1] && (link[0].substr(0,4)=="tZer" || link[0].substr(54,5)=="xtraz" || link[0].substr(55,5)=="xtraz")) {
    link = link[1].split("http://");
     
    //-------------------------------------------------------------------------------------
    // hier muss der Pfad zu den tZers (.swf- und .png-Dateien) angepasst werden, damit diese gefunden werden
    // Standardmäßig ist hier das "tZersFiles"-Verzeichnis gewählt, das, von der .ivt-Datei aus gesehen, eine Ebene höher liegt.
    //-------------------------------------------------------------------------------------
    swfname = link[1].substring(link[1].lastIndexOf('/')+1,link[1].length-1);
    bildlink= tzerbildpfad+swfname+'.png';
    swflink = tzeranimpfad+swfname+'.swf';
    tzersname = eingangsmeldung;
    if ((eingangsmeldung.substr(54,5)=='xtraz') || (eingangsmeldung.substr(55,5)=='xtraz')) {
      tzersname =  tzersname.split('.');
      tzersname =  tzersname[2].substring(tzersname[2].lastIndexOf('/')+1,tzersname[2].length)+'';
      switch (tzersname) {
        case "gangsta":   case 'gangsterSheep': tzersname = "Gangsta";       break;
        case "canthearu": case 'cant_hear':     tzersname = "Can't Hear U";  break;
        case "skratch":   case 'scratch':       tzersname = "Scratch";       break;
        case "boo":                             tzersname = "Booooo";        break;
        case "kisses":                          tzersname = "Kisses";        break;
        case "chillout":  case 'rastamab':      tzersname = "Chill Out!";    break;
        case "akitaka":   case 'sappuko':       tzersname = "Akitaka";       break;
        case "sorry":                           tzersname = "I'm Sorry";     break;
        case "laugh":                           tzersname = "Hilaaarious";   break;
        case "duh":       case 'dahh':          tzersname = "Like Duh!";     break;
        case "beback":    case 'beBack':        tzersname = "L8R";           break;
        case "likeu":     case 'iLikeU':        tzersname = "Like U!";       break;
        default: break;
      }
    }
    else {
      // (1) für ICQJ Plus 0.3.8.105 bis Build #93
      // (2) für ICQJ Plus 0.3.8.105 ab  Build #94
      // (3) [noch nicht vergeben]
      icqversion = "2";
      switch (icqversion) {
        case "1":
          tzersname = tzersname[1].split('"');
          tzersname = tzersname[0].split('freeData');
          tzersname = tzersname[0].split('url');
          tzersname = tzersname[0].split('"');
          tzersname = tzersname[0].substring(7,tzersname[0].length-7);
        break;
        case "2":
          tzersname = tzersname.split("tZer received:");
          tzersname = tzersname[1].split('"');
          tzersname = tzersname[0].split('http://');
          tzersname = tzersname[0].substring(1,tzersname[0].length-13);
        break;
        case "3":
          // noch nicht vorhanden 
        break;
        default:
        break;
      }
    } 
      
    if (zeigetzersname == 'yes') {
      //-------------------------------------------------------------------------------------
      // die folgende IF-Anweisung erzeugt und gibt den tZers-Namen aus, wenn ein tZer empfangen wird. Kann auch weggelassen oder durch einen anderen
      // document.write-Befehl ersetzt werden. Der erzeugte Name wird nicht in der History gespeichert. Dort wird lediglich der unveränderte Ausdruck gespeichert.
      //-------------------------------------------------------------------------------------
      if (bekommensenden == 'get') {
        document.write(chatpartner+' tZers you with <font class="tzerlink" onmouseout="this.className=\'tzerlink\'" onmouseover="this.className=\'tzerlink2\'" onclick="tzersabspielen(&quot;'+swflink+'&quot;)">'+tzersname+'</font>');
      }  
      else {
        document.write('You tZer with <font class="tzerlink" onmouseout="this.className=\'tzerlink\'" onmouseover="this.className=\'tzerlink2\'" onclick="tzersabspielen(&quot;'+swflink+'&quot;)">'+tzersname+'</font>');
      }
    }

    if (tzersoderbild=="tzers" || tzersoderbild=="both") {
      tzersabspielen(swflink);
    }
    if (tzersoderbild=="picture" || tzersoderbild=="both") {
      document.write('<div class="tzerbildbereich">');
      document.write('<img class="tzerbild" src="'+bildlink+'" alt="'+tzersname+'" title="'+tzersname+'" onclick="tzersabspielen(&quot;'+swflink+'&quot;)">'); 
      document.write('</div>');
    }
    return eingangsmeldung.split("</a>")[1];
  }

  else {
    return eingangsmeldung;
  }
}