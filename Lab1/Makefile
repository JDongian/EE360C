JFLAGS = -g
JC = javac
OUTPUT = class

build:
	mkdir -p class; $(JC) -d $(OUTPUT) src/*.java

clean:
	rm -f class/*.class

%.class : %.java
	$(JAVAC) $<

.PHONY: format
format:
	astyle -n -r --style=java *.java
