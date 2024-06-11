// Version 0.1.1.7:   Einstellungsüberprüfung ausgelagert -> optionscheck.js
// Version 0.1.1.6:   Funktion zur Überprüfung von Einstellungen (siehe showdata.js) ; Funktion zur Ermittlung des Icons eines Dateitypen
// Version 0.1.1.5:   screenbildersteller als Variablen in die config.js ausgelagert
// Version 0.1.1.4:   User Einstellungen ins die Config.js verschoben
// Version 0.1.1.3:   Videobuttonverhalten lassen sich in der config.js per Variable anpassen
// Version 0.1.1.2:   Class Link-Foto + Class Link-Foto2 ersetzt durch Class Imagebox + Imagebox2
// Version 0.1.1.1:   Weiterer  Screenbildersteller hinzugefügt
// Version 0.1.1.0:   1. Stable

//Funktion zur Ermittlung des Icons eines Dateitypen
function dateitypiconermittlung(dateiendung) {
  dateiendung = dateiendung.toLowerCase();    
  switch (dateiendung) {
  // Archive
  case "7z":   icon = 'Archive/7zip'; break;
  case "rar":  icon = 'Archive/rar';  break;
  case "zip":  icon = 'Archive/zip';  break;        
  // Audio
  case "mp3":  icon = 'Audio/mp3'; break;
  case "mid":  icon = 'Audio/mid'; break;
  case "ogg":  icon = 'Audio/ogg'; break;
  case "wav":  icon = 'Audio/wav'; break;
  case "wma":  icon = 'Audio/wma'; break;                      
  // Bilder
  case "bmp":  icon = 'Image/bmp';  break;
  case "gif":  icon = 'Image/gif';  break;
  case "jpg":  icon = 'Image/jpg';  break;
  case "jpeg": icon = 'Image/jpeg'; break;    
  case "png":  icon = 'Image/png';  break;  
  case "tif":  icon = 'Image/tif';  break;                           
  // Office
  case "csv":  icon = 'Office/csv'; break;       
  case "doc":  icon = 'Office/doc';  break;    
  case "docx": icon = 'Office/docx'; break;  
  case "mdb":  icon = 'Office/mdb';  break;
  case "mdbx": icon = 'Office/mdbx'; break;    
  case "pdf":  icon = 'Office/pdf';  break;     
  case "ppt":  icon = 'Office/ppt';  break;
  case "pptx": icon = 'Office/pptx'; break;          
  case "vsd":  icon = 'Office/vsd';  break;
  case "xls":  icon = 'Office/xls';  break;
  case "xlsm": icon = 'Office/xlsx'; break;         
  case "xlsx": icon = 'Office/xlsx'; break;                                                 
  // Videos      
  case "avi":  icon = 'Video/avi';   break;
  case "divx": icon = 'Video/divx';   break;  
  case "flv":  icon = 'Video/flv';   break;        
  case "mkv":  icon = 'Video/mkv';   break;  
  case "mp4":  icon = 'Video/mp4';   break;              
  case "mpg":  icon = 'Video/mpg';   break;     
  case "mpeg": icon = 'Video/mpeg';  break; 
  case "mov":  icon = 'Video/mov';   break;                 
  case "wmv":  icon = 'Video/wmv';   break; 
  // System
  case "asp":  icon = 'System/asp';  break;   
  case "bat":  icon = 'System/bat';  break;
  case "bin":  icon = 'System/bin';  break;   
  case "css":  icon = 'System/css';  break; 
  case "cue":  icon = 'System/cue';  break;
  case "exe":  icon = 'System/exe';  break;        
  case "htm":  icon = 'System/htm';  break;   
  case "html": icon = 'System/html'; break;      
  case "ini":  icon = 'System/ini';  break;
  case "iso":  icon = 'System/iso';  break;
  case "nfo":  icon = 'System/nfo';  break;
  case "psd":  icon = 'System/psd';  break;   
  case "txt":  icon = 'System/txt';  break;
  case "xml":  icon = 'System/xml';  break;              
  default:  icon = 'System/default'; break;                                             
  }
  
  return icon;
 }

// animierte Avatare werden ermittelt und im Ausgabebereich dargestellt
function getavatar(avatar){
  if( avatar.indexOf('.swf',0)!=-1) {
    document.write('<object width="48" height="60" name="movie" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" class="headeravaflash">'
    + '<param name=movie value=' + avatar + '>'
    + '<param name=quality value=high>'
    + '<embed src=' + avatar + ' quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" class="headeravaflash">'
    + '</embed></object>');
  }
  else {
    document.write('<img src="' + avatar + '" width="48" />');
  }
}

