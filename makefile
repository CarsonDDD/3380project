.PHONY := build


NarutoScraper.class: NarutoScraper.java 
	javac NarutoScraper.java

build: NarutoScraper.class 

run: build
	java -cp ".;json-simple-1.1" NarutoScraper