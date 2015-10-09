/**
 * Copyright 2009 Humboldt-Universität zu Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.corpus_tools.pepper.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.corpus_tools.pepper.exceptions.PepperException;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
/**
 * This class is a helper class for developing {@link PepperModule}s. The {@link XMLTagExtractor} generates a dictionary of the xml vocabulary. 
 * The dictionary consists of xml tag names, xml namespaces and attribute names from a source file and generates a java interface and a java class 
 * as well. The interface contains the xml namespace declarations, the xml element and attribute names as fields (public static final Strings). 
 * The generated java class implements that interface and further extends the {@link DefaultHandler2} class, to read a xml file
 * following the generated xml dictionary. 
 * <br/>
 * This class can be very helpful, when creating {@link PepperImporter} or {@link PepperExporter} 
 * classes consuming or producing xml formats. In that case, a sample xml file (containing most or 
 * better all of the elements) can be used to extract all element names as keys for the 
 * implementation.
 * <br/>
 * For instance, the following xml file:
 * <pre>
 * &lt;sentence xml:lang="en">
 *   &lt;token pos="VBZ">Is&lt;/token>
 *   &lt;token pos="DT" lemma="this">this&lt;/token>
 *   &lt;token>example&lt;/token>
 * &lt;/sentence>
 * </pre>
 * will be result in the following interface:
 * <pre>
 *  public interface INTERFACE_NAME{
 *    public static final String TAG_TOKEN= "token";
 *    public static final String TAG_SENTENCE= "sentence";
 *    public static final String ATT_LEMMA= "lemma";
 *    public static final String ATT_XML_LANG= "xml:lang";
 *    public static final String ATT_POS= "pos";
 * }
 * </pre>
 * where INTERFACE_NAME is the name of the xml file.
 * <br/>
 * and in the following class:
 * <pre>
 *  public class INTERFACE_NAMEReader extends DefaultHandler2 implements Bergleute_WebLicht_BitPar {
 *  	public void startElement(	String uri,
 *  								String localName,
 *  								String qName,
 *  								Attributes attributes)throws SAXException
 *  	{
 *  		if (TAG_TOKEN.equals(qName)){
 *  		}
 *  		else if (TAG_SENTENCE.equals(qName)){
 *  		}
 *  	}
 * }
 * </pre>
 * <br/>
 * Using as a library:</br>
 * <pre>
 * XMLTagExtractor extractor= new XMLTagExtractor();
 * extractor.setXmlResource(input);
 * extractor.setJavaResource(output);
 * extractor.extract();
 * </pre>
 * <br/>
 * Running this tiny program from command line:<br/>
 * <br/>
 * java XMLTagExtractor.class -i XML_FILE -o OUTPUT_PATH
 * 
 * 
 * @author Florian Zipser
 *
 */
public class XMLTagExtractor extends DefaultHandler2 
{
	/** XML file to pasre **/
	private URI xmlResource= null;
	
	/** Sets xml file to be parsed. 
	 * @throws FileNotFoundException **/
	public void setXmlResource(URI resource) throws FileNotFoundException
	{
		if (resource== null)
			throw new NullPointerException("Cannot start extracting, xml resource is empty.");
		
		File inFile= new File(resource.toString());
		if (!inFile.exists())
			throw new FileNotFoundException("Cannot start extracting, xml resource '"+inFile+"' does not exist.");
		this.xmlResource= resource;
	}
	/** returns xml file to be parsed. **/
	public URI getXmlResource()
	{
		return(this.xmlResource);
	}
	
	/** Java file to be output **/
	private URI javaResource= null;
	
	/** Sets java file to be parsed. 
	 * @throws FileNotFoundException **/
	public void setJavaResource(URI resource) throws FileNotFoundException
	{
		if (resource== null)
			throw new NullPointerException("Cannot start extracting, xml resource is empty.");
		File outFile= new File(resource.toString());
		if (!outFile.exists()){
			outFile.mkdirs();
//			throw new FileNotFoundException("Cannot start extracting, java resource '"+outFile+"' does not exist.");
		}

		this.javaResource= resource;
	}
	/** returns java file to be parsed. **/
	public URI getJavaResource()
	{
		return(this.javaResource);
	}
	/**
	 * Name of prefix for xml namespaces prefix. For instance the xml namespace prefix &lt;myns:token xmlns:myns="..."> will result in field:
	 * <br/>
	 * NS_MYNS
	 */
	public static final String PREFIX_NAMESPACE="NS_";
	/**
	 * Name of prefix for xml namespaces. For instance the xml namespace &lt;myns:token xmlns:myns="https://ns.de"> will result in field:
	 * <br/>
	 * NS_VALUE_MYNS="https://ns.de"
	 */
	public static final String PREFIX_NAMESPACE_VALUE="NS_VALUE_";
	/**
	 * Name of prefix for xml tags. For instance the xml tag &lt;token> will result in field:
	 * <br/>
	 * TAG_TOKEN
	 */
	public static final String PREFIX_ELEMENT="TAG_";
	/**
	 * Name of prefix for xml attribute. For instance the xml attribute &lt;token pos="..."> will result in field:
	 * <br/>
	 * ATT_POS
	 */
	public static final String PREFIX_ATTRIBUTE="ATT_";
	
