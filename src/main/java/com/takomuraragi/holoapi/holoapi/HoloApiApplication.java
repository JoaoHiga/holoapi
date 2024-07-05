package com.takomuraragi.holoapi.holoapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.takomuraragi.holoapi.holoapi.main.Main;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;

@SpringBootApplication
public class HoloApiApplication implements CommandLineRunner {
    @Value("${api.key}")
    String API_KEY;
    @Value("${api.url}")
    String BASE_URL;

	public static void main(String[] args) {
		SpringApplication.run(HoloApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Main main = new Main();
        try {
            main.execute(BASE_URL, API_KEY);
        } catch (JsonProcessingException e) {
            System.out.println("Invalid JSON format");
        } catch (URISyntaxException e) {
            System.out.println("Invalid URI format");
        }
	}
}
