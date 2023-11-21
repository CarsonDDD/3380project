.PHONY := build


NarutoScraper.class: NarutoScraper.java 
	javac NarutoScraper.java

build: NarutoScraper.class 

run: build
	java -cp .:org.json.jar Scraper.NarutoScraper
