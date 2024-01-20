/**
 * 
 */
package com.publicissapient.assignment.model;

import java.io.Serializable;
import java.util.List;

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
public class CityWeatherDetail implements Serializable {

	private static final long serialVersionUID = -1772620235081211786L;
	
	private int dt;
    private MainWeather main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private int pop;
    private Sys sys;
    private String dt_txt;

}
