package org.corpus_tools.pepper.service.interfaces;

public interface JobSignal {
	public static final String SIG_STOP = "STOP";
	public static final String SIG_START = "START";
	
	public static final String ERR_SIG_NOT_SUPPORTED = "This operation is not supported by pepper framework.";	
}
