# Needful Scripts
This is a bunch of needful scripts to process a bunch of modules (for instance all modules). 

## Update all modules

Updates all modules via git.

```
python updateModules.py( PATH_TO_MODULE)+
``` 
If no modules are passed, it is assumed, that modules are located in "../pepperModules".

## release all modules

Release all modules via git. This means:
* branch 'develop'is checked out and updated
* branch 'master' is checked out and updated
* 'develop' is merged in 'master'
* 'master' is pushed 

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