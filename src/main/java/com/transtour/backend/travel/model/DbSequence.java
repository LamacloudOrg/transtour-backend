package com.transtour.backend.travel.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sequence")
public class DbSequence {
    @Id
    private String id;
    private Long seq;
}
