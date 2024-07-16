package com.util;

import com.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final ConfigService configService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        configService.listDatabases();
    }

}
