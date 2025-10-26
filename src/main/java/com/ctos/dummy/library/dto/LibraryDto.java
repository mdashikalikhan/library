package com.ctos.dummy.library.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDto {
    private int libraryId;
    @NotBlank(message = "Library name must not be blank")
    private String libraryName;
    private List<AisleDto> aisles;
}
