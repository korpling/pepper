Pepper module developers helper {#helper}
====

To read xml files Pepper provides a simple method, which shortcuts the instantiation of a SAX parser (see <http://www.saxproject.org/quickstart.html>). This method can be used in a derivation of @ref org.corpus_tools.pepper.modules.PepperImporter as shown in the following snippet:
\code
    readXMLResource(contentHandler, documentLocation);
\endcode

XML extractor
====

The `XMLTagExtractor` generates a dictionary of the xml vocabulary. The dictionary consists of xml tag names, xml namespaces and attribute names from a source file and generates a Java interface and a java class as well. The interface contains the xml namespace declarations, the xml element and attribute names as fields (public static final Strings). The generated java class implements that interface and further extends the `DefaultHandler2` class to read an xml file following the generated xml dictionary.

This class can be very helpful, when creating importer or exporter classes consuming or producing xml formats. In that case, a sample xml file (containing most or better all of the elements) can be used to extract all element names as keys for the implementation.

For instance, the following xml file:
\code
     <sentence xml:lang="en">
       <token pos="VBZ">Is</token>
       <token pos="DT" lemma="this">this</token>
       <token>example</token>
     </sentence>
\endcode

results in the following interface, where INTERFACE\_NAME is the name of the xml file:

     public interface INTERFACE_NAME{
       public static final String TAG_TOKEN= "token";
       public static final String TAG_SENTENCE= "sentence";
       public static final String ATT_LEMMA= "lemma";
       public static final String ATT_XML_LANG= "xml:lang";
       public static final String ATT_POS= "pos";
     }
     

and in the following class:

    public class INTERFACE_NAME_Reader extends DefaultHandler2 
       implements INTERFACE_NAME{
       public void startElement(  String uri,
                                  String localName,
                                  String qName,
                                  Attributes attributes)throws SAXException
       {
          if (TAG_TOKEN.equals(qName)){
          }else if (TAG_SENTENCE.equals(qName)){
          }
       }
    }

For generating the class and interface you can either run the extractor as a library in your code, as shown in the following snippet:

    XMLTagExtractor extractor= new XMLTagExtractor();
    extractor.setXmlResource(input);
    extractor.setJavaResource(output);
    extractor.extract();

or from the command line with the following call:

    java -cp lib/org.corpus-tools.pepper*.jar:plugins/* org.corpus_tools.pepper.cli.XMLTagExtractor -i XML_FILE -o OUTPUT_PATH             
