# Tutorial: Develop with Eclipse {#useEclipse}

Download Eclipse from https://eclipse.org/

## Import and run your module in Eclipse

**Step 1** Open the Import dialog via: File -> Import... 

**Step 2** Choose the type, how to import your project: Maven -> Existing Maven Projects

**Step 3** Select Directory MY_MODULE_HOME (the pom.xml should now be automatically selected)

**Step 4** Click: Finish

Your module should now be imported into Eclipse.  

**Step 5** Right click on your project and choose Run As -> Maven install 

Debugging your module with Eclipse
---

Debugging in Eclipse is quit easy, Eclipse provides a debug mode, where it is possible to proceed your module step by step, analyze specific variables or methods and so on. With breakpoints you can stop the processing of your module at any line of code and go on step by step. To debug your module in Eclipse which is running in Pepper, you need to link Eclipse to a different VM (the VM which was started by Pepper).  How to do this is explained in the following: 

**Step 1** Set a breakpoint at a place in your code for which you are sure it will be processed. For us a good point to start is the constructor of your module. Go to a line in the constructor and press 'Ctr' + 'Shift' + 'B'. A blue point will appear next to the line.  

**Step 2** Open the Debug dialog via: 'Run' -> 'Debug Configurations'

**Step 2** Search for the entry 'Remote Java Application' and right click 'New' 
![](./moduleDevelopers/images/eclipse_debug_new.png)

**Step 3** Set the configurations as follows:

* Choose a name for debug configurations for instance '**my-module-debug**'
* In 'Connection Type' choose 'Standard (Socket Listen)'
* In 'Connection Properties' choose the port '**2010**' (this is the port used in pepper-debug)
* Enable 'Allow termination of remote VM''
![](./moduleDevelopers/images/eclipse_debug.png)

**Step 4** Click 'Debug'. Now Eclipse will start the debug mode and will wait until Pepper is started at port '**2010**'.

**Step 5** Go to PEPPER_HOME and start Pepper. Note, that it is important to start Debugging first. Do not forget to point Pepper's dropin path to the target folder of your module and to run 'mvn install'. 
\code
	pepper-debug.bat	  (for Windows)
	./pepper-debug.sh  	  (for Linux)
\endcode

**Step 6** To trigger Pepper accessing your module, use the 'list' command. Pepper will create an instance of your module to display information about your module. This is a simple trick to start your module, but you can also create a workflow including your module to let Pepper invoke all methods of your module. 
\code
	pepper>list
\endcode

**Step 7** Eclipse now stops in the constructor, where you set your breakpoint.
![](./moduleDevelopers/images/eclipse_debug_breakpoint.png)

**Step 8** With the 'Step into' and 'Step over' you can proceed step by step.
![](./moduleDevelopers/images/eclipse_debug_panel.png)

If you are not family with Debugging in Eclipse, take a look at this tutorial: http://www.vogella.com/tutorials/EclipseDebugging/
