Im- and exporting corpus structure (only im- and exporters) {#corpusStructure}
==================================

The classes @ref org.corpus_tools.pepper.modules.impl.PepperImporterImpl and @ref org.corpus_tools.pepper.modules.impl.PepperExporterImpl provide an automatic mechanism to im- or export the corpus structure. This mechanism is adaptable step by step, according to your specific purpose. A lot of formats do not care about the corpus structure and they only encode the document structure. In this case the corpus often simulate a corpus structure encoded in the file structure of the corpus.

Pepper's default mapping maps the root folder to a root corpus (`SCorpus` object). A sub folder then corresponds to a sub corpus (`SCorpus` object). The relation between super and sub corpus, is represented as a `SCorpusRelation` object. Following the assumption, that files contain the document structure, there is one `SDocument` corresponding to each file in a sub folder. The `SCorpus` and the `SDocument` objects are linked with a `SCorpusDocumentRelation`. To get an impression of the described mapping, the next two figures show the file structure and the corresponding corpus structure.

![file structure](./moduleDevelopers/images/importCorpusStructure.png)
![corpus structure](./moduleDevelopers/images/corpus-structure.png)

For keeping the correspondence between the corpus structure and the file structure, both the im- and the exporter make use of a map, which can be accessed via @ref org.corpus_tools.pepper.modules.PepperImporter.getIdentifier2ResourceTable() or @ref org.corpus_tools.pepper.modules.PepperExporter.getIdentifier2ResourceTable(). 

<table>
<tr><td>salt://superCorpus</td><td>/superCorpus</td></tr>
<tr><td>salt://superCorpus/subCorpus1/doc1</td>	<td>file://superCorpus/subCorpus1/doc1.xml</td></tr>
<tr><td>salt://superCorpus/subCorpus1/doc2</td>	<td>file://superCorpus/subCorpus1/doc2.xml</td></tr>
<tr><td>salt://superCorpus/</td>				<td>file://superCorpus/subCorpus2</td></tr>
<tr><td>salt://superCorpus/subCorpus2</td>		<td>file://superCorpus/subCorpus2</td></tr>
<tr><td>salt://superCorpus/subCorpus2/doc3</td>	<td>file://superCorpus/subCorpus2/doc3.xml</td></tr>
<tr><td>salt://superCorpus/subCorpus2/doc4</td>	<td>file://superCorpus/subCorpus2/doc4.xml</td></tr>
</table>

Whereas other formats do not encode the document structure in just one file, they use a bunch of files instead. In that case the folder containing all the files (let's call it leaf-folder: ./doc1, ./doc2, ./doc3 and ./doc4) corresponds to a `SDocument` object. 

![file structure, where one document is encoded in multiple files](./moduleDevelopers/images/importCorpusStructure2.png)

In the following two sections, we are going to describe the import and the export mechanism separately.

Importing the corpus structure
------------------------------

The default mechanism for importing a corpus structure is implemented in the method @ref org.corpus_tools.pepper.modules.PepperImporter.importCorpusStructure()

The import mechanism traverses the file structure beginning at the super folder via the sub folders to the files and creates a `Identifier` object corresponding to each folder or file and puts them into the map. This map is necessary for instance to retrieve the physical location of a document structure during the mapping and can be set as shown in the following snippet:

    public PepperMapper createPepperMapper(Identifier identifier){
        ...
        mapper.setResourceURI(getIdentifier2ResourceTable().get(identifier));
        ...
    }

The import mechanism can be adapted by two parameters (or more precisely two lists). An ignore list containing file endings, which are supposed to be ignored during the import and a list of file endings which are supposed to be used for the import. The following snippet is placed into the method @ref org.corpus_tools.pepper.modules.PepperModule.isReadyToStart(), but even could be located inside the constructor:

    public boolean isReadyToStart(){
        ...
        //option 1
        getSDocumentEndings().add(ENDING_XML);
        getSDocumentEndings().add(ENDING_TAB);
        //option 2
        getSDocumentEndings().add(ENDING_ALL_FILES);
        getIgnoreEndings().add(ENDING_TXT)
        //option 3
        getSDocumentEndings().add(ENDING_LEAF_FOLDER);
        ...
    }

In general the parameter of the method @ref org.corpus_tools.pepper.modules.PepperImporter.getDocumentEndings() is just a String, but there are some predefined endings you can use. 

The two lines marked as **option 1**, will add the endings 'xml' and 'tab' to the list of file endings to be imported. That means, that all files having one of these endings will be read and mapped to a document structure. 

The first line of **option 2** means to read each file, no matter what is its ending. But the following line excludes all files having the ending 'txt'. 

Last but not least we look at **option 3**, which is supposed to treat leaf folders as document structures and to create one `SDocument` object for each leaf folder and not for each file.

For entirely changing the default behavior just override the method @ref org.corpus_tools.pepper.modules.PepperImporter.importCorpusStructure().


Exporting the corpus structure
------------------------------

Similar to the import of the corpus structure, Pepper provides a default behavior for the export and possibilities for adaption. The corpus structure export is handled in the method @ref org.corpus_tools.pepper.modules.PepperExporter.exportCorpusStructure(). It is invoked on top of the method @ref org.corpus_tools.pepper.impl.PepperExporterImpl.start(). For entirely changing the default behavior just override this method. The aim of this method is to fill the map of corpus structure and corresponding file structure.

> **Note**
>
> The file structure is automatically created, there are just `URI`s pointing to the virtual file or folder. The creation of the file or folder has to be done by the Pepper module itself in method @ref org.corpus_tools.pepper.modules.PepperMapper.mapSCorpus() or @ref org.corpus_tools.pepper.modules.PepperMapper.mapSDocument().

To adapt the creation of this 'virtual' file structure, you first have to choose the mode of export. You can do this for instance in method @ref org.corpus_tools.pepper.modules.PepperModule.readyToStart(), as shown in the following snippet. But also in the constructor as well.

    public boolean isReadyToStart(){
        ...
        //option 1
        setExportMode(EXPORT_MODE.NO_EXPORT);
        //option 2
        setExportMode(EXPORT_MODE.CORPORA_ONLY);
        //option 3
        setExportMode(EXPORT_MODE.DOCUMENTS_IN_FILES);
        setSDocumentEnding(ENDING_TAB);
        ..
    }

In this snippet, **option 1** means that nothing will be mapped. **Option 2** means that only `SCorpus` objects are mapped to a folder and `SDocument` objects will be ignored. And **option 3** means that `SCorpus` objects are mapped to a folder and `SDocument` objects are mapped to a file. The ending of that file can be determined by passing the ending with method @ref org.corpus_tools.pepper.modules.PepperExporter.setDocumentEnding(). In the given snippet a `URI` having the ending '*tab*' is created for each `SDocument`.