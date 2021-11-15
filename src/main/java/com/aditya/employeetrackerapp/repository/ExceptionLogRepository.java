package com.aditya.employeetrackerapp.repository;

import com.aditya.employeetrackerapp.model.ExceptionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends MongoRepository<ExceptionLog,Object> {

}
