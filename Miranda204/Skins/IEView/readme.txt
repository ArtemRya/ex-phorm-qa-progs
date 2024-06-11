IEview Fusion 1.5 
(28.02.2009) 

File content:
-------
- fusion-black.ivt 
- fusion-blue.ivt
- fusion-white.ivt (chrome)
- readme.txt

Installation:
------------

1. Unzip the folder "fusion ieview" from *.zip archive to your miranda folder, preferentially to "Skins"-folder.
   Don't change the folder structure!
2. start miranda --> settings --> Message Sessions --> IEview --> Tab: Message Log --> 
   Use templates --> select your wanted template (*.ivt)
3. klick "apply", maybe a restart of miranda is necessary

Notice:
-------

- requires the plugin "IEview", Internet Explorer 7.0, to receive/send ICQ-Tzer you need ICQPlus plugin.
- all templates have fixed values for fontsize and fontcolor. You can change it manually via editor. Therefore open the relevant *.css
  and change value "font-size" in "body" or "color" in "*.content"
- for inactivating the "url-preview" feature open the basic.js an uncomment the line "ausgabe = linkconvert(ausgabe,allparameter[4],allparameter[5]);" with '//'
  same for the email-parser (ausgabe = parser(ausgabe);)
- for inactivating the "system uptime" in the right upper corner open the relevant *.ivt and search for: <!--system uptime ### Delete the following line if not wanted ###-->

Changelog:
---------

** v1.0 **
- initial release

** v1.1 **
- some code cleaning
- bugfix: runtime error 'sirka'

** v1.2 **
- changed url-capture service provider
- modify e-mail parser, now also works between text content
- forgot "readme"
- 2 languages (engl, germ) for each template

** v1.3 **
- support for MSN custom smileys

** v1.4 **
- complete rewritte code for showing picture & video links
- some optical changes
- show different icons for different incoming files in message log
- hide "uptime" clock by default
- preview of links via mouseover


** v1.5 **
- fixed "undefined" issue for IE6.0 users.



Publisher
---------

Template created by puttee.
Template is part of the miranda IM package: Miranda Fusion
more information you'll find here: http://www.miranda-fusion.de/
private page: http://www.puttee.de/
Contact: puttee@miranda-fusion.de


ADDON-Page
----------

IEview Templates. 3 different color themes (black, blue, chrome)

more information:
- group session support
- receive tzers from ICQ or Miranda (with ICQ Plus mod) user
- picture link support, shows pictures directly in ieview, with zoom-function.
- webpage preview link support, shows webpage preview in message window
- video support such as Youtube, directly watch video in message window
- shows status change of user
- shows received file transfer
- as gimmick: shows system uptime in right upper corner

- there are also adequate skins for tabsrmm, clist_modern, popupplus, tipper ym available
  http://www.miranda-fusion.de/download/

- All skins will be part of the miranda fusion package 2.0: www.miranda-fusion.de
