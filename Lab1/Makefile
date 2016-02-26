JFLAGS = -g
JC = javac
OUTPUT = class
INPUT = ../test/4.in

build:
	mkdir -p class; $(JC) -d $(OUTPUT) src/*.java

clean:
	rm -f class/*.class *.zip

rung:
	cd class; java Driver -g $(INPUT)

runb:
	cd class; java Driver -b $(INPUT)

output:
	zip jid295_dong_joshua src/Program1.java src/Tenant.java src/Landlord.java src/Apartment.java writeup/report.pdf

%.class : %.java
	$(JAVAC) $<

.PHONY: format
format:
	astyle -n -r --style=java *.java
