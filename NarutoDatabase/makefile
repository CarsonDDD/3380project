# Makefile for NarutoDatabase project on Windows

# Variables
JAVAC = javac
JAVA = java
JAR = src/json-simple-1.1.jar
CLASSPATH = .;$(JAR);bin


# Find Java source files
LOGGER_SRCS := $(wildcard $(SRC_DIR)/Logger/*.java)
PROCESSOR_SRCS := $(wildcard $(SRC_DIR)/Processor/*.java)
SCRAPER_SRCS := $(wildcard $(SRC_DIR)/Scraper/*.java $(SRC_DIR)/Scraper/Entities/*.java $(SRC_DIR)/Scraper/Entities/Interfaces/*.java)

# Default rule
all: scraper processor

# Rule to compile the Scraper
scraper: $(LOGGER_SRCS) $(SCRAPER_SRCS)
	@if not exist "$(BIN_DIR)" mkdir $(BIN_DIR)
	$(JAVAC) -cp $(CLASSPATH) -d $(BIN_DIR) $^

# Rule to compile the Processor
processor: $(LOGGER_SRCS) $(PROCESSOR_SRCS)
	@if not exist "$(BIN_DIR)" mkdir $(BIN_DIR)
	$(JAVAC) -cp $(CLASSPATH) -d $(BIN_DIR) $^

# Rule to clean the project
clean:
	@if exist "$(BIN_DIR)" rd /s /q $(BIN_DIR)

# Rule to run the Scraper
run-scraper:
	$(JAVA) -cp $(BIN_DIR);$(CLASSPATH) Scraper.NarutoScraper

# Rule to run the Processor
run-processor:
	$(JAVA) -cp $(BIN_DIR);$(CLASSPATH) Processor.Program
