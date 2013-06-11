#find firefox
PATHS=( '/usr/lib/firefox-1.0.4/' '/usr/lib/firefox-1.5.0.10/' '/usr/lib/firefox-1.5.0.12/' '/usr/lib64/firefox-1.5.0.12/' '/usr/lib/firefox-3.0b5/' '/usr/lib64/firefox-3.0b5/' '/usr/lib/firefox-3.0.1/' '/usr/lib64/firefox-3.0.1/' '/usr/lib/firefox/' '/opt/MozillaFirefox/lib/')
for ELEMENT in ${PATHS[@]}
  do	
    if [ -f $ELEMENT/firefox ]
      then
        export FF_BIN=$ELEMENT/firefox
        export LD_LIBRARY_PATH=$ELEMENT
    fi
    if [ -f $ELEMENT/firefox-bin ]
      then
        export FF_BIN=$ELEMENT/firefox-bin
        export LD_LIBRARY_PATH=$ELEMENT
    fi
done