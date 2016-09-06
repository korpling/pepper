Using third-party dependencies {#dependencies}
==============================

Third-party dependencies that are available from a public Maven repository are added to the module via Maven's dependency mechanism.

Simply add the dependency (and - if necessary - the repository) to your module's `pom.xml`:

    <dependencies>
      <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.8.2</version>
      </dependency>
    </dependencies>

    <repositories>
      <repository>
        <id>great-public-repo</id>
        <url>http://server/repo</url>
      </repository>
    </repositories>
    
Now the third-party dependency will be bundled with the module when assembling.

Using dependencies during development
-------------------------------------

Simply adding the dependencies to `pom.xml` will, however, **not** make them automatically available in the local version that you might be testing with the help of JRebel or the way described at \subpage useEclipse.

In order to be able to use the dependency, go to the command line and copy all dependencies to a specific subfolder:

    mvn dependency:copy-dependencies
    
Check that your `/target` folder now has a sub-folder `dependency`, and that it contains the respective dependency JAR. Now you can simply add this dependency folder to `pepper.dropin.paths=` in the Pepper properties file, and start using the dependency for debugging.