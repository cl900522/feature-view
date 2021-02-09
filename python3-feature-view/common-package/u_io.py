#!python
#!/usr/bin/python3

import os
import sys

filePath = "./temp.txt"

file = open(filePath, "w+")
file.writelines("Hello\nGood\nThere")
print(file.tell())
file.truncate(10)
file.close()

for line in open(filePath, 'r', encoding='UTF-8'):
    print(line, end='')

os.remove(filePath)

print("\n\n")

os.path.exists("")

os.sep

os.getuid()

os.geteuid()

print("uid->{},euid->{}".format(os.getuid(), os.geteuid()))

print(os.name)

print(sys.version_info)
print(sys.version)
