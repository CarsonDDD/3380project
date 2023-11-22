# Define the compiler and compiler flags
BIN_DIR = bin

# Define classpath for the processor
PROCESSOR_CLASSPATH = $(BIN_DIR)

# Define the classpath for the scraper including the JSON library for compilation
SCRAPER_CLASSPATH = $(BIN_DIR);NarutoDatabase/lib/json-simple-1.1.jar

# Define the entry point of your programs
SCRAPER_MAIN = NarutoDatabase.Scraper.NarutoScraper
PROCESSOR_MAIN = NarutoDatabase.Processor.Program

# Find all Java files in the src directory
SCRAPER_SOURCES := $(wildcard NarutoDatabase/src/NarutoDatabase/Scraper/*.java)
PROCESSOR_SOURCES := $(wildcard NarutoDatabase/src/NarutoDatabase/Processor/*.java)

# Default build target
all: build-scraper build-processor

# Compile the scraper source files into .class files
build-scraper:
	javac -d $(BIN_DIR) -cp $(SCRAPER_CLASSPATH) $(SCRAPER_SOURCES)

# Compile the processor source files into .class files
build-processor:
	javac -d $(BIN_DIR) -cp $(PROCESSOR_CLASSPATH) $(PROCESSOR_SOURCES)

# Run the scraper program
run-scraper: build-scraper
	java -cp $(SCRAPER_CLASSPATH) $(SCRAPER_MAIN)

# Run the processor program
run-processor: build-processor
	java -cp $(PROCESSOR_CLASSPATH) $(PROCESSOR_MAIN)

# Clean the build
clean:
	del /Q $(BIN_DIR)\*.class
	del /Q $(BIN_DIR)\NarutoDatabase\Scraper\*.class
	del /Q $(BIN_DIR)\NarutoDatabase\Processor\*.class


