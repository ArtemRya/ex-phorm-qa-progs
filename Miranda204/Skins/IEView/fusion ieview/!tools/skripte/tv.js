// Version 0.0.0.3:  Weitere TV Stream eingefügt, TV Stream Funktion eingefügt
// Version 0.0.0.2:   Classen TVText + TVText2 eingebracht
// Version 0.0.0.1:   klicken geht

function TVStream (TVStreamName, TVStreamAdresse, TVBreite, TVHoehe) {
// Zwar etwas umständlich , aber für Testzwecke sehr übersichtlich 
  liveTVstring = '';
// Maus Steuerung, Klassenfestlegung
  liveTVstring = liveTVstring + '<font class="TVText"';
  liveTVstring = liveTVstring + ' onmouseout="this.className=\'TVText\';"';
  liveTVstring = liveTVstring + ' onmouseover="this.className=\'TVText2\';"';
  liveTVstring = liveTVstring + ' onClick="streamplay(this.alt);" alt=\'';
// Stream Player
  liveTVstring = liveTVstring + '<object width="' + TVBreite ; + '" height="' + TVHoehe + '" ';
  liveTVstring = liveTVstring + 'codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" ';
  liveTVstring = liveTVstring + 'classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" ';
  liveTVstring = liveTVstring + 'standby="Loading Stream..." ';
  liveTVstring = liveTVstring + 'type="application/x-oleobject">';
// Stream Player Parameter Werte
  liveTVstring = liveTVstring + '<param value="' + TVBreite + '" name="width" />';
  liveTVstring = liveTVstring + '<param value="' + TVHoehe  + '" name="height" />';
  liveTVstring = liveTVstring + '<param value="' + TVStreamAdresse + '" name="src" />';
  liveTVstring = liveTVstring + '<param value="' + TVStreamAdresse + '" name="url" />';
// Stream Player Parameter Namen
  liveTVstring = liveTVstring + '<param name="uiMode" value="none"/>';
  liveTVstring = liveTVstring + '<param name="enableContextMenu" value="false">';
  liveTVstring = liveTVstring + '<param name="stretchToFit" value="false">';
  liveTVstring = liveTVstring + '<param name="windowlessvideo" value="flase">';
  liveTVstring = liveTVstring + '<param name="backgroundColor" value="black">';
  liveTVstring = liveTVstring + '<param name="autostart" value="true">';
// Stream Einbindung
  liveTVstring = liveTVstring + '<embed width="' + TVBreite ; + '" height="' + TVHoehe + '" ';
  liveTVstring = liveTVstring + 'src="' + TVStreamAdresse + '" ';
  liveTVstring = liveTVstring + 'id="nolplayer1" name="mediaPlayerObject" ';
  liveTVstring = liveTVstring + 'type="application/x-mplayer2" ';
  liveTVstring = liveTVstring + 'showcontrols="0" showdisplay="0" ';
  liveTVstring = liveTVstring + 'autostart="1" showstatusbar="0" />';
  liveTVstring = liveTVstring + '</object>\'>[' + TVStreamName +']</font>';
  return liveTVstring
}



document.write('<div id="tvlistopener" onClick="changeview();" style=\"margin-top: 0px; margin-left: 0px; overflow:hidden; position:absolute; z-index:180;\">');
document.write('<font class="TVText" onmouseout="this.className=\'TVText\';" onmouseover="this.className=\'TVText2\';">></font>');
document.write('</div>');
document.write('<div id="tvwahl" style=\"visibility:hidden; margin-top: 0px; margin-left: 11px; overflow:hidden; position:absolute; z-index:181;\">');

// TV Streams
document.write(TVStream ('3D TV', 'mms://livestream.comsys.de:8080', 100, 100));
document.write(' ');
document.write(TVStream ('3.Sat', 'mms://c36000-l.w.core.cdn.streamfarm.net/36000zdf/live/3546zdf/encoder.zdf.3sat_h_16zu9.wmv', 100, 100));
document.write(' ');
document.write(TVStream ('n-tv', 'mms://stream.wmv.n-tv.de/ntvlive', 100, 100));
document.write(' ');
document.write(TVStream ('Phoenix', 'mms://c36000-l.w.core.cdn.streamfarm.net/36000zdf/live/3546zdf/encoder.zdf.phoenix_h.wmv', 133, 100));
document.write(' ');
document.write(TVStream ('Deluxe Music', 'http://projects.eviscobroadcast.com/deluxemusic/free-stream/s_stream/060623_deluxemusic-live.wvx', 133,100));
document.write(' ');
document.write(TVStream ('Giga TV', 'http://www.giga.de/live/stream/giga/', 133,100));
document.write(' ');
//

document.write('<font class="TVText" onmouseout="this.className=\'TVText\';" onmouseover="this.className=\'TVText2\';" onClick="streamplay(this.alt);" alt=\'\'> (X) </font>');
document.write('</div>');
document.write('<div id="streamarea" style=\"visibility:hidden; background-color:black; overflow:hidden; width:40%; height: 40%; position:absolute; right:0px; top:0px; z-index:185;\"></div>');

setInterval("newtvposis()", 1);

function changeview(){
  if (document.getElementById("tvwahl").style.visibility=="hidden") {
    document.getElementById("tvwahl").style.visibility = "visible";
    document.getElementById("streamarea").style.visibility = "visible";
  }
  else {
    document.getElementById("tvwahl").style.visibility="hidden";
    document.getElementById("streamarea").style.visibility="hidden";
  }
}

function streamplay(StreamObjekt) {
  if  (document.getElementById("streamarea").innerHTML!=StreamObjekt) {
    document.getElementById("streamarea").innerHTML=StreamObjekt;
  }
  else {
    document.getElementById("streamarea").innerHTML="";
  }
}

function newtvposis() {
  // Je nach <!DOCTYPE> in der .ivt-Datei wird eine andere IF-Anweisung durchgeführt.  
  var scrollPos;
  if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
    scrollPos  = document.documentElement.scrollTop;
  }
  else 
    if (typeof document.body != 'undefined') {
      if(document.getElementById){
        scrollPos  = document.getElementById("Body").scrollTop;
      }
      else {
        scrollPos  = document.all.body.scrollTop;
      }
    }

  with (document.getElementById("tvlistopener").style) {
    top = scrollPos;
  }
  with (document.getElementById("tvwahl").style) {
    top = scrollPos;
  }
  with (document.getElementById("streamarea").style) {
    top = scrollPos-5;
  }
  
}