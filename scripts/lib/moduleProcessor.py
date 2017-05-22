#!/usr/bin/python
# This class implements the strategy patttern to process a list of 
# functions on a list of modules.
import os
class OnModules:
	modules=[]
	
	def __init__(self, modules=None):
		if modules:
			self.modules=modules
	
	#walks through all modules and switches current path to modules path, 
	#then calls each function and finally returns to former path
	def do(self, functions=None):
		if self.modules and functions:
			for modulePath in self.modules:
				formerPath= os.getcwd()
				os.chdir(modulePath)
				print("--------- "+modulePath+" --------")
				for function in functions:
					function(modulePath)
				os.chdir(formerPath)
