package com.amt.acme.stream;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class DFSFileStreamTest {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("DFSFileStream");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(60));

        // 创建监听文件流
        JavaDStream<String> lines = jssc.textFileStream(args[0]);

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String x) {
                System.out.println(Arrays.asList(x.split(" ")).get(0));
                return Arrays.asList(x.split(" ")).iterator();
            }
        });

        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) {
                return new Tuple2<String, Integer>(s, 1);
            }
        });

        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        wordCounts.print();

        wordCounts.dstream().saveAsTextFiles(args[1], "spark");

        jssc.start();
        jssc.awaitTermination();
    }
}
