package org.corpus_tools.pepper.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.eclipse.emf.common.util.URI;
import org.xml.sax.SAXException;

import com.google.common.base.Strings;
import com.google.common.io.Files;

public class XmlComparator {
	protected final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

	public boolean isXmlFile(File supposedlyXmlFile) {
		final String firstLine;
		try {
			firstLine = Files.readFirstLine(supposedlyXmlFile, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			return false;
		}
		if (!Strings.isNullOrEmpty(firstLine)) {
			if (firstLine.contains("<?xml")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is called by {@link #compare(URI, URI)} to compare two
	 * xmlfiles with each other.
	 * 
	 * <strong> You are free to overwrite this method with your own comparison.
	 * </strong>
	 * 
	 * @param actualXmlFile
	 *            the xml file produced by the module itself (which was
	 *            generated from the files from the input corpus path)
	 * @param expectedXmlFile
	 *            the xml file, which is contained in expected corpus path.
	 * @return true, when both files are equal, false otherwise
	 */
	protected boolean compare(final File actualXmlFile, final File expectedXmlFile) {
		final DocumentBuilder docBuilder;
		final Diff diff;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			XMLUnit.setIgnoreWhitespace(true);
			XMLUnit.setIgnoreComments(true);
			XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
			diff = XMLUnit.compareXML(docBuilder.parse(expectedXmlFile), docBuilder.parse(actualXmlFile));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return false;
		}
		diff.overrideElementQualifier(new ElementNameQualifier());
		return diff.identical();
	}
}
