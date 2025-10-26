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
public class Aisle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aisleId;
    private String isleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "aisle_book",
    joinColumns = @JoinColumn(name = "aisle_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aisle)) return false;
        return aisleId != 0 && aisleId == ((Aisle) o).getAisleId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(aisleId);
    }
}
