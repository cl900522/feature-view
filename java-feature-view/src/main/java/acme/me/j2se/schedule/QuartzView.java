package acme.me.j2se.schedule;

import org.junit.Before;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzView {
    private JobDetail job1;
    private JobDetail job2;
    private Scheduler scheduler;

    @Before
    public void init() throws SchedulerException {
        job1 = JobBuilder.newJob(Job1.class).withIdentity("dummyJobName1", "group1").build();
        job2 = JobBuilder.newJob(Job1.class).withIdentity("dummyJobName2", "group1").build();
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    @Test
    public void test1() throws Exception {
        SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName1", "group1").withSchedule(simpleSchedule).build();
        scheduler.scheduleJob(job1, trigger);
        Thread.sleep(15000);
    }

    @Test
    public void test2() throws Exception {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName2", "group1").withSchedule(cronSchedule).build();
        JobKey jobKey = new JobKey("dummyJobName2", "group1");

        scheduler.scheduleJob(job2, trigger);
        System.out.println("Start job");
        Thread.sleep(7000);

        scheduler.pauseJob(jobKey);
        System.out.println("Stop job");
        Thread.sleep(7000);

        scheduler.resumeJob(jobKey);
        System.out.println("Resume job");
        Thread.sleep(7000);

        scheduler.deleteJob(jobKey);
        System.out.println("Delete job");
        Thread.sleep(17000);
    }

    public static class Job1 implements Job {

        /**
         * 要调度的具体任务
         */
        @Override
        public void execute(JobExecutionContext arg0) throws JobExecutionException {
            System.out.println("定时任务执行中…");
        }
    }
}
