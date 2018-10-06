package com.amt.acme.mongo;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;

public class MongoSparkSqlTest {
    public static void main(final String[] args) throws InterruptedException {

        SparkSession spark = SparkSession.builder()
            .master("local")
            .appName("MongoSparkConnectorIntro")
            .config("spark.mongodb.input.uri", "mongodb://192.168.2.208:27017/tj.DeviceOnlineRecordCollection201801")
            .config("spark.mongodb.output.uri", "mongodb://192.168.2.208:27017/tj.DeviceOnlineSummary")
            .getOrCreate();
        SparkSession sparkSession = spark;
        // Create a JavaSparkContext using the SparkSession's SparkContext
        // object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Load data and infer schema, disregard toDF() name as it returns
        // Dataset
        Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
        implicitDS.printSchema();
        implicitDS.show();

        // Load data with explicit schema
        Dataset<Character> explicitDS = MongoSpark.load(jsc).toDS(Character.class);
        explicitDS.printSchema();
        explicitDS.show();

        // Create the temp view and execute the query
        explicitDS.createOrReplaceTempView("records");
        Dataset<Row> centenarians = spark.sql("SELECT brand,type,count(serialNumber) FROM records group by brand,type");
        centenarians.show();

        // Write the data to the "hundredClub" collection
        MongoSpark.write(centenarians).option("DeviceOnlineRecordCollection201804", "hundredClub").mode("overwrite").save();

        /** Map<String, String> readOverrides = new HashMap<String, String>();
        readOverrides.put("collection", "DeviceOnlineSummary201708");
        readOverrides.put("readPreference.name", "primaryPreferred");

        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);
        JavaMongoRDD<Document> rdd1 = MongoSpark.load(jsc, readConfig);**/
        
        // Load the data from the "hundredClub" collection
        MongoSpark.load(sparkSession, ReadConfig.create(sparkSession).withOption("collection", "hundredClub"), Character.class).show();

        jsc.close();

    }
}
