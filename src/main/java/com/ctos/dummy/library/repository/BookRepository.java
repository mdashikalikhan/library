package com.ctos.dummy.library.repository;

import com.ctos.dummy.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b join b.aisles a where a.aisleId=:aisleId")
    List<Book> findByAisle(@Param("aisleId") int aisleId);
}
