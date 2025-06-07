package com.loveapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loveapp.model.Memory;
import com.loveapp.service.MemoryService;

@RestController
@RequestMapping("/api/memories")
public class MemoryRestController {

    @Autowired
    private MemoryService memoryService;

    @GetMapping
    public List<Memory> getAllMemories() {
        return memoryService.getAllMemories();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Memory> memoryOpt = memoryService.findById(id);
        if (memoryOpt.isPresent() && memoryOpt.get().getImage() != null) {
            Memory memory = memoryOpt.get();
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(memory.getImageContentType()))
                .body(memory.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveMemory(
        @RequestParam("title") String title,
        @RequestParam("date") String date,
        @RequestParam("description") String description,
        @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        Memory memory = new Memory();
        memory.setTitle(title);
        memory.setDate(LocalDate.parse(date));
        memory.setDescription(description);

        if (imageFile != null && !imageFile.isEmpty()) {
            memory.setImage(imageFile.getBytes());
            memory.setImageContentType(imageFile.getContentType());
        }

        memoryService.save(memory);
    }
    @DeleteMapping("/{id}")
    public void deleteMemory(@PathVariable Long id) {
        memoryService.deleteById(id);
}
}