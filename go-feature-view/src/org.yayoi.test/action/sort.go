package main

import "fmt"

func main() {
	arr := makeNewArr(10)
	bubbleSort(arr)
	fmt.Println(arr)

	arr = makeNewArr(10)
	selectionSort(arr)
	fmt.Println(arr)

	arr = makeNewArr(10)
	insertSort(arr)
	fmt.Println(arr)
}

func makeNewArr(size int) []int {
	arr := []int{2, 5, 456, 4, 2, 34, 68, 8, 43, 38, 39}
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
		}
	}

	return arr
}
