package com.example.demo.service.impl;

import com.example.demo.controller.handler.LibraryException;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.impl.LibraryMapper;
import com.example.demo.entity.Library;
import com.example.demo.repository.CustomLibraryRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.service.LibraryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final CustomLibraryRepository customLibraryRepository;
    private final Logger logger = LogManager.getLogger(getClass());

    @Override
    public LibraryDto getLibrary(Long id) {
        logger.debug("Getting library with id = " + id);
        Library library = libraryRepository.findById(id).orElseThrow(() -> new LibraryException("No library with id " + id));
        logger.debug("Found library. " + libraryMapper.toDto(library));
        return libraryMapper.toDto(library);
    }

    @Override
    public Page<LibraryDto> getLibraries(Pageable pageable) {
        logger.debug("Getting page of libraries. " + pageable);
        Page<Library> libraries = libraryRepository.findAll(pageable);
        logger.debug("Page was found");
        return new PageImpl<>(libraries.stream()
                .map(libraryMapper::toDto)
                .toList());
    }

    @Override
    public LibraryDto addLibrary(Library library) {
        logger.debug("Adding new library to repository.");
        library.setBooks(new ArrayList<>());
        Library newLibrary = libraryRepository.save(library);
        logger.debug("New library was added." + libraryMapper.toDto(newLibrary));
        return libraryMapper.toDto(newLibrary);
    }

    @Override
    public void deleteLibrary(Long id) {
        logger.debug("Deleting library with id = " + id);
        libraryRepository.deleteById(id);
        logger.debug("Library was deleted.");
    }

    @Override
    public LibraryDto updateLibrary(Library library) {
        logger.debug("Updating library. " + library);
        Library newLibrary = libraryRepository.save(library);
        logger.debug("Library was updated successfully");
        return libraryMapper.toDto(newLibrary);
    }

    @Override
    public List<LibraryDto> getByFilters(FilterDto filterDto) {
        List<Library> libraries = customLibraryRepository.findByFilters(filterDto.getName(), filterDto.getCity());
        return libraryMapper.listToDto(libraries);
    }


}