// verkürzt einen Link
function linkverkuerzer(orilink) {
var kurzerlink;
if ((linkverkuerzen.toLowerCase()!="yes") && (linkverkuerzen.toLowerCase()!="ja")) {
  kurzerlink = orilink;
}
else {
  kurzerlink = orilink.replace(/(\/\/[a-zA-Z0-9._% -]*\/)([a-zA-Z0-9._% -~\/]*)(\/[a-zA-Z0-9._% -~]*)/g,'$1...$3');
  kurzerlink = kurzerlink.replace(/(\/\/[a-zA-Z0-9._% -~:\/]*).php?([a-zA-Z0-9._% -~:\/]*)/g,'$1');
}
return kurzerlink;
}

function mausdrauf(ids) {
  if ((vorschaubildview.toLowerCase()=="yes") || (vorschaubildview.toLowerCase()=="ja")) {
    idlink = ids.split('[trenn]')[0];
    idbildlink = ids.split('[trenn]')[1];
    if (idbildlink) {
      idname = ids.split('[trenn]')[2];
      if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
        scrollPos     = document.documentElement.scrollTop;
        Fensterhoehe  = document.documentElement.offsetHeight;
        Fensterbreite = document.documentElement.offsetWidth;
        ScrollbalkenY = 2;
        ScrollbalkenX = 2;
      }
      else {
        if (typeof document.body != 'undefined') {
          if(document.getElementById){
            scrollPos     = document.getElementById("Body").scrollTop;
            Fensterhoehe  = document.getElementById("Body").offsetHeight;
            Fensterbreite = document.getElementById("Body").offsetWidth;
            ScrollbalkenY = 20;
            ScrollbalkenX = 20;
          }
          else {
            scrollPos     = document.all.body.scrollTop;
            Fensterhoehe  = document.all.body.offsetHeight;
            Fensterbreite = document.all.body.offsetWidth;
            ScrollbalkenY = 20;
            ScrollbalkenX = 20;
          }
        }
      }
      document.getElementById("mausbereich").innerHTML='<img id="bildvorschau" class="bildvorschau" src="'+idbildlink+'">'; 

      if (vorschaubildposiY==-1) {
        if (window.event.clientY + document.getElementById("bildvorschau").height+ScrollbalkenY+10 > Fensterhoehe) {
          YY = scrollPos+Fensterhoehe-document.getElementById("bildvorschau").height-ScrollbalkenY;
        }
        else {
          YY = scrollPos+window.event.clientY+10;
        }
      }
      else {
        YY = scrollPos+(Fensterhoehe-document.getElementById("bildvorschau").height-ScrollbalkenY)/100*vorschaubildposiY;
      }
      
      if (vorschaubildposiX==-1) {
        if (window.event.clientX + document.getElementById("bildvorschau").width+ScrollbalkenX+10 > Fensterbreite) {
          XX = Fensterbreite-document.getElementById("bildvorschau").width-ScrollbalkenX;
        }
        else {
          XX = window.event.clientX+10;
        }
      }
      else {
        XX = (Fensterbreite-document.getElementById("bildvorschau").width-ScrollbalkenX)/100*vorschaubildposiX;
      }
      
      document.getElementById("mausbereich").style.top  = YY;
      document.getElementById("mausbereich").style.left = XX;
    }
  }
}

function mausrunter() {
  document.getElementById("mausbereich").innerHTML="";
}

function otherarea(iddiv,ids) {
  if (iddiv.innerHTML!="") {
    iddiv.innerHTML="";
    iddiv.style.display="none";
  }
  else {
    idlink = ids.split('[trenn]')[0];
    ausgabe = '<br>&nbsp;&nbsp;&nbsp;<a class="Link-Text" onmouseout="this.className=\'Link-Text\';" onmouseover="this.className=\'Link-Text2\';" href="'+idlink+'">'+idlink+'</a><br><br>';
    iddiv.innerHTML=ausgabe;
    iddiv.style.display="block";
  }
}  

