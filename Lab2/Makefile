JFLAGS = -g
JC = javac
OUTPUT = class
INPUT = ../test/4.in

build:
	mkdir -p class; $(JC) -d $(OUTPUT) src/*.java;

tex:
	cd writeup; texi2pdf report.tex; 

clean:
	rm -f class/*.class *.zip writeup/report.pdf

run:
	cd class && clear && java Driver ../testInputs/SmallInputGraph.in -t 5 10 15 20 25

runl:
	cd class && clear && java Driver ../testInputs/LargeInputGraph.in -t 5 10 15 20 25

output:
	zip jid295_dong_joshua src/Program1.java src/Tenant.java src/Landlord.java src/Apartment.java writeup/report.pdf

%.class : %.java
	$(JAVAC) $<

.PHONY: format
format:
	astyle -n -r --style=java *.java
