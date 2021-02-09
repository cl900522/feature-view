
foo = [2, 18, 9, 22, 17, 24, 8, 12, 27]

print("filter out:", "-"*10)
f = filter(lambda x: x % 3 == 0, foo)
for o in f:
    print(o)


print("map out:", "-"*10)
m = map(lambda x: x * 2 + 10, foo)
for o in m:
    print(o)
