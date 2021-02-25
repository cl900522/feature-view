import requests
from bs4 import BeautifulSoup
import webbrowser
import os

'''

AppID：23658912
Appkey：jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8
Secretkey：xBVScjRgY6OFBNfD8XWwmUZe5TaGhzOV
Signkey：W%82SbN2M8UfRjjAn-0flDSY0%q0i#xR
'''

headers = {
    'User-Agent': 'pan.baidu.com'
}

accessToken = "121.c3dfd823cdc07c24262085bf7e3daddb.Y7Dov6v4ReVpcmB2ipvTF78lOSy64EJvWsfy_U5.y2EZRA"


authPageUrl = "http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8&redirect_uri=oob&scope=basic,netdisk&display=page&qrcode=0&force_login=0"

authPageUrl2 = "http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8&redirect_uri=http%3a%2f%2flocalhost&scope=basic,netdisk&display=page&qrcode=0&force_login=1"


getTokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=de6d06dab68ed1a138c9d930d917f043&client_id=jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8&client_secret=xBVScjRgY6OFBNfD8XWwmUZe5TaGhzOV&redirect_uri=http%3a%2f%2flocalhost"

refreshTokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grans_type=refresh_token&refresh_token=REFRESH_TOKEN&client_id=mn54eflQl3NFCP6NWcX0sfNt&client_secret=32bTxIZvSOchabiThumXoeF2oNssVNRe"

getTokenDirectUrl = "http://openapi.baidu.com/oauth/2.0/authorize?response_type=token&client_id=mn54eflQl3NFCP6NWcX0sfNt&redirect_uri=oob&scope=basic,netdisk&display=page&state=xxx"


def getToken():
    getTokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=73a8c515f9e2acedd3991e01877f2d9a&client_id=jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8&client_secret=xBVScjRgY6OFBNfD8XWwmUZe5TaGhzOV&redirect_uri=http%3a%2f%2flocalhost"
    r = requests.request("GET", getTokenUrl, headers=headers,
                         data={}, files={})
    # print(r.text)

    refreshTokenUrl = "https://openapi.baidu.com/oauth/2.0/token?grans_type=refresh_token&refresh_token=121.7b59104db0310d7c45547113fd57ad8e.YQf2jNj2V2AejaN3uP9v8jgltVax3Ya9yzMBE9Q.SmRt-w&client_id=jl7GShLx9vdzCDcTRgn5iyNvG04FQqu8&client_secret=xBVScjRgY6OFBNfD8XWwmUZe5TaGhzOV "

    r = requests.request("GET", refreshTokenUrl, headers=headers,
                         data={}, files={})
    print(r.text)


def getFileList():
    getFileListUrl = "https://pan.baidu.com/rest/2.0/xpan/file?method=list&access_token=" + \
        accessToken
    payload = {
        "access_token": accessToken,
        "start": 0,
        "limit": 100,
        "folder": 0,
        "order": "name",  # time  size
        "desc": "name",
        "dir": "/"
    }
    files = {}
    r = requests.request("GET", getFileListUrl, headers=headers,
                         data=payload, files=files)
    print(r.request.url)
    r.raise_for_status()
    r.encoding = r.apparent_encoding
    print(r.text.encode("utf-8"))


def getFileMeta():
    getFileMetaUrl = "https://pan.baidu.com/rest/2.0/xpan/multimedia?method=filemetas&access_token=" + \
        accessToken+"&fsids="+'[1098091656457856,85855711317195]'+"&dlink=1"
    payload = {
        "access_token": accessToken,
        "start": 0,
        "limit": 100,
        "folder": 0,
        "order": "name",  # time  size
        "desc": 1,
        "dir": "/"
    }
    files = {}
    r = requests.request("GET", getFileMetaUrl, headers=headers,
                         data=payload, files=files)
    print(r.request.url)
    r.raise_for_status()
    r.encoding = r.apparent_encoding
    print(r.text)


