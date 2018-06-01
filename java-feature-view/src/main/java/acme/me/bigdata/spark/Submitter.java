package acme.me.bigdata.spark;

import org.apache.spark.deploy.SparkSubmit;

public class Submitter {
    public static void main(String[] args) {
        String[] args1 = new String[] { 
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "test java submit job to spark",
                "--class", "com.amt.acme.WordCount",
                "file:/web/soft/spark-demo-1.0.jar",
                "hdfs://192.168.2.208:9000/Hadoop/Input/wordcount.txt" };
        SparkSubmit.main(args1);
    }
}
