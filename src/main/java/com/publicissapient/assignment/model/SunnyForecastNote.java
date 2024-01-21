/**
 * 
 */
package com.publicissapient.assignment.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter
@Setter
@ToString
@JsonTypeName("SunnyForecastNote")
public class SunnyForecastNote implements ForecastNote {

	private static final long serialVersionUID = 8540032174581234081L;

	private static final String msg = "Use sunscreen lotion";

	@Override
	public String getMessage() {
		return msg;
	}

}
