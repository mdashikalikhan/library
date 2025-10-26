package com.ctos.dummy.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AisleDto {
    private int aisleId;
    private String isleName;
    private List<BookDto> books;
}
