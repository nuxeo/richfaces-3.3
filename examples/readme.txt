1.  Introduction
This folder contains real world applications developed using RichFaces library. The primary purpose of the applications is to demonstrate that RichFaces is a library designed to rapidly develop high quality web applications with rich user interface.

2. Building and Running Examples

2.1 Requirements

- Maven 2.0.9 or later
- JBoss Application Server (4.2.3.GA, 5.0.x.GA)

You can import the application into your favorite IDE.  We recommend using Eclipse IDE and JBoss Tools as they are specially tailored to fully support the technologies used in the applications. 

2.2 Building Application

To build an application you need to run

mvn clean install 

in the  folder that contains the application you are intending to build.

If you would like to build a application for Eclipse IDE you need to run

mvn clean install eclipse:clean eclipse:eclipse

When you see the BUILD SUCCESSFUL message you can run the built application on the Jboss Application server either manually or from IDE.
Let's take the Photo Album application as an example.
To run the application you need to deploy it on the server by copying the photoalbum/sources/ear/target/photoalbum-ear-1.0-SNAPSHOT.ear file to the JBOSS_HOME/server/default/deploy folder.  Launch the run.bat file from JBOSS_HOME/bin/ directory to start the server.
To explore the application start your favorite browser and point it to http://localhost:8080/photoalbum.
In order to explore, run and deploy the application in Eclipse IDE build it with the

 mvn clean install eclipse:clean eclipse:eclipse eclipse:eclipse command 
 
and import the project to the IDE.  More details you can find  in the JBoss Server Manager Reference Guide (http://download.jboss.org/jbosstools/nightly-docs/en/as/html/index.html)

 