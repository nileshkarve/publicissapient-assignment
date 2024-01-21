/**
 * 
 */
package com.publicissapient.assignment.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
		@JsonSubTypes.Type(value = RainForecastNote.class, name = "RainForecastNote"),
		@JsonSubTypes.Type(value = WindForecastNote.class, name = "WindForecastNote"),
		@JsonSubTypes.Type(value = SunnyForecastNote.class, name = "SunnyForecastNote")
	})
public interface ForecastNote extends Serializable {
	String getMessage();
}
