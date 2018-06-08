package com.amt.acme.local;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class LocalRun {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("My App");
        JavaSparkContext sc = new JavaSparkContext(conf);
        
    }
}
