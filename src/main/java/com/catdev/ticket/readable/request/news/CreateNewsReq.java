package com.catdev.ticket.readable.request.news;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateNewsReq {
    private String title;
    private String content;
    private boolean active;
    private Long createdBy;
}
