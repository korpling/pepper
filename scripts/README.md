# Needful Scripts
This is a bunch of needful scripts to process a bunch of modules (for instance all modules). 

## Update all modules

Updates all modules via git.

```
python updateModules.py( PATH_TO_MODULE)+
``` 
If no modules are passed, it is assumed, that modules are located in "../pepperModules".

## Release all modules

Release all modules via git. This means:

1. branch 'develop'is checked out and updated
1. branch 'master' is checked out and updated
1. 'develop' is merged in 'master'
1. 'master' is pushed 

```
python releaseModules.py( PATH_TO_MODULE)+
``` 
If no modules are passed, it is assumed, that modules are located in "../pepperModules".

## Change pepper-parent of all modules

The pepper-parent version of all modules will be changed.

```
python changeParentversion.py NEW_VERSION( PATH_TO_MODULE)+
``` 
If no modules are passed, it is assumed, that modules are located in "../pepperModules".
