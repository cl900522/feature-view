package com.amt.acme;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class WordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCountTest");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile(args[0]);

        /* 过滤提取INFO行 */
        JavaRDD<String> filteredList = lines.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String line) {
                return line.contains("初始化开机") && (!line.contains("非初始化开机"));
            }
        });

        // 先切分为单词，扁平化处理
        JavaRDD<String> flatMapRdd = filteredList.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String str) {
                int indexOf = str.indexOf("初始化开机");
                return Arrays.asList(str.substring(indexOf - 17, indexOf)).iterator();
            }
        });

        // 再转化为键值对
        JavaPairRDD<String, Integer> pairRdd = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
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
        results.saveAsTextFile(args[1]);
        sc.close();
    }
}
