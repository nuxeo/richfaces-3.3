#!/bin/sh
echo ' <resource>' >> $1
echo -n '  <name>' >> $1
echo -n $2 >>$1
echo  '</name>' >> $1
echo -n '  <path>org/richfaces/renderkit/html/' >> $1
echo -n $2 >>$1
echo  '</path>' >> $1
echo ' </resource>' >> $1
