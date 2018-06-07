package com.amt.acme.stream;

import org.apache.spark.deploy.SparkSubmit;

public class Submitter {
    public static void main(String[] args) {
        String[] args1 = new String[] { 
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "spark-demo-1.1",
                "--class", "com.amt.acme.stream.DFSFileStreamTest",
                "hdfs:/Jars/spark-stream-demo-1.2.jar",
                "hdfs://192.168.2.208:9000/Hadoop/Input/Story/",
                "hdfs://192.168.2.208:9000/Hadoop/Output/Story/"};
        SparkSubmit.main(args1);
    }
}
