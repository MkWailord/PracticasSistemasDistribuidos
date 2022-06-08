#Banderas
JFLAGS = -d
FLAGS = -cf

#Compiladores
JC = javac
JR = jar

#Otras Variables
RUTASOURCE = ./source/*.java
RUTACLASSES = .
APP = Application
APPJR = Application.jar
APPCL = Application.class
AGG = Aggregator.class
MTS = MultithreadingSend.class
NET = networking/WebClient.class
CLASSES = source/WebClient.java \
	  source/Application.java \
	  source/MultithreadingSend.java \
	  source/Aggregator.java

#Sufijos
.SUFFIXES: .java .class .jar

.java.class:
	$(JC) $(JFLAGS) $(RUTACLASSES) $(RUTASOURCE)

.class.jar:
	$(JR) $(FLAGS) $(APPJR) $(APPCL) $(AGG) $(MTS) $(NET)

#Comandos

default: $(CLASSES:.java=.class) $(APPCL:.class=.jar)

clean:
	$(RM) *.class
	$(RM) *.jar
	rm -r networking

run:
	java -cp $(APPJR) $(APP)
