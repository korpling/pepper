#!/usr/bin/python
import os
import sys

DEFAULT_MODULE_PATH="../../pepperModules/"

def findModulePathes(defaultPath=None, modulePathNames= None):
	if len(modulePathNames)== 1:
		return findModulesInPath(defaultPath)
	else:	
		return modulePathNames[1:]

def findModulesInPath(path=None):
	if path:
		absPath= os.path.abspath(path)
		if not os.path.isdir(absPath):
			exitWhenDefaultPathDoesNotExist(absPath)
		modulePathes= []
		for moduleFolder in os.listdir(absPath):
			modulePath= os.path.join(absPath, moduleFolder)
			if os.path.isdir(modulePath) == True:
				modulePathes.append(os.path.abspath(modulePath))
		return modulePathes

def exitWhenDefaultPathDoesNotExist(path):
	print "ERROR: The default path '"+path+"' does not exist."
	sys.exit(-1)

def exitWhenNoModulePathesGiven(modulePathes=None):
	if len(modulePathes)==0:
		print "ERROR: Please pass a list of modules, the default path '"+DEFAULT_MODULE_PATH+"' was empty."
		sys.exit(-1)
