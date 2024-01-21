/**
 * 
 */
package com.publicissapient.assignment.data.ingestion;

import java.time.LocalDate;

import com.publicissapient.assignment.data.entity.ForecastDetail;

/**
 * @author Nilesh
 *
 */
public interface WeatherDataIngestor {

	public ForecastDetail ingestWeatherData(String cityName, LocalDate date);
}
