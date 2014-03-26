package de.hu_berlin.german.korpling.saltnpepper.pepper.common;
/**
 * Status of a job in Pepper, the status could be one of the following:
 * <ul>
 * 	<li>{@link #NOT_STARTED}, if the job was not started</li>
 * 	<li>{@link #IN_PROGRESS}, if the job is running</li>
 * 	<li>{@link #ENDED}, if the job ended successful</li>
 * 	<li>{@link #ENDED_WITH_ERRORS}, if the job ended with errors</li>
 * </ul>
 * @author Florian Zipser
 *
 */
public enum JOB_STATUS {
	NOT_STARTED, 
	IN_PROGRESS,
	ENDED,
	ENDED_WITH_ERRORS;
}
