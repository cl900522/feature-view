import webbrowser


# new:0/1/2 0：同一浏览器窗口打开 1：打开浏览器新的窗口，2：打开浏览器窗口新的tab
webbrowser.open("http://www.google.com", new=2, autoraise=True)

webbrowser.open_new("http://www.google.com")
webbrowser.open_new_tab("http://www.google.com")


'''
chromepath = 'C:\***\***\***\***\Google\Chrome\Application\chrome.exe'
webbrowser.register('chrome', None, webbrowser.BackgroundBrowser(chromepath))
webbrowser.get('chrome').open('http://www.baidu.com')
'''
