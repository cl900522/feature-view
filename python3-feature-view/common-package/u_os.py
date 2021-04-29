#!python
#!/usr/bin/python3

import os

tempFilePath = "./temp.py"
tempFile = open(tempFilePath, "w+")

os.dup2(1, 1000)

os.dup2(tempFile.fileno(), 1)
print("All folders in /->")

# print all folders in /
path = "/"
dirs = os.listdir(path)
for dir in dirs:
    print(dir)

os.dup2(1000, 1)
os.close(tempFile.fileno())
# os.remove(tempFilePath)
