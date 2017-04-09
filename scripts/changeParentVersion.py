#!/usr/bin/python
# This script replaces the version in a pom.xml file of the parent project. It is used to update the pepper-parent project version.
import sys
import os
from xml.etree.ElementTree import dump
import xml.etree.ElementTree as ET
from lib.moduleProcessor import *
from lib.modulePathes import *

def printHello():
	print "+--------------------------------------------------------+"
	print "|                                                        |"
	print "| Changes the version of the dependency to pepper-parent |"
	print "|                                                        |"
	print "+--------------------------------------------------------+"

def printWhatWillHappen():
	print("The pepper-parent version of the following modules will be changed: ")
	for modulePath in modulePathes:
		print("\t"+modulePath)

def exitWhenNoVersionAndNoModulesGiven(args):
	if len(args)< 2:
		print "Please pass a new version for Pepper: python updatePepperVersion.py NEW_VERSION (PATH)?"
		exit(-1)

def changeVersion(modulePath):
	#read all files in passed path argument or current path
	for root, subFolders, files in os.walk(modulePath):
		if POM_XML in files:
			currentfile= os.path.join(root, POM_XML)
			print 'parsing ',currentfile,' ...'
			#set namespace for ELementTree, otherwise the namespace is printed in output prefixing every element 
			ET.register_namespace('', "http://maven.apache.org/POM/4.0.0")
			mydoc = ET.parse(currentfile)
			namespace = "{http://maven.apache.org/POM/4.0.0}"
			#find version
			for e in mydoc.findall('{0}parent/{0}version'.format(namespace)):
				e.text=newVersion
			mydoc.write(currentfile)

#name of file to read and write
POM_XML="pom.xml"

if __name__ == "__main__":
	printHello()
	exitWhenNoVersionAndNoModulesGiven(sys.argv)
	modulePathes= findModulePathes(DEFAULT_MODULE_PATH, sys.argv[1:])	
	exitWhenNoModulePathesGiven(modulePathes)
	printWhatWillHappen()
	
	newVersion= sys.argv[1]
	print 'newVersion: ',newVersion

	OnModules(modulePathes).do([changeVersion])
