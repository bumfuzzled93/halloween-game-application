package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Staff;
import com.mavrictan.halloweengameapplication.exception.BadRequestException;
import com.mavrictan.halloweengameapplication.exception.NoSuchPlayerException;
import com.mavrictan.halloweengameapplication.repository.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffService {

    StaffRepository staffRepository;
    @SneakyThrows
    public Optional<Staff> login(String playerUsername, String md5Password) {
        if (!md5Password.toUpperCase().matches("[A-F0-9]{32}")) {
            throw new BadRequestException("Invalid password");
        }

        return Optional.ofNullable(staffRepository.findByUsernameAndPassword(playerUsername, md5Password)
                .orElseThrow(NoSuchPlayerException::new));
    }
}
