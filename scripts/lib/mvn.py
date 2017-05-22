#!/usr/bin/python
import os
import subprocess
from subprocess import CalledProcessError

def clean(modulePath):
	print("clean "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['mvn', 'clean'], stdout=devnull)

def build(modulePath):
	print("build "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['mvn', 'install'], stdout=devnull)