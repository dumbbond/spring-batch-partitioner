package com.spring.batch.springbatchpartitioner.processor;

import com.spring.batch.springbatchpartitioner.dto.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("itemProcessor")
@Scope(value = "step")
public class UserProcessor implements ItemProcessor<User, User> {

    @Value("#{stepExecutionContext[name]}")
    private String threadName;

    @Override
    public User process(User item) throws Exception {

        return item;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

}
