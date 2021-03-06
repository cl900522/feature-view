#!/usr/bin/python

import unittest
from u_unittest_fun import *

'''
unittest 要求单元测试类必须继承 unittest.TestCase，该类中的测试方法需要满足如下要求：
测试方法应该没有返回值。
测试方法不应该有任何参数。
测试方法应以test 开头。
'''


class TestFun(unittest.TestCase):
    # 测试一元一次方程的求解
    def test_one_equation(self):
        # 断言该方程求解应该为-1.8
        self.assertEqual(one_equation(5, 9), -1.8)
        # 断言该方程求解应该为-2.5
        self.assertTrue(one_equation(4, 10) == -2.5, .00001)
        # 断言该方程求解应该为27/4
        self.assertTrue(one_equation(4, -27) == 27 / 4)
        # 断言当a == 0时的情况，断言引发ValueError
        with self.assertRaises(ValueError):
            one_equation(0, 9)
    # 测试一元二次方程的求解

    def test_two_equation(self):
        r1, r2 = two_equation(1, -3, 2)
        self.assertCountEqual((r1, r2), (1.0, 2.0), '求解出错')
        r1, r2 = two_equation(2, -7, 6)
        self.assertCountEqual((r1, r2), (1.5, 2.0), '求解出错')
        # 断言只有一个解的情形
        r = two_equation(1, -4, 4)
        self.assertEqual(r, 2.0, '求解出错')
        # 断言当a == 0时的情况，断言引发ValueError
        with self.assertRaises(ValueError):
            two_equation(0, 9, 3)
        # 断言引发ValueError
        with self.assertRaises(ValueError):
            two_equation(4, 2, 3)


if __name__ == '__main__':
    unittest.main()


'''
1. 使用python -m unitest unittestfile.py 指定测试文件执行

2. 使用 python -m unittest 命令运行测试用例时，如果没有指定测试用例，
该命令将自动查找并运行当前目录下的所有测试用例。因此，程序也可直接使用如下命令来运行所有测试用例：
'''

'''

======================================
以下是被测试的方法，可以放入到单独的文件中
======================================

'''


def one_equation(a, b):
    '''
    求一元一次方程a * x + b = 0的解
    参数a - 方程中变量的系数
    参数b - 方程中的常量
    返回 方程的解
    '''
    # 如果a = 0，则方程无法求解
    if a == 0:
        raise ValueError("参数错误")
    # 返回方程的解
    else:
        #        return -b / a  # ①
        return b / a


def two_equation(a, b, c):
    '''
    求一元二次方程a * x * x + b * x + c = 0的解
    参数a - 方程中变量二次幂的系数
    参数b - 方程中变量的系数
    参数c - 方程中的常量
    返回 方程的根
    '''
    # 如果a == 0，变成一元一次方程
    if a == 0:
        raise ValueError("参数错误")
    # 有理数范围内无解
    elif b * b - 4 * a * c < 0:
        raise ValueError("方程在有理数范围内无解")
    # 方程有唯一的解
    elif b * b - 4 * a * c == 0:
        # 使用数组返回方程的解
        return -b / (2 * a)
    # 方程有两个解
    else:
        r1 = (-b + (b * b - 4 * a * c) ** 0.5) / 2 / a
        r2 = (-b - (b * b - 4 * a * c) ** 0.5) / 2 / a
        # 方程的两个解
        return r1, r2
