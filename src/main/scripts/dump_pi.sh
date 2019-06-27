#!/bin/sh
if [ $# -ne 1 ]; then
         echo 1>&2 Usage: $0 filnamn
         exit 127
fi

pg_dump -c -E UTF8 -h 192.168.1.4 -U dromelvan dromelvan > "../data/d11/postgres/$1"


exit 0
