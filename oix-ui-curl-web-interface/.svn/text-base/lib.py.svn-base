#!/usr/bin/python
# -*- coding: utf-8 -*-

import subprocess as sub
import os, shlex, sys, binascii, hmacsha1, time, settings
template_var_start = '###'
template_var_end = '###'

# execute bash commands
def exe(cmd):
    p = sub.Popen(shlex.split(cmd),stdout=sub.PIPE,stderr=sub.PIPE)
    f = open(settings.file_curl_log, "a+")
    f.write(cmd + '\n')
    f.close()
    return p.communicate()

# collect statistic:  ip: <count of use>    
def statistic():
    try:
        f = open("attendance", "r+")
        ip_list = dict( (x.split(":")[0], int(x.split(":")[1])) for x in f.readlines())
        ip_list.setdefault( os.environ['REMOTE_ADDR'], 0)
        ip_list[os.environ['REMOTE_ADDR']] += 1
        f.seek(0)
        for x in ip_list:
	    f.write( "%s:%i\n" % (x, ip_list[x]) )
        f.close()
    except:
	pass

# in 'line' replaces all 'variables' like ###url### with corresponding keys from dictionary 'data'
def template_replace(line, data):
    for key in data:
        if isinstance(data[key], basestring):
            line = line.replace (template_var_start + key + template_var_end, data[key])
    return line

# in template.html file replaces all 'variables' like ###url### with corresponding keys from dictionary 'data' [which is locals()]
def print_html(data):
    file = open("template.htm","r")
    print "Content-Type: text/html \n\n"
    line = "nonempty"
    while (line != ""):
        line = file.readline()
        line = template_replace (line, data)
        print line
    file.close()
    statistic()

# returns HMAC_SHA1 signature of timestamp_str (the message) with secure_key_base64 (key)
# all in/out values are in encodings used in OIX UI, all strings 
def hmac_sha1_signature_base64(secure_key_base64, timestamp_str):
    result_binary = hmacsha1.digest(binascii.a2b_base64(secure_key_base64), timestamp_str)
    return binascii.b2a_base64(result_binary).strip('\r\n \t')

# returnes string difference of given time from now in 'mmm:ss' format
def seconds_from_now(timestamp_string):
    try:
        difference_seconds = int(timestamp_string)/1000 - time.time()
        difference_minutes = int(difference_seconds / 60)
        difference_seconds = int(abs(difference_seconds) - abs(difference_minutes) * 60)
        if (difference_seconds < 10):
            difference_seconds = '0' + str (difference_seconds)
        else:
            difference_seconds = str (difference_seconds)
        return str(difference_minutes) + ":" + str(difference_seconds)
    except:
        e = sys.exc_info()[1]
        return "*failed: %s*" % e
