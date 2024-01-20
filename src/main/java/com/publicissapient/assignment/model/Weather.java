/**
 * 
 */
package com.publicissapient.assignment.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nilesh
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Serializable {

	private static final long serialVersionUID = -5950314731081743105L;
	
	private int id;
    private String main;
    private String description;
    private String icon;

}
