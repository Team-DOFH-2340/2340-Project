compile:
	javac -cp src/sqlite-jdbc.jar src/Test.java src/SQLInterface.java src/Controller.java
run:
	cd src && java -cp .;sqlite-jdbc.jar Test && cd ..
clean:
	del src/test.db
