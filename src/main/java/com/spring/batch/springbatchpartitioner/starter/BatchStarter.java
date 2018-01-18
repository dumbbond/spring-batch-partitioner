package com.spring.batch.springbatchpartitioner.starter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by NassiB on 18/01/2018.
 */
@Component
public class BatchStarter {

    public void run(){
        String[] springConfig = { "batch/jobs/job-partitioner.xml" };

        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("partitionJob");

        try {

            //JobParameters param = new JobParametersBuilder().addString("age", "20").toJobParameters();

            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Exit Status : " + execution.getStatus());
            System.out.println("Exit Status : " + execution.getAllFailureExceptions());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}
