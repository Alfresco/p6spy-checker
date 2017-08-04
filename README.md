# p6spy-checker
A small class to parse through some p6spy output and isolate the longest running queries

## What does it do?
Basically takes a p6spy output file (these can be extremely large and difficult to read) and parses it. It then does the following:
- isolates the n longest running queries (n is configurable)
- it then gets the actual queries being run for each long-running query and spits that out too.

## How to make it work
There are just a couple of things in the code you need to change.

```line 25
String p6spyfile = ....
```
* change this to the actual path of the output file.


```line 17
private static int BATCH = ...
```
* how many longest queries do you want to find, i.e. of the longest queries identified, how far back do tou want to go?


