import math
import cmath

print("pi->", math.pi)
print("e->", math.e)

print("math.sin(30):", math.sin(30))
print("math.sin(60):", math.sin(60))

print("math.sqrt(9):", math.sqrt(9))
print("cmath.sqrt(-3):", cmath.sqrt(-3))
print("pow(3,2):", pow(3, 2))

print("math.log10(100):", math.log10(100))
print("math.log(100,math.e):", math.log(100, math.e))
print("cmath.log10(-100):", cmath.log10(-100))

print("abs(-1):", abs(-1))
print("fabs(-10):", math.fabs(-10))

print("math.ceil(4.1):", math.ceil(4.1))
print("math.floor(4.5):", math.floor(4.5))

print("round(2.142,2):", round(2.142, 2))
print("round(2.642,1):", round(2.642, 1))

x = [2, 12, 51, 23, 12612, 612, 3, 12, 2, 31]
print("max(", repr(x), ")", max(x))
print("min(", repr(x), ")", min(x))
