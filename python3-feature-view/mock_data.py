#!python

import os, sys, random, io
import codecs

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf8')

skuPrefix = "sku-"
skuSize = 1000000

poolPrefix="pool-"
poolSize = 100000
skuInPoolSize = 200

userPrefix = "user-"
userSize = 5000
userOwnPoolSize = 200

def getRandomSkus(size, totalRange):
    ar = set()
    for i in range(size):
        ar.add(random.randrange(1, totalRange));
    return ar

# gen skus
skuDataFilePath = "./skuData.csv"
skuDataFile = open(skuDataFilePath, "w+", encoding="utf-8")

skuDataFile.writelines("\"uId:ID\",\"skuId\",\"name\",\":LABEL\"\n")
for i in range(skuSize):
    fm = "\"sku-{0}\",{1},\"商品-{2}\",\"Sku\"\n"
    skuDataFile.writelines(fm.format(i,i,i))

skuDataFile.close()

# gen pool

poolDataFilePath = "./skuPool.csv"
poolDataFile = open(poolDataFilePath, "w+", encoding="utf-8")

poolDataFile.writelines("\"uId:ID\",\"id\",\"name\",\"type\",\":LABEL\"\n")
for i in range(poolSize):
    fm = "\"pool-{0}\",{1},\"池子-{2}\",{3},\"SkuPool\"\n"
    poolDataFile.writelines(fm.format(i, i, i, i%2))

poolDataFile.close()

# gen pool to Sku
poolRelationPath = "./skuPoolRelation.csv"
poolRelationFile = open(poolRelationPath, "w+", encoding="utf-8")

poolRelationFile.writelines("\":START_ID\",\":END_ID\",\":TYPE\"\n")
for i in range(poolSize):
    for v in getRandomSkus(skuInPoolSize, skuSize):
        fm = "\"pool-{0}\",\"sku-{1}\",\"POOL_CONTAINS\"\n"
        poolRelationFile.writelines(fm.format(i, v, i%2))

poolRelationFile.close()


# gen user

userDataFilePath = "./user.csv"
userDataFile = open(userDataFilePath, "w+", encoding="utf-8")

userDataFile.writelines("\"uId:ID\",\"pin\",\"type\",\":LABEL\"\n")
for i in range(userSize):
    fm = "\"user-{0}\",\"用户-{1}\",{2},\"User\"\n"
    userDataFile.writelines(fm.format(i, i, i%2))

userDataFile.close()

# gen users to pool
userRelationPath = "./userPoolRelation.csv"
userRelationFile = open(userRelationPath, "w+", encoding="utf-8")

userRelationFile.writelines("\":START_ID\",\":END_ID\",\":TYPE\"\n")
for i in range(userSize):
    for v in getRandomSkus(userOwnPoolSize, poolSize):
        fm = "\"user-{0}\",\"pool-{1}\",\"USER_OWN\"\n"
        userRelationFile.writelines(fm.format(i, v, i%2))

userRelationFile.close()

print("数据生成结束,使用以下命令导入neo4j:")
command = "neo4j-admin import --nodes {0} --nodes {1} --nodes {2}  --relationships {3} --relationships {4}"
print(command.format(userDataFilePath,skuDataFilePath,poolDataFilePath,poolRelationPath,userRelationPath))
