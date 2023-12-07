yes() {
    set +x
    local count=$1
    for ((i = 0; i < count; i++)); do
        echo "yes"
    done
    set -x
}

set -x
wsadminp="/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin"
USER_WAS="wsadmin"
PSW_WAS="$(cat /tmp/PASSWORD)"

yes 5 | $wsadminp/wsadmin.sh -lang jython -f "$(pwd)/createJMSFactory.py" -user ${USER_WAS} -password ${PSW_WAS}