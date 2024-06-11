	Fingeprint Plus Plugin for Miranda IM

	Authors:
	Code written and optimised by (and they are THE BEST!):
 Artem Shpynov, mail:ashpunov@gmail.com, homepage: http://fyr.mirandaim.ru/
 Bio, mail: bio@ktaspb.ru, nomepage: http://www.etplanet.com/bio/miranda/
 Nullbie, mail: nullbie@gmail.com, homepage: http://nullbie.miranda-im.org.ua/
	Original idea, functional design, images, masks and option configuration,
	plugin updates, maintenance and support by
 Angeli-Ka, mail: angil@nm.ru, homepage: http://angeli-ka.my1.ru/

	
 This module analyzes value in Contact/protocolName/MirVer value and set extra image for contact
 in contact list accordingly. 
 Please read fist post for more detais, post bug reports and request here:
 http://forums.miranda-im.org/showthread.php?t=6465
 For latest TEST version look in :
 http://angeli-ka.my1.ru/load/8-1-2
 ALL LOGOS (or parts) USED FOR DESIGN OF APPROPRIATE CLIENT ICONS ARE PROPRIETARY OF THEIR OWNERS AND CAN BE
 PROTECTED BY COPYRIGHT LAW.


 Fingerprint Plus is derivate of Fingerpint plugin and is released under freeware License
 (included in this archive, please READ IT)
 Permission for use former GPL code from all authors are included too.


Instalation notes:
Unpack archive in main Miranda directory.
Go to the icons/fingerprint/ folder and delete fp_name.dll you don't need.
FP will check for icon libraries in:
1 - custom path set by "Custom profile folders" plugin
2 - any relative path set manually in database using "IconDir" string key
3 - default path /icons/fingerprint/
4 - /icons/

TEST version contain ONLY updated icon libraries so, 
make sure you have latest stable release installed before use it.


 Changelog:
Version 1.1.12. (14.04.2010)
- Added "QQ 2010", "QIP 2010", "Juick", "RobyerPack"  client icons.
- Redrawn some "on ..." and added "on Symbian" and "on web" overlay icons
- Anticipated upgrade for QQ, WLM, Gadu-Gadu, Tlen protocol's version overlays.
- Fixed FoldersRegister translate - by Nullbie.


Version 1.1.11. (14.03.2010)
Added "SworIM", "LocID", "ICQ 7", "Yahoo 10", "WTW", "Orkut" client icons

Version 1.1.10.
- Added "Yambi", "Schuelervz",  "AIM 7", "Android", "Glu", "RealMeteo", "Meteo-gid" client icons.
- Moved "Miranda 0.8.x" to stable and added  "Miranda 0.9.x" as "Dev.build"
- Fixed Windows Live (MSN) for Yahoo, JabberDisk, Pidgin and AIM MirVers.

Version 1.1.9.0 (15.04.09)
- Added "Facebook", "Gadu-Gadu v.8", "Xfire", "Meteonovosti", "Fensterln" client icons.
- Patch for extraicons service - by Pescuma. Left click on client icon in clist will open user details.
- Fixed "Libpurple" mask.

Version 1.1.8.1 (7.02.09)
- Added "StudiVZ" client icon.
- Larger "Never show news" label - by Nullbie.
- Added back all icon libraries.

Version 1.1.8.0 (4.02.09)
- Added "Smack", "Beejive", "Libpurple", "Bri edition", "LEXSOR's", "ChaosPack" client icons.
- Added mobile overlay "on BlackBerry"
- Fixed "Never show news" translation - by Nullbie.

Version 1.1.7.0 (28.10.08)
- Fixed "SIM/Win32" and  "Amatory Pack" overlay icons.
- Added "Fring", "BayanICQ", "Goober", "Icqkid2", "WebIcqPro", "Diesel Pack", "Vista Pack" client icons.

Version 1.1.6.0 (07.09.08)
- Added "Webagent" , "Talkonaut", "я ќлайн", "Pilot Pack", "Amatory Pack"client icons.
- Fixed translation by Nullbie.

Version 1.1.5.0(20.07.08)
- Added "PIGEON!" mobile, "New Style" and "qutIM" pack client icons. 
- Sinked jabber client masks with new MirVer format. 

Version 1.1.4.0
- Added QQ 2008 and 2009, Intellicast, Aim v.6.x and Aim Bot client icons.
- AntonKingPk, LPack(Lestat), Fusion packs and Vista overlays.

Version 1.1.3.0 (20.04.08)
- Separated Libyahoo2 from Yahoo 5.x
- Fixed CenterICQ detection
- Moved QIP Infium to Multiprotocol icon group
- Added updater support for test versions - by Nullbie
- Added ICQ v0.5.x version recognition to ICQJ overlay
- Added "Digsby" multiprotocol client, "japp" mobile client, "Pack Freize" overlay

Version 1.1.2.0 (04.02.08)
- Added "LCS", "YAHOO" and "E-Mail Only" clients for MSN protocol
- Added "Core Pager", "Ya.ru", "FChat", "D[i]Chat", "CenterIM" clients, "Woerterbuch" Jabber Service and "HierOS" overlay icons
- Fixed "s& & SSS ICQ mod" and "SSS build" overlay interlaps.
- Fixed some options not working

Version 1.1.1.0 (27.01.08)
- Added extrachecks for icon libraries by Nullbie.
- Added mobile version for Mail.Ru Agent.
- Added "S.I.A.", "SSS build", "Georgij"and "Ga88eR" pack overlays
- Added Hyves chat, site, Kwekker, Yapp! and SmapeR clients recognition.

