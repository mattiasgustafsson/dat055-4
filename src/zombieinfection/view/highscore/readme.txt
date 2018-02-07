In order to run the PoC highscore application you must first go through the 
following steps to import the Postgresql jdbc driver:

Right click on your project
Choose property
Choose Java build path
Choose Add external JARS.. and select the location to the JDBC driver.

After that, you want to execute the file "create_table.sql" in order to create the highscores table
in the database.

If the software compiles without problems after this step, then you should have full access to your database and
be ready to go!