JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Aggregator.java \
		  WebClient.java \
		  Application.java \
		  WebServer.java \
		  SerializationUtils.java \
		  Demo.java \
		  PoligonoIrreg.java \
		  Coordenada.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	java Application

