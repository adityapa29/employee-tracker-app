package com.aditya.employeetrackerapp.service;

import com.aditya.employeetrackerapp.entity.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public long generateEmployeeId(String sequenceKey) {
        SequenceGenerator counter = mongoOperations.findAndModify(query(where("id").is(sequenceKey)),
                new Update().inc("value", 1), options().returnNew(true).upsert(true),
                SequenceGenerator.class);
        return !Objects.isNull(counter) ? counter.getValue()+999 : 1000;
    }
}
