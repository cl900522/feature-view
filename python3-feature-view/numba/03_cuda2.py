
'''
Matrix multiplication sample, some numba and CUDA testing code
'''
import math
import time
import numpy as np
from numba import cuda, jit, float64

TPB =  32 # thread per block

@cuda.jit
def mat_mul_naive_kernal(A, B, C):
    '''matrix multiplication on gpu, naive method using global device memory
    '''
    x = cuda.blockIdx.x
    y = cuda.blockIdx.y
    tx = cuda.threadIdx.x
    ty = cuda.threadIdx.y

    i, j = cuda.grid(2)
    if i < C.shape[0] and j < C.shape[1]:
            C[i, j] =y

def host_naive(A, B, C):
    '''host code for calling naive kernal
    '''
    d_A = cuda.to_device(A)  # d_ --> device
    d_B = cuda.to_device(B)
    d_C = cuda.device_array(C.shape, np.float64)

    threadsperblock = (TPB, TPB)

    mat_mul_naive_kernal[(4,4), threadsperblock](d_A, d_B, d_C)

    return d_C.copy_to_host()

def main():
    '''main
    '''
    A = np.full((TPB*4, TPB*4), 0.5, dtype=np.float64)
    B = np.full((TPB*4, TPB*4), 2, dtype=np.float64)
    C = np.full((TPB*4, TPB*4), 0, dtype=np.float64)

    ans = host_naive(A, B, C)
    start = time.time()
    ans = host_naive(A, B, C)
    print('gpu mat mul global:', time.time()-start)
    print(ans)

if __name__ == '__main__':
    main()
