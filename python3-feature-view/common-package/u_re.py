# 正则表达式
import re

numRe = r"[0-9]{1,}"
text = "dagadf 169812839812asdfsa"


def printMatch(m):
    if m is not None:
        span = m.span()

        print("匹配字符:", m.group(0))
        # 匹配的字符串在原始字符传的开始结束
        print("start:{0}, end:{1}".format(span[0], span[1]))
        print("start:{0}, end:{1}".format(m.start(), m.end()))

        # 搜索的开始和结束位置
        print("pos:{0}, endpos:{1}".format(m.pos, m.endpos))
    else:
        print("未匹配")


print("\nsearch in:", text)
m = re.search(numRe, text)
printMatch(m)

print("\nmatch with:", text)
m = re.match(numRe, text)
printMatch(m)

print("\nmatch: with", "169812839812asdfsa")
m = re.match(numRe, "169812839812asdfsa")
printMatch(m)

text = "hello world\ngreat wall\nnice world"

# 找到所有返回
print("\nfindall :")
m = re.findall(r"\w{1}\sworld", text, flags=re.M)
for mi in m:
    print(mi)

# 返回匹配的迭代
print("\nfinditer ite:")
for m in re.finditer(r"\w{1}\sworld", text, flags=re.M):
    print(m.group())

# 返回替换后的
split = re.split(r"world", text)
print("\nsplit list:\n", split)

# 返回替换后的
newText = re.sub(r"world", "java", text)
print("\nsub newText:\n", newText)


m = re.match(r"\d{1,}", "1923789123")
print("贪婪匹配结果:", m.group())

m = re.match(r"\d{6,}?", "1923789123")
print("最小匹配结果:", m.group())
