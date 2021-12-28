#!python
#!/usr/bin/pytyon3

from datetime import datetime, date, time, timezone
import time as ctime

today = date.today()
print(today)

today == date.fromtimestamp(ctime.time())
print(today)

d = date(2005, 7, 14)
t = time(12, 30)
ts = datetime.combine(d, t)
print(ts)
print(type(ts))

ts = datetime(2005, 7, 14, 12, 30)
print(ts)
print(type(ts))


t = ts.timetuple()
for i in t:
    print(i)
