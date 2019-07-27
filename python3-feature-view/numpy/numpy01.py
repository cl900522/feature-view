#!/usr/bin/python

import numpy as np
import numpy

print("="*27+"basic"+"="*27)

a = np.arange( 2,31,2).reshape(3,5)
print(a)
print(a.dtype)
print(a.dtype.name)
print(type(a))
print(a.ndim)
print(a.shape)
print(a.size)
print(a.itemsize)
c = np.array( [ [1,2], [3,4] ], dtype=np.complex )
c = np.array( [ [1,2], [3,4] ], dtype=complex )
print(c)

"""
The function zeros creates an array full of zeros,
 the function ones creates an array full of ones, 
 and the function empty creates an array whose initial content is random and depends on the state of the memory.
  By default, the dtype of the created array is float64.
"""
np.ones( (2,3,4), dtype=np.int16 )


print("="*27+"arange"+"="*27)

a = numpy.arange(24)
print("dim:{}".format(a.ndim))            # a 现只有一个维度
print(a, end="\n\n")

# 现在调整其大小
b = a.reshape(2, 4, 3)  # b 现在拥有3个维度
print("dim:{}".format(b.ndim))
print(b)

print("="*27+"array"+"="*27)

a = np.array([1, 3, 4, 5, 6, 6, 7, 8, 8, 9, 9, 0])
print("dim:{}".format(a.ndim))
print(a, end="\n\n")

# 现在调整其大小
b = a.reshape(2, 6)
print("dim:{}".format(b.ndim))
print(b)

print("="*27+"empty"+"="*27)

x = np.empty([3, 2], dtype=int)
print("dim:{}".format(x.ndim))
print(x, end="\n\n")

'''
数组元素为随机值，因为它们未初始化。
'''

print("="*27+"linspace"+"="*27)
"""
linespace将区间拆分为固定数量的数组个数
"""
import numpy as np

x = np.linspace(0, 2, 9)
print(x)
print(x.size)
x = np.linspace(0, 2*np.pi, 100)
print(x)


print("="*27+"asarray"+"="*27)

a = np.asarray([1, 3, 4, 5, 6, 6, 7, 8, 8, 9, 9, 0])
print("dim:{}".format(a.ndim))
print(a, end="\n\n")

print("="*27+"zeros"+"="*27)

a = np.zeros([2, 2], dtype=float, order='C')
print("dim:{}".format(a.ndim))
print(a, end="\n\n")

print("="*27+"ones"+"="*27)

a = np.ones([4, 3], dtype=None, order='C')
print("dim:{}".format(a.ndim))
print(a, end="\n\n")

print("="*27+"random"+"="*27)

a = np.random.random((2,3))
print("dim:{}".format(a.ndim))
print(a, end="\n\n")

print("="*27+"nditer"+"="*27)

a = np.arange(6).reshape(2,3)
print ('原始数组是：')
print (a)
print ('\n')
print ('迭代输出元素：')
for x in np.nditer(a):
    print (x, end=", " )
print ('\n')

print("="*27+"nditer2"+"="*27)

a = np.arange(6).reshape(2,3)
for x in np.nditer(a.T):
    print (x, end=", " )
print ('\n')
 
for x in np.nditer(a.T.copy(order='C')):
    print (x, end=", " )
print ('\n')

print("="*27+"nditer3"+"="*27)

a = np.arange(6).reshape(2,3)
print("a",end="\n")
print(a)

print("a.T",end="\n")
b = a.T 
print (b) 
print ('\n') 
print ('以 C 风格顺序排序：') 
c = b.copy(order='C')  
print (c)
for x in np.nditer(c):  
    print (x, end=", " )
print  ('\n') 
print  ('以 F 风格顺序排序：')
c = b.copy(order='F')  
print (c)
for x in np.nditer(c):  
    print (x, end=", " )
