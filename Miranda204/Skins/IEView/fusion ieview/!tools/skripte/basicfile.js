// Version 0.1.2.6:   User Einstellungen in die Config.js verschoben
// Version 0.1.2.5:   allparameter[6] für Seitenumwandlung in der Config.js hinzugefügt
// Version 0.1.2.4:   Variable "meldungsart" um ein Element, "Position des tZers-Bildes", verkürzt; CSS realisiert das nun
// Version 0.1.2.3:   Aufruf von globalfunctions.js eingebracht
// Version 0.1.2.2:   meldungsart[] bestimmt welcher Bereich in der IVT benutzt wird;  allparameter[6] für SWF Abfrage eingebracht
// Version 0.1.2.1:   Einbinden der anderen JS-Dateien
// Version 0.1.2.0:   2. Stable
// Version 0.1.1.0:   1. Stable

document.write('<script src="'+ToolPfad+'skripte/globalfunctions.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/tzerausgabe.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/convert.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/videos.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/parser.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/showdata.js"></script>');
document.write('<script src="'+ToolPfad+'skripte/validateconfig.js"></script>');
//document.write('<script src="'+ToolPfad+'skripte/clock.js"></script>');
//document.write('<script src="'+ToolPfad+'skripte/tv.js"></script>');
document.write('<script src="'+ToolPfad+'anims/'+animation+'"></script>');
document.write('<link href="config.css" rel="stylesheet" type="text/css">');

function getitall(eingangsmeldung, chatpartner, uin, pfad, allparameter) {
 
  allparameter = allparameter.split(':');
  ausgabe = eingangsmeldung;
  ausgabe2 = eingangsmeldung;
  
  // hier werden die Veränderungsskripte nacheinander aufgerufen
  ausgabe = parser(ausgabe);
  ausgabe2 = linkconvert(ausgabe2,allparameter[3],allparameter[4],allparameter[5],allparameter[6]);
  ausgabe = tZersOutput(ausgabe,chatpartner,allparameter[0],allparameter[1],allparameter[2]);
  
  
  document.write(ausgabe);
  
  // wenn test="1" gesetzt wird, kann man nach Aktuallisierung des Ausgabefenster sehen, welche Indexe die Werte des 3. Parameters haben
  // dadurch kann man schneller sehen, welche Werte für die jeweiligen Veränderungsskripte gedacht sind
  test = "0"; 
  if (test=="1") {
    for (index=0; index<allparameter.length; index++) {
      document.write('<br>allparameter['+index+'] : '+allparameter[index])
    }
  }

}