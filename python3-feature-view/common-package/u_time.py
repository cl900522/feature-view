#!python
#!/usr/bin/pytyon3

import time

t = time.time()
print(t)

# 时间秒转换为struct_time
ts = time.localtime(t)
print(ts)

# struct_time转换时间秒
t = time.mktime(ts)
print(t)

# 格式化日期
ts = time.localtime(t-3600)
print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
print(time.strftime("%Y-%m-%d %H:%M:%S", ts))

# 解析日期
timeStr = time.strftime("%Y-%m-%d %H:%M:%S", ts)
ts = time.strptime(timeStr, "%Y-%m-%d %H:%M:%S")
print(ts)

time.sleep(3)

# 返回计时器的精准时间（系统的运行时间），包含整个系统的睡眠时间。
print(time.perf_counter())
# 返回当前进程执行 CPU 的时间总和，不包含睡眠时间。
print(time.process_time())

# 时区
print(time.timezone/3600)
print(time.tzname)


'''

%y 两位数的年份表示（00-99）
%Y 四位数的年份表示（000-9999）
%m 月份（01-12）
%d 月内中的一天（0-31）
%H 24小时制小时数（0-23）
%I 12小时制小时数（01-12）
%M 分钟数（00=59）
%S 秒（00-59）
%a 本地简化星期名称
%A 本地完整星期名称
%b 本地简化的月份名称
%B 本地完整的月份名称
%c 本地相应的日期表示和时间表示
%j 年内的一天（001-366）
%p 本地A.M.或P.M.的等价符
%U 一年中的星期数（00-53）星期天为星期的开始
%w 星期（0-6），星期天为星期的开始
%W 一年中的星期数（00-53）星期一为星期的开始
%x 本地相应的日期表示
%X 本地相应的时间表示
%Z 当前时区的名称
%% %号本身

'''
