package com.ctos.dummy.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String bookName;

    @ManyToMany(mappedBy = "books")
    private Set<Aisle> aisles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        return bookId != 0 && bookId == ((Book) o).getBookId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(bookId);
    }
}
