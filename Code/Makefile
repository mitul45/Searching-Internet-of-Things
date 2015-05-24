#COMPONENT=MediatorAppC
BUILD_EXTRA_DEPS += GUI.class
CLEAN_EXTRA = *.class PullMsg.java AggregationMsg.java

GUI.class: $(wildcard *.java) PullMsg.java
	javac -target 1.4 -source 1.4 *.java
GUI.class: $(wildcard *.java) AggregationMsg.java
	javac -target 1.4 -source 1.4 *.java

PullMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=PullMsg Header.h PullMsg -o $@

AggregationMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=AggregationMsg Header.h AggregationMsg -o $@


include $(MAKERULES)

