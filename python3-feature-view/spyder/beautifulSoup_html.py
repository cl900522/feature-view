#!python
# pip install --upgrade requests
# pip install --upgrade beautifulsoup4

import re
import requests
from bs4 import BeautifulSoup


import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')


def searchBaidu(keyWord):
    hkv = {"user-agent": "Mozilla/5.0"}
    params = {"wd": keyWord}

    try:
        r = requests.request("GET", "http://www.baidu.com/s",
                             params=params, timeout=10, headers=hkv)

        r.raise_for_status()
        print("访问的url: ", r.request.url)
        r.encoding = r.apparent_encoding
        return r.text
    except Exception as e:
        print(e)
        return "<html><body><p>Error</p></body></html>"


pageHtml = searchBaidu("java")
soup = BeautifulSoup(pageHtml, "html.parser")
# 美化输出，便于查看和分析
# print(soup.prettify())
print("-"*20)
if soup.parent is None:
    print("soup has no parent")

print("-"*20)
tops = soup.find_all("tr", attrs={"class", "toplist1-tr"})
index = 1
for top in tops:
    innerA = top.a
    innerA = top.findAll("a")[0]
    print(index, innerA.string.strip("$\t\n "), sep=". ")
    index = index+1

print("-"*20)
# 以b 开头的所有标签
for tag in soup.find_all(re.compile("^b")):
    print(tag.name, tag.string, sep="->")

print("-"*20)
for tag in soup.find_all(attrs={"class", "toplist1-tr"}):
    print(tag.name)
