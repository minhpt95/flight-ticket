package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "cron")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CronEntity extends CommonEntity {
    @Column(name = "cron_code", unique = true)
    private String cronCode;

    @Column
    private String cronName;

    @Column
    private String cronExpression;
}
