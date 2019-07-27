#!/usr/bin/python3

import urllib.request

print(("="*27+"{}"+"="*27).format("common http"))

file = urllib.request.urlopen('http://www.baidu.com')
data = file.read(100)  # 读取全部
print(data)

print(("="*27+"{}"+"="*27).format("proxy"))

proxy_handler = urllib.request.ProxyHandler({'https': 'socks://192.168.100.100:1080'})
'''
proxy_auth_handler = urllib.request.ProxyBasicAuthHandler()
proxy_auth_handler.add_password('realm', 'host', 'username', 'password')
'''

https_handler = urllib.request.HTTPSHandler()

opener = urllib.request.build_opener( proxy_handler,https_handler)
# This time, rather than install the OpenerDirector, we use it directly:
w = opener.open('https://www.baidu.com/')

print(w.read(100))