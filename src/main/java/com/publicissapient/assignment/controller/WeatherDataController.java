/**
 * 
 */
package com.publicissapient.assignment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.publicissapient.assignment.data.entity.ForecastDetail;
import com.publicissapient.assignment.data.manager.WeatherDataManager;

/**
 * 
 */
@RestController("weatherDataController")
@RequestMapping(path = "/weather")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeatherDataController {

	@Autowired
	private WeatherDataManager weatherDataManagerImpl;
	
	@GetMapping(path = {"/getForcaste"})
	public List<ForecastDetail> getForcaste(@RequestParam("cityName") String cityName, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return weatherDataManagerImpl.getWeatherForecast(cityName, date);
	}
	
	@PostMapping(path = {"/ingest"})
	public void ingestForcast(@RequestParam("cityName") String cityName, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		weatherDataManagerImpl.ingestWeatherData(cityName, date);
	}
}
