/**
 * 
 */
package com.publicissapient.assignment.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nilesh
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sys implements Serializable {

	private static final long serialVersionUID = -7626002536104221058L;
	
	private String pod;

}
