package com.amt.acme.mongo;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;

public class MongoSparkTest {
    public static void main(String[] args) throws Exception {
        /* Create the SparkSession.
         * If config arguments are passed from the command line using --conf,
         * parse args for the values to set.
         */
        SparkSession spark = SparkSession.builder()
          .master("local")
          .appName("MongoSparkConnectorIntro")
          .config("spark.mongodb.input.uri", "mongodb://192.168.2.208:27017/tj.DeviceOnlineSummary201806")
          .config("spark.mongodb.output.uri", "mongodb://192.168.2.208:27017/tj.DeviceOnlineSummaryBck")
          .getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        Map<String, String> readOverrides = new HashMap<String, String>();
        readOverrides.put("collection", "DeviceOnlineSummary201708");
        readOverrides.put("readPreference.name", "primaryPreferred");

        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        JavaMongoRDD<Document> rdd1 = MongoSpark.load(jsc, readConfig);

        // Create a custom ReadConfig
        readOverrides = new HashMap<String, String>();
        readOverrides.put("collection", "DeviceOnlineSummary201806");
        readOverrides.put("readPreference.name", "primaryPreferred");
        readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        JavaMongoRDD<Document> rdd2 = MongoSpark.load(jsc, readConfig);
        JavaRDD<Document> rdd = rdd1.union(rdd2);

        // Create a custom ReadConfig
        readOverrides = new HashMap<String, String>();
        readOverrides.put("uri", "mongodb://192.168.2.202:9999");
        readOverrides.put("database", "tj");
        readOverrides.put("collection", "DeviceOnlineSummary201806");
        readOverrides.put("readPreference.name", "primaryPreferred");
        readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        JavaMongoRDD<Document> rdd3 = MongoSpark.load(jsc, readConfig);
        rdd = rdd.union(rdd3);

        Map<String, String> writeOverrides = new HashMap<String, String>();
        writeOverrides.put("collection", "DeviceOnlineSummaryBck");
        writeOverrides.put("writeConcern.w", "majority");
        WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);
        MongoSpark.save(rdd, writeConfig);
        
        jsc.close();
    }
}
