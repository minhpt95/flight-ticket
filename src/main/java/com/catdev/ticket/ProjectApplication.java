package com.catdev.ticket;

import com.catdev.ticket.service.UserService;
import com.catdev.ticket.util.CommonUtil;
import com.catdev.ticket.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.TimeZone;


@SpringBootApplication
@EntityScan(basePackages = {"com.catdev.ticket"})
@EnableScheduling
@EnableWebMvc
@EnableAsync
@Log4j2
@AllArgsConstructor
public class ProjectApplication extends SpringBootServletInitializer {

    final UserService userService;

    final Environment env;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(ProjectApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void clearToken(){
        userService.clearAllToken();
        log.info("Clear Token After Start Application : {}", () -> "clear Token");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setApplicationTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("TimeZone : {} , Instant : {} , Timestamp : {}", TimeZone::getDefault, Instant::now,() -> Timestamp.from(DateUtil.getInstantNow()));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testLib(){
        String numberToText = CommonUtil.convertMoneyToText("123456789012345");
        log.info("Number to text : {}",() -> numberToText);
    }

}
