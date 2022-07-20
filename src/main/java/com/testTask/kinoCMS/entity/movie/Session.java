package com.testTask.kinoCMS.entity.movie;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateStart;
}
