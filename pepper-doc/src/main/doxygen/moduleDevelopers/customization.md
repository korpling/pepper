Customizing the mapping {#customization}
=======================

When creating a mapping, it is often a matter of choice to map some data this way or another. In such cases it might be clever not to be that strict and allow only one possibility. It could be beneficially to leave this decision to the user. Customizing a mapping will increase the power of a Pepper module enormously, since it can be used for a wider range of purposes without rewriting parts of it. The Pepper framework provides a property system to access such user customizations. Nevertheless, a Pepper module shall not be dependent on user customization. The past showed, that it is very frustrating for a user, when a Pepper module breaks up, because of not specified properties. You should always define a default behavior in case that the user has not specified a property.

Property
--------

A property is just an attribute-value pair, consisting of a name called *property name* and a value called *property value*. Properties can be used for customizing the behavior of a mapping. Such a property must be specified by the user and determined in the Pepper workflow. The Pepper framework will pass all customization properties directly to the instance of the Pepper module.

A customization property in Pepper is represented by an object of type @ref org.corpus_tools.pepper.modules.PepperModuleProperty. A property a minimum contains a name, the value's datatype and a description.
\code
    PepperModuleProperty.create().withName(String).withType(Class<T>).withDescription(String).build(); 
\endcode
You can further initialize a property with a default value and whether it is required.
\code
    PepperModuleProperty.create().withName(String).withType(Class<T>).withDescription(String).withDefaultValue(boolean).isRequired(boolean).build();
\endcode
To register a customization property, you need to add the created object to the registry, which is managed and accessed by the Pepper framework. The registry is realized via a specified @ref org.corpus_tools.pepper.modules.PepperModuleProperty object. To create such an object, first implement a registry class as shown in the following snippet:
\code
    //...
    import org.corpus_tools.pepper.modules.PepperModuleProperties;
    import org.corpus_tools.pepper.modules.PepperModuleProperty;
    import static org.corpus_tools.pepper.modules.PepperModuleProperty.create;
    //...
    public class MyModuleProperties extends PepperModuleProperties {
        //...            
        public MyModuleProperties(){  
           //...
           //1: adding a customization property to registry
           this.addProperty(create().withName(("MyProp").withType(String.class).withDescription("description of MyProp").build());
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
\endcode                    

The snippet shows on position 1 how to create, specify and register a @ref org.corpus_tools.pepper.modules.PepperModuleProperty. The method 'getMyProp()' on position 2 shows the creation of a specialized method to access the property. Such a getter-method would be very helpful to have a simplified access without casting values inside your mapping code. Position 3 shows the method 'checkProperty()', which can be used to check the passed property's value. Since customization properties or more specific their values are entered manually by the user, it might be necessary to check the passed values. The Pepper framework calls this method before the mapping process is started. If a constraint fails the user will be informed immediately.

Last but not least, you need to initialize your property object. The best place for doing this is the constructor of your module. Such an early initialization ensures, that the Pepper framework will use the correct object and will not create a generic @ref org.corpus_tools.pepper.modules.PepperModuleProperties object. Initialize your property object via calling:
\code
    this.setProperties(new MyModuleProperties());
\endcode
You can access the @ref org.corpus_tools.pepper.modules.PepperModuleProperties object at all places in your @ref org.corpus_tools.pepper.modules.PepperModule object or your @ref org.corpus_tools.pepper.modules.PepperMapper object. The following snippet shows how:
\code
    getProperty(String propName);
\endcode