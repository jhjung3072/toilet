package com.jaeho.toilet.repository;

import com.jaeho.toilet.model.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToiletRepository extends JpaRepository<Toilet,Long> {

    List<Toilet> findByIsAvailable(Boolean isAvailable);

}
