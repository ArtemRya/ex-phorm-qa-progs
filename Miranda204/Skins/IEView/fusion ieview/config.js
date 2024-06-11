// Grundeinstellung
var ToolPfad = './!tools/';                          // Hier den Pfad bis zum "!tools"-Verzeichnis angeben
document.write('<script src="'+ToolPfad+'skripte/basicfile.js"></script>');
iconpfad = "./!tools/filetypes/"

// E i n s t e l l u n g   f ü r   g l o b a l f u n c t i o n . j s
// "1"   http://images.websnapr.com         [1. 92x70  pixel]  [2. 202x152 pixel]
// "2"   http://livethumb.de/                    [1. 80x60  pixel]  [2. 160x120 pixel] [3. 320x240 pixel] [4. 400x300 Pixel]
// "3"   http://www.artviper.net               [1. 90x60  Pixel]  [2. 120x90  Pixel] [3. 240x180 Pixel]
// "4"   http://www.shrinktheweb.com       [1. 120x90Pixel]  [2. 200x150 pixel] [3. 320x240]   (ACHTUNG: Benötigt Account)
makeurlscreenanbieter  = "1";                         // Angabe welcher Anbieter von Internetseiten die Bilder erstellen soll
anbieterpasswort         = "";                          // Benötigtes Passwort für den Internetseiten-Bildersteller
vorschausize              = "2";                         // Wahl der Größe

// E i n s t e l l u n g e n   f ü r   s h o w d a t a . j s
empfangsdateipfad = "/Erhaltene Dateien/";            // Empfangsverzeichnis vom Mirandaverzeichnis aus gesehen, muss auch in den Einstellung von Miranda eingetragen werden
// "1" Name - vom Chatpartner der Kontaktlistenname                     (%nick% in den Mirandaeinstellungen)
// "2" UIN - Kontakt ID                                                                      (%userid% in den Mirandaeinstellungen)
// "3" CID - Kontakt ID oder Benutzer ID , äbhänig vom Aufruf   (%userid% in den Mirandaeinstellungen)
unterverzeichnis  = "1";       // Unterverzeichnis vom empfangsdateipfad kann mit "1", "2" oder "3" gesetzt werden, muss auch in den Einstellungen von Miranda eingetragen werden


// Einstellungen für basicfile.js
var meldungsart = new Array ();
// in IVT %txt% durch <script>getitall('%\text%','%\name%',meldungsart[X])</script> ersetzen:   ACHTUNG X anpassen, z.B. X=0 bei Message In
// meldungsart[X] enthält derzeit 7 Parameter duch ":" getrennt.
meldungsart[0] = 'tzers:yes:get:yes:yes:yes:yes';      // X=0  bei  Message In
meldungsart[1] = 'tzers:yes:send:yes:yes:yes:yes';     // X=1  bei  Message Out
meldungsart[2] = 'tzers:yes:get:yes:yes:yes:yes';      // X=2  bei  Message In Grouped   (Start & Inner)
meldungsart[3] = 'tzers:yes:send:yes:yes:yes:yes';     // X=3  bei  Message Out Grouped  (Start & Inner)
meldungsart[4] = 'none:yes:get:yes:yes:yes:yes';   // X=4  bei  History Message In
meldungsart[5] = 'picture:yes:send:yes:yes:yes:yes';  // X=5  bei  History Message Out
meldungsart[6] = 'picture:yes:get:yes:yes:yes:yes';   // X=6  bei  History Message In Grouped   (Start & Inner)
meldungsart[7] = 'picture:yes:send:yes:yes:yes:yes';  // X=7  bei  History Message Out Grouped  (Start & Inner)
// 1. Parameter: "picture": zeigt das Bild an, "tZer" spielt die Animation ab und "both" sowohl Bild als auch Animation werden gezeigt
// 2. "yes" zeigt den Namen des tZers im Chatfenster an; "no" zeigt den Namen des tZers NICHT an
// 3. Immer "get" bei erhaltenen Messeges und immer " send" bei gesendeten Messeges wählen
// 4. "yes" Bildlinks umwandeln (klickbar machen und ggf mit Icons versehen);      "no" Bildlinks so lassen, ACHTUNG: werden dann wie Seitenlinks behandelt
// 5. "yes" Videolinks umwandeln (klickbar machen und ggf mit Icons versehen);   "no" Videoumwandlung so lassen, ACHTUNG: werden dann wie Seitenlinks behandelt
// 6. "yes" SWFLinks umwandeln (klickbar machen und ggf mit Icons versehen);   "no" SWF-Umwandlung so lassen, ACHTUNG: werden dann wie Seitenlinks behandelt
// 7. "yes" Seitenlinks umwandeln (klickbar machen und ggf mit Icons versehen);  "no" Seitenumwandlung so lassen
var animation  = "";                           // Animation angeben (nicht jede Animation funktioniert bei jedem DOCTYPE), falls keine Animation gewünscht: LEER LASSEN

