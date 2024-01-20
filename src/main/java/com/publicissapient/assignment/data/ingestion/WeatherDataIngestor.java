/**
 * 
 */
package com.publicissapient.assignment.data.ingestion;

import java.util.Map;

/**
 * @author Nilesh
 *
 */
public interface WeatherDataIngestor {

	public void ingestWeatherData(Map<String, Object> ingestionParams);
}
