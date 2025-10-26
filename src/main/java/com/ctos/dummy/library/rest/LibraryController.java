package com.ctos.dummy.library.rest;

import com.ctos.dummy.library.dto.AisleDto;
import com.ctos.dummy.library.dto.BookDto;
import com.ctos.dummy.library.dto.LibraryDto;
import com.ctos.dummy.library.service.LibraryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/isles/{libraryName}")
    public ResponseEntity<List<AisleDto>> getIslesByLibrary(@PathVariable @NotBlank String libraryName) {
        List<AisleDto> aisles = libraryService.getAllIslesByLibrary(libraryName);
        return ResponseEntity.ok(aisles);
    }

    @GetMapping("/library/{libraryName}/isles/{isleName}/books")
    public ResponseEntity<List<BookDto>> getBooksByIsleAndLibrary(
            @PathVariable @NotBlank String libraryName,
            @PathVariable @NotBlank String isleName) {
        List<BookDto> books = libraryService.getAllBooksByIsleAndLibrary(libraryName, isleName);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/library")
    public ResponseEntity<LibraryDto> saveLibrary(@Valid @RequestBody LibraryDto libraryDto) {
        LibraryDto saved = libraryService.saveLibrary(libraryDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/library/{libraryId}")
    public ResponseEntity<LibraryDto> updateLibrary(
            @PathVariable @Min(1) int libraryId,
            @Valid @RequestBody LibraryDto libraryDto) {
        LibraryDto updated = libraryService.updateLibrary(libraryId, libraryDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/libraries")
    public ResponseEntity<List<LibraryDto>> getAllLibraries() {
        List<LibraryDto> libraries = libraryService.getAllLibraries();
        return ResponseEntity.ok(libraries);
    }

}
