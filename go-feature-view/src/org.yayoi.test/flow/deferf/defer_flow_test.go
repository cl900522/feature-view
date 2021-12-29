package deferf

import "testing"

/**
 * 函数中通过调用testing.T的Error, Errorf, FailNow, Fatal, FatalIf方法，说明测试不通过，调用Log方法用来记录测试的信息。
 * @type {[type]}
 */
func TestDefer0(t *testing.T) {
	Defer0()
	t.Error("Defer is error")
}

func TestDefer1(t *testing.T) {
	Defer1()
	t.Error("Defer is error")
}
