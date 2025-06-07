package com.loveapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.loveapp.model.Memory;
import com.loveapp.repository.MemoryRepository;

@Service
public class MemoryService {
    
    @Autowired
    private MemoryRepository memoryRepository;

    public Optional<Memory> findById(Long id) {
        return memoryRepository.findById(id);
    }


    public void deleteById(Long id) {
        memoryRepository.deleteById(id);
    }

    public List<Memory> getAllMemories() {
        return memoryRepository.findAllByOrderByDateDesc();
    }
    
    public Optional<Memory> getMemoryById(Long id) {
        return memoryRepository.findById(id);
    }
    
    public Memory saveMemory(Memory memory, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            memory.setImage(imageFile.getBytes());
            memory.setImageContentType(imageFile.getContentType());
        }
        return memoryRepository.save(memory);
    }
    
    public void save(Memory memory) {
        memoryRepository.save(memory);
    }
    
    public void deleteMemory(Long id) {
        memoryRepository.deleteById(id);
    }
    
    public List<Memory> getRecentMemories() {
        return memoryRepository.findTop10ByOrderByCreatedAtDesc();
    }

}