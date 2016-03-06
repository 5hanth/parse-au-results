#!/bin/env sh
pattern=211512104
echo "REG.NO,NAME," $(echo $@ | xargs -n1 | paste -s -d',')
for reg_no in $(ls $pattern*); do \
  echo $( grep -A 1 $pattern $reg_no | \
            cut -d'>' -f 4 | \
            cut -d'<' -f 1 | \
            paste -s -d"," - ) "," \
       $( for subject in $@; do \
            echo $(grep -A 1 $subject $reg_no | \
                      tail -1 | \
                      cut -d'>' -f 3 | \
                      cut -d'<' -f 1) ","; \
          done);
done;

# cd fetched-html

# Theory
# sh ../parse.sh CS2401 CS2402 CS2403 IT2024 IT2032 MG2452

# With Labs
# sh ../parse.sh CS2401 CS2402 CS2403 IT2024 IT2032 MG2452 CS2405 CS2406 > /tmp/parsed.csv

# import to libre calc
