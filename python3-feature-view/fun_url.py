#!python
#!/usr/bin/python3

import urllib.request
import os

tfile = open("./baidu.com.html", "wb")

file=urllib.request.urlopen('http://www.baidu.com')
data=file.read()    #读取全部

tfile.write(data)
tfile.close()
# os.remove("./baidu.com.html")
