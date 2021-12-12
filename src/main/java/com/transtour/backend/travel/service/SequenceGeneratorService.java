package com.transtour.backend.travel.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import com.transtour.backend.travel.model.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public synchronized Long getSequenceNumber(String sequenceName){
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("seq",1);
        DbSequence counter = mongoOperations.findAndModify(query,
                        update,
                        options().returnNew(true).upsert(true),
                        DbSequence.class
                        );
        return !Objects.isNull(counter) ? counter.getSeq() : 1l;
    }
}
