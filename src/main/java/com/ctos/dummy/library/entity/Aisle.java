package com.ctos.dummy.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aisle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aisleId;
    private String isleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToMany
    @JoinTable(name = "aisle_book",
    joinColumns = @JoinColumn(name = "aisle_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

}
