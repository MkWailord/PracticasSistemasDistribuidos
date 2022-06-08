JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  PruebaPoligono.java \
		  Rectangulo.java \
		  Coordenada.java \
		  PoligonoIrregS.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	java PruebaPoligono
