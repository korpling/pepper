#!/usr/bin/env python
import sys
from lib.moduleProcessor import *
from lib.git import *
from lib.modulePathes import *

def printHello():
	print "+--------------------------------------------------------+"
	print "|                                                        |"
	print "|                 Updates Pepper modules                 |"
	print "|                                                        |"
	print "+--------------------------------------------------------+"


def printWhatWillHappen():
	print("The following modules will be updated: ")
	for modulePath in modulePathes:
		print("\t"+modulePath)

if __name__ == "__main__":
	printHello()
	modulePathes= findModulePathes(DEFAULT_MODULE_PATH, sys.argv)	
	exitWhenNoModulePathesGiven(modulePathes)
	printWhatWillHappen()
	OnModules(modulePathes).do([update])
