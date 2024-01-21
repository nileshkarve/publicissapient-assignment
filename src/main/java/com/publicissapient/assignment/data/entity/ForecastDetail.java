/**
 * 
 */
package com.publicissapient.assignment.data.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.publicissapient.assignment.model.ForecastNote;
import com.publicissapient.assignment.model.Rain;
import com.publicissapient.assignment.model.Snow;
import com.publicissapient.assignment.utils.PublicisSapientAssignmentConstant;
import com.publicissapient.assignment.utils.WEATHER_CATEGORY;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDetail implements Serializable {

	private static final long serialVersionUID = -416230513665156581L;

	private String cityName;
	private Double minTemperature;
	private Double maxTemperature;
	@JsonFormat(pattern = PublicisSapientAssignmentConstant.LOCAL_DATE_FORMAT)
	private LocalDate forecastDate;
	private Double windSpeed;
	private WEATHER_CATEGORY predictedWeather;
	private String weatherDescription;
	private Rain rain;
	private Snow snow;
	private ForecastNote note;
}
