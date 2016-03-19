Customizing the mapping {#customization}
=======================

When creating a mapping, it is often a matter of choice to map some data this way or another. In such cases it might be clever not to be that strict and allow only one possiblity. It could be beneficially to leave this decision to the user. Customizing a mapping will increase the power of a Pepper module enormously, since it can be used for a wider range of purposes without rewriting parts of it. The Pepper framework provides a property system to access such user customizations. Nevertheless, a Pepper module shall not be dependent on user customization. The past showed, that it is very frustrating for a user, when a Pepper module breaks up, because of not specifified properties. You should always define a default behavior in case that the user has not specified a property.

Property
--------

A property is just an attribute-value pair, consisting of a name called *property name* and a value called *property value*. Properties can be used for customizing the behavior of a mapping. Such a property must be specified by the user and determined in the Pepper workflow. The Pepper framework will pass all customization properties directly to the instance of the Pepper module.

> **Note**
>
> In the current version of Pepper, one has to specify a property file by its location in the Pepper workflow file (.pepperParams) in the attribute @specialParams inside the \<importerParams\>, \<exporterParams\> or \<moduleParams\> element. In the next versions this will change to a possibility for adding properties directly to the Pepper workflow file.

One customization property in Pepper is represented with an object of type `PepperModuleProperty`. Such an object consists of the property's name, its datatype, a short description and a flag specifying whether this property is optional or mandatory as shown in the following snippet:

    PepperModuleProperty(String name, Class<T> clazz, String description, 
                         Boolean required)

Even a default value could be passed:

    PepperModuleProperty(String name, Class<T> clazz, String description,
                         T defaultValue, Boolean required)                

To register a customization property, you need to add the created object to registry object, which is managed and accessed by the Pepper framework. The registry is realized via a specified `PepperModuleProperties` object. To create such an object, first implement a registry class as shown in the following snippet:

    //...
    import de.hu_berlin.german.korpling.saltnpepper.pepper
        .pepperModules.PepperModuleProperties;
    import de.hu_berlin.german.korpling.saltnpepper.pepper
        .pepperModules.PepperModuleProperty;
    //...
    public class MyModuleProperties extends PepperModuleProperties {
        //...            
        public MyModuleProperties(){  
           //...
           //1: adding a customization property to registry
           this.addProperty(new PepperModuleProperty<String>
               ("MyProp", String.class, "description of MyProp", true));
           //...
        }
        //2: return value of customization property
        public String getMyProp(){
            return((String)this.getProperty("MyProp").getValue());
        }
        
        //3: check constraints on customization property 
        public boolean checkProperty(PepperModuleProperty<?> prop){
       //calls the check of constraints in parent, 
       //for instance if a required value is set
       super.checkProperty(prop);
       if ("myProp".equals(prop.getName())){
            File file= (File)prop.getValue();
            //throws an exception, in case that the file does not exist
            if (!file.exists()){
                throw new PepperModuleException("The file "+
                "set to property 'myProp' does not exist.");
            }
       }
       return(true);
    }
                    

The snippet shows on position 1 how to create, specify and register a `PepperModuleProperty`. The method 'getMyProp()' on position 2 shows the creation of a specialized method to access the property. Such a getter-method would be very helpful in your code to have a fast access without casting values in your mapping code. Position 3 shows the method 'checkProperty()', which can be used to check the passed property's value. Since customization properties or more specificly their values are entered manually by the user, it might be necessary to check the passed values. The Pepper framework calls this method before the mapping process is started. If a constraint fails the user will be informed immediately.

Last but not least, you need to initialize your property object. The best place for doing this is the constructor of your module. Such an early initialization ensures, that the Pepper framework will use the correct object and will not create a general `PepperModuleProperties` object. Initialize your property object via calling:

    this.setProperties(new MyModuleProperties());

You can access the `PepperModuleProperties` object at all places in your `PepperModule` object or your `PepperMapper` object. The following snippet shows how:

    this.getProperty(String propName);