#!/bin/sh
mvn scm:checkout -DconnectionType=developerConnection
cd target/checkout
mvn release:branch -P docs -DdryRun=true -DautoVersionSubmodules=true \
      -DbranchName=$1 -DupdateBranchVersions=true \
      -DupdateVersionsToSnapshot=false \
      -DupdateWorkingCopyVersions=false \
      -DtagBase=https://svn.jboss.org/repos/richfaces/tags <<-EOF
$1
EOF
find . -name pom.xml.branch -execdir mv pom.xml.branch pom.xml ';'
#mvn install
#cd ..
#svn copy checkout  https://svn.jboss.org/repos/richfaces/tags/$1 -m " create tag for a release $1"