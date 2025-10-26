package com.ctos.dummy.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(
        name = "Library.findByLike",
        query = "select l from Library l where lower(l.libraryName) " +
                " like  lower(concat('%', :name, '%') ) "
)
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libraryId;
    @Column(unique = true, nullable = false)
    private String libraryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library", orphanRemoval = true,
    fetch = FetchType.LAZY)
    private List<Aisle> aisles = new ArrayList<>();

    public void addAisle(Aisle aisle) {
        aisles.add(aisle);
        aisle.setLibrary(this);
    }

    public void removeAisle(Aisle aisle) {
        aisles.remove(aisle);
        aisle.setLibrary(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Library)) return false;
        return libraryId != 0 && libraryId == ((Library) o).getLibraryId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(libraryId);
    }
}
