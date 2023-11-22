# Define the compiler and compiler flags
JAVAC = javac
JAVA = java
SRC_DIR = NarutoDatabase/src
BIN_DIR = bin

# Define classpath including the bin directory for dependencies
CLASSPATH = $(BIN_DIR)

# Define the entry point of your programs
SCRAPER_MAIN = NarutoDatabase.Scraper.NarutoScraper
PROCESSOR_MAIN = NarutoDatabase.Processor.Program

# Find all Java files in the src directory
SOURCES := $(wildcard $(SRC_DIR)/NarutoDatabase/*/*.java)

# Default build target
all: build

# Compile the source files into .class files
build:
	$(JAVAC) -d $(BIN_DIR) -cp $(CLASSPATH) $(SOURCES)

# Run the scraper program
run-scraper: build
	$(JAVA) -cp $(CLASSPATH) $(SCRAPER_MAIN)

# Run the processor program
run-processor: build
	$(JAVA) -cp $(CLASSPATH) $(PROCESSOR_MAIN)

# Clean the build
clean:
	$(RM) $(BIN_DIR)/*.class
	$(RM) $(BIN_DIR)/NarutoDatabase/*/*.class
