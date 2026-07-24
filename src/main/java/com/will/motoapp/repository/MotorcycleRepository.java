package com.will.motoapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.will.motoapp.models.entity.Motorcycle;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
    
    
}
