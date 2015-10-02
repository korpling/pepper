import glob, os, fileinput, sys
os.chdir(".")

def rename(file):
    print("rename "+file)
    for line in fileinput.input(file, inplace = 1): 
	#global 
	line= line.replace('SaltFactory.eINSTANCE', 'SaltFactory')
	line= line.replace('new BasicEList<', 'new ArrayList<') # must be before next row
	line= line.replace('EList<', 'List<')
	line= line.replace('STYPE_NAME', 'SALT_TYPE')
	
	#salt core
	line= line.replace('setSName', 'setName')
	line= line.replace('getSName', 'getName')
	line= line.replace('setSId', 'setId')
	line= line.replace('getSId', 'getId')
        line= line.replace('getOutEdges(', 'getOutRelations(')
        line= line.replace('getInEdges(', 'getInRelations(')
	line= line.replace('addSNode', 'addNode')
	line= line.replace('addSRelation', 'addRelation')
	line= line.replace('getSNodes', 'getNodes')
	line= line.replace('getSNode', 'getNode')
	line= line.replace('getSRelations', 'getRelations')
	line= line.replace('getSRelation', 'getRelation')
	line= line.replace('Edge', 'Relation')
	line= line.replace('edge', 'relation')
	line= line.replace('createSAnnotation(', 'createAnnotation(')
	line= line.replace('createSMetaAnnotation(', 'createMetaAnnotation(')
	line= line.replace('createSProcessingAnnotation(', 'createProcessingAnnotation(')
	line= line.replace('createSFeature(', 'createFeature(')
	line= line.replace('getSAnnotations()', 'getAnnotations()')
	line= line.replace('getSMetaAnnotations()', 'getMetaAnnotations()')
	line= line.replace('getSProcessingAnnotations()', 'getProcessingAnnotations()')
	line= line.replace('getSFeature()', 'getFeature()')
	line= line.replace('getSTarget()', 'getTarget()')
	line= line.replace('setSTarget(', 'setTarget(')
	line= line.replace('getSSource()', 'getSource()')
	line= line.replace('setSSource(', 'setSource(')
	line= line.replace('addSType(', 'setType(')
	line= line.replace('getSNS()', 'getNamespace()')
	line= line.replace('setSNS(', 'setNamespace(')
	line= line.replace('getSValue()', 'getValue()')
	line= line.replace('setSValue(', 'setValue(')

	#corpus structure
        line= line.replace('addSSubCorpus(', 'addSubCorpus(')
	line= line.replace('getSDocument', 'getDocument')
	line= line.replace('getSCorpusGraphs', 'getCorpusGraphs')
	line= line.replace('setSCorpus', 'setCorpus')
	line= line.replace('setSDocument', 'setDocument')
	line= line.replace('getSCorpusDocumentRelations', 'getCorpusDocumentRelations')
	line= line.replace('addSSubCorpus', 'addSubCorpus')
	line= line.replace('addSDocument', 'addDocument')
	line= line.replace('getSCorpus', 'getCorpus')
	line= line.replace('getSRootCorpus', 'getRoots')

	#document structure
	line= line.replace('SAudioDSRelation', 'SMedialRelation')
	line= line.replace('SAudioDataSource', 'SMedialDS')
	line= line.replace('createSAudioDS(', 'createSMedialDS(')
	line= line.replace('SDataSourceSequence', 'DataSourceSequence')
	line= line.replace('SaltFactory.createDataSourceSequence();', 'new DataSourceSequence();')
	line= line.replace('setSSequentialDS(', 'setDataSource(')
	line= line.replace('getSSequentialDS()', 'getDataSource()')

	line= line.replace('setSStart(', 'setStart(')
	line= line.replace('getSStart()', 'getStart()')
	line= line.replace('setSEnd(', 'setEnd(')
	line= line.replace('getSEnd()', 'getEnd()')
	line= line.replace('setSTextualDS(', 'setTarget(')
	line= line.replace('getSSpan()', 'getSource()')
	line= line.replace('setSSpan(', 'setSource(')
	line= line.replace('getSStructuredTarget()', 'getTarget()')
	line= line.replace('setSStructuredTarget(', 'setTarget(')
	line= line.replace('getSStructuredSource()', 'getSource()')
	line= line.replace('setSStructuredSource(', 'setSource(')
	line= line.replace('getSStructure()', 'getTarget()')
	line= line.replace('setSStructure(', 'setTarget(')


	line= line.replace('getSDocumentGraph()', 'getGraph()')
	line= line.replace('setSDocumentGraph(', 'setGraph(')
	line= line.replace('getSTextualDSs()', 'getTextualDSs()')
	line= line.replace('getSText()', 'getText()')
	line= line.replace('setSText(', 'setText(')
	line= line.replace('setSTimeline(', 'setTimeline(')
	line= line.replace('getSTimeline()', 'getTimeline()')
	line= line.replace('getSPointsOfTime()', 'getEnd()')
	line= line.replace('getSTokens()', 'getTokens()')
	line= line.replace('getSSpans()', 'getSpans()')
	line= line.replace('getSStructures()', 'getStructures()')
	line= line.replace('getSTextualRelations()', 'getTextualRelations()')
	line= line.replace('getSSpanningRelations()', 'getSpanningRelations()')
	line= line.replace('getSPointingRelations()', 'getPointingRelations()')
	line= line.replace('getSDominanceRelations()', 'getDominanceRelations()')
	line= line.replace('getSOrderRelations()', 'getOrderRelations()')
	line= line.replace('getSTimelineRelations()', 'getTimelineRelations()')
	line= line.replace('sortSTokenByText()', 'sortTokenByText()')
	line= line.replace('getSortedSTokenByText()', 'getSortedTokenByText()')

	line= line.replace('getRootsBySRelation(', 'getRootsByRelation(')
	line= line.replace('getNodeBySequence', 'getNodesBySequence')

	#in tests
	line= line.replace('this.getFixture()', 'getFixture()')

	#exceptions
	line= line.replace('SaltElementNotContainedInGraphException', 'SaltElementNotInGraphException')

        sys.stdout.write(line)


if len(sys.argv)>1:
    sourcePath= sys.argv[1]
else:
    sourcePath="" 

if os.path.isdir(sourcePath):
    for file in glob.glob(sourcePath+"*.java"):
        rename(file)
else:
   rename(sourcePath)


