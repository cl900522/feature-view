package main

import (
	"fmt"
)

func main() {

	var arr []int = []int{723345, 49823, 223, 872, 55, 3834, 1124, 8734, 34, 23}
	mergeSort(arr, 0, len(arr)-1)
	fmt.Println(arr)
}

func mergeSort(arr []int, start int, end int) {
	len := end - start + 1
	if len == 1 {
		return
	} else if len == 2 {
		if arr[start] > arr[end] {
			temp := arr[start]
			arr[start] = arr[end]
			arr[end] = temp
		} else {
			return
		}
	} else {
		mid := len/2 + start - 1
		mergeSort(arr, start, mid)
		mergeSort(arr, mid+1, end)

		tarr := make([]int, len)
		i, j := 0, 0
		for n := 0; n < len; n++ {
			if i > mid-start {
				tarr[n] = arr[mid+1+j]
				j++
				continue
			}
			if j > end-mid-1 {
				tarr[n] = arr[start+i]
				i++
				continue
			}
			if arr[start+i] < arr[mid+1+j] {
				tarr[n] = arr[start+i]
				i++
			} else {
				tarr[n] = arr[mid+1+j]
				j++
			}
		}
		for n := 0; n < len; n++ {
			arr[start+n] = tarr[n]
		}
	}
	fmt.Println(arr)
}
