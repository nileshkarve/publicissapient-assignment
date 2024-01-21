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
public class MainWeather implements Serializable {

	private static final long serialVersionUID = -583633668247025547L;
	
	private Double temp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private int pressure;
    private int sea_level;
    private int grnd_level;
    private int humidity;
    private Double temp_kf;

}
