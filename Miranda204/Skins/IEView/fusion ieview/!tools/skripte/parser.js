// Version 0.1.1.1:   Größe von Bilder im Text werden nun in der CSS bei .textimages definiert
// Version 0.1.1.0:   1. Stable

function parser(message) {
  
  // parser: Bilder von http://www.upload.de anzeigen
  message = message.replace(/http\:\/\/www.abload.de\/image.php\?img\=/g, 'http://www.abload.de/img/');
  
  // parser:eMails anklickbar machen
  message = message.replace(/([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)(?=(\s|<|$))/g, '<img src="images/mail.png" width="19px" hight="17" class="icon"><a href="mailto:$&"><span style="color:#999999; text-decoration: underline;">$&</span></a>');
  
  // parser: DIV-Konstrokt entfernen, die auftauchen, wenn ICQ-User aus dem Chat Fenster Nachrichten kopieren und verschicken
  message = message.replace(/&lt;DIV&gt;/g,'');
  message = message.replace(/&lt;\/DIV&gt;/g,'');
  
  // TEST
  //message = message.replace(/</g,'[');
  //message = message.replace(/>/g,']');
  
  return message;
}
