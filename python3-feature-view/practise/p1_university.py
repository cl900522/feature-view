#!python

import bs4
import requests
from bs4 import BeautifulSoup


class Unversity:
    order = 0
    name = ""
    score = 0.0

    def __init__(self, order, name, score):
        self.order = order
        self.name = name
        self.score = score


def getUniversityPage():
    try:
        r = requests.get(
            "https://www.shanghairanking.cn/rankings/bcur/2020", timeout=10)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        print(r.encoding)
        return r.text
    except Exception as e:
        print(e)
        return ""


def parseUniversitys(html):
    ulist = []
    soup = BeautifulSoup(html, "html.parser")
    print(soup.prettify())
    table = soup.find_all("table", attrs={"class": "rk-table"})[0]
    # trs = table.find("tbody").findAll("tr")
    trs = table.tbody.children
    for row in trs:
        if(isinstance(row, bs4.element.Tag)):
            tds = row("td")
            order = tds[0].text.strip("\n ")
            name = tds[1]("div", attrs={"class": "univname"})[
                0]("div")[0]("a")[0].string
            score = tds[4].text.strip("\n ")
            ulist.append(Unversity(order, name, score))
    return ulist


def printToScreen(ulist):
    # 中文的空格
    chspace = chr(12288)
    # {3} 表示用第三个参数进行空格填充
    print("{0:<4}\t{1:{3}<10}\t{2:<10}".format("排名", "名称", "总分", chspace))
    for u in ulist:
        print("{0:<4}\t{1:{3}<10}\t{2:<10}".format(
            u.order, u.name, u.score, chspace))


if __name__ == '__main__':
    html = getUniversityPage()
    uList = parseUniversitys(html)
    printToScreen(uList)
