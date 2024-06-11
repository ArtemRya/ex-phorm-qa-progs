#!/bin/bash

# previous instructions are in ../deploy.txt

# 2.1. create empty "response.xml" and "curl_usecase.txt" and grant it READ-WRITE permissions:
touch response.xml; chmod a+rw response.xml
touch curl.log; chmod a+rw curl.log
touch request.xml; chmod a+rw request.xml

# 2.2. create links to response.xml as .csv, .xls, .xlsx files:
ln -s response.xml csv_report.csv; ln -s response.xml excel_report.xls; ln -s response.xml excel-X_report.xlsx
