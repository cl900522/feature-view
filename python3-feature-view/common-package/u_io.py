#!python
#!/usr/bin/python3

import os

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

print(os.sep)

print(os.name)

print("uid->{},euid->{}".format(os.getuid(), os.geteuid()))
