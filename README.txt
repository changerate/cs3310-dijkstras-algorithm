# CS 3310, Design and Analysis of Algorithms
At Cal Poly Pomona, Fall 2025 – **Assignment 3**

## Description
This program primarily finds the optimal paths between nodes in a directed graph. 
  
## Compilation instructions
from the root dir run:
```
javac *.java
```
Then to see results fast just run:
```
java Main.java
```
If you want to run your own matrix file just include it as an argument:
```
java Main.java ./path/inputA
```
If you want to run your own matrix file AND include posts to test right off the bat:
```
java Main.java ./path/inputA 0 1
```
  
  
## Classes
### 1. Floyd's Algorithm
Purpose: This class holds the logic needed to find the optimal paths from each node to every other node.