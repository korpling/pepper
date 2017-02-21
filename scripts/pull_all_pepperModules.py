# This script is aimed to check all Pepper modules (all subfolders of the current folder) whether there have been changes since the last release and the current HEAD
import subprocess
import os
import zipfile
from os import walk
from subprocess import CalledProcessError

isCleanUp= False
isBuild= False
isAssembling= False

def buildPepper(pepperPath):
	print("Pepper:")
	os.chdir(pepperPath)
	#buildAndAssemble(pepperPath)
	pepperPluginPath=os.path.join(pepperPath+"/pepper-lib/target/distribution/pepper-lib-3.1.0-SNAPSHOT/org.corpus-tools.pepper-lib_3.1.0-SNAPSHOT/plugins/");
	return pepperPluginPath

def buildAndAssemble(pepperPath):
	devnull = open(os.devnull, 'w')
	if isCleanUp:
		print("\tcleaning...")			
		subprocess.call(['mvn', 'clean'], stdout=devnull)
	if isBuild:
		print("\tbuilding...")			
		subprocess.call(['mvn', 'install'], stdout=devnull)
	if isAssembling:
		print("\tassembling...")			
		subprocess.call(['mvn', 'assembly:single'], stdout=devnull)

def listModulePathes(moduleBasePath):
	modulePathes= []
	for moduleFolder in os.listdir(moduleBasePath):
		modulePath= os.path.join(moduleBasePath, moduleFolder)
		if os.path.isdir(modulePath) == True:
			modulePathes.append(os.path.abspath(modulePath))
	return modulePathes

def processModules(modulePathes, pepperPluginPath):
	for modulePath in modulePathes:
		os.chdir(modulePath)
		print(os.path.split(modulePath)[1]+"("+modulePath+"):")
		updateModule(modulePath)
		buildAndAssemble(modulePath)
		copyModule(modulePath, pepperPluginPath)

def updateModule(modulePath):
	print("\tupdating...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['git', 'pull'], stdout=devnull)
	
def copyModule(modulePath, pepperAssemblyPath):
	print("\tcopying...")
	zipFile= findAssemblyFile(modulePath)
	extractAssembly(zipFile, pepperAssemblyPath)

def findAssemblyFile(modulePath):
	modulePluginPath= os.path.join(os.path.abspath(modulePath), "target", "distribution")
	for file in os.listdir(modulePluginPath):
		assemblyFile= modulePluginPath= os.path.join(modulePluginPath, file)
		if assemblyFile.endswith(".zip"):
			return assemblyFile	 

def extractAssembly(zippedFile, to):
	zfile = zipfile.ZipFile(zippedFile)
	zfile.extractall(to)
	
	
moduleBasePath="../test"
pepperPath="../pepper"
 
pepperPluginPath= buildPepper(pepperPath)
modulePathes= listModulePathes(moduleBasePath)
processModules(modulePathes, pepperPluginPath)
