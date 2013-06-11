#!/bin/sh
FULLPATH=`wich $0`
CDIR=`pwd` 
#`dirname $FULLPATH`
RFILE=${CDIR}/resources/resources-config.xml
echo "create Config file $RFILE"
echo '<?xml version="1.0" encoding="UTF-8"?>'> $RFILE
echo '<resource-config>' >> $RFILE
cd ${CDIR}/../resources/org/richfaces/renderkit/html/
find script/tiny_mce -name .svn -prune -o -type f -exec $CDIR/addres.sh $RFILE '{}' ';' 
echo '</resource-config>' >> $RFILE