def downloadFile():
    url = "https://d.pcs.baidu.com/file/35405adbd46c0198a36c65d79c4401f3?fid=1342310203-250528-85855711317195&rt=pr&sign=FDtAERV-DCb740ccc5511e5e8fedcff06b081203-XvVOLeEA5SEWvbVvh7k9OgA5gVE%3D&expires=8h&chkbd=0&chkv=2&dp-logid=456792960140446359&dp-callid=0&dstime=1613065427&r=758271372&origin_appid=23658912&file_type=0"+"&access_token="+accessToken

    z = "https://d.pcs.baidu.com/file/35405adbd46c0198a36c65d79c4401f3?fid=1342310203-250528-85855711317195&rt=pr&sign=FDtAERV-DCb740ccc5511e5e8fedcff06b081203-YkLrA4Q3S6EI9WVhJAKRGnP%2Bhb8%3D&expires=8h&chkbd=0&chkv=2&dp-logid=426652286752712338&dp-callid=0&dstime=1613151028&r=211624296&origin_appid=23658912&file_type=0&access_token121.d511fc135618fe489b1f890e9d67a196.Y_kyYjMNW0yfgbiaH00L0FP11rI7gfUviktZDg8.51wtrQ"

    r = requests.request("GET", url, headers=headers)
    r.raise_for_status()
    file = open("a.pdf", "wb")
    file.write(r.content)
    file.close()


if __name__ != "__main__":
    webbrowser.open(authPageUrl)
