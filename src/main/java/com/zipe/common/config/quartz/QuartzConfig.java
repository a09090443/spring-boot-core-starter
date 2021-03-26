package com.zipe.common.config.quartz;

import com.zipe.employee.job.FindEmployeeJob;
import com.zipe.util.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Objects;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/20 上午 10:16
 **/
@Configuration
public class QuartzConfig {

    private final JobFactory jobFactory;

    private final Environment env;

    @Autowired
    public QuartzConfig(JobFactory jobFactory, Environment env) {
        this.jobFactory = jobFactory;
        this.env = env;
    }

    @Bean
    public JobDetailFactoryBean findEmployeeJob() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setJobClass(FindEmployeeJob.class);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean findEmployeeJobCornTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(findEmployeeJob().getObject()));
        cronTriggerFactoryBean.setCronExpression("0 0 */3 * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        if(Objects.requireNonNull(env.getProperty("quartz.enabled")).equalsIgnoreCase(StringConstant.TRUE)){
            schedulerFactoryBean.setTriggers(findEmployeeJobCornTrigger().getObject());
        }
        return schedulerFactoryBean;
    }
}
