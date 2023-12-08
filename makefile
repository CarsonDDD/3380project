# Define variables
JAVAC=javac
JAVA=java
CLASSPATH=.;sqlite-jdbc-3.39.3.0.jar
SRC=NarutoDatabase/Database.java NarutoDatabase/CommandProcessor.java NarutoDatabase/NarutoDatabase.java NarutoDatabase/Program.java

# This is the default target, which will be built when you run 'make'
all: compile

# This rule tells make how to compile your program
compile:
	$(JAVAC) -g -cp $(CLASSPATH) $(SRC)

# This rule tells make how to run your program
run: compile
	$(JAVA) -cp $(CLASSPATH) NarutoDatabase/Program

# This rule tells make how to clean your directory, removing class files
clean:
	del NarutoDatabase\*.class

# Phony targets are ones that are not associated with files
.PHONY: all compile run clean