// Einstellungen für convert.js
var linkverkuerzen       = "nein";                      // Lange Links werden bei "yes" oder "ja" in der Mitte durch ... ersetzt weiterhin wird bei einer  PHP-URL ab dem ? alles entfernt
var proportion           = 4/3;                       // Seitenverhältnis des SWF- und Video-Bereichs: Breite im Bezug zu Höhe; z.B.: 2/1 Breite ist doppelt so lang als die Länge der Höhe
var vorschaupopup        = "11";                      // Vorschaupopupbilder 1=an und 0=aus, in der Reihenfolge für Bildlink und URLBildlink
var vorschaubuttonstatus = "10101010";                // Abwechselnd für Icons vor und hinter dem Link mit 1=an und 0=aus, in der Reihenfolge für Bildlink, URLBildlink, Videolink und SWFlink
var vorschaubildview     = "yes"                      // beim Überfahren von Bild-, Video-, und URLBildlinks werden bei "yes" oder "ja" Vorschaupopupbilder angezeigt
var vorschaubildposiX    = -1;                        // horizontale Position des Vorschaupopupbilder: 0=ganz links; 50=mittig; 100=ganz rechts; -1=anMauszeiger)
var vorschaubildposiY    = -1;                        // vertikale  Position  des  Vorschaupopupbilder:  0=ganz oben; 50=mittig; 100=ganz unten;   -1=anMauszeiger)

// Einstellungen für video.js
var Videobutton = 0;                                  // Bei (0) beide Videobuttons  öffnen und  -schließen Bereich und bei (1) Fenster öffen;   bei (2): beide Videobuttons unterschiedliches verhalten
var maxwidth    = "300";                              // Große Bilder werden auf die maximale Breite geschrumpft
var maxheight   = "200";                              // Große Bilder werden auf die maximale Höhe geschrumpft

// Einstellungen für clock.js
var uhrbreite          = 60;                          // Breite der Uhr
var uhrhoehe           = 60;                          // Höhe der Uhr
var uhrauswahl         = "uhr.swf";                   // Hier kann man die Flash-Uhr wählen, falls keine Uhr gewünscht: LEER LASSEN
var sichtbarkeitinnen  = "100";                       // Legt fest, wie undurchlässig die Anzeige des inneren Bereiches der Uhr ist ( 100% komplett sichtbar, 0% nicht sichtbar )
var sichtbarkeitaussen = "100";                       // Legt fest, wie undurchlässig die Anzeige des äusseren Bereiches der Uhr ist ( 100% komplett sichtbar, 0% nicht sichtbar )
var uhrpositionv       = 100;                         //Position der Uhr: (0) ganz links, (50) mittig, (100) ganz rechts
var uhrpositionh       = 1;                           //Position der Uhr: (0) ganz oben, (50) mittig, (100) ganz unten

