# Fitness check {#dev_fitnessCheck}

The fitness check ensures, that a module fits into the Pepper platform. It checks a set of features describing whether a module implements all fields and methods which should be implemented. Some of the features are required (health features) and some are optional (fitness features). When the module does not pass at least one of the health features, the overall module fitness is set to *critical*. When all health features are passed and at least one fitness is missed, the overall module fitness is *healthy*. When the module passes all features, the overall status is *fit*.

To run the fitness check for an installed module run:
\endcode
pepper>fitness
\endcode

This functionality is implemented in the class @ref org.corpus_tools.pepper.core.ModuleFitnessChecker.

A description of the particular features can be found here: @ref fitness. 