package com.ctos.dummy.library.service;

import com.ctos.dummy.library.dto.AisleDto;
import com.ctos.dummy.library.dto.BookDto;
import com.ctos.dummy.library.dto.LibraryDto;
import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Book;
import com.ctos.dummy.library.entity.Library;
import com.ctos.dummy.library.exception.DuplicateResourceException;
import com.ctos.dummy.library.exception.ErrorCode;
import com.ctos.dummy.library.exception.ResourceNotFoundException;
import com.ctos.dummy.library.repository.AisleRepository;
import com.ctos.dummy.library.repository.BookRepository;
import com.ctos.dummy.library.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final AisleRepository aisleRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public LibraryDto saveLibrary(LibraryDto dto) {
        try {
            Library library = modelMapper.map(dto, Library.class);

            if (library.getAisles() != null) {
                library.getAisles().forEach(a -> a.setLibrary(library));
            }

            Library saved = libraryRepository.save(library);
            return modelMapper.map(saved, LibraryDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException(
                    ErrorCode.DUPLICATE_LIBRARY_NAME,
                    "Library name already exists: " + dto.getLibraryName()
            );
        }
    }


    @Transactional
    public LibraryDto updateLibrary(int libraryId, LibraryDto dto) {
        Library existing = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.LIBRARY_NOT_FOUND, "Library not found with id: " + libraryId));

        existing.setLibraryName(dto.getLibraryName());

        if (dto.getAisles() != null) {
            existing.getAisles().clear();
            dto.getAisles().forEach(aisleDto -> {
                Aisle aisle = modelMapper.map(aisleDto, Aisle.class);
                aisle.setLibrary(existing);
                existing.getAisles().add(aisle);
            });
        }

        Library updated = libraryRepository.save(existing);
        return modelMapper.map(updated, LibraryDto.class);
    }


    public List<AisleDto> getAllIislesByLibrary(String libraryName) {
        List<Library> libraries = libraryRepository.findByLike(libraryName);

        if (libraries.isEmpty()) {
            throw new ResourceNotFoundException(
                    ErrorCode.LIBRARY_NOT_FOUND, "Library not found for name like: " + libraryName);
        }

        return libraries.stream()
                .flatMap(l -> aisleRepository.findByLibrary(l).stream())
                .map(a -> modelMapper.map(a, AisleDto.class))
                .collect(Collectors.toList());
    }


    public List<BookDto> getAllBooksByIsleAndLibrary(String libraryName, String aisleName) {
        Library library = libraryRepository.findByLike(libraryName).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.LIBRARY_NOT_FOUND, "Library not found for name: " + libraryName));

        Aisle aisle = aisleRepository.findByLibrary(library).stream()
                .filter(a -> a.getIsleName().equalsIgnoreCase(aisleName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.AISLE_NOT_FOUND, "Aisle not found: " + aisleName));

        List<Book> books = bookRepository.findByAisle(aisle.getAisleId());
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

}