	/**
	 * {@inheritDoc XMLTagExtractor}
	 */
	public void extract()
	{
		File resourceFile= new File(getXmlResource().toString());
		if (!resourceFile.exists()) 
			throw new PepperException("Cannot load a xml-resource, because the file does not exist: " + resourceFile);
		
		if (!resourceFile.canRead())
			throw new PepperException("Cannot load a xml-resource, because the file can not be read: " + resourceFile);
		
		
		File outFile= new File(this.getJavaResource().toString()); 
		if (outFile.isDirectory())
		{
			String[] parts= resourceFile.getName().split("[.]");
			String outFileName= parts[0]+".java";
			outFile= new File(outFile.getAbsolutePath()+"/"+outFileName);
		}
		
		SAXParser parser;
        XMLReader xmlReader;
        
        SAXParserFactory factory= SAXParserFactory.newInstance();
        
        try
        {
       	parser= factory.newSAXParser();
	        xmlReader= parser.getXMLReader();
	        xmlReader.setContentHandler(this);
        } catch (ParserConfigurationException e) {
        	throw new PepperException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e);
        }catch (Exception e) {
	    	throw new PepperException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e);
		}
       
        
        try {
         	InputStream inputStream= new FileInputStream(resourceFile);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			xmlReader.parse(is);
        } catch (SAXException e) 
        {
        	
            try
            {
				parser= factory.newSAXParser();
		        xmlReader= parser.getXMLReader();
		        xmlReader.setContentHandler(this);
				xmlReader.parse(resourceFile.getAbsolutePath());
            }catch (Exception e1) {
            	throw new PepperException("Cannot load a xml-resource '"+resourceFile.getAbsolutePath()+"'.", e1);
			}
		}
        catch (Exception e) {
			if (e instanceof PepperException)
				throw (PepperException)e;
			else throw new PepperException("Cannot read xml-file'"+getXmlResource()+"', because of a nested exception. ",e);
		}
        
        PrintWriter writer = null;
        String dictionaryName= null;
        try
		{
        	//create interface file
        	writer= new PrintWriter(outFile, "UTF-8");
        	dictionaryName= outFile.getName().replace(".java", "");
        	writer.println("package myPackage;");
        	writer.println("");
        	writer.println("/**");
        	writer.println("* This interface is a dictionary for files following the model of '"+outFile.getName().replace(".java", "")+"'.");
        	writer.println("*");
        	writer.println("* @author "+XMLTagExtractor.class.getSimpleName());
        	writer.println("**/");
        	writer.println("public interface "+dictionaryName+"{");
        	for (String key: getNamespaces().keySet())
        	{
        		String ns= getNamespaces().get(key);
        		writer.println("\t\t/** constant to address the xml-namespace prefix'"+key+"'. **/");
        		writer.println("\t\tpublic static final String "+PREFIX_NAMESPACE+key.toUpperCase().replace(":", "_").replace("-", "_")+"= \""+key+"\";");
        		
        		writer.println("\t\t/** constant to address the xml-namespace '"+ns+"'. **/");
        		writer.println("\t\tpublic static final String "+PREFIX_NAMESPACE_VALUE+key.toUpperCase().replace(":", "_").replace("-", "_")+"= \""+ns+"\";");
        	}
        	writer.println();
        	for (String tagName: getTagNames())
        	{
        		writer.println("\t\t/** constant to address the xml-element '"+tagName+"'. **/");
        		writer.println("\t\tpublic static final String "+PREFIX_ELEMENT+tagName.toUpperCase().replace(":", "_").replace("-", "_")+"= \""+toNCNames(tagName)+"\";");
        	}
        	writer.println();
        	for (String attName: getAttributeNames())
        	{
        		writer.println("\t\t/** constant to address the xml-attribute '"+attName+"'. **/");
        		writer.println("\t\tpublic static final String "+PREFIX_ATTRIBUTE+attName.toUpperCase().replace(":", "_").replace("-", "_")+"= \""+toNCNames(attName)+"\";");
        	}
        	writer.println("}");
            
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        finally
        {
        	if (writer != null)
        		writer.close();
        }
        
        try
		{
        	//create class file 
        	outFile= new File(outFile.getAbsolutePath().replace(".java", "")+"Reader"+".java");
        	writer= new PrintWriter(outFile, "UTF-8");
        	
        	writer.println("package myPackage;");
        	writer.println("");
        	writer.println("import org.xml.sax.Attributes;");
        	writer.println("import org.xml.sax.SAXException;");
        	writer.println("import org.xml.sax.ext.DefaultHandler2;");
        	writer.println("");
        	writer.println("/**");
        	writer.println("* This class parses an xml file following the model of '"+outFile.getName().replace(".java", "")+"'.");
        	writer.println("*");
        	writer.println("* @author "+XMLTagExtractor.class.getSimpleName());
        	writer.println("**/");
        	writer.println("public class "+outFile.getName().replace(".java", "")+" extends DefaultHandler2 implements "+dictionaryName+" {");
        	
        	writer.println("\t\t@Override");
        	writer.println("\t\tpublic void startElement(	String uri,");
        	writer.println("\t\t\t\tString localName,");
        	writer.println("\t\t\t\tString qName,");
        	writer.println("\t\t\t\tAttributes attributes)throws SAXException");
        	writer.println("\t\t{");
        	int i=0;
        	for (String tagName: getTagNames())
        	{
        		writer.print("\t\t\t");
        		if (i== 0)
        			writer.print("if");
        		else
        			writer.print("else if");
        		i++;
        		writer.println(" ("+PREFIX_ELEMENT+tagName.toUpperCase().replace(":", "_").replace("-", "_")+".equals(qName)){");
        		writer.println("\t\t\t}");
        	}
        	writer.println("\t\t}");
        	
        	writer.println("}");
            
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        finally
        {
        	if (writer != null)
        		writer.close();
        }
	}
	
	/** contains all xml element names contained in xml file **/
	private HashSet<String> tagNames= null;
	/** returns all xml element names contained in xml file**/
	private HashSet<String> getTagNames()
	{
		if (tagNames== null)
			tagNames= new HashSet<String>();
		return(tagNames);
	}
	
	/** contains all xml element names contained in xml file **/
	private HashSet<String> attributeNames= null;
	/** returns all xml element names contained in xml file**/
	private HashSet<String> getAttributeNames()
	{
		if (attributeNames== null)
			attributeNames= new HashSet<String>();
		return(attributeNames);
	}
	/** contains all namespaces of xml file, having the prefix as key and the namespace as value **/
	private Map<String, String> namespaceDeclaration= null;
	/** Returns a map containing all namespaces of xml file, having the prefix as key and the namespace as value **/
	private Map<String, String> getNamespaces(){
		if (namespaceDeclaration== null){
			namespaceDeclaration= new Hashtable<String, String>();
		}
		return(namespaceDeclaration);
	}
	
	@Override
	public void startElement(	String uri,
            					String localName,
            					String qName,
            					Attributes attributes)throws SAXException
    {
		getTagNames().add(qName);
		for (int i= 0; i< attributes.getLength(); i++)
		{
			String attName= attributes.getQName(i);
			if (attName.startsWith("xmlns:")){
				String prefix= attName.replace("xmlns:", "");
				String ns= attributes.getValue(i);
				//attribute is a namespace declaration.
				if (!getNamespaces().containsKey(prefix)){
					getNamespaces().put(prefix, ns);
				}
			}else{
				getAttributeNames().add(attributes.getQName(i));
			}
		}
    }
	/**
	 * Transform passed name to a NCName conform String.
	 * @param name
	 * @return
	 */
	private String toNCNames(String name){
		name= name.substring(name.lastIndexOf(":")+1);
		return(name);
	}
	
	/** argument for command line call for determine input file **/
	public static final String ARG_INPUT="-i";
	/** argument for command line call for determine output file **/
	public static final String ARG_OUTPUT="-o";
	
	/**
	 * {@inheritDoc XMLTagExtractor}
	 * java XMLTagExtractor.class -i XML_FILE -o OUTPUT_PATH
	 * 
	 * @param args -i XML_FILE -o OUTPUT_PATH
	 */
	public static void main(String[] args)
	{
		URI input= null;
		URI output= null;
		if (args!= null)
		{
			for (int i= 0; i< args.length; i++)
			{
				if (ARG_INPUT.equals(args[i]))
				{
					input= URI.create(args[i+1]);
					i++;
					
				}
				else if (ARG_OUTPUT.equals(args[i]))
				{
					output= URI.create(args[i+1]);
					i++;
				}
			}
		}
		
		XMLTagExtractor extractor= new XMLTagExtractor();
		try
		{
			System.out.println("input: "+ input);
			System.out.println("output: "+ output);
			extractor.setXmlResource(input);
			extractor.setJavaResource(output);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		extractor.extract();
	}
}
