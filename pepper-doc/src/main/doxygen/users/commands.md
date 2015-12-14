Commands in Pepper console{#commands}
======

Pepper offers you a set of commands (1) to create and run a conversion or and (2) to get informations about Pepper or it's modules.
You can enter the commands listed in the following table into the Pepper console:

\code
pepper>
\endcode

+---------------------+------+----------------+------------------------------------------------------------------------+
| command             | short| parameters     | description                                                            |
+---------------------+------+----------------+------------------------------------------------------------------------+
| version             | v    | no arguments   | prints pepper version                                                  |
| dependencies        | deps | Bundle id or   | displays all dependencies of the specified component                   |
|                     |      | GROUP_ID::     |                                                                        |
|                     |      | ARTIFACT_ID::  |                                                                        |
|                     |      | VERSION::      |                                                                        |
|                     |      | MAVEN_REPOSITOR|                                                                        |
|                     |      | Y_URL or       |                                                                        |
|                     |      | plugin names   |                                                                        |
|                     |      | split by space;|                                                                        |
|                     |      |  parameter all |                                                                        |
|                     |      | prints         |                                                                        |
|                     |      | dependencies   |                                                                        |
|                     |      | for all plugins|                                                                        |
| update              | u    | module name or | Updates the pepper module(s). Parameter "all" updates all modules      |
|                     |      | location       | listed in modules.xml.                                                 |
| list                | l    |  --            | A table with information about all available Pepper modules.           |
| list                | l    | module name    | A table with information about the passed Pepper module.               |
| conf                | co   |  --            | Shows the configuration for current Pepper instance.                   |
| help                | h    |  --            | Prints this help.                                                      |
| self-test           | st   |  --            | Tests if the Pepper framework is in runnable mode or if any problems   |
|                     |      |                | are detected, either in Pepper itself or in any registered Pepper      |
|                     |      |                | module.                                                                |
| exit                | e    |  --            | Exits Pepper.                                                          |
| convert             | c    | workflow file  | If no workflow file is passed, Pepper opens a conversion wizard,       |
|                     |      |                | which help you through the definition of a workflow proecess. If a '   |
|                     |      |                | worklow file' is passed, this file is load and the described workflow  |
|                     |      |                | will be started.                                                       |
| osgi                | o    |  --            | Opens a console to access the underlying OSGi environment, if OSGi is  |
|                     |      |                | used.                                                                  |
| install_start       | is   | module path    | Installs the Pepper module located at 'module path' and starts it.     |
| remove              | re   | bundle name    | Removes all Pepper modules, being contained in the budnle with name '  |
|                     |      |                | bundle name'. To find out the bundle name open the osgi console and    |
|                     |      |                | list all bundles.                                                      |
| start-osgi          | start|  --            | Starts the OSGi environment (the plugin system of Pepper).             |
| stop-osgi           | stop |  --            | Stops the OSGi environment (the plugin system of Pepper).              |
| clean               | cl   |  --            | Cleans the current Pepper instance and especially removes the OSGi     |
|                     |      |                | workspace.                                                             |
| debug               | d    |  --            | Switches on/off the debug output.                                      |
| repeat              | r    |  --            | Repeats the last command.                                              |
+---------------------+------+----------------+------------------------------------------------------------------------+

Commands in Pepper OSGi console
=======

Pepper comes with a very simplified OSGi console. With it's help, you can check whether some modules or dependent bundles have been loaded or not. To access the OSGi console use the command 'osgi' in the Pepper console:

\code
pepper>osgi
pepper/osgi>
\endcode

Here you can use the following commands:

+----------------------+-------+-----------------+------------------------------------------------------------------------+
| command              | short | parameters      | description                                                            |
+----------------------+-------+-----------------+------------------------------------------------------------------------+
| ss                   | ss    |  --             | display installed bundles (short status)                               |
| list                 | ls    |  --             | lists all components                                                   |
| install              | i     | bundle path     | install and optionally start bundle from the given URL                 |
| uninstall            | un    | bundle id       | uninstall the specified bundle                                         |
| start                | s     | bundle id       | start the specified bundle                                             |
| stop                 | stop  | bundle id       | stop the specified bundle                                              |
| install_start        | is    | bundle path     | installs the bundle located at 'bundle path' and starts it             |
| update               | up    | module-path     | updates the Pepper module, which was installed from 'module path'.     |
| remove               | re    | bundle name     | removes the bundle 'bundle name' from the OSGi context and from plugin folder. |
| help                 | h     |  --             | Prints this help.                                                      |
| exit                 | e     |  --             | exits Pepper                                                           |
+----------------------+-------+-----------------+------------------------------------------------------------------------+
