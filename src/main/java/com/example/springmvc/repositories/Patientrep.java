package com.example.springmvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springmvc.entities.Patient;

public interface Patientrep extends JpaRepository<Patient, Long> {
public Page<Patient> findByNomContains(String mc,Pageable pageable);
public List<Patient> findByMalade(boolean b);
public List<Patient> findByNomContainsAndMalade(String Nom,boolean b);
}