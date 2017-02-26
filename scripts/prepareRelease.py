#!/usr/bin/python
import sys
from lib.moduleProcessor import *
from lib.git import *
from lib.modulePathes import *

def printHello():
	print "+--------------------------------------------------------+"
	print "|                                                        |"
	print "|          Prepare Releases of Pepper modules            |"
	print "|                                                        |"
	print "+--------------------------------------------------------+"


def printWhatWillHappen():
	print("The following modules will be prepared for release: ")
	for modulePath in modulePathes:
		print("\t"+modulePath)

if __name__ == "__main__":
	printHello()
	modulePathes= findModulePathes(DEFAULT_MODULE_PATH, sys.argv)	
	exitWhenNoModulePathesGiven(modulePathes)
	printWhatWillHappen()
	OnModules(modulePathes).do([reset, checkoutDevelop, update, checkoutMaster, update, mergeMasterInDevelop])
