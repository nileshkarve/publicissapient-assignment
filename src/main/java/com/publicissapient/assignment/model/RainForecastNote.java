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
@JsonTypeName("RainForecastNote")
public class RainForecastNote implements ForecastNote {

	private static final long serialVersionUID = 7135384186630225286L;
	
	private static final String msg = "Carry umbrella.";

	@Override
	public String getMessage() {
		return msg;
	}

}
