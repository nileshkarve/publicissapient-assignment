/**
 * 
 */
package com.publicissapient.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Nilesh
 *
 */
@SpringBootApplication
@EnableScheduling
public class PublicisSapientAssignment {

	public static void main(String[] args) {
		try {
			SpringApplication.run(PublicisSapientAssignment.class, args);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
