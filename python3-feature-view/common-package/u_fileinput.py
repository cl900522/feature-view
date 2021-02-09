import fileinput

i = 0
for line in fileinput.input():
    if i == 100:
        fileinput.close()  # 关闭文件队列

    fileinput.filename()       # 返回当前正在读取的文件的名称
    fileinput.fileno()  # 返回当前文件的整数“文件描述符”

    fileinput.lineno()  # 返回刚刚读取的行的累计行号

    fileinput.filelineno()  # 返回当前文件中的行号

    fileinput.isfirstline()  # 判断读取的行是不是该文件的第一行，是就返回true，否则返回false

    fileinput.isstdin()  # 判断读取的是否是sys.stdin的最后一行，是就返回true，否则返回false

    fileinput.nextfile()  # 关闭当前文件，以便下一次迭代将读取下一个文件的第一行（如果有的话）

    i += 1
    print(line, end="")
