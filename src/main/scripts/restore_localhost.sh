#!/bin/sh

if [ $# -ne 1 ]; then
         echo 1>&2 Usage: $0 filnamn
         exit 127
fi

psql -h localhost -U dromelvan -d dromelvan < "../data/d11/postgres/$1"


exit 0
