#!/bin/bash

PIPEFILE="pipefile"
ENTRY_PID=""

[[ -p "$PIPEFILE" ]] || mkfifo "$PIPEFILE"
/docker-entrypoint.sh postgres > "$PIPEFILE" & ENTRY_PID="$!"

while read LINE ; do
    echo $LINE

    if [[ "$LINE" == *"ready for start up"* ]] ; then
        sleep 3
        break
    fi
done < "$PIPEFILE"
echo "set up pg, finish"
kill -s TERM "$ENTRY_PID" && wait "$ENTRY_PID"