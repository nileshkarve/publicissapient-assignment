/**
 * 
 */
package com.publicissapient.assignment.data.ingestion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.publicissapient.assignment.data.entity.ForecastDetail;
import com.publicissapient.assignment.data.repo.CityWeatherDataRepo;
import com.publicissapient.assignment.exception.PublicisSapientAssignmentException;
import com.publicissapient.assignment.model.CityWeatherDetail;
import com.publicissapient.assignment.model.WeatherAPIResponse;
import com.publicissapient.assignment.utils.PublicisSapientAssignmentConstant;
import com.publicissapient.assignment.utils.WEATHER_CATEGORY;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author Nilesh
 *
 */
@Slf4j
@Service("apiWeatherDataIngestor")
public class APIWeatherDataIngestor implements WeatherDataIngestor {
	
	public static final String UNIT_PARAMETER_VALUE_METRIC = "metric";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PublicisSapientAssignmentConstant.LOCAL_DATE_TIME_FORMAT);
	
	@Autowired
	private CityWeatherDataRepo cityWeatherDataRepoImpl;

	@Value("${application.external.web.weatherAPIBaseURL}")
	private String weatherAPIBaseURL;

	@Value("${application.external.web.weatherforecastUrlResource}")
	private String weatherforecastUrlResource;

	@Value("${application.external.web.appIdForWeatherAPI}")
	private String appIdForWeatherAPI;
	
	@Override
	public ForecastDetail ingestWeatherData(String cityName, LocalDate date) {
		WeatherAPIResponse response = fetchWeatherDetails(cityName, date);
		log.info("Weather API response received for city {} and date {} : {}", cityName, date, response);
		ForecastDetail forcastDetailsForDate = extractWeatherAPIResponseForDate(cityName, date, response);
		log.info("Forecast details extracted from response for city {} and date {} : {}", cityName, date, forcastDetailsForDate);
		cityWeatherDataRepoImpl.saveForecastDetails(forcastDetailsForDate);
		return forcastDetailsForDate;
	}

	private ForecastDetail extractWeatherAPIResponseForDate(String cityName, LocalDate date, WeatherAPIResponse response) {
		List<CityWeatherDetail> forecastsForDate =  response.getList().stream().filter(weatherDetail -> date.compareTo(LocalDateTime.parse(weatherDetail.getDt_txt(), formatter).toLocalDate()) == 0).collect(Collectors.toList());
		return extractWeatherDetails(cityName, date, forecastsForDate);
	}

	private ForecastDetail extractWeatherDetails(String cityName, LocalDate date, List<CityWeatherDetail> forecastsForDate) {
		ForecastDetail forecastDetail = new ForecastDetail();
		forecastDetail.setCityName(cityName);
		forecastDetail.setForecastDate(date);
		forecastDetail.setMaxTemperature(extractMaxTemperature(forecastsForDate));
		forecastDetail.setMinTemperature(extractMinTemperature(forecastsForDate));
		CityWeatherDetail mainWeatherDetail = extractMainWeatherDetail(forecastsForDate);
		forecastDetail.setPredictedWeather(deriveWeatherCategory(mainWeatherDetail.getWeather().get(0).getMain()));
		forecastDetail.setRain(mainWeatherDetail.getRain());
		forecastDetail.setSnow(mainWeatherDetail.getSnow());
		forecastDetail.setWeatherDescription(mainWeatherDetail.getWeather().get(0).getDescription());
		forecastDetail.setWindSpeed(mainWeatherDetail.getWind().getSpeed());
		return forecastDetail;
	}

	private Double extractMaxTemperature(List<CityWeatherDetail> weatherDetails) {
		return weatherDetails.stream().max(Comparator.comparing(detail -> detail.getMain().getTemp_max())).get().getMain().getTemp_max();
	}
	
	private Double extractMinTemperature(List<CityWeatherDetail> weatherDetails) {
		return weatherDetails.stream().min(Comparator.comparing(detail -> detail.getMain().getTemp_min())).get().getMain().getTemp_min();
	}
	
	private CityWeatherDetail extractMainWeatherDetail(List<CityWeatherDetail> weatherDetails) {
		Set<WEATHER_CATEGORY> categoriesForDate = weatherDetails.stream().map(detail -> deriveWeatherCategory(detail.getWeather().get(0).getMain())).collect(Collectors.toSet());
		if(categoriesForDate.contains(WEATHER_CATEGORY.SNOW)) {
			return weatherDetails.stream().filter(detail -> deriveWeatherCategory(detail.getWeather().get(0).getMain()).equals(WEATHER_CATEGORY.SNOW)).findAny().get();
		}
		else if(categoriesForDate.contains(WEATHER_CATEGORY.RAIN)) {
			return weatherDetails.stream().filter(detail -> deriveWeatherCategory(detail.getWeather().get(0).getMain()).equals(WEATHER_CATEGORY.RAIN)).findAny().get();
		}
		else if(categoriesForDate.contains(WEATHER_CATEGORY.CLOUD)) {
			return weatherDetails.stream().filter(detail -> deriveWeatherCategory(detail.getWeather().get(0).getMain()).equals(WEATHER_CATEGORY.CLOUD)).findAny().get();
		}
		else {
			return weatherDetails.stream().findAny().get();
		}
	}

	private WEATHER_CATEGORY deriveWeatherCategory(String weatherMain) {
		switch(weatherMain) {
		case "Clouds" : return WEATHER_CATEGORY.CLOUD;
		case "Rain" : return WEATHER_CATEGORY.RAIN;
		case "Snow" : return WEATHER_CATEGORY.SNOW;
		default : return WEATHER_CATEGORY.CLEAR;
		}
	}

	private WeatherAPIResponse fetchWeatherDetails(String cityName, LocalDate date) {
		int count = deriveCountToFetch(date);
		WebClient webClient = WebClient.builder().baseUrl(weatherAPIBaseURL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		return webClient.get()
				.uri(uriBuilder -> uriBuilder.path(weatherforecastUrlResource).queryParam("q", "{cityName}")
						.queryParam("appid", "{appid}").queryParam("cnt", "{count}").queryParam("units", "{units}")
						.build(cityName, appIdForWeatherAPI, count, UNIT_PARAMETER_VALUE_METRIC))
				.retrieve()
		        .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
		                clientResponse -> handleErrorResponse(clientResponse.statusCode(), cityName))
		        .bodyToMono(WeatherAPIResponse.class).block();
	}

	private int deriveCountToFetch(LocalDate date) {
		return ((Long)ChronoUnit.DAYS.between(LocalDate.now(), date)).intValue() * 8;
	}

	private Mono<? extends Throwable> handleErrorResponse(HttpStatus statusCode, String cityName) {
		log.error("Error while fetching weather forecast for city : {}", cityName);
		return Mono.error(new PublicisSapientAssignmentException("Error while fetching weather forecast"));
	}
	
