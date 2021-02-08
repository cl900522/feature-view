#!python
# pip install --upgrade requests
# pip install beautifulsoup4

import requests
from bs4 import BeautifulSoup


def getUrl(url):

    proxies = {
        "https": "http://127.0.0.1:1081",
        "http": "http://127.0.0.1:1081"
    }
    hkv = {"user-agent": "Mozilla/5.0"}
    try:
        r = requests.request("GET", url, timeout=10,
                             proxies=proxies, headers=hkv)
        # r = requests.get(url, timeout=3, proxies=pxs)
        r.raise_for_status()

        r.encoding = r.apparent_encoding
        print(r.request.headers)
        return r.text

    except:
        return 'Error'


# print(getUrl("https://www.youtube.com/watch?v=kBu8vUkk2S0/"))


def searchBaidu(keyWord):
    hkv = {"user-agent": "Mozilla/5.0"}
    params = {"wd": keyWord}
    try:
        r = requests.request("GET", "http://www.baidu.com/s",
                             params=params, timeout=10, headers=hkv)
        # r = requests.get(url, timeout=3, proxies=pxs)
        r.raise_for_status()
        print(r.request.url)
        r.encoding = r.apparent_encoding
        return r.text
    except:
        return 'Error'


# print(searchBaidu("python"))

pageHtml = searchBaidu("java")
# print(pageHtml)
soup = BeautifulSoup(pageHtml, "html.parser")

tops = soup.find_all("tr", attrs={"class", "toplist1-tr"})
for top in tops:
    a3 = top.findAll("a")
    print(a3[0].string.strip("$\t\n "))
