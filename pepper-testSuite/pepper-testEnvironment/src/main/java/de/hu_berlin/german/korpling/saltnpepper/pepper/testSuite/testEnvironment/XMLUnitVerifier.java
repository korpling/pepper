package de.hu_berlin.german.korpling.saltnpepper.pepper.testSuite.testEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
/*
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
*/
import org.xml.sax.SAXException;


import de.hu_berlin.german.korpling.saltnpepper.pepper.pepperFW.PepperConverter;

public class XMLUnitVerifier {

	public static void verify(PepperConverter converter){
		converter.getPepperParams().getPepperJobParams().get(0);
		File inputDir = new File(converter.getPepperParams().getPepperJobParams().get(0).getImporterParams().get(0).getSourcePath().toFileString());
		File outputDir = new File(converter.getPepperParams().getPepperJobParams().get(0).getExporterParams().get(0).getDestinationPath().toFileString());
		EList<File> inFiles = new BasicEList<File>();
		getFileListing(inputDir,inFiles);
		
		String inFile;
		String outFile;
		for (File f : inFiles){
			inFile =  f.getAbsolutePath();
			System.out.print("Comparing File "+inFile+" to ");
			outFile = outputDir.getAbsolutePath()+f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(inputDir.getName())-1);
			System.out.println(outFile);
			FileReader fr1 = null;
			FileReader fr2 = null;
			try {
				fr1 = new FileReader(inFile);
				fr2 = new FileReader(outFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			/*
			try {
				Diff diff = new Diff(fr1, fr2);
				System.out.println("Similar? " + diff.similar());
				System.out.println("Identical? " + diff.identical());

				DetailedDiff detDiff = new DetailedDiff(diff);
				EList differences = (EList) detDiff.getAllDifferences();
				for (Object object : differences) {
					Difference difference = (Difference)object;
					System.out.println("***********************");
					System.out.println(difference);
					System.out.println("***********************");
				}

			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}*/

		}
	}
	
	private static void getFileListing(File dir, EList<File> fileList){
		for (File f : Arrays.asList(dir.listFiles())){
			if (f.isFile()){
				fileList.add(f);
			}else{
				if (f.isDirectory()){
					getFileListing(f,fileList);
				}
			}
		}
	}
}