else:
    getToken()
    '''
    {"errno":0,"guid_info":"","list":[{"server_filename":"apps","category":6,"unlist":0,"isdir":1,"oper_id":0,"server_atime":0,"server_ctime":1393038679,"wpfile":0,"local_mtime":1393038679,"size":0,"server_mtime":1393038679,"share":0,"path":"\\/apps","local_ctime":1393038679,"pl":0,"fs_id":2282824889},{"server_filename":"Books","category":6,"unlist":0,"isdir":1,"oper_id":0,"server_atime":0,"server_ctime":1461583296,"wpfile":0,"local_mtime":1461583296,"size":0,"server_mtime":1461583296,"share":0,"path":"\\/Books","local_ctime":1461583296,"pl":0,"fs_id":279051365407147},{"server_filename":"Documents","category":6,"unlist":0,"isdir":1,"oper_id":0,"server_atime":0,"server_ctime":1461583317,"wpfile":0,"local_mtime":1461583317,"size":0,"server_mtime":1461583317,"share":0,"path":"\\/Documents","local_ctime":1461583317,"pl":0,"fs_id":352852397726486},{"server_filename":"Video","category":6,"unlist":0,"isdir":1,"oper_id":0,"server_atime":0,"server_ctime":1461426267,"wpfile":0,"local_mtime":1461426267,"size":0,"server_mtime":1461426267,"share":0,"path":"\\/Video","local_ctime":1461426267,"pl":0,"fs_id":34402313381390},{"server_filename":"\\u6211\\u7684\\u8d44\\u6e90","category":6,"unlist":0,"isdir":1,"oper_id":1342310203,"server_atime":0,"server_ctime":1561730081,"wpfile":0,"local_mtime":1561730081,"size":0,"server_mtime":1561730081,"share":0,"path":"\\/\\u6211\\u7684\\u8d44\\u6e90","local_ctime":1561730081,"pl":0,"fs_id":680675788327548},{"category":6,"unlist":0,"fs_id":1098091656457856,"server_filename":"xDroidInstall-x86_64-v2.7000-20190621155253.tar.gz","pl":0,"server_atime":0,"server_ctime":1561368222,"wpfile":0,"local_mtime":1561104883,"md5":"f6ca732ef8be7ebd27edcd6b3f9659b7","share":0,"size":718974612,"path":"\\/xDroidInstall-x86_64-v2.7000-20190621155253.tar.gz","local_ctime":1561368222,"server_mtime":1567784578,"isdir":0,"oper_id":0},{"category":4,"unlist":0,"fs_id":85855711317195,"server_filename":"[\\u7b97\\u6cd5\\u4e4b\\u9053(\\u7b2c\\u4e8c\\u7248)].pdf","pl":0,"server_atime":0,"server_ctime":1574867461,"wpfile":0,"local_mtime":1574867461,"md5":"35405adbd46c0198a36c65d79c4401f3","share":0,"size":37083146,"path":"\\/[\\u7b97\\u6cd5\\u4e4b\\u9053(\\u7b2c\\u4e8c\\u7248)].pdf","local_ctime":1574867461,"server_mtime":1580796664,"isdir":0,"oper_id":0},{"category":6,"unlist":0,"fs_id":861383017706780,"server_filename":"\\u5c0f\\u9738\\u738bFC\\u6e38\\u620f\\u5927\\u5408\\u96c6\\uff0c\\u4f60\\u8981\\u7684\\u90fd\\u6709\\uff08\\u5305\\u542b\\u65e0\\u654c\\u7248\\u548c\\u4fee\\u6539\\u7248\\uff09.rar","pl":0,"server_atime":0,"server_ctime":1483083088,"wpfile":0,"local_mtime":1483083088,"md5":"3ce17663c286c2573cbe40c97c253d2f","share":0,"size":196014152,"path":"\\/\\u5c0f\\u9738\\u738bFC\\u6e38\\u620f\\u5927\\u5408\\u96c6\\uff0c\\u4f60\\u8981\\u7684\\u90fd\\u6709\\uff08\\u5305\\u542b\\u65e0\\u654c\\u7248\\u548c\\u4fee\\u6539\\u7248\\uff09.rar","local_ctime":1483083088,"server_mtime":1554632791,"isdir":0,"oper_id":1342310203}],"request_id":975571321659648201,"guid":0}
    '''
 #   getFileList()

    '''
    {"errmsg":"succ","errno":0,"list":[{"category":4,"dlink":"https://d.pcs.baidu.com/file/35405adbd46c0198a36c65d79c4401f3?fid=1342310203-250528-85855711317195\u0026rt=pr\u0026sign=FDtAERV-DCb740ccc5511e5e8fedcff06b081203-XvVOLeEA5SEWvbVvh7k9OgA5gVE%3D\u0026expires=8h\u0026chkbd=0\u0026chkv=2\u0026dp-logid=456792960140446359\u0026dp-callid=0\u0026dstime=1613065427\u0026r=758271372\u0026origin_appid=23658912\u0026file_type=0","filename":"[算法之道(第二版)].pdf","fs_id":85855711317195,"isdir":0,"md5":"35405adbd46c0198a36c65d79c4401f3","oper_id":0,"path":"/[算法之道(第二版)].pdf","server_ctime":1574867461,"server_mtime":1580796664,"size":37083146},{"category":6,"dlink":"https://d.pcs.baidu.com/file/f6ca732ef8be7ebd27edcd6b3f9659b7?fid=1342310203-250528-1098091656457856\u0026rt=pr\u0026sign=FDtAER-DCb740ccc5511e5e8fedcff06b081203-6UXHrDdSfKnLzP%2BJ5XWDhuNZe1g%3D\u0026expires=8h\u0026chkbd=0\u0026chkv=0\u0026dp-logid=456793048965677502\u0026dp-callid=0\u0026dstime=1613065427\u0026r=758271372\u0026origin_appid=23658912\u0026file_type=0","filename":"xDroidInstall-x86_64-v2.7000-20190621155253.tar.gz","fs_id":1098091656457856,"isdir":0,"md5":"f6ca732ef8be7ebd27edcd6b3f9659b7","oper_id":0,"path":"/xDroidInstall-x86_64-v2.7000-20190621155253.tar.gz","server_ctime":1561368222,"server_mtime":1567784578,"size":718974612}],"names":{},"request_id":"456751995765227852"}
    '''
    # getFileMeta()

    # downloadFile()
