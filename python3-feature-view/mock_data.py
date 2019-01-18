#!python

import os, sys, random, io

skuSize = 10000

poolSize = 500
skuInPoolSize = 100

userSize = 5000
userOwnPoolSize = 50

def getRandomSkus(size, totalRange):
    ar = set()
    for i in range(size):
        ar.add(random.randrange(1, totalRange));
    return ar


# gen skus
skuDataFilePath = "./skuData.csv"
skuDataFile = open(skuDataFilePath, "w+")

skuDataFile.writelines("\"skuId:ID\",\"name\",\":LABEL\"\n")
for i in range(skuSize):
    fm = "\"{0}\",\"商品-{1}\",\"Sku\"\n"
    skuDataFile.writelines(fm.format(i,i))

skuDataFile.close()

# gen pool

poolDataFilePath = "./skuPool.csv"
poolDataFile = open(poolDataFilePath, "w+")

poolDataFile.writelines("\"id:ID\",\"name\",\"type\",\":LABEL\"\n")
for i in range(poolSize):
    fm = "\"{0}\",\"池子-{1}\",{2},\"SkuPool\"\n"
    poolDataFile.writelines(fm.format(i, i, i%2))

poolDataFile.close()

# gen pool to Sku
poolRelationPath = "./skuPoolRelation.csv"
poolRelationFile = open(poolRelationPath, "w+")

poolRelationFile.writelines("\":START_ID(SkuPool)\",\":END_ID(Sku)\",\":TYPE\"\n")
for i in range(poolSize):
    for v in getRandomSkus(skuInPoolSize, skuSize):
        fm = "\"{0}\",\"{1}\",\"POOL_CONTAINS\"\n"
        poolRelationFile.writelines(fm.format(i, v, i%2))

poolRelationFile.close()


# gen user

userDataFilePath = "./user.csv"
userDataFile = open(userDataFilePath, "w+")

userDataFile.writelines("\"id:ID\",\"pin\",\"type\",\":LABEL\"\n")
for i in range(userSize):
    fm = "\"{0}\",\"用户-{1}\",{2},\"User\"\n"
    userDataFile.writelines(fm.format(i, i, i%2))

userDataFile.close()

# gen users to pool
userRelationPath = "./userPoolRelation.csv"
userRelationFile = open(userRelationPath, "w+")

userRelationFile.writelines("\":START_ID(User)\",\":END_ID(SkuPool)\",\":TYPE\"\n")
for i in range(poolSize):
    for v in getRandomSkus(userOwnPoolSize, poolSize):
        fm = "\"{0}\",\"{1}\",\"USER_OWN\"\n"
        userRelationFile.writelines(fm.format(i, v, i%2))

userRelationFile.close()

sys.stdout=io.TextIOWrapper(sys.stdout.buffer,encoding='utf8')

print("数据生成结束,使用以下命令导入neo4j:")
command = "neo4j-import --into ./graph.db --nodes {0} --nodes {1} --nodes {2}  --relationships {3} --relationships {4}"
print(command.format(userDataFilePath,skuDataFilePath,poolDataFilePath,poolRelationPath,userRelationPath))