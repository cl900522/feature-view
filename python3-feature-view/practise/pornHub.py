#!python

import urllib.request
import urllib.parse
from urllib import request
from bs4 import BeautifulSoup

siteUrl = "https://www.pornhub.com"
keyWord = "中国"

serchKey = urllib.parse.quote(keyWord)
pageSize = 1
num = 100

class Movie:
    id = 0
    title = ''
    url = ''
    img = ''
    shortMov = ''

    def __init__(self, d, t, u, i):
        self.id = d
        self.title = t
        self.url = siteUrl+u
        self.img = i

'''
print(type(soup))
print(soup.find_all('span'))  #标签查找
print(soup.find_all('a',id='link1'))  #属性加标签过滤
print(soup.find_all('a',attrs={'class':'sister','id':'link3'})) #多属性
print(soup.find_all('p',class_='title'))  #class特殊性,此次传入的参数是**kwargs
print(soup.find_all(text=re.compile('Tillie')))  #文本过滤
print(soup.find_all('a',limit=2))  #限制输出数量
'''

def printMoreMovies(s,k):
    urlTpl = siteUrl+"/video/search?page={0}&search={1}"
    page = urllib.request.urlopen(urlTpl.format(s,k), timeout=10)
    pageHtml = page.read()

    soup = BeautifulSoup(pageHtml, "html.parser")

    allLis = soup.find_all("li", attrs={"class", "videoblock"})
    for li in allLis:
        spans = li.findAll("span", attrs={"class", "title"})
        imgs = li.findAll("img")

        span = spans[0]
        img = imgs[0]

        movie = Movie(img.attrs["data-video-id"],
                    span.a.string,
                    span.a.attrs["href"], 
                    img.attrs["data-thumb_url"])
        print("{m.id:<10}{m.title:<100}{m.url:<150}{m.img:<150}".format(m=movie))

    return len(allLis)


while num > 0:
    print("Got at page ", pageSize)
    size = printMoreMovies(pageSize, serchKey)
    if(size >=0):
        num -= size
        print("Have got ", size)
    else:
        break

    pageSize += 1
