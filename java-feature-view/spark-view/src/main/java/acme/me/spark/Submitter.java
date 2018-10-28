package acme.me.spark;

import org.apache.spark.deploy.SparkSubmit;
import org.junit.Test;

public class Submitter {
    private static final String jarFilePath = "hdfs:/Jars/spark-demo-1.0.jar";
    @Test
    public void wordCount() {
        String[] args1 = new String[] {
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "spark-demo-1.1",
                "--class", "com.amt.acme.rdd.WordCount",
                jarFilePath,
                "hdfs://192.168.2.208:9000/Hadoop/Input/acs.log",
                "hdfs://192.168.2.208:9000/Hadoop/Output/spark-demo-1.1"};
        SparkSubmit.main(args1);
    }

    @Test
    public void hdfsStream() {
        String[] args1 = new String[] {
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "spark-demo-1.1",
                "--class", "com.amt.acme.stream.DFSFileStreamTest",
                jarFilePath,
                "hdfs://192.168.2.208:9000/Hadoop/Input/Story/",
                "hdfs://192.168.2.208:9000/Hadoop/Output/Story/"};
        SparkSubmit.main(args1);
    }

    @Test
    public void sparkMongo() {
        String[] args1 = new String[] {
                "--master", "spark://192.168.2.208:6066",
                "--deploy-mode", "cluster",
                "--name", "spark-demo-1.1",
                "--class", "com.amt.acme.mongo.MongoSparkTest",
                jarFilePath};
        SparkSubmit.main(args1);
    }
}
