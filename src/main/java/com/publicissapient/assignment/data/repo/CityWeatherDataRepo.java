/**
 * 
 */
package com.publicissapient.assignment.data.repo;

import java.time.LocalDate;
import java.util.List;

import com.publicissapient.assignment.data.entity.ForecastDetail;

/**
 * 
 */
public interface CityWeatherDataRepo {

	public void saveForecastDetails(ForecastDetail detail);

	public List<ForecastDetail> getForecastDetails(String cityName, LocalDate fromDate, LocalDate toDate);
}
