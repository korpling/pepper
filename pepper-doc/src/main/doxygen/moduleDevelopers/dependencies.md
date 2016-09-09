# Using third-party dependencies {#dependencies}

## Compile time

Third-party dependencies that are available from a public Maven repository are added to the module via Maven's dependency mechanism.
Simply add the dependency to your module's `pom.xml`:
```
    <dependencies>
      <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.8.2</version>
      </dependency>
    </dependencies>
```
and - if necessary - also add the repository to your pom 
```
    <repositories>
      <repository>
        <id>great-public-repo</id>
        <url>http://server/repo</url>
      </repository>
    </repositories>
```
Now the 3rd party library is available resolvable for maven and is used to compile the project. But you also need to make it accessible for the Pepper platform during the runtime.

## Runtime 

Since Pepper is build upon the OSGi framework, it is necessary to register the library in OSGi. To do so, there are two ways. 
1. When the 3rd party library is already an OSGi bundle, you can add it to the Pepper plugins folder and run Pepper. The library is started automatically. When you provide your module via a public maven repository and let Pepper install it automatically, Pepper sancs the pom file and will also install such 3rd party libraries
1. When the 3rd party library is not already an OSGi bundle, you can let maven package it into your module's jar file. Therefore you need to add the override the maven-bundle-plugin configuration in your pom.xml:
```
...
<plugin>
	<groupId>org.apache.felix</groupId>
	<artifactId>maven-bundle-plugin</artifactId>
	<version>${maven-bundle-plugin.version}</version>
	<extensions>true</extensions>
	<executions>
		<execution>
			<phase>process-classes</phase>
			<goals>
				<goal>bundle</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<manifestLocation>${META-INF}</manifestLocation>
		<source>${java.version}</source>
		<target>${java.version}</target>
		<instructions>
			<Bundle-Name>${project.artifactId}</Bundle-Name>
			<Bundle-SymbolicName>${project.groupId}.${project.artifactId};singleton:=true</Bundle-SymbolicName>
			<Bundle-Version>${project.version}</Bundle-Version>
			<Bundle-RequiredExecutionEnvironment>JavaSE-${java.version}</Bundle-RequiredExecutionEnvironment>
			<Service-Component>${allServiceComponents}</Service-Component>
			<Embed-Dependency>3RD_PARTY_ARTIFACT_ID</Embed-Dependency>
			<Bundle-ClassPath>.,{maven-dependencies} </Bundle-ClassPath>
			<Include-Resource> {maven-resources}, {maven-dependencies},
				LICENSE, NOTICE</Include-Resource>
			<_exportcontents>
				3RD_PARTY_ARTIFACT_ID
			</_exportcontents>
		</instructions>
	</configuration>
</plugin>
...
```

### Using JRebel

Simply adding the dependencies to `pom.xml` will, however, **not** make them automatically available in the local version that you might be testing with the help of JRebel or the way described at \subpage useEclipse.

In order to be able to use the dependency, go to the command line and copy all dependencies to a specific subfolder:

    mvn dependency:copy-dependencies
    
Check that your `/target` folder now has a sub-folder `dependency`, and that it contains the respective dependency JAR. Now you can simply add this dependency folder to `pepper.dropin.paths=` in the Pepper properties file, and start using the dependency for debugging.