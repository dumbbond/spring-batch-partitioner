package com.spring.batch.springbatchpartitioner;

import com.spring.batch.springbatchpartitioner.config.ElasticSearchConfiguration;
import com.spring.batch.springbatchpartitioner.init.InsertData;
import com.spring.batch.springbatchpartitioner.starter.BatchStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, BatchAutoConfiguration.class })
@Import(ElasticSearchConfiguration.class)
public class SpringBatchPartitionerApplication implements CommandLineRunner {

    @Autowired
    private InsertData insertData;
    @Autowired
    private BatchStarter batchStarter;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start application");
        insertData.insertDataIntoUserTable();

        batchStarter.run();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBatchPartitionerApplication.class, args);
    }


}
