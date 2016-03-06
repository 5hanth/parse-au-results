# parse-au-results

A parser for Anna University Nov/Dec 2015 results.

## Installation

0. [Install Leningen](http://leiningen.org/)

## Usage

0. git clone <this-repo-url>
1. cd parse-au-results
3. lein uberjar
4. java -jar target/uberjar/parse-au-results-0.1.0-SNAPSHOT-standalone.jar <reg-no-start> <reg-no-end>
5. *grab some red-bull*
6. hard-code pattern for reg-no in ./parsed.sh
7. sh ./parse.sh <SUB-CODE1> <SUB-CODE2> ... <SUB-CODEN> > parsed.csv
8. libreoffice parsed.csv

## License

© 2016 Shanthakumar

Distributed under the [WTFPL](http://www.wtfpl.net/txt/copying/)
either version 2.0 or any later version.
