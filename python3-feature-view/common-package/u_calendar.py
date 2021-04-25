#!python
#!/usr/bin/pytyon3

import calendar
import time


# 设置日历的每周第一天是星期一
calendar.setfirstweekday(0)
# 设置日历的每周第一天是星期六
# calendar.setfirstweekday(6)
print("日历每周第一天是星期 %d " % ((calendar.firstweekday()+1)))

year = time.localtime().tm_year
month = time.localtime().tm_mon
day = 1
# 返回一个多行字符串格式的year年年历，3个月一行，间隔距离为c。 每日宽度间隔为w字符。每行长度为21* W+18+2* C。l是每星期行数。
cal = calendar.calendar(year, w=2, l=1, c=6)
print(cal)

# 返回一个多行字符串格式的year年month月日历，两行标题，一周一行。每日宽度间隔为w字符。每行的长度为7* w+6。l是每星期的行数。
cal = calendar.month(year, month)
print("以下输出%d年%d月份的日历:" % (year, month))
print(cal)

dayran = calendar.monthcalendar(year, month)
print(dayran)

dOfWeek = calendar.weekday(year, month, day)
print("%d 年 %d 月的第 %d 天 是星期 %d" % (year, month, day, dOfWeek+1))

day, ndays = calendar.monthrange(year, month)
print("%d 年 %d 月 第一天是星期 %d,  总共有 %d 天" % (year, month, day+1, ndays))

if calendar.isleap(year):
    print("%s 是闰年" % year)
else:
    print("%s 不是闰年" % year)
