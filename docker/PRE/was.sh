/opt/WebSphere/AppServer85/bin/wsadmin.sh -tracefile "/tmp/trace.log" -p /opt/WebSphere/AppServer85/properties/wsadmin.properties -javaoption "-Dpython.cachedir.skip=false" -user decadmin -password decdec -c "print AdminConfig.show()"