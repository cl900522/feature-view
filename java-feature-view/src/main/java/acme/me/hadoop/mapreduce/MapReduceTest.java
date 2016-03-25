package acme.me.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapReduceTest extends Configured implements Tool {
    enum Counter {
        LINESKIP,
    }

    @Override
    public int run(String[] args) throws Exception {
        JobConf conf = new JobConf(getConf(), MapReduceTest.class);
        conf.setJarByClass(Map.class);
        conf.setJarByClass(Reduce.class);
        conf.setJar("logAnalysis");

        Job job = new Job(conf);
        job.setJobName("logAnalysis");
        job.setJarByClass(MapReduceTest.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        // keep the same format with the output of Map and Reduce
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);

        return job.isSuccessful() ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MapReduceTest(), args);
        System.exit(res);
    }
}
