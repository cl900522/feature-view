#!python

import numpy as np
from sklearn.datasets import load_iris
from sklearn import tree

iris_d = load_iris()

print(iris_d.feature_names)
print(iris_d.target_names)
print(iris_d.data[0])
print(iris_d.target[0])

for i in range(len(iris_d.target)):
    print("Example %d: label %s, features %s" %(i, iris_d.target[i], iris_d.data[i]))


#testing data
test_ids = [0, 50, 100]

train_target = np.delete(iris_d.target, test_ids)
train_data = np.delete(iris_d.data, test_ids, axis=0)

print("="*4+"Train"+"="*4)
print("Data:",len(train_data))
print(train_data)

print("Targe:",len(train_data))
print(train_target)

clf = tree.DecisionTreeClassifier()
clf = clf.fit(train_data, train_target)

print("="*4+"Train Finish"+"="*4)


test_target = iris_d.target[test_ids]
test_data = iris_d.data[test_ids]

print("="*4+"Tes Data"+"="*4)
print(test_data)

print("="*4+"Test Target"+"="*4)
print(clf.predict(test_data))

print("="*4+"Assert Target"+"="*4)
print(test_target)

