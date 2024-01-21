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
public class City implements Serializable {

	private static final long serialVersionUID = 4551040991482615473L;
	
	private int id;
    private String name;
    private Coord coord;
    private String country;
    private Long population;
    private int timezone;
    private Long sunrise;
    private Long sunset;

}
