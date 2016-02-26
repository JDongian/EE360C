JFLAGS = -g
JC = javac
OUTPUT = class
INPUT = ../test/4.in

build:
	mkdir -p class; $(JC) -d $(OUTPUT) src/*.java

clean:
	rm -f class/*.class

rung:
	cd class; java Driver -g $(INPUT)

runb:
	cd class; java Driver -b $(INPUT)

%.class : %.java
	$(JAVAC) $<

.PHONY: format
format:
	astyle -n -r --style=java *.java
