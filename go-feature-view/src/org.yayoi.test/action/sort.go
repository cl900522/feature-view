package main

import (
	"container/list"
	"fmt"
	"math"
)

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

	arr = makeNewArr(10)
	heapSort(arr)
	fmt.Println("heapSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeArrOfRange(10, 1, 100)
	countSort(arr, 2, 100)
	fmt.Println("countSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeArrOfRange(10, 1, 100)
	bucketSort(arr, 2)
	fmt.Println("bucketSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeArrOfRange(10, 1, 100)
	radixSort(arr, 2)
	fmt.Println("radixSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))

	arr = makeNewArr(10)
	radixSort(arr, 6)
	fmt.Println("radixSort: ", arr)
	fmt.Println("IsSorted:", isSorted(arr))
}

func makeNewArr(size int) []int {
	arr := []int{723345, 49823, 223, 872, 55, 3834, 1124, 8734, 34, 23}
	return arr
}

func makeArrOfRange(size int, startVal, endVal int) []int {
	arr := []int{23, 74, 44, 12, 57, 3, 57, 73, 29, 99}
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

/**
堆排序
*/
var size int = 0

func heapSort(arr []int) []int {
	buildMaxHeap(arr)

	for i := len(arr) - 1; i > 0; i-- {
		swap(arr, 0, i)
		size--
		heapify(arr, 0)
	}
	return arr
}

func buildMaxHeap(arr []int) { // 建立大顶堆
	size = len(arr)

	t := int(math.Floor(float64(size / 2)))
	for i := t; i >= 0; i-- {
		heapify(arr, i)
	}
}

func heapify(arr []int, i int) { // 堆调整
	left := 2*i + 1
	right := 2*i + 2
	largest := i

	if left < size && arr[left] > arr[largest] {
		largest = left
	}

	if right < size && arr[right] > arr[largest] {
		largest = right
	}

	if largest != i {
		swap(arr, i, largest)
		heapify(arr, largest)
	}
}

/**
计数排序
*/
func countSort(arr []int, startVal, endVal int) {
	tempArr := make([]int, endVal-startVal+1)
	for i := 0; i < len(arr); i++ {
		if arr[i] < startVal || arr[i] > endVal {
			continue
		}

		tempArr[arr[i]-startVal]++
	}
	m := 0
	for j := 0; j < len(tempArr); j++ {
		for {
			if tempArr[j] <= 0 {
				break
			}
			tempArr[j]--
			arr[m] = startVal + j
			m++
		}
	}
}

const DEFAULT_BUCKET_SIZE int = 5 // 设置桶的默认数量为5
/**
桶排序
*/
func bucketSort(arr []int, bucketSize int) []int {
	if len(arr) == 0 {
		return arr
	}

	minValue := arr[0]
	maxValue := arr[0]
	for i := 1; i < len(arr); i++ {
		if arr[i] < minValue {
			minValue = arr[i] // 输入数据的最小值
		} else if arr[i] > maxValue {
			maxValue = arr[i] // 输入数据的最大值
		}
	}

	// 桶的初始化
	if bucketSize <= 1 {
		bucketSize = DEFAULT_BUCKET_SIZE
	}
	buckets := make([]*list.List, bucketSize)
	for i := 0; i < len(buckets); i++ {
		buckets[i] = list.New()
	}

	bucketCount := (int)(math.Floor((float64)((maxValue-minValue)/bucketSize)) + 1)

	// 利用映射函数将数据分配到各个桶中
	for i := 0; i < len(arr); i++ {
		inx := (int)(math.Floor((float64)((arr[i] - minValue) / bucketCount)))
		buckets[inx].PushBack(arr[i])
	}

	outInx := 0
	for i := 0; i < len(buckets); i++ {
		if buckets[i] == nil {
			continue
		}

		tempArr := make([]int, buckets[i].Len())
		idx := 0
		//(buckets[i]) // 对每个桶进行排序，这里使用了插入排序
		for el := buckets[i].Front(); el != nil; el = el.Next() {
			tempArr[idx] = el.Value.(int)
			idx++
		}
		selectionSort(tempArr)

		for j := 0; j < len(tempArr); j++ {
			arr[outInx] = tempArr[j]
			outInx++
		}
	}

	return arr
}

/**
基数排序
*/
func radixSort(arr []int, digest int) {
	mod := 1
	for i := 0; i < digest; i++ {
		buckets := make([]*list.List, 10)

		for j := 0; j < len(arr); j++ {
			modVal := arr[j] / mod
			bucket := modVal % 10

			if buckets[bucket] == nil {
				buckets[bucket] = list.New()
			}
			buckets[bucket].PushBack(arr[j])
		}

		writeIdx := 0

		for j := 0; j < len(buckets); j++ {
			if buckets[j] == nil {
				continue
			}
			for t := buckets[j].Front(); t != nil; t = t.Next() {
				arr[writeIdx] = t.Value.(int)
				writeIdx++
			}
		}

		mod *= 10
	}
}
