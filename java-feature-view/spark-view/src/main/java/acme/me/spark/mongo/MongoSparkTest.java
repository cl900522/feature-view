package acme.me.spark.mongo;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.mongodb.spark.MongoSpark;

public class MongoSparkTest {

    public static void main(final String[] args) throws InterruptedException {
        /* Create the SparkSession.
         * If config arguments are passed from the command line using --conf,
         * parse args for the values to set.
         */

        SparkSession spark = SparkSession.builder()
          .master("local")
          .appName("MongoSparkConnectorIntro")
          .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/test.myCollection")
          .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/test.myCollection")
          .getOrCreate();

        // Create a JavaSparkContext using the SparkSession's SparkContext object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        // More application logic would go here...

        Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
        implicitDS.printSchema();
        implicitDS.show();

        // Load data with explicit schema
        Dataset<Character> explicitDS = MongoSpark.load(jsc).toDS(Character.class);
        explicitDS.printSchema();
        explicitDS.show();

        // Create the temp view and execute the query
        explicitDS.createOrReplaceTempView("characters");
        Dataset<Row> centenarians = spark.sql("SELECT name, age FROM characters WHERE age >= 100");
        centenarians.show();

        // Write the data to the "hundredClub" collection
        MongoSpark.write(centenarians).option("collection", "hundredClub").mode("overwrite").save();

        jsc.close();
        spark.close();
      }
}