function picturearea(bilderdiv,ids) {
  if (bilderdiv.innerHTML!="") {
    bilderdiv.innerHTML="";
    bilderdiv.style.display="none";
  }
  else {
    idlink = ids.split('[trenn]')[0];
    idbildlink = ids.split('[trenn]')[1];
    idname = ids.split('[trenn]')[2];
      //toolbar
    ausgabe = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
             +'<img src="'+imagespfad+'/arrow_turn_left.png" class="icons" onmouseover="src=\''+imagespfad+'/arrow_turn_left_d.png\';" onmouseout="src=\''+imagespfad+'/arrow_turn_left.png\'" onmousedown="getElementById(\''+idname+'i\').style.filter=\'progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=3);\'" title="Nach links gedreht">'
             +'<img src="'+imagespfad+'/arrow_up.png" class="icons" onmouseover="src=\''+imagespfad+'/arrow_up_d.png\';" onmouseout="src=\''+imagespfad+'/arrow_up.png\'" onmousedown="getElementById(\''+idname+'i\').style.filter=\'progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=4)\'" title="Standardausrichtung">'
             +'<img src="'+imagespfad+'/arrow_turn_right.png" class="icons" onmouseover="src=\''+imagespfad+'/arrow_turn_right_d.png\';" onmouseout="src=\''+imagespfad+'/arrow_turn_right.png\'" onmousedown="getElementById(\''+idname+'i\').style.filter=\'progid:DXImageTransform.Microsoft.BasicImage(grayscale=0, xray=0, mirror=0, invert=0, opacity=1, rotation=1)\'" title="Nach rechts gedreht">&nbsp;&nbsp;&nbsp;';
      //toolbar2
    ausgabe = ausgabe+'<img src="'+imagespfad+'/zoom_in.png" class="icons" onmouseover="src=\''+imagespfad+'/zoom_in_d.png\';" onmouseout="src=\''+imagespfad+'/zoom_in.png\'" onmousedown="resize_big(document.getElementById(\''+idname+'i\'))" title="Vergr&ouml;&szlig;ern">'
                     +'<img src="'+imagespfad+'/zoom_out.png" class="icons" onmouseover="src=\''+imagespfad+'/zoom_out_d.png\';" onmouseout="src=\''+imagespfad+'/zoom_out.png\'" onmousedown="resize_small(document.getElementById(\''+idname+'i\'))" title="Verkleinern"><br>';
    ausgabe = ausgabe+'<a href='+idlink+'><img id="'+idname+'i" class="imagebox" onmouseout="this.className=\'imagebox\'" onmouseover="this.className=\'imagebox2\'" src="'+idbildlink+'" alt="'+idlink+'" onload="resize(this)"></a><br>';
    bilderdiv.innerHTML=ausgabe;
    bilderdiv.style.display="block";
  }
}

function resize_animation(bereich,veraengerungsart) { 
  bereich.display = "inline"; 
  switch (veraengerungsart) {
  case "vergroessern": 
    if ((bereich.height<3200) && (bereich.width<3200)) {
      bereich.height = (bereich.height * 1.1) + "px"; 
      bereich.width  = (bereich.width  * 1.1) + "px"; 
    }
  break;
  case "verkleinern": 
    if ((bereich.height<3200) && (bereich.width<3200)) {
      bereich.height = (bereich.height / 1.1) + "px"; 
      bereich.width  = (bereich.width  / 1.1) + "px"; 
    }
  break;
  case "seitenverhaeltnisgroesser":
    if (seitenverhaeltnis < 20/9) {
      seitenverhaeltnis += 1/18;
      bereich.width = bereich.width * seitenverhaeltnis / (seitenverhaeltnis-1/18);
    }
  break;
  case "seitenverhaeltnisreset":
    seitenverhaeltnis = proportion;
    bereich.width = bereich.height * seitenverhaeltnis;
  break;
  case "seitenverhaeltniskleiner":
    if (seitenverhaeltnis > 9/9) {
      seitenverhaeltnis -= 1/18;
      bereich.width = bereich.width * seitenverhaeltnis / (seitenverhaeltnis+1/18);
    }
  break;
  }
} 

//resize
function resize(img) { 
  img.style.display = "inline"; 
  if(img.width > maxwidth) {
    img.style.height = (img.height * maxwidth / img.width) + "px"; 
    img.style.width = maxwidth+'px'; 
  } 
  if(img.height > maxheight) {
    img.style.width = (img.width * maxheight / img.height) + "px"; 
    img.style.height = maxheight+'px';
  }
}  

//resize +
function resize_big(img) { 
  img.style.display = "inline"; 
  if ((img.height<3200) && (img.width<3200)) {
    img.style.height = (img.height * 1.1) + "px"; 
    img.style.width  = (img.width  * 1.1) + "px"; 
  }
} 

//resize -
function resize_small(img) { 
  img.style.display = "inline"; 
  if ((img.height>10) && (img.width>10)) { 
    img.style.height = (img.height / 1.1) + "px"; 
    img.style.width  = (img.width  / 1.1) + "px"; 
  }
} 

