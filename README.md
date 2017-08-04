# p6spy-checker
A small class to parse through some p6spy output and isolate the longest running queries

## What does it do?
Basically takes a p6spy output file (these can be extremely large and difficult to read) and parses it. It then does the following:
** isolates the n longest running queries (n is configurable)
** it then gets the actual queries being run for each long-running query and spits that out too.

