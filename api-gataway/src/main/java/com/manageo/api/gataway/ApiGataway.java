/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.manageo.api.gataway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author wassim
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.manageo.api.gataway",       
    "com.manageo.api.gataway.config",
})
public class ApiGataway {

    public static void main(String[] args) {
		SpringApplication.run(ApiGataway.class, args);
    }
}
