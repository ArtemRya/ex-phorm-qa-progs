This CGI script provide access to (interacts with) OIX UI Restful HTTP API now called Web Services: https://confluence.ocslab.com/display/TDOC/Web+Services

Default settings can be changed in settings.py (also see point 2.3 below)



*****************************
**  Deployment instruction **
*****************************

1.1. Put the project root folder files to "cgi-bin/oix-ui-curl-web-interface" folder of Apache
i.e. "*.py" and "template.htm" files are in "/var/www/cgi-bin/qa-oix-api-client-pyCurl/<<version folder>>" folder, where version is supposed to be this API client version (see https://confluence.ocslab.com/display/QA/OIX+API+clients ).
win pscp command example:
> H:\Programs\PuTTY\pscp.exe -pw <<YOUR_PASSWORD>> *.py ..\template.htm YOUR_NAME@vauto:/var/www/cgi-bin/qa-oix-api-client-pyCurl/33b  


1.2. "reports.py" must have exec permission (chmod a+x reports.py), others should have at least read permission.


1.3. Create empty "attendance" file with READ-WRITE permissions:
$ touch attendance; chmod a+rw attendance 


2.1. Put all the files of /html project folder to "html/oix-ui-curl-web-interface" folder of Apache
win pscp command example:
> H:\Programs\PuTTY\pscp.exe -pw <<YOUR_PASSWORD>> html\*.* YOUR_NAME@vauto:/var/www/html/oix-ui-curl-web-interface


2.2. execute html.deploy.sh (it has comments on what it does)
$ chmod a+x html.deploy.sh; ./html.deploy.sh


2.3. Make sure "cgi-bin/oix-ui-curl-web-interface/settings.py" has correct path of "html/oix-ui-curl-web-interface" folder:
folder_html = '/var/www/html/oix-ui-curl-web-interface/'
(default settings can be changed there also)