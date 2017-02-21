#!/usr/bin/python
import os
import subprocess
from subprocess import CalledProcessError

BRANCH_DEVELOP="develop"
BRANCH_MASTER="master"

def checkoutDevelop(modulePath):
	checkoutBranch(modulePath, BRANCH_DEVELOP)

def checkoutMaster(modulePath):
	checkoutBranch(modulePath, BRANCH_MASTER)
	
def checkoutBranch(modulePath, branch):
	print("checking out '"+branch+"' branch "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['git', 'checkout', branch], stdout=devnull)

def update(modulePath):
	print("updating "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['git', 'pull'], stdout=devnull)

def mergeMasterInDevelop(modulePath):
	print("merging "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['git', 'merge', BRANCH_DEVELOP], stdout=devnull)
	
def reset(modulePath):
	print("resetting "+modulePath+"...")
	devnull = open(os.devnull, 'w')
	subprocess.call(['git', 'reset', '--hard'], stdout=devnull)
