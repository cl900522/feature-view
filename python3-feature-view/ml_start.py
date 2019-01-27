#!python

from sklearn import tree

collors = {
    "Green": 1,
    "Orange": 2,
    "Red": 3
}

touchFeel = {
    "Smooth": 1,
    "Barbed": 2
}

fruits = {
    "WaterMelon" : 1,
    "Cucumber": 2,
    "Juice": 3,
    "Peach": 4
}


inDatas = [
    [collors["Green"], 2000, touchFeel["Smooth"]],
    [collors["Green"], 2100, touchFeel["Smooth"]],
    [collors["Green"], 2120, touchFeel["Smooth"]],
    [collors["Green"], 500, touchFeel["Barbed"]],
    [collors["Green"], 520, touchFeel["Barbed"]],

    [collors["Orange"], 100, touchFeel["Smooth"]],
    [collors["Orange"], 90, touchFeel["Smooth"]],
    [collors["Orange"], 104, touchFeel["Smooth"]],
    [collors["Red"], 115, touchFeel["Barbed"]],
    [collors["Red"], 103, touchFeel["Barbed"]],

    [collors["Red"], 141, touchFeel["Barbed"]],
    [collors["Orange"], 120, touchFeel["Smooth"]]
]

labels = [
    fruits["WaterMelon"], fruits["WaterMelon"], fruits["WaterMelon"], fruits["Cucumber"], fruits["Cucumber"],
    fruits["Juice"], fruits["Juice"], fruits["Juice"], fruits["Peach"], fruits["Peach"],
    fruits["Peach"], fruits["Juice"]
]

clf = tree.DecisionTreeClassifier()
clf = clf.fit(inDatas, labels)

result = clf.predict([
    [collors["Green"], 3000, touchFeel["Smooth"]],
    [collors["Green"], 460, touchFeel["Barbed"]],
    [collors["Green"], 350, touchFeel["Barbed"]],
    [collors["Red"], 100, touchFeel["Barbed"]],
    [collors["Orange"], 90, touchFeel["Smooth"]],
])

def gotFruit(code):
    for k in fruits:
        if fruits[k] == code:
            return k

print(result)
for r in result:
    print(gotFruit(r))


