package com.transtour.backend.travel.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("orderNumber")
public class OrderNumber {
    @Id
    private String id;
    private Long number;
}
