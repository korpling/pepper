#!/usr/bin/python
import os

DEFAULT_MODULE_PATH="./pepperModules/"

def findModulePathes(defaultPath=None, modulePathNames= None):
	if len(modulePathNames)== 1:
		return findModulesInPath(defaultPath)
	else:	
		return modulePathNames[1:]

def findModulesInPath(path=None):
	if path:
		modulePathes= []
		for moduleFolder in os.listdir(path):
			modulePath= os.path.join(path, moduleFolder)
			if os.path.isdir(modulePath) == True:
				modulePathes.append(os.path.abspath(modulePath))
		return modulePathes

def exitWhenNoModulePathesGiven(modulePathes=None):
	if len(modulePathes)==0:
		print "Please pass a list of modules, the default path '"+DEFAULT_MODULE_PATH+"' was empty."
		sys.exit(-1)
