package com.example.demo.service;

import com.example.demo.controller.handler.LibraryException;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.impl.LibraryMapper;
import com.example.demo.entity.Library;
import com.example.demo.repository.CustomLibraryRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.service.impl.LibraryServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceImplTest {
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private LibraryMapper libraryMapper;
    @Mock
    private CustomLibraryRepository customLibraryRepository;
    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Test
    void givenId_whenFindLibrary_thenSuccess() {
        //given
        Long id = 1L;

        Library library = Library.builder()
                        .id(id)
                        .name("library")
                        .build();
        LibraryDto libraryDto = LibraryDto.builder()
                        .id(id)
                        .name(library.getName())
                        .build();

        //when(libraryRepository.save(any(Library.class))).thenReturn(library);
        when(libraryMapper.toDto(library)).thenReturn(libraryDto);
        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
        //when
        LibraryDto result = libraryService.getLibrary(id);
        //then
        assertEquals(libraryDto, result);
    }

    @Test
    void givenId_whenFindLibrary_thenException() {
        //given
        //when
        //then
        assertThrows(LibraryException.class, () -> libraryService.getLibrary(2L));
    }

    @Test
    void givenId_whenDeleteBook_thenSuccess() {
        //given
        Long id = 1L;
        doNothing().when(libraryRepository).deleteById(id);
        //when
        libraryService.deleteLibrary(id);
        //then
        verify(libraryRepository).deleteById(id);
    }

    @Test
    void givenLibraryDto_whenAddLirbary_thenSuccess() {
        //given
        Library library = Library.builder()
                .id(1L)
                .name("library")
                .build();
        when(libraryRepository.save(any(Library.class))).thenReturn(library);
        //when
        LibraryDto result = libraryService.addLibrary(library);
        //then
        assertEquals(library, result);
    }

    @Test
    public void givenLibraryDto_whenUpdateLibrary_thenSuccess() {
        //given
        Library libraryDto = Library.builder()
                .id(1L)
                .name("library NEW")
                .city("astana")
                .build();
        Library library = Library.builder()
                .id(1L)
                .name("old library")
                .city("astana")
                .build();
        when(libraryRepository.save(library)).thenReturn(library);
        //when
        LibraryDto result = libraryService.updateLibrary(libraryDto);
        //then
        assertEquals("library NEW", result.getName());
    }

    @Test
    void givenBadFilters_whenFindByFilters_thenNoResult() {
        //given
        FilterDto libraryDto = FilterDto.builder()
                .name("")
                .city("")
                .build();
        when(customLibraryRepository.findByFilters(libraryDto.getName(), libraryDto.getCity())).thenReturn(Collections.emptyList());
        //when
        List<LibraryDto> result = libraryService.getByFilters(libraryDto);
        //then
        assertEquals(0, result.size());
    }

    @Test
    void givenGoodFilters_whenFindByFilters_thenSuccess() {
        // given
        FilterDto libraryDto = FilterDto.builder()
                .city("city1")
                .build();
        Library library1 = Library.builder()
                .id(1L)
                .name("library1")
                .city("city1")
                .build();
        Library library2 = Library.builder()
                .id(2L)
                .name("library2")
                .city("city1")
                .build();
        List<Library> libraries = new ArrayList<>();
        libraries.add(library1);
        libraries.add(library2);
        LibraryDto libraryDto1 = LibraryDto.builder()
                .id(1L)
                .name("library1")
                .city("city1")
                .build();
        LibraryDto libraryDto2 = LibraryDto.builder()
                .id(2L)
                .name("library2")
                .city("city1")
                .build();
        List<LibraryDto> list = new ArrayList<>();
        list.add(libraryDto1);
        list.add(libraryDto2);
        when(customLibraryRepository.findByFilters(libraryDto.getName(), libraryDto.getCity())).thenReturn(libraries);
        when(libraryMapper.listToDto(libraries)).thenReturn(list);
        //when
        List<LibraryDto> result = libraryService.getByFilters(libraryDto);
        //then
        assertEquals(list, result);
    }

    @Test
    public void givenPageable_whenFindAll_thenSuccess() {
        //given
        Pageable pageable = PageRequest.of(0, 20);

        Library library1 = Library.builder()
                .id(1L)
                .name("library1")
                .city("city1")
                .build();
        Library library2 = Library.builder()
                .id(2L)
                .name("library2")
                .city("city1")
                .build();

        LibraryDto libraryDto1 = LibraryDto.builder()
                .id(library1.getId())
                .name(library1.getName())
                .city(library1.getCity())
                .build();

        LibraryDto libraryDto2 = LibraryDto.builder()
                .id(library2.getId())
                .name(library2.getName())
                .city(library2.getCity())
                .build();

        List<Library> list = new ArrayList<>();
        list.add(library1);
        list.add(library2);

        Page<Library> libraries = new PageImpl<>(list);

        when(libraryMapper.toDto(library1)).thenReturn(libraryDto1);
        when(libraryMapper.toDto(library2)).thenReturn(libraryDto2);
        when(libraryRepository.findAll(pageable)).thenReturn(libraries);

        //when
        Page<LibraryDto> result = libraryService.getLibraries(pageable);

        //then
        assertNotNull(result);
        Assertions.assertEquals(2, result.getSize());

    }
}
