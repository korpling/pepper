Analyzing an unknown corpus (#analyze)
===========================

According to our experience a lot of users, do not care a lot about formats and don't want to. Unfortunately, in most cases it is not possible to not annoy the users with the details of a mapping. But we want to reduce the complexity for the user as much as possible. Most users are not very interested in the source format of a corpus, they just want to bring the corpus into any kind of tool to make further annotations or analyses. Therefore Pepper provides a possibility to automatically detect the source format of a corpus. Unfortunately this task highly depends on the format and the module processing the format. That makes the detection a task of the modules implementor. We are sorry. The mechanism of automatic detection is not a mandatory task, but it is very useful, what makes it recommended.

The class `PepperImporter` defines the method isImportable(URI corpusPath) which can be overridden. The passed `URI` locates the entry point of the entire corpus as given with the Pepper workflow (so it points to the same location as getCorpusDefinition().getCorpusPath() does). Depending on the formats you want to support with your importer the detection can be very different. In the simplest case, it only is necessary, to search through the files at the given location (or to recursivly traverse through directories, in case the location points to a directory), and to read their header section. For instance some formats like the xml formats PAULA (see: <http://www.sfb632.uni-potsdam.de/en/paula.html>) or TEI (see: <http://www.tei-c.org/Guidelines/P5/>) start with a header section like:

    <?xml version="1.0" standalone="no"?>
    <paula version="1.0">
    <!-- ... -->

or

    <?xml version="1.0" encoding="UTF-8"?>
    <!-- ... -->
    <TEI xmlns="http://www.tei-c.org/ns/1.0">
    <!-- ... -->

Formats, where reading only the first lines will provide information about the format name and its version make automatic detection very easy. The method isImportable(URI corpusPath) shall return 1 if the corpus is importable by your importer, 0 if the corpus is not importable or a value between 0 \< X \< 1, if no definitive answer is possible. The default implementation returns '*null*', what means that the method is not overridden and Pepper ignores the module in automatic detection phase.