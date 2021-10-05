package com.transtour.backend.travel.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@NoArgsConstructor
@Data
@QueryEntity
@Document("error")
public class LoginError implements Serializable {

    private String dateCreated;
    private String time;
    private String repoName;
    private String stackTrace;
    private String comment;
}
