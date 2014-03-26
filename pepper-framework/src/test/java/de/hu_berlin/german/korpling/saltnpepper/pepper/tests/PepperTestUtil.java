package de.hu_berlin.german.korpling.saltnpepper.pepper.tests;

import java.io.File;

import org.eclipse.emf.common.util.URI;

public class PepperTestUtil {

	public static String getTmpStr(){
		return(System.getProperty("java.io.tmpdir")+"/pepper_test/");
	}
	
	public static File getTmpFile(){
		File retFile= null;
		retFile= new File(getTmpStr());
		retFile.mkdirs();
		return(retFile);
	}
	
	public static URI getTmpURI(){
		return(URI.createFileURI(getTmpFile().getAbsolutePath()));
	}
}
