package com.amt.acme.local;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.network.client.TransportClient;

import scala.Tuple2;

public class LocalRun {
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		String string = "yarn";
//		String string = "local";
		SparkConf conf = new SparkConf().setMaster("yarn-client").setAppName("My App");
		
//		conf.set("spark.yarn.dist.files", "/web/soft/hadoop-2.7.4/etc/hadoop/yarn-site.xml");
//		conf.set("spark.yarn.jar", "/web/soft/spark-2.3.0/yarn/spark-assembly-1.5.2-hadoop2.6.0.jar");
		conf.set("spark.yarn.stagingDir", "hdfs://192.168.100.200:9000/spark/stage");
		
		String [] jars = {"/home/chenmx/.m2/repository/com/amt/spark-demo/1.0/spark-demo-1.0.jar"};
		conf.setJars(jars);
		conf.set("spark.submit.deployMode", "client");
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
        results.saveAsTextFile("hdfs://192.168.100.200:9000/Hadoop/Output/g");
		
		sc.close();
	}
}
