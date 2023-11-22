# Define the compiler and compiler flags
BIN_DIR = bin

# Define classpath for the processor
PROCESSOR_CLASSPATH = $(BIN_DIR)
DEBUG_CLASSPATH = $(BIN_DIR)
SCRAPER_CLASSPATH = $(BIN_DIR);NarutoDatabase/json-simple-1.1.jar

# Define the entry point of your programs
SCRAPER_MAIN = NarutoDatabase.Scraper.NarutoScraper
PROCESSOR_MAIN = NarutoDatabase.Processor.Program

# Find all Java files in the src directory
SCRAPER_SOURCES := $(wildcard NarutoDatabase/src/NarutoDatabase/Scraper/*.java)
PROCESSOR_SOURCES := $(wildcard NarutoDatabase/src/NarutoDatabase/Processor/*.java)
DEBUG_SOURCES := $(wildcard NarutoDatabase/src/NarutoDatabase/Logger/*.java)

# Default build. Builds debug twice.....
all: build-scraper build-processor

build-scraper: build-debug
	javac -d $(BIN_DIR) -cp $(SCRAPER_CLASSPATH) $(SCRAPER_SOURCES)

build-processor: build-debug
	javac -d $(BIN_DIR) -cp $(PROCESSOR_CLASSPATH) $(PROCESSOR_SOURCES)


build-debug:
	javac -d $(BIN_DIR) -cp $(DEBUG_CLASSPATH) $(DEBUG_SOURCES)


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
	del /Q $(BIN_DIR)\NarutoDatabase\Logger\*.class