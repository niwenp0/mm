mm: simple spray-based RESTful api
===================================
 
This server is intended to upload text files (ASCII) no bigger than 10m and return details such as word count, and
count per word.

Usage
======

clone this repo for testing/usage (You need SBT to run it) 

 ```console
//For starting the server:
    sbt run
//unit testing:
    sbt test
```

The api has 3 endpoints (port 8080): 

for uploading a file: POST -> /upload
for viewing existing stats on a file: GET -> /stats/"filename"
for viewing existing stats without a given keyword: POST -> /filteredStats

examples with curl :

 ```console

    curl -X POST -F "filename=file-name" -F "filecontent=@file/path" http://127.0.0.1:8080/upload

    curl -X POST -F "filename=file-name" -F "keyword=any-word" http://127.0.0.1:8080/filteredStats

    curl http://127.0.0.1:8080/stats/filename
 ```

To Stop the server, hit "Enter"