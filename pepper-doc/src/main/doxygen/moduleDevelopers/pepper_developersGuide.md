Foreword
========

The intention of this document is first to give a guide to the user of how to use the here mentioned pepper modules and how to utilize a mapping performed by them. Second this document shall give a closer view in the details of such a mapping in a declarative way, to give the user a chance to understand how specific data will be mapped by the presented pepper modules.

Subprojects
===========

- Pepper is a service infrastructure, therefore more parallel jobs are possible, so one conversion is a job, job is identified by unique id - a job consists of steps (identifying module, carrying customization of step and im-or export path if neccessary) - DocumentController (abstraction for SDocument, knows the status global and of each module, can send SDcoument to sleep or awake) - ModuleController and DocumentBus between controllers - some (very simple) modules are part of pepper distribution like SaltXML importer and DoNothing to make sure, something is there
