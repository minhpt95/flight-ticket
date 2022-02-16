package com.catdev.project;

import com.catdev.project.service.UserService;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
@Log4j2
@AllArgsConstructor
public class ProjectApplication {

    final UserService userService;

    final Environment env;

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
        log.info("TimeZone : {} , Instant : {} , Timestamp : {}", TimeZone::getDefault, Instant::now,() -> Timestamp.from(Instant.now()));
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void testLib(){
//        String numberToText = convertMoneyToText("123456789012345");
//        logger.info("Number to text : {}",() -> numberToText);
//    }
//
//    public static String convertMoneyToText(String input) {
//        String output = "";
//        try {
//            NumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale("vi", "VN"), RuleBasedNumberFormat.SPELLOUT);
//            output = ruleBasedNumberFormat.format(Long.parseLong(input)) + " Đồng";
//        } catch (Exception e) {
//            output = "Không đồng";
//        }
//        return output.toUpperCase();
//    }
}
