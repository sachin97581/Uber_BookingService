package com.example.uber_BookinService.repository;

import com.example.uberProject_EntityService.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.sql.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
