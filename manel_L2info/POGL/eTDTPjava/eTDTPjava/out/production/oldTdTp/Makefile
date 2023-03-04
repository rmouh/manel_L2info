JUNIT  = junit-4.12.jar
HC     = hamcrest-core-1.3.jar
RUNNER = org.junit.runner.JUnitCore

CLASSPATH = .:$(JUNIT):$(HC)

CLASSES = Field FieldObject Moveable Robot
JAVASRC = $(CLASSES:%=%.java)
TESTSRC = $(CLASSES:%=%Test.java)
TESTCLS = $(CLASSES:%=%Test)

all:
	javac $(JAVASRC)
	javac -cp $(CLASSPATH) $(TESTSRC)
	java -cp $(CLASSPATH) $(RUNNER) $(TESTCLS)

compile:
	javac $(JAVASRC)

compile_test:
	javac -cp $(CLASSPATH) $(JAVASRC)

test:
	java -cp $(CLASSPATH) $(RUNNER) $(JAVASRC)

clean:
	rm -f *.class *~