//	private LocalDateTime deriveForecastEndTime(LocalDateTime forecastStartTime) {
//		return forecastStartTime.plusHours(2).plusMinutes(59).plusSeconds(59);
//	}
//	
//	@Override
//	public void ingestWeatherData(Map<String, Object> ingestionParams) {
//		String cityName = (String) ingestionParams.get(PublicisSapientAssignmentConstant.CITY_NAME_PARAMETER);
//		WeatherAPIResponse response = fetchWeatherDetails(cityName, COUNT);
//		log.info("Response received from weather API for city : {} with count : {} is {}", cityName, COUNT, response);
//		List<ForecastDetail> forecastDetails = convertWeatherAPIResponseToDatewiseForecastDetails(response);
//		log.info("Total forecast details extracted from response : {}", forecastDetails.size());
//		cityWeatherDataRepoImpl.saveForecastDetails(forecastDetails);
//	}
//
//	private List<ForecastDetail> convertWeatherAPIResponseToDatewiseForecastDetails(WeatherAPIResponse response) {
//		List<ForecastDetail> details = new ArrayList<>();
//		String cityName = response.getCity().getName();
//		response.getList().forEach(detail -> {
//			ForecastDetail forecastDetail = new ForecastDetail();
//			forecastDetail.setCityName(cityName);
//			LocalDateTime forecastStartTime = LocalDateTime.parse(detail.getDt_txt(), formatter);
//			forecastDetail.setForecastStartTime(forecastStartTime);
//			forecastDetail.setForecastEndTime(deriveForecastEndTime(forecastStartTime));
//			forecastDetail.setMaxTemperature(detail.getMain().getTemp_max());
//			forecastDetail.setMinTemperature(detail.getMain().getTemp_min());
//			forecastDetail.setPredictedWeather(deriveWeatherCategory(detail.getWeather().get(0).getMain()));
//			forecastDetail.setRain(detail.getRain());
//			forecastDetail.setSnow(detail.getSnow());
//			forecastDetail.setVisibility(detail.getVisibility());
//			forecastDetail.setWeatherDescription(detail.getWeather().get(0).getDescription());
//			forecastDetail.setWindSpeed(detail.getWind().getSpeed());
//			details.add(forecastDetail);
//		});
//		return details;
//	}
}
