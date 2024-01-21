/**
 * 
 */
package com.publicissapient.assignment.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Snow implements Serializable {

	private static final long serialVersionUID = -4006754719259943159L;

	@JsonProperty("3h")
	private Long last3h;
}
