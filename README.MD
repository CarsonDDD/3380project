# Welcome, to the NarutoDB.xyz scraper

This program cycles through all the endpoints of the [naruto database](https://narutodb.xyz/), converts the endpoint entities (and our sub entities) into Objects in code. Doing this allows us to have full control on how we manipulate the data when constructing our own tables. This approach was needed because the API endpoints where far from the entities we wanted and closer to wiki data.

## What am I looking at?

The scraper/populator is located inside `3380project/`. The code and packages are located inside `3380project/src/`.

The scraper was built using Intelij.
The main packages are:

- The `Logger`: Controlls our output to specific files. It is helpful when testing to write the sql in multiple files to allow us to visually check all the inserts.
- `Entites`: Represent the entities/tables OUR database supports, these tables are the entites from the original database, however also entities we broken up into there own tables when normalizing.
- `Entities.Interfaces`: allows to easily create the relations between entities.

Outside the packages are:

- `NarutoScraper.java`: entry point for program and calls below classes to handle logic
- `JsonToOOP.java`: scans all the json endpoints and converts them into java objects for us to use.
- `OOPToSQL`: Converts our java objects to `sql` inserts for the direct entities themselves, and also the relations.

note: The creation of the tables we have done manually, as we were constantly editing them and since we only need them once, we can copy paste them to the top of our output file. This scrapers purpose is to only generate the inserts and relations NOT the creation of the tables.

## How to run?

Since the scraper was created using Intelij, we have built a jar for you to run and test.

The jar is located at: `3380project/NarutoScraper.java`

**Windows:** `java -jar NarutoScraper.jar`

**Mac/Unix:** `java -jar NarutoScraper.jar`

Running this command should output 2 `.txt` files. One contain debug informaton (`debug.txt`). The other: `all.txt` containing all of our databased inserts. When creating our `.db` file, we take `all.txt`, add our tables, rename to the file to `Naruto.sql` (yes lots of these steps can be automated), then run (on windows) `Get-Content Naruto.sql | sqlite3 Naruto.db` to get our database file.