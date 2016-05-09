package acme.me.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import acme.me.hadoop.mapreduce.MapReduceTest.Counter;

public class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static final IntWritable one = new IntWritable(1);

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        try {
            String[] words = line.split(" ");
            if(words == null){
                return;
            }
            for (String word : words) {
                if (word != null && !word.trim().equals(""))
                    context.write(new Text(word), one);
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            context.getCounter(Counter.LINESKIP).increment(1);
        }
    }
}
