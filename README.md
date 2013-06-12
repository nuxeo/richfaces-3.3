richfaces
=========

Fork of the richfaces 3.3.1.GA sources for patches needed by Nuxeo. Original sources can be downloaded here:

http://anonsvn.jboss.org/repos/richfaces/tags/3.3.1.GA

## How to build

See https://community.jboss.org/wiki/HowToBuildRichFacesSnapshotsManually33x.

Don't forget to configure Maven to use the JBoss Repository. See https://community.jboss.org/wiki/MavenGettingStarted-Users.

Don't build from the root directory, you'll also build useless stuff (demo, samples) of which dependencies are hardly found. 

The jars we might want to build are:
-  **richfaces-api-3.3.1.GA.jar**

  run `mvn clean install` in $ROOT/framework/api
-  **richfaces-impl-3.3.1.GA.jar**

  run `mvn clean install` in $ROOT/framework/impl
-  **richfaces-ui-3.3.1.GA.jar**

  run `mvn clean install -Dmaven.test.skip=true` in $ROOT/ui. Some functional tests might not pass anymore depending on your environment.

## Patch guidelines

The *.pack.js files (such as framework.pack.js or ui.pack.js) are created at build time, they aggregate other existing js files.

If you want to patch *.pack.js file you must therefore do it in the relevant js source file.

## Deploy

To deploy the patched jar to nexus, follow instructions given on the intranet for third-party libraries.
