#!/usr/bin/python3

import numpy as np 
import numpy
 
print("="*27+"乘法"+"="*27)

a = numpy.linspace(1,12,4).reshape(2,2)
print(a)
b = np.linspace(0,9,4).reshape(2,2)
print(b)

print("a*b=")
print(a*b)

print("a@b=")
print(a@b)

print("a.dot(b)=")
print(a.dot(b))

print("="*27+"函数"+"="*27)
import numpy as  np

a = np.linspace(0,12,4).reshape(2,2)**2
print(a)

print("exp(a)")
print(np.exp(a))

print("sqrt(a)")
print(np.sqrt(a))

b = np.arange(4).reshape(2,2)
print(b)

print(np.add(a, b))

print("="*27+"加法"+"="*27)
"""
+=和*=不会生成一个新的向量数组，而是将第一个操作数组的值修改掉
"""

a = np.ones((2,3), dtype=int)
b = np.random.random((2,3))
a *= 3
print(a)
b += a
print(b)

print("="*27+"对比"+"="*27)
import numpy as np
a = np.arange(12).reshape(3,4)
print(a)

print("sum:{}".format(a.sum()))
print("min:{}".format(a.min()))
print("max:{}".format(a.max()))

print(a.sum(axis=0) )
print(a.min(axis=1))
print(a.cumsum(axis=1))

print("="*27+"统计"+"="*27)

""" 
 numpy.amin() 用于计算数组中的元素沿指定轴的最小值。
numpy.amax() 用于计算数组中的元素沿指定轴的最大值。
""" 

a = np.array([[3,7,5],[8,4,3],[2,4,9]])  
print ('我们的数组是：')
print (a)
print ('\n')
print ('调用 amin() 函数：')
print (np.amin(a,1))
print ('\n')
print ('再次调用 amin() 函数：')
print (np.amin(a,0))
print ('\n')
print ('调用 amax() 函数：')
print (np.amax(a))
print ('\n')
print ('再次调用 amax() 函数：')
print (np.amax(a, axis =  0))

"""
numpy.median() 函数用于计算数组 a 中元素的中位数（中值）
"""

a = np.array([[30,65,70],[80,95,10],[50,90,60]])  
print ('我们的数组是：')
print (a)
print ('\n')
print ('调用 median() 函数：')
print (np.median(a))
print ('\n')
print ('沿轴 0 调用 median() 函数：')
print (np.median(a, axis =  0))
print ('\n')
print ('沿轴 1 调用 median() 函数：')
print (np.median(a, axis =  1))


print("="*27+"截取"+"="*27)
import numpy as np

def f(x,y):
    return x*10+y

b = np.fromfunction(f,(5,4),dtype=int)

#行2,列3
b[2,3]

#0-5行（实际0-4行数据，前闭后开），1列
b[0:5, 1]

#所有行，1列
b[:, 1]

#0-5行，1列，高纬度的b[...,1] 意思为 b[:,:,:,:,1]
b[..., 1]

#1-3行，所有列
b[1:3, : ]

print("="*27+"操作"+"="*27)

import numpy as np

a = np.floor(10*np.random.random((3,4)))

a.shape

a.ravel()  # returns the array, 拉平

a.T  # returns the array, 颠倒

print(a.reshape(2,6))
print(a)

print(a.resize(2,6))
print(a)

print(a.reshape(12,-1)) # -1表示自动计算列
print(a.reshape(3,-1)) # -1表示自动计算列