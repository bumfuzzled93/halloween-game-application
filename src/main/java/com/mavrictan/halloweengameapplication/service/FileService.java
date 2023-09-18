package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.File;
import com.mavrictan.halloweengameapplication.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        File f = File.builder()
                .originalFileName(fileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .uuid(UUID.randomUUID().toString())
                .data(file.getBytes())
                .build();

        return fileRepository.save(f);
    }

    public Optional<File> getFile(long id) {
        return fileRepository.findById(id);
    }


    public Optional<File> getFile(String uuid) {
        return fileRepository.findFileByUuid(uuid);
    }

}
