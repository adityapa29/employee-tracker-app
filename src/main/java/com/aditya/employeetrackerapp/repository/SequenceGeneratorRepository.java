package com.aditya.employeetrackerapp.repository;

import com.aditya.employeetrackerapp.entity.SequenceGenerator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceGeneratorRepository extends MongoRepository<SequenceGenerator,String> {
}