Version 1.1.0.0 (12.01.08)
- Separate icon libraries and folders support for it - by Nullbie.
- All icons, except Miranda and Multi-Protocol, have been allocated in fp_name.dll placed by default in /icons/fingerprint/ folder
- Added "Miranda Friendly" pack overlay.
- Changed iconlib tree structure(mobile and web-based are now subtree brunches for multi-protocol). 
NB:this will broke your current translation.
- Updated MRA masks.
- Version bump due to major upgrade.
- Reverted changelog order in readme.
 
Version 1.0.12.0
- Added Climm, MCabber, Slick clients recognition.
- Added uzm, @lfaMaR, Philip, Razhunter and ProZ packs overlay. 
- Added [debug] overlay.
- Added core check by Nulbie.
- Removed "HotCofee" pack overlay because it violates Miranda IM,
Fingerprint PLus and AniSmiley's license.
- Fixed few minor glitches.
- Code optimization by Bio.

Version 1.0.11.0
- Fix for experimental support up to 65000 extra images by FYR 
- Fixed IRC detection 
- Added option to show/hide jabber transport overlay 
- Added Tencent Messenger2007 and 2008, QQ 2007 and WLM 9.0 recognition
- Added (preventive-not yet commited for Yahoo proto)Yahoo 9.0, Yahoo Web Messenger and "unknown" overlay. 

Version 1.0.10.0
- Added Xeus2, SamePlace, Campustalk clients recognition.
- Added translate.JRuDevels.org, IMified bots recognition
- Added "bots" and "services" subgroups to "Jabber" icon group
- Added @news.jabbim service recognition.
- Removed ICQJ dev cose it was released.

Version 1.0.9.0
- Added NOAA Wheather recognition
- Added WEB.DE MultiMessenger and 1&1 Messenger
- Fixed SoapBox Communicator detection
- Added RSS icon group and option for RSS, Atom Web feed version recognition
- Added Jabber transport overlay to Stoat Headline Service

Version 1.0.8.0
- Added Jabber Disk service recognition.
- Fixed "apply" button not been active when icon slot is changed(by Nullbie)
- Added Jabber Twitter Bot recognition.
- Added AQQ client recognition.
- Added GMX MultiMessenger, Rival Messenger clients recognition
- Added Tlen icon group, version overlays and Miranda protocol overlay
- Added Helga-Bot, JWChat (WebChat) clients recognition
- Fixed recognizing partial caps MirVer for Trillian clients

Version 1.0.7.0 
- Fixed showing Google Talk as C6
- Fixed epmty icons instead of Miranda icon for some MirVers
- Changed dev.build icon to Miranda 0.8 and added Miranda 0.7 icon.

 Version 1.0.6.0
- Fixed showing "+SimpPro" as "SIM" client
- Added "Openfire" and "Stoat Headline Service" clients to "Jabber" icon group.
- few more mask fixed.


Version 1.0.5.0
- Fixed "JIT - Jabber ICQ Transport" detection.
- Separate "jabber transport" overlay icon (centered)
- More flexible recognition of jabber clients with MirVer's displayed as links.
- Added "Google Talk Gadget" client recognition in "Web-based" icon group.
- Added "SieJc" client recognition in "Mobile" icon group.

Version 1.0.4.0
 - made new icon group "Web-based" clients
 - renamed "Other" icon group to "multi-protocol" 
and removed it from autoload list, also added ovelray subgroup to it.
 - removed few redundant icons.
 - added new clients:
  packs overlays;
Zeleboba's pack
 mobile:
Bombusmod, Chatopus, eqo, Mig33, Mundu, ThumbXP, Valhallachat, Wizzper.
 Jabber:
Akeni, Dziobber, Leaf, Oracle, Palpala, Psyc, Soap Box Comunicator, Tapioca,Tipic, Wija, Xeus.
 Multi-Protocol:
Beenut, EyeBall, IMVU, Meca, Odigo, PalTalk, SAPO, Wipien.
 Web-based:
IloveIM, KoolIM.
 - updated several clients logo.
 - fixed showing ICQ v0.3.10.4 as ISee 
 - changed "Gadu-Gadu > 7.6" to "Gadu-Gadu >= 7.7"

 Version 1.0.3.0
 - Fixed broken translation for options page
 - Added Visoft Build icon
 - Some icons redrawn
 - Added WLM 8.5 and WLM Unknown detection
 - added Wengo Phone cient detection
 - Added LJ bot client detection

 Version 1.0.2.0
 - "Gadu-Gadu > 7.6" will have now "7" overlay
 - Added Weather protocol icon group and icons for all available weather.ini files
 - Added option to disable Weather Client ID icons.
 - Added MDpack icon.
 - Some icons redrawn.
 - By Nullbie:
   unicode support for options,
   use checkbox from Miranda core. 

Version 1.0.1.0
 - Changed Fingeprint License from GPL to Freeware
 Read included docs for more details on it.
 - Changed radio buttons with checkboxes.
 (Now icons can be disabled or used together).
 - Some tweaks for options page.
 - Changed Miranda LS pack icon.
 - Fixed tipo on WLM 8.1 in options.
 - Added Gadu-Gadu > 7,6 detection.
 - Added QIP PDA on Symbian OC detection.
 - Added jabber overlay for mChat.
 - Changed QIP Infium icon according to
 screenshots by InF.
 - Moved Naim icon to Other icon group.
 - Added VmICQ client recognition.
 - Added Carleone Pack icon.
 - Code clean up and reorganizing.
 Removed a lot of redundant  masks,
  fixed few bugs(few more added ;-p )
 - By Nullbie: added warning message box for icon group options.

Version 1.0.0.0
 by Nullbie:
 - Options page
 - This changelog box
 by Angeli-Ka:
 - transfered plugin releases to new Fingerprint Plus account
 - Configured icon options
 - Added different icons for ICQ, MSN, Yahoo versions

 
 

 












