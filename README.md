richfaces
=========

Fork of the richfaces 3.3.1.GA sources for patches needed by Nuxeo. Original sources can be downloaded here:

http://anonsvn.jboss.org/repos/richfaces/tags/3.3.1.GA

## How to build

See https://community.jboss.org/wiki/HowToBuildRichFacesSnapshotsManually33x.

Don't forget to configure Maven to use the JBoss Repository. See https://community.jboss.org/wiki/MavenGettingStarted-Users.

## Guidelines

The *.pack.js files (such as framework.pack.js or ui.pack.js) are created at build time, they aggregate other existing js files.

If you want to patch *.pack.js file you must therefore do it in the relevant js source file.

## Deploy

To deploy the patched jar to nexus, follow instructions given on the intranet for third-party libraries.
