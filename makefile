build:
	javac -cp src/sqlite-jdbc.jar src/App.java src/SQLInterface.java src/Controller.java
run:
	cd src && java -cp .;sqlite-jdbc.jar App && cd ..
clean:
	del src/test.db
