Pepper Documentation
===================

Build
-----

To generate this documentation, you need to install Doxygen (http://www.doxygen.org).
The "doxygen" executable must be in the system path.

Then you can execute
```
mvn clean package -P doxygen
```
to compile everything. The result will be located in the "target/doxygen" folder.

