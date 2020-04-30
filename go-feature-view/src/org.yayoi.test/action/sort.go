package main

import "fmt"

func main() {
	arr := makeNewArr(10)
	fmt.Println("original: ", arr)

	arr = makeNewArr(10)
	bubbleSort(arr)
	fmt.Println("bubbleSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	selectionSort(arr)
	fmt.Println("selectionSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	insertSort(arr)
	fmt.Println("insertSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	insertSort2(arr)
	fmt.Println("insertSort2: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	stepInsertSort(arr, 1)
	fmt.Println("stepInsertSort step=1: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	shellSort(arr)
	fmt.Println("shellSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	mergeSort(arr, 0, len(arr)-1)
	fmt.Println("mergetSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	qucikSort(arr, 0, len(arr)-1)
	fmt.Println("qucikSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))
}

func makeNewArr(size int) []int {
	arr := []int{723345, 49823, 223, 872, 55, 3834, 1124, 8734, 34, 23}
	return arr
}

/**
冒泡排序
*/
func bubbleSort(arr []int) []int {
	var len = len(arr)
	for i := 0; i < len-1; i++ {
		for j := 0; j < len-1-i; j++ {
			if arr[j] > arr[j+1] { // 相邻元素两两对比
				temp := arr[j+1] // 元素交换
				arr[j+1] = arr[j]
				arr[j] = temp
			}
		}
	}
	return arr
}

/**
选择排序
*/
func selectionSort(arr []int) []int {
	var len = len(arr)
	var minIndex, temp int
	for i := 0; i < len-1; i++ {
		minIndex = i
		for j := i + 1; j < len; j++ {
			if arr[j] < arr[minIndex] { // 寻找最小的数
				minIndex = j // 将最小数的索引保存
			}
		}
		temp = arr[i]
		arr[i] = arr[minIndex]
		arr[minIndex] = temp
	}
	return arr
}

/**
插入排序
*/
func insertSort(arr []int) []int {
	var len = len(arr)

	for i := 1; i < len; i++ {
		current := arr[i]
		for j := i; j > 0; j-- {
			if current < arr[j-1] {
				arr[j] = arr[j-1]
			} else {
				arr[j] = current
				break
			}
			if j == 1 {
				arr[j-1] = current
			}
		}
	}

	return arr
}

/**
基于for-if 实现 while-do
*/
func insertSort2(arr []int) []int {
	var len = len(arr)

	for i := 1; i < len; i++ {
		current := arr[i]
		preIndex := i - 1
		for {
			if preIndex < 0 || arr[preIndex] <= current {
				break
			}
			arr[preIndex+1] = arr[preIndex]
			preIndex--
		}
		arr[preIndex+1] = current
		// fmt.Println("insertSort2 temp: ", arr, i)
	}

	return arr
}

func shellSort(arr []int) {
	for gap := len(arr)/2 - 1; gap > 0; gap = gap/2 - 1 {
		stepInsertSort(arr, gap)
	}

}

func stepInsertSort(arr []int, step int) {
	var len = len(arr)

	for i := step; i < len; i++ {
		current := arr[i]
		preIndex := i - step
		for {
			if preIndex < 0 || arr[preIndex] <= current {
				break
			}

			arr[preIndex+step] = arr[preIndex]
			preIndex -= step
		}
		arr[preIndex+step] = current
		// fmt.Println("stepInsertSort temp: ", arr, i, step)
	}
}

/**
归并排序
*/
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
	// fmt.Println(arr)
}

/**
快排
*/
func qucikSort(arr []int, ran ...int) {
	start, end := 0, len(arr)-1
	if len(ran) > 0 {
		start = ran[0]
		end = ran[1]
	}

	if start < end {
		partIdx := findPartIdx(arr, start, end)
		qucikSort(arr, start, partIdx-1)
		qucikSort(arr, partIdx+1, end)
	}
}

func findPartIdx(arr []int, start int, end int) int {
	startVal := arr[start]
	idx := start + 1
	for i := start + 1; i <= end; i++ {
		if arr[i] < startVal {
			swap(arr, i, idx)
			//fmt.Println("findPartIdx: ", arr)
			idx++
		}
	}
	swap(arr, idx-1, start)
	//fmt.Println("findPartIdx: ", arr)
	return idx - 1
}

func swap(arr []int, i, j int) {
	temp := arr[i]
	arr[i] = arr[j]
	arr[j] = temp
}

func isSorted(arr []int) bool {
	for i := 0; i < len(arr)-1; i++ {
		if arr[i] > arr[i+1] {
			return false
		}
	}
	return true
}
