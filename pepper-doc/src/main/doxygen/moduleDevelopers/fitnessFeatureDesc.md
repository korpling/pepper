# Fitness Check {#fitness}

The fitness check ensures, that a module fits into the Pepper platform. It checks a set of features describing whether a module implements all fields and methods which should be implemented. Some of the features are required (health features) and some are optional (fitness features). When the module does not pass at least one of the health features, the overall module fitness is set to *critical*. When all health features are passed and at least one fitness is missed, the overall module fitness is *healthy*. When the module passes all features, the overall status is *fit*.

To run the fitness check for an installed module run:
```
pepper>fitness
``` 

## Health Features

The health features are required features and ensure that a module is runnable inside Pepper.

### Feature 1


## Fitness features

The fitness features are optional and must not necessarily be passed to run a module in Pepper. But they ensure, that the module implements a kind of a standard and supports information which are helpful to the user.
