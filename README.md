# p6spy-checker
A small class to parse through some p6spy output and isolate the longest running queries

## What does it do?
Basically takes a p6spy output file (these can be extremely large and difficult to read) and parses it. It then does the following:
- isolates the n longest running queries (n is configurable)
- it then gets the actual queries being run for each long-running query and spits that out too.

## How to make it work
There are just a couple of things in the code you need to change.

```
line 25
String p6spyfile = ....
```
* change this to the actual path of the output file.


```
line 17
private static int BATCH = ...
```
* how many longest queries do you want to find, i.e. of the longest queries identified, how far back do tou want to go?

## Prerequisites
* Eclipse
* Java 7 or 8

## Output example:
```
P6Spy checker
-------------
4 of the longest query times:
QueryTime (ms): 1648749
QueryTime (ms): 838461
QueryTime (ms): 706475
QueryTime (ms): 658068
--------------------------------
Date/Time: 26/07/2017 22:46:28 | Query Time (ms): 1648749 | Connection ID: 999 | Category: statement
Query: 
            assoc.id                    as id,
            parentNode.id               as parentNodeId,
            .......
--------------------------------

Date/Time: 26/07/2017 23:19:03 | Query Time (ms): 658068 | Connection ID: 998 | Category: statement
Query: 
            assoc.id                    as id,
            parentNode.id               as parentNodeId,
			....
```

## Note
The code for this checker looks for delimited patterns based on the 'logMessageFormat' found [here](https://p6spy.github.io/p6spy/2.0/configandusage.html)
