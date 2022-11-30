package com.catdev.ticket.readable.response.news;

import com.catdev.ticket.dto.employee.EmployeeAuthorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsPageableRes {
    private String title;
    private String content;
    private Long id;
    private Instant createdTime;
    private Instant modifiedTime;
    private EmployeeAuthorDto createdBy;
    private EmployeeAuthorDto modifiedBy;
}
