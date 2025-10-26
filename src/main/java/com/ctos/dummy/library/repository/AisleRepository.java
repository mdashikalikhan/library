package com.ctos.dummy.library.repository;

import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AisleRepository extends JpaRepository<Aisle, Integer> {

    List<Aisle> findByLibrary(Library library);
}
