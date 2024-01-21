/**
 * 
 */
package com.publicissapient.assignment.data.manager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publicissapient.assignment.data.entity.ForecastDetail;
import com.publicissapient.assignment.data.ingestion.WeatherDataIngestor;
import com.publicissapient.assignment.data.repo.CityWeatherDataRepo;
import com.publicissapient.assignment.model.RainForecastNote;
import com.publicissapient.assignment.model.SunnyForecastNote;
import com.publicissapient.assignment.model.WindForecastNote;
import com.publicissapient.assignment.utils.WEATHER_CATEGORY;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Component("weatherDataManager")
public class WeatherDataManagerImpl implements WeatherDataManager {
	
	private static final Double MAX_TEMP_FOR_TOO_SUNNY_NOTE = 40D;
	private static final Double MAX_WIND_SPEED_FOR_WINDY_NOTE = 4.4704D; // m/s to mi/hr

	@Autowired
	private WeatherDataIngestor apiWeatherDataIngestor;
	
	@Autowired
	private CityWeatherDataRepo cityWeatherDataRepoImpl;

	@Override
	public List<ForecastDetail> getWeatherForecast(String cityName, LocalDate date) {
		log.info("Fetching weather forecast for city {} for date {}", cityName, date);
		List<ForecastDetail> forecastDetails = cityWeatherDataRepoImpl.getForecastDetails(cityName, date, date.plusDays(2));
		log.debug("Next 2 day's forecast details found for city in database {} for date {} : {}", cityName, date, forecastDetails);
		log.info("Total forecast details found for city for next 2 days in database {} for date {} : {}", cityName, date, forecastDetails.size());
		ingestAndPopulateForcastIfNotFound(cityName, date, forecastDetails);
		addForecastNote(forecastDetails);
		return forecastDetails;
	}
	
	@Override
	public ForecastDetail ingestWeatherData(String cityName, LocalDate date) {
		return apiWeatherDataIngestor.ingestWeatherData(cityName, date);
	}
	
	private void ingestAndPopulateForcastIfNotFound(String cityName, LocalDate date, List<ForecastDetail> forecastDetails) {
		Set<LocalDate> datesToIngestDataFor = new HashSet<>();
		for(int i=0;i<3;i++) {
			LocalDate dateToLookFor = date.plusDays(i);
			if(forecastDetails.stream().filter(detail -> dateToLookFor.compareTo(detail.getForecastDate()) == 0).findAny().isEmpty()) {
				datesToIngestDataFor.add(dateToLookFor);
			}
		}
		datesToIngestDataFor.forEach(ingestionDate -> {
			ForecastDetail detail = ingestWeatherData(cityName, ingestionDate);
			forecastDetails.add(detail);
		});
	}
	
	private void addForecastNote(List<ForecastDetail> forecastDetails) {
		for(ForecastDetail detail : forecastDetails) {
			if(isRainPredicted(detail)) {
				detail.setNote(new RainForecastNote());
			}
			else if(isTooSunny(detail)) {
				detail.setNote(new SunnyForecastNote());
			}
			else if(isTooWindy(detail)) {
				detail.setNote(new WindForecastNote());
			}
		}
	}

	private boolean isTooWindy(ForecastDetail detail) {
		return detail.getWindSpeed().compareTo(MAX_WIND_SPEED_FOR_WINDY_NOTE) > 0;
	}
	
	private boolean isTooSunny(ForecastDetail detail) {
		return detail.getMaxTemperature().compareTo(MAX_TEMP_FOR_TOO_SUNNY_NOTE) >= 0;
	}

	private boolean isRainPredicted(ForecastDetail detail) {
		return detail.getPredictedWeather().equals(WEATHER_CATEGORY.RAIN);
	}
}
