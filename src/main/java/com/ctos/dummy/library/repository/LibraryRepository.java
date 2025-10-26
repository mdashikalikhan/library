package com.ctos.dummy.library.repository;

import com.ctos.dummy.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

    List<Library> findByLike(@Param("name") String name);
}
