# -*- coding: utf-8 -*-
#!python

# pip install --upgrade requests

import os
import requests
import time

import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

root = "C://sources/"


def downloadSource(downUrl):
    global root
    dtr = time.strftime("%Y-%m-%d", time.localtime(time.time()))
    folder = root + dtr + "/"

    path = downUrl.split("/")[-1]
    path = path.split("?")[0]
    path = folder + path

    try:
        if not os.path.exists(folder):
            os.makedirs(folder)

        if not os.path.exists(path):
            r = requests.get(downUrl)
            with open(path, "wb") as f:
                f.write(r.content)
                f.close()
                print("下载成功:", path)
        else:
            print("文件已存在")
    except Exception as e:
        print(e)


downloadSource("https://pics7.baidu.com/feed/30adcbef76094b362daf02bb671378d18c109d8a.jpeg?token=0f070f2671d19774ce47ca55500d2da8&s=47741BC446F10D867B05789103009088")
downloadSource(
    "https://imgm.gmw.cn/attachement/jpg/site215/20210208/5084241082508722333.jpg")
