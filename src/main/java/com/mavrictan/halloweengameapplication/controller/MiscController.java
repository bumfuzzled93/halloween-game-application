package com.mavrictan.halloweengameapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping(path = "misc")
public class MiscController {

    @RequestMapping(value = "/serverTime", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> serverTime() {
        return ResponseEntity.ok(LocalTime.now().toString());
    }
}
