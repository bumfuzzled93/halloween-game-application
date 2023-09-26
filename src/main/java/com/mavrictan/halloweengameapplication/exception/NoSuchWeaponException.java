package com.mavrictan.halloweengameapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such weapon")
public class NoSuchWeaponException extends RuntimeException {
}
