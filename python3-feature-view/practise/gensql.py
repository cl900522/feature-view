import os

for row in open("C:/Users/jj/Desktop/storeType错误的单据.csv"):
    vals = row.split(",")
    storeId = vals[0]
    sheetId = vals[1]
    sheetId = sheetId.replace("\n", "")
    print("update async_ivc_msg_record_0{} set processed = 0, created=now() where store_id={} and req_id = '1-{}_1' and yn=1;".format(
        storeId[-1:], storeId, sheetId))
