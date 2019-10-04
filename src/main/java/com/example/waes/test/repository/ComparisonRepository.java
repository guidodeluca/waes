package com.example.waes.test.repository;

import com.example.waes.test.entities.ComparisonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonRepository  extends CrudRepository<ComparisonEntity, String> {
}
