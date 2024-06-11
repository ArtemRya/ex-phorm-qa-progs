#!/usr/bin/python
# -*- coding: utf-8 -*-
import time


###    Settings (change when directory structure changes)
##########################################################

folder_html = '/var/www/html/oix-ui-curl-web-interface/'

file_request_xml = folder_html + 'request.xml'
file_response_xml = folder_html + 'response.xml'
file_curl_log = folder_html + 'curl.log'


###    Default values
###############################

url = 'https://ui.oix-rubytest.net/rs/channels/advertising?channel.ids=2148627'
method = 'GET'

xml = '''
<operations xmlns="http://phorm.com/ui/rs-client/model">
<operation type="UPDATE"> <!-- or CREATE -->




</operation>
</operations>
'''

token = 'c27cfbc0-bbf1-4154-8ded-4e2d983de5b1'
secure_key = 'BMmUIXrKjKrLcqg15yj561cfAyYkzdXUraLREK+0nz/LEimGWwPkEGhnubP74xTU5d+He5pv9nPsrlGjokJMMQ=='

default_autorenew_ts = 'checked'
content_type = 'application/xml'
curl_log = '** NO LOG **'


file_chunk_size = 10000

################################
def timestamp():
    return str (int (time.time() * 1000))
