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
public class WeatherAPIResponse implements Serializable {

	private static final long serialVersionUID = -5059480033474422700L;
	
	private String cod;
    private int message;
    private int cnt;
    private List<CityWeatherDetail> list;
    private City city;

}
