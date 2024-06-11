#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
George Regentov, Artem Ryabov
"""
import  cgi, re, urlparse, settings, lib, sys, os, time, logging
import cgitb; cgitb.enable()


logging.basicConfig(format='%(asctime)s %(message)s', filename='api-pycurl.log', datefmt='%d/%m/%y %H:%M:%S', level=logging.INFO)
logging.debug ("START")



##########        Get & preocess input data, calculate form labels & stuff
###############################################################################

message = ''
NOT_SET = '-no-' # just an indicator that the value is Not Set (absent) 

form = cgi.FieldStorage() 
url  = form.getvalue('url', settings.url)
xml  = form.getvalue('xml', settings.xml)
token = form.getvalue('token', settings.token)
secure_key  = form.getvalue('secure_key', settings.secure_key)
#submit_generate = form.getvalue('generate', NOT_SET)
submit = form.getvalue('submit', NOT_SET)
content_type = form.getvalue('content_type', settings.content_type)
curl_log = form.getvalue('curl_log', settings.curl_log)

if 'file_to_upload' in form:
    fileitem = form['file_to_upload']
    fn = os.path.basename(fileitem.filename)
    if not (fileitem.file and fn <> ''):
        fileitem = NOT_SET
else:
    fileitem = NOT_SET

autorenew_timestamp_signature = form.getvalue('autorenew_timestamp_signature', '')
if submit == NOT_SET: # user has just navigated to client - INITIAL STATE
    autorenew_timestamp_signature = settings.default_autorenew_ts
if autorenew_timestamp_signature:
    timestamp =  settings.timestamp()
else:
    timestamp = form.getvalue('timestamp', settings.timestamp())

if (submit == NOT_SET or autorenew_timestamp_signature or submit == "recalculate signature"):
    hmac_sha1_signature = lib.hmac_sha1_signature_base64(secure_key, timestamp) # FORCE generate signature
else: # Submit button or 1st load
    hmac_sha1_signature = form.getvalue(
                                         'hmac_sha1_signature',
                                         lib.hmac_sha1_signature_base64(secure_key, timestamp)
                                         )

minutes_from_now = lib.seconds_from_now(timestamp)



##    Form cUrl command 
##########################

if (submit != NOT_SET and submit != "recalculate signature"):  # this page is displayed after Submit button was clicked)
    request = 'curl -v -k -L -H "Accept: */*" -H "X-Timestamp: ' + timestamp
    request += '" -H "Authorization: TIMESTAMP ' + token + ':' + hmac_sha1_signature
    request += '" ' + url + ' -o ' + settings.file_response_xml
    if submit.find('POST') >= 0:
        if fileitem != NOT_SET: #) and (fileitem.filename):
            logging.debug ("POSTing file " + fn)
            fn = '/tmp/qa-oix-api-client-pyCurl/' + os.path.basename(fileitem.filename)
            if not os.path.exists(os.path.dirname(fn)):
                os.makedirs(os.path.dirname(fn))
            f = open(fn, 'wb', settings.file_chunk_size)
            logging.debug ("starting to write the file...")
            chunk = fileitem.file.read (settings.file_chunk_size)
            i = 0
            start = time.time()
            last_print = start
            while chunk:
                i+=1
                f.write(chunk)
                chunk = fileitem.file.read (settings.file_chunk_size)

                now = time.time()
                if now - last_print >= 5.0:
                    if start == last_print: #very 1st print => print headers
                        print "Content-Type: text/html\n\n<html><body>\n"
                    last_print = now
                    logging.debug ("%s s: %s Mb", (now-start), i * settings.file_chunk_size / 1000000)
                    print now-start, "s: ", i * settings.file_chunk_size / 1000000, "Mb<br/>\n"
            f.close()
            message += 'The file "' + fn + '" was written to server successfully <BR/>\n'
        else:
            message += "No file loaded, postong XML <br/>\n"
            content_type = "application/xml"
            f = open(settings.file_request_xml, "w")
            f.write(xml)
            f.close()
            fn = settings.file_request_xml
        request += ' -H "Content-type: ' + content_type + '" --data-binary "@' + fn + '" '
    elif submit.find('GET') == -1:
        message += '<h1><font color="red"> !! Unknown method setting - error in template.htm !! </font></h1>\n'


    ########## Execute cUrl command (send request to the OIX API)
    #############################################################

    output = lib.exe(request)
    curl_log = output[1]
    
    http_codes =  re.findall('HTTP\S*\s\d{3}\s[\w\s]*', curl_log)
    message +=  '<table><tr>\n<td> Codes: </td>\n'
    for code in http_codes:
        if re.search("2\d\d", code):
            bgcolor = "green"
        elif code.find("100") > -1:
            bgcolor = "gray"
        else:
            bgcolor = "red"
        message += '<td bgcolor="' + bgcolor + '"><b> ' + code + '</b></td>\n'
    message +=  '</tr></table>\n'


# Send CGI response
###################

lib.print_html(locals())
sys.exit(0)
