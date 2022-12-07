package com.example.demo.service.impl;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.dto.mapper.LibraryMapper;
import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Library;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    @Override
    public LibraryDTO getLibrary(Long id) {
        Library library = libraryRepository.findById(id).orElse(null);
        return libraryMapper.toDTO(library);
    }

    @Override
    public List<LibraryDTO> getLibraries() {
        List<LibraryDTO> libraryDTOS = new ArrayList<>();
        for (Library l : libraryRepository.findAll()){
            libraryDTOS.add(libraryMapper.toDTO(l));
        }
        return libraryDTOS;
    }

    @Override
    public LibraryDTO addLibrary(LibraryDTO library) {
        Library newLibrary = libraryRepository.save(libraryMapper.toLibrary(library));
        return libraryMapper.toDTO(newLibrary);
    }

    @Override
    public void deleteLibrary(LibraryDTO libraryDTO) {
        libraryRepository.deleteById(libraryDTO.getId());
    }

    @Override
    public LibraryDTO updateLibrary(LibraryDTO library) {
        Library newLibrary = libraryRepository.save(libraryMapper.toLibrary(library));
        return libraryMapper.toDTO(newLibrary);
    }

}
