/--------------------------------------------------------------
|
| Satin (Port) by Russellc (http://chunk.3rror.com)
|    - thechunkster2k3 (AOLIM)
|    - i_use@email.com (MSN)
|    - thechunk@gmail.com (E-Mail)
|
| Original Satin by Yojimbo (AIM - yojimbo311) - http://www.adiumxtras.com/index.php?a=users&do=profile&user_id=230
|    - Original template URL - http://www.adiumxtras.com/index.php?a=xtras&xtra_id=305
|
|
|       -Thanks to Yojimbo for a great original template!-
|
|       NOTE: WHEN UPDATING, replace all files with ones included in archive
|
|
\--------------------------------------------------------------
 |
 |  How do I install this? (assuming you have IEView set up properly)
 |    1) Extract the files using 7zip, Winrar, or XP's built in ZIP support
 |       (anything that can extract .ZIPs properly will do) to any folder
 |        > It is -recommended- to extract in the Miranda folder.
 |    2) Go to the IEView options and go to the Templates tab.
 |    3) Browse (...) for the folder Satin\ that you should have remembered
 |       and double click the satin.ivt file (in Satin\ folder).
 |    4) Hit Apply and start chatting!
 |
 |
 |  Stuff you'll want to know:
 |    -  What variations of Satin are available to me?
 |        > satin_protocol.ivt - Like the actual Satin from AdiumX, it will
 | 				 show the protocol in place of "Me" and "Buddy"
 |			       - Requires latest IEView (1.0.2.0)
 |        > satin.ivt - This is the original one. Only shows time for new
 |                      grouped messages and the words "Buddy" and "Me" in
 |                      the creation of a new message group.
 |        > satin_avatarsright.ivt - Same as original, except the avatar position
 |                                   is moved to the right side of all messages
 |                                   like in the MSN Messenger client.
 |        > satin_bigavatars.ivt - Same as original, but shows avatars with
 |                                 dimensions of 48x48 pixels.
 |        > satin_bigavatarsright.ivt - Same as satin_bigavatars.ivt except shows
 |                                      avatars on the right side of conversation
 |        > satin_cid.ivt - Same as original, except instead of "Buddy" and
 |                          "Me", it shows the contact ID.
 |        > satin_duckavatars.ivt - Same as original, but shows default duck
 |                                  avatars instead of the actual avatar of
 |                                  your buddy.
 |        > satin_noavatars.ivt - Same as original, but does not display an 
 |                                avatar box, so it will allow more room for
 |                                the message to expand into.
 |        > satin_timedate.ivt - Same as original, but instead of "Buddy" and
 |                               "Me", it displays the Time and Date. This is
 |                               dependant on your IEView settings. 
 |
 |    -  Why isn't my avatar showing up?
 |        > You need to save your avatar under the \Satin\ folder
 |          that is extracted from the .ZIP archive as myavatar.jpg.
 |          There is already a file named myavatar.jpg; it is the
 |          default avatar to be in place of your actual avatar.
 |
 |    -  Why isn't my buddy's avatar showing up?
 |        > You need the latest version of IEView (1.0.1.7) and
 |          TabSRMM (http://tabsrmm.sf.net)
 |
 |    -  Is this the only colour scheme?
 |        > Nope! There are 2 other colour schemes. The default
 |          template is Green-Blue-Orange. There are the following:
 |            = Green-Blue-Purple   (greenbluepurple.css)
 |            = Red-Blue-Purple     (redbluepurple.css)
 |            = Green-Blue-Orange   (greenblueorange.css)
 |
 |        > To change schemes:
 |            1) Open the file satin.ivt in Notepad
 |            2) Pick the scheme you want to change to
 |                ex. Red-Blue-Purple (redbluepurple.css)
 |            3) Return to satin.ivt in Notepad and take the text in
 |               parenthesis (brackets) and paste it into the line
 |               near the top that, by default, says greenblueorange.css
 |                ex. <link rel="stylesheet" type="text/css" href="greenblueorange.css" />
 |                                     - BECOMES -
 |                    <link rel="stylesheet" type="text/css" href="redbluepurple.css" /> 
 |            4) Save satin.ivt and close Notepad
 |            5) Open Miranda IM Options and navigate:
 |                Message Sessions -> IEView plugin -> Templates
 |            6) Click on the Browse (...) button and rechoose satin.ivt
 |            7) Hit Apply
 |            8) Close all conversation windows and reopen them
 |            9) The scheme should now be changed =)
 |
 |    -  Does message grouping work?
 |        > Yup! Just enable it in Miranda IM Options -> IEView plugin -> Templates tab
 |    
 |    -  How did you get it working? I heard you had lots of problems fixing it.
 |        > I must thank cpm for the Javascript code which made the
 |          message grouping a LOT easier to make working.
 |        > THANKS again cpm!
 |
 |
 |  Changelog:
 |   March 23, 2005 (v1.2.3)
 |   -  New variation
 |       > satin_protocol.ivt - More like the actual template on AdiumX
 | 			      - It shows the protocol rather than "Me"
 |				and "Buddy".
 |                            - REQUIRES IEView 1.0.2.0 (latest)
 |                                > http://developer.berlios.de/project/showfiles.php?group_id=3292&release_id=5082
 |
 |   March 6, 2005 (v1.2.2)
 |   -  Minor Bugfix
 |       > Long non-spaced lines would not wrap.
 |
 |   March 5, 2005 (v1.2.1)
 |   -  Minor Bugfix
 |       > When not using Group Messaging, the historical message time would
 |         be in an incorrect place.
 |
 |   March 4, 2005 (v1.2)
 |   -  The "bubbles" on right side of the name bar does not have the left side
 |      snipped a bit when using small sized conversation window.
 |   -  Two new modifications for satin.ivt (48x48px avatars)
 |       > satin_bigavatars.ivt shows avatars on left side, like satin.ivt with
 |         dimensions of 48px width and height.
 |       > satin_bigavatarsright.ivt does the same, but shows avatars on the
 |         right side.
 |       > Fixed the weird bug when sending a single character message with 
 |         the option to display time unchecked.
 |
 |   March 3, 2005 (v1.1)
 |   -  Minor re-organization
 |   -  Emoticons/Smileys no longer "stick" to the top of each message
 |   -  Two modified versions of original satin.ivt to include time & date and
 |      the contactid (in the filenames, respectively)
 |       > Original satin.ivt shows only time.
 |       > satin_timedate.ivt, you can toggle between showing time and date
 |         (through IEView options)
 |   -  Two more modified versions of satin.ivt
 |       > satin_duckavatars.ivt shows the avatar box, but with the default
 |         "duck" avatars only. This can be for people who don't like avatars
 |       > satin_noavatars.ivt does not show the avatar box at all, allowing
 |         more space for the message to expand. This can be for people who 
 |         use only protocols that do not have avatar support or do not like
 |         seeing avatars at all or want more space for messages sent/received.
 |   -  History templates done. Report bugs please, if any.
 |   -  Small font modifications (Name and "Buddy"/"Me", variations included,
 |      are now Tahoma.
 |   -  One final update: One more variation of satin.ivt
 |       > satin_avatarsright.ivt changes the avatar position from the left
 |         side to the right side of the messages (like in MSN Messenger)
 |
 |   March 2, 2005 (v1.0)
 |   -  Permission granted, release time.
 |   -  Link styles added
 |   
 |   Feb 28, 2005 (Testing)
 |   -  It is operational, now for bugfixes and final touches
 |
 |
 |  Permission from Yojimbo:
 |   Hi there, yeah as long as you put appropriate credit and the
 |   source of the original in your documentation and website
 |   comments it's fine
 |
 |
 |  What should be done?
 |   -  Proportional and Antialiased resizing of avatars
 |      (do not know how to achieve this yet)
 |
 |
 |
 |
 |
 |
 \-------------------------------------------------------------