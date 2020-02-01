package hr.kipson.karolina.ecommerce.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail productPrintJobDetail() {
        return JobBuilder.newJob(ProductPrintJob.class).withIdentity("productPrintJob")
                .storeDurably().build();
    }

    @Bean
    public SimpleTrigger productPrintTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5).repeatForever();

        return TriggerBuilder.newTrigger().forJob(productPrintJobDetail())
                .withIdentity("productPrintTrigger").withSchedule(scheduleBuilder).build();
    }
}
