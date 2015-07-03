#!/usr/bin/python
# This script replaces the version in a pom.xml file of the parent project. It is used to update the pepper-parent project version.

import sys
import os
from xml.etree.ElementTree import dump
import xml.etree.ElementTree as ET

print "+--------------------------------------------------------+"
print "|                                                        |"
print "| Updates the version of the dependency to pepper-parent |"
print "|                                                        |"
print "+--------------------------------------------------------+"

# extract arguments for path and version
if len(sys.argv)< 2:
	print "Please pass a new version for Pepper: python updatePepperVersion.py NEW_VERSION (PATH)?"

newVersion= sys.argv[1]
rootPath="./"
if len(sys.argv)>2:
	rootPath= sys.argv[2]
print 'newVersion: ',newVersion
print 'path:       ',rootPath

#name of file to read and write
pomXML="pom.xml"

#read all files in passed path argument or current path
for root, subFolders, files in os.walk(rootPath):
	if pomXML in files:
		currentfile= os.path.join(root, pomXML)
		print 'parsing ',currentfile,' ...'
		#set namespace for ELementTree, otherwise the namespace is printed in output prefixing every element 
		ET.register_namespace('', "http://maven.apache.org/POM/4.0.0")
		mydoc = ET.parse(currentfile)
		namespace = "{http://maven.apache.org/POM/4.0.0}"
		#find version
		for e in mydoc.findall('{0}parent/{0}version'.format(namespace)):
		    e.text=newVersion
		mydoc.write(currentfile)
