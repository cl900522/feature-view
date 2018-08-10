package basic

type G struct {
	Id string
}

func GetPointer(o G) *G {
	o.Id = "2"
	return &o
}

func ChangeArr(o [3]int) [3]int {
	for i := len(o) - 1; i >= 0; i-- {
		o[i] = 100
	}
	return o
}

func ChangeSplice(o []int) []int {
	for i := len(o) - 1; i >= 0; i-- {
		o[i] = 100
	}
	return o
}
