/**
 * 
 */
package com.publicissapient.assignment.data.repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.publicissapient.assignment.data.entity.ForecastDetail;

/**
 * 
 */
@Component("cityWeatherDataRepo")
public class CityWeatherDataRepoImpl implements CityWeatherDataRepo {

	private static final List<ForecastDetail> forecastDetails = new ArrayList<>();
	
	@Override
	public void saveForecastDetails(ForecastDetail detail) {
		removeExistingRecord(detail.getCityName(), detail.getForecastDate());
		forecastDetails.add(detail);
	}

	@Override
	public List<ForecastDetail> getForecastDetails(String cityName, LocalDate fromDate, LocalDate toDate) {
		List<ForecastDetail> requestedDetails = new ArrayList<>();
		forecastDetails.forEach(detail -> {
			if(detail.getCityName().equalsIgnoreCase(cityName)
				&& (fromDate.compareTo(detail.getForecastDate()) <= 0 && toDate.compareTo(detail.getForecastDate()) >= 0)) {
				requestedDetails.add(detail);
			}
		});
		return requestedDetails;
	}
	
	private void removeExistingRecord(String cityName, LocalDate forecastDate) {
		Optional<ForecastDetail> existingDetail = forecastDetails.stream().filter(detail -> detail.getCityName().equalsIgnoreCase(cityName) && detail.getForecastDate().compareTo(forecastDate) == 0).findAny();
		if(existingDetail.isPresent()) {
			forecastDetails.remove(forecastDetails.indexOf(existingDetail.get()));
		}
	}
}
