# This script is aimed to check all Pepper modules (all subfolders of the current folder) whether there have been changes since the last release and the current HEAD
import subprocess
import os
from os import walk
from subprocess import CalledProcessError
from tabulate import tabulate
modules = []
for (dirpath, dirnames, filenames) in walk("."):
	modules.extend(dirnames)
	break

#table to store whether a module has changed
table=[]

for module in modules:
	os.chdir("./"+module)
	testLog=""
	try:
		lastTag =""
		#update local git repo
		subprocess.check_output(["git", "pull"])
		#find out last tag version
		lastTag = subprocess.check_output(["git", "describe", "--abbrev=0", "--tags"]).replace("\n","")
		#store all changes between last tag and current head
		log = subprocess.check_output(["git", "shortlog", lastTag+"..HEAD"])
		log= log.replace("korpling-Server (1):\n      [maven-release-plugin] prepare for next development iteration","");
		testLog= log.replace("\n","")
		if testLog=="":
			table.append([module, "--", lastTag]);
		else: 
			table.append([module, "X", lastTag]);
	except CalledProcessError as e:
		print "#"+e.output+"#"		
		if "fatal: Keine Namen gefunden, kann nichts beschreiben." in e.output:
			print "HALLO"
		table.append([module, "F", lastTag]);
	os.chdir("../")
print tabulate(table, headers=['module-name', 'has changed', 'last tag'], tablefmt='orgtbl')

