package com.catdev.ticket.service.impl.schedule;



import com.catdev.ticket.respository.CronRepository;
import com.catdev.ticket.service.schedule.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private CronRepository cronRepository;

}
