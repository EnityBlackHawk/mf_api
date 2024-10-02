package org.utfpr.mf.mf_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MfApiApplication {
    public static final boolean INSERT_TEST_DATA = true;
    public static void main(String[] args) {
        SpringApplication.run(MfApiApplication.class, args);
    }

}
