# ToPython Compiler
This is a small compiler built using Antlr that translates source code written in a source language to a destination language. 

The compiler takes the source code as input, translates to Python and executes the translated code. The compiler is built using Antlr and Java.


### Features

- Translates source code written in a source language to a destination language. See Input folder in this repo.
- Produces a parse tree for the source code.
- Executes the translated code.


### Requirements

To run this compiler, you need to have the following software installed on your machine:

- [Java Development Kit (JDK) version 19 or higher](https://openjdk.org/)
- [Antlr 4 (Java runtime) version 4.11.1 or higher](https://www.antlr.org/download.html)
- [Jython standalone jar version 2.7.3 or higher](https://www.jython.org/download.html)

### Improvements

- Improve the Translation process to use a counter  for indentation. At the moment the indentation is partially correct for certain use cases.
- Error Handling is already sufficient for the current use cases. However, it can be improved to handle more use cases.