// hmm it's hard for me, please help coding :-)
function rotate(img) {  
  imgRotation = new Array();
  if (!imgRotation[obj.src]) {
    imgRotation[obj.src] = 1;
  }
  else {
    imgRotation[obj.src]++;
  }      
  obj.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+(imgRotation[obj.src]%4)+')';
}

// Hier wird der Anbieter ausgewählt, der von Internetseiten Grafiken erstellt, die dann als Vorschau dienen
function screenbildersteller(link) {
  var anbieter = '\"1\" -> http://images.websnapr.com\n\"2\" -> http://ss.puxz.com\n\"3\" -> http://screen.puxz.com\n\"4\" -> http://www.artviper.net\n\"5\" -> http://www.shrinktheweb.com   (ACHTUNG: Benötigt Account)';
  einstellungspruefung_globalfunctions(makeurlscreenanbieter,1,'makeurlscreenanbieter','config.js',anbieter);
  einstellungspruefung_globalfunctions(anbieterpasswort,2,'anbieterpasswort','config.js','Bitte geben Sie ein Passwort an!');
  switch (makeurlscreenanbieter) {
    case "1":
      screenshotlink = 'http://images.websnapr.com/?url='+link;
    break;
    case "2":
      screenshotlink = 'http://ss.puxz.com//'+link;
    break;
    case "3":
      screenshotlink = 'http://screen.puxz.com/1024x768/'+link;
    break;
    case "4":
      screenshotlink = 'http://www.artviper.net/screenshots/screener.php?url='+link+'&w=200&q=80';
    break;
    case "5":
      screenshotlink = 'http://www.shrinktheweb.com/xino.php?embed=1&stwu=ca504&STWAccessKeyId='+anbieterpasswort+'&Size=xlg&stwUrl='+link;  // Benötigt Passwort
    break;
    default:
    break;
  }
  return screenshotlink;
}

function swfarea(swfflash,ids) { 
  if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
    swfbereich = swfflash.style;
    hoch  = document.documentElement.offsetHeight;
    breit = document.documentElement.offsetWidth;
  }
  else {
    if (typeof document.body != 'undefined') {
      if(document.getElementById){
        swfbereich = swfflash.style;
        if (document.getElementById("Body").offsetWidth>document.getElementById("Body").offsetHeight){
          hoch  = document.getElementById("Body").offsetHeight * 3/4;
          breit = hoch * seitenverhaeltnis;
        }
        else {
          breit = document.getElementById("Body").offsetWidth * 3/4;
          hoch  = breit * 1/seitenverhaeltnis;
        }
      }
      else {
        if (document.getElementById("Body").offsetWidth>document.getElementById("Body").offsetHeight){
          hoch  = document.all.body.offsetHeight * 3/4;
          breit = hoch * seitenverhaeltnis;
        }
        else {
          breit = document.all.body.offsetWidth * 3/4;
          hoch  = breit * 1/seitenverhaeltnis;
        }
      }
    }
  }
  idlink = ids.split('[trenn]')[0];
  idname = ids.split('[trenn]')[1];
  if (swfflash.innerHTML!="") {
    swfflash.innerHTML="";
    swfbereich.display = 'none';
  }
  else {
      //toolbar2
    ausgabe = '<img src="'+imagespfad+'zoom_in.png" class="icons" onmouseover="src=\''+imagespfad+'zoom_in_d.png\';" onmouseout="src=\''+imagespfad+'zoom_in.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'swf\'),\'vergroessern\')" title="Vergr&ouml;&szlig;ern">'
             +'<img src="'+imagespfad+'zoom_out.png" class="icons" onmouseover="src=\''+imagespfad+'zoom_out_d.png\';" onmouseout="src=\''+imagespfad+'zoom_out.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'swf\'),\'verkleinern\')" title="Verkleinern">&nbsp;&nbsp;&nbsp;';
      //toolbar3
    ausgabe = ausgabe+'<img src="'+imagespfad+'breiter.png" class="icons" onmouseover="src=\''+imagespfad+'breiter_d.png\';" onmouseout="src=\''+imagespfad+'breiter.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'swf\'),\'seitenverhaeltnisgroesser\')" title="Seitenverh&auml;ltnis vergr&ouml;&szlig;ern">'
                     +'<img src="'+imagespfad+'reset.png" class="icons" onmouseover="src=\''+imagespfad+'/reset_d.png\';" onmouseout="src=\''+imagespfad+'/reset.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'swf\'),\'seitenverhaeltnisreset\')" title="Seitenverh&auml;ltnis wiederherstellen">'
                     +'<img src="'+imagespfad+'schmaler.png" class="icons" onmouseover="src=\''+imagespfad+'/schmaler_d.png\';" onmouseout="src=\''+imagespfad+'/schmaler.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'swf\'),\'seitenverhaeltniskleiner\')" title="Seitenverh&auml;ltnis verkleinern"><br>';
    ausgabe = ausgabe+'<object id="'+idname+'swf" width="'+breit+'" height="'+hoch+'" classid="CLSID:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://active.macromedia.com/flash2/cabs/swflash.cab#version=4,0,0,0">';
    ausgabe = ausgabe+'<param name="movie" value="'+idlink+'">';
    ausgabe = ausgabe+'</object>';
    swfflash.innerHTML=ausgabe;
    swfbereich.display = 'block';
  }
}

