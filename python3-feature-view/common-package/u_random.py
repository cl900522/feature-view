import random

a = random.choice(range(100))
print("choice:", a)

for i in range(1, 100, 10):
    print(i, end=',')

print("\nrandrange(1,100,2)=", random.randrange(1, 100, 2))

print("random()=", random.random())


r = [1,2,333,1521,20303,39122]
random.shuffle(r)
print("shuffle(r)=", r)

print("uniform(100,200)=", random.uniform(100, 200))
