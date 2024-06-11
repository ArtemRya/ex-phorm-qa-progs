#!/usr/bin/python
# -*- coding: utf-8 -*-
import hmac, binascii, sha
def digest(key,message):
    return hmac.new(key, message, sha).digest()