function videoarea(videos,ids,popup) {
  if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') {
      videobereich = videos.style;
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
      // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
      hoch  = document.documentElement.offsetHeight * 3/4;
      breit = document.documentElement.offsetWidth * 3/4;
  }
  else {
    if (typeof document.body != 'undefined') {
      if(document.getElementById){
        videobereich = videos.style;
        // ohne DOCTYPE
        // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 2.0//EN"> 
        // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN"
        // <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
        if (document.getElementById("Body").offsetWidth>document.getElementById("Body").offsetHeight){
          hoch  = document.getElementById("Body").offsetHeight * 3/4;
          breit = hoch * seitenverhaeltnis;
        }
        else {
          breit = document.getElementById("Body").offsetWidth * 3/4;
          hoch  = breit * 1/seitenverhaeltnis;
        }
      }
      else {
        if (document.getElementById("Body").offsetWidth>document.getElementById("Body").offsetHeight){
          hoch  = document.all.body.offsetHeight * 3/4;
          breit = hoch * seitenverhaeltnis;
        }
        else {
          breit = document.all.body.offsetWidth * 3/4;
          hoch  = breit * 1/seitenverhaeltnis;
        }
      }
    }
  }
  idlink = ids.split('[trenn]')[0];
  idname = ids.split('[trenn]')[1];
  if (Videobutton!=2) {
    popup = Videobutton;
  }
  if (popup == 1) {
    videoplay(idlink,idname,hoch,breit,popup);
  }
  else {  
    if (videos.innerHTML!="") {
      if (videobereich.display == 'none') {
        videobereich.display = 'block';
      }
      else {
        videobereich.display = 'none';
      }
    }
    else {
      var videostring = videoplay(idlink,idname,hoch,breit,popup);
      if (videostring!=0) {
          //toolbar2
        ausgabe = '<img src="'+imagespfad+'/zoom_in.png" class="icons" onmouseover="src=\''+imagespfad+'/zoom_in_d.png\';" onmouseout="src=\''+imagespfad+'/zoom_in.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'video\'),\'vergroessern\')" title="Vergr&ouml;&szlig;ern">'
                 +'<img src="'+imagespfad+'/zoom_out.png" class="icons" onmouseover="src=\''+imagespfad+'/zoom_out_d.png\';" onmouseout="src=\''+imagespfad+'/zoom_out.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'video\'),\'verkleinern\')" title="Verkleinern">&nbsp;&nbsp;&nbsp;';
          //toolbar3
        ausgabe = ausgabe+'<img src="'+imagespfad+'/breiter.png" class="icons" onmouseover="src=\''+imagespfad+'/breiter_d.png\';" onmouseout="src=\''+imagespfad+'/breiter.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'video\'),\'seitenverhaeltnisgroesser\')" title="Seitenverh&auml;ltnis vergr&ouml;&szlig;ern">'
                         +'<img src="'+imagespfad+'/reset.png" class="icons" onmouseover="src=\''+imagespfad+'/reset_d.png\';" onmouseout="src=\''+imagespfad+'/reset.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'video\'),\'seitenverhaeltnisreset\')" title="Seitenverh&auml;ltnis wiederherstellen">'
                         +'<img src="'+imagespfad+'/schmaler.png" class="icons" onmouseover="src=\''+imagespfad+'/schmaler_d.png\';" onmouseout="src=\''+imagespfad+'/schmaler.png\'" onmousedown="resize_animation(document.getElementById(\''+idname+'video\'),\'seitenverhaeltniskleiner\')" title="Seitenverh&auml;ltnis verkleinern"><br>';
        ausgabe = ausgabe+videoplay(idlink,idname,hoch,breit,popup);
        videobereich.display = 'block';
      }
      else {
        ausgabe = "";
        videobereich.display = 'none';
      }
      videos.innerHTML=ausgabe;
    }
  }
}
