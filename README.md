richfaces
=========

Fork of the richfaces 3.3.1.GA sources for patches needed by Nuxeo. Original sources can be downloaded here:

http://anonsvn.jboss.org/repos/richfaces/tags/3.3.1.GA

## How to build

Run `mvn clean install -DskipTests=true`. Some functional tests might not pass anymore depending on your environment.

The resulting jars:
-  **richfaces-api-3.3.1.GA.jar** (We assume the api won't change)

-  **richfaces-impl-3.3.1.GA-NX8-SNAPSHOT.jar**

-  **richfaces-ui-3.3.1.GA-NX8-SNAPSHOT.jar**
  

## Patch guidelines

The *.pack.js files (such as framework.pack.js or ui.pack.js) are created at build time, they aggregate other existing js files.

If you want to patch *.pack.js file you must therefore do it in the relevant js source file.

