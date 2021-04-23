from numba import jit
import numpy as np
import numba
import random

import time

print("="*27+"monte_carlo_pi"+"="*27)


@jit
def monte_carlo_pi(nsamples):
    acc = 0
    for i in range(nsamples):
        x = random.random()
        y = random.random()
        if (x ** 2 + y ** 2) < 1.0:
            acc += 1
    return 4.0 * acc / nsamples


def monte_carlo_pi2(nsamples):
    acc = 0
    for i in range(nsamples):
        x = random.random()
        y = random.random()
        if (x ** 2 + y ** 2) < 1.0:
            acc += 1
    return 4.0 * acc / nsamples


# 没什么作用，预热
monte_carlo_pi(1)

s = time.time()
print(s)
monte_carlo_pi(1000)
e1 = time.time()
print(e1)
monte_carlo_pi2(1000)
e2 = time.time()
print(e2)
print("ratio:{}".format((e2-e1)/(e1-s)))

print("="*27+"abc_model_numba"+"="*27)


@jit
def abc_model_numba(a, b, c, rain):
    outflow = np.zeros((rain.size), dtype=np.float64)
    state_in = 0
    state_out = 0
    for i in range(rain.size):
        state_out = (1 - c) * state_in + a * rain[i]
        outflow[i] = (1 - a - b) * rain[i] + c * state_out
        state_in = state_out
    return outflow


def abc_model_numba2(a, b, c, rain):
    outflow = np.zeros((rain.size), dtype=np.float64)
    state_in = 0
    state_out = 0
    for i in range(rain.size):
        state_out = (1 - c) * state_in + a * rain[i]
        outflow[i] = (1 - a - b) * rain[i] + c * state_out
        state_in = state_out
    return outflow


rain = np.arange(500)

# 没什么作用，预热
abc_model_numba(0.2, 0.6, 0.1, rain)

s = time.time()
print(s)
abc_model_numba(0.2, 0.6, 0.1, rain)
e1 = time.time()
print(e1)
abc_model_numba2(0.2, 0.6, 0.1, rain)
e2 = time.time()
print(e2)
print("ratio:{}".format((e2-e1)/(e1-s)))
