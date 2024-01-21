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
@JsonTypeName("WindForecastNote")
public class WindForecastNote implements ForecastNote {

	private static final long serialVersionUID = -1076132572331664018L;

	private static final String msg = "It’s too windy, watch out!";

	@Override
	public String getMessage() {
		return msg;
	}

}
