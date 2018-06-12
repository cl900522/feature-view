package com.amt.acme.local;

import java.util.Arrays;

import org.apache.hadoop.yarn.client.api.impl.YarnClientImpl;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class LocalRun {

	public static void main(String[] args) {
//		String string = "spark://192.168.2.208:7077";
		String string = "yarn-client";
//		String string = "local";
		SparkConf conf = new SparkConf().setMaster(string).setAppName("My App");

		String [] jars = {"/02_git/feature-view/java-feature-view/spark-view/target/spark-demo-1.0.jar"};
		conf.setJars(jars);
		conf.set("spark.yarn.stagingDir", "/temp/stag");
		conf.set("spark.yarn.archive", "hdfs://192.168.2.208:9000/spark-jars.zip");
        conf.set("spark.executor.instances", "30");
        conf.set("spark.executor.cores", "3");
        conf.set("spark.executor.memory", "5G");
        conf.set("spark.driver.memory", "3G");
        conf.set("spark.driver.maxResultSize", "10G");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> parallelize = sc.parallelize(Arrays.asList("a.", "a.c", "b,c"));
		parallelize = parallelize.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String line) {
				return line.startsWith("a");
			}
		});

        JavaPairRDD<String, Integer> pairRdd = parallelize.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        // 对每个词语进行计数
        JavaPairRDD<String, Integer> countRdd = pairRdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        JavaRDD<String> results = countRdd.keys();
		System.out.println(results.first());
        results.saveAsTextFile("hdfs://192.168.2.208:9000/Hadoop/Output/a");

		sc.close();
	}
}
