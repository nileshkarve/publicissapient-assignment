/**
 * 
 */
package com.publicissapient.assignment.data.manager;

import java.time.LocalDate;
import java.util.List;

import com.publicissapient.assignment.data.entity.ForecastDetail;

/**
 * 
 */
public interface WeatherDataManager {

	List<ForecastDetail> getWeatherForecast(String cityName, LocalDate date);

	ForecastDetail ingestWeatherData(String cityName, LocalDate date);

}
