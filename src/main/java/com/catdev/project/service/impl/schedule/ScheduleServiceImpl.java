package com.catdev.project.service.impl.schedule;



import com.catdev.project.respository.CronRepository;
import com.catdev.project.service.schedule.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private CronRepository cronRepository;

}
