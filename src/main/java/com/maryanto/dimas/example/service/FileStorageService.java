package com.maryanto.dimas.example.service;

import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    public String getHomeDirectory() {
        return System.getProperty("user.home");
    }
}
