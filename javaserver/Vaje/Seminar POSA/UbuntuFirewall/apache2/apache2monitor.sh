#!/bin/sh^M
# Script Name: apache2mon^M
# Author Name: Gregor Grajzar 2007^M
# Date: Jul 11 T 2007^M
# Description: ^M
# This is basic monitor, that monitors if Apache2 is running^M
# if by any chance or DOS attack the service is down:^M
# if so, it will (re)start Apache2 service!!!^M
^M
^M
^M
#^M
# test whether the Apache process is running^M
# returns process ID number^M
#^M
^M
PIDS=`pidof apache2`^M
^M
#varianta 1: testiram, ce sem dobil argument^M
#^M
#if [ -z "$PIDS"  ]     ^M
# then  ^M
#       echo "OK"^M
#       exit^M
#^M
#else^M
#       ^M
#       PIDS=`/etc/init.d/apache restart`^M
#       ^M
#fi^M
#^M
^M
# vatianta 2: sam testiram dolzino^M
if [$PIDS == "" ]       ^M
then ^M
^M
                PIDS=`/etc/init.d/apache restart`^M
^M
fi^M
^M
# varianta 3: bzv^M
# if ["$PIDS" == "" ];  then^M
#       PIDS=`/etc/init.d/apache restart`^M
# fi^M
^M
^M
^M
#^M
# simple end of our test^M
# should be installed as a deamon, that runs every 1-15 min^M
#^M
^M
^M
#^M
# Installation notice:^M
#^M
exit

