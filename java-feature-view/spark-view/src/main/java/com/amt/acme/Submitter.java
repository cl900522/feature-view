package com.amt.acme;

import org.apache.spark.deploy.SparkSubmit;

public class Submitter {
    public static void main(String[] args) {
        String[] args1 = new String[] { 
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "spark-demo-1.1",
                "--class", "com.amt.acme.WordCount",
                "hdfs:/Jars/spark-demo-1.1.jar",
                "hdfs://192.168.2.208:9000/Hadoop/Input/acs.log",
                "hdfs://192.168.2.208:9000/Hadoop/Output/spark-demo-1.1"};
        SparkSubmit.main(args1);
    }
}
