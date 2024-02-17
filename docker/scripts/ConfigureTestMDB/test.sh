

set -x
wsadminp="/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin"
USER_WAS="wsadmin"
PSW_WAS="$(cat /tmp/PASSWORD)"
$wsadminp/wsadmin.sh -conntype SOAP -port 8881 -host localhost -user ${USER_WAS} -password ${PSW_WAS}
