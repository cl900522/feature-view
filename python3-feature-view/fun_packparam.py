#!python
#!/usr/bin/python3

x,y,z = 1,2,3
print(x,y,z,sep='-',end='!')
print("end")

def getPerson() :
    return "Jon", 12, "Male"


name,age,sex = getPerson()
print(name,age,sex)

age = int(input("Input your age?"))
assert age<200, "Are you kidding me?"
if age < 18:
    print("You are too young")
elif age<30:
    print("You are at great age")
elif age<50:
    print("enjoy your life")
elif age<80:
    print("Geat!")
else:
    print("Joke")
    '''do nothing'''
    pass

d = {'x':12,'y':2245,'z':92,'a':252}
for key in d:
    print(key)

for key,value in d.items():
    print(key,value,sep='->')
