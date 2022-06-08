import java.util.*;
import java.lang.*;
import java.io.*;

public class Coordenada{
    private double x, y;
    private int magnitud;

    public Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
	this.magnitud = (int)Math.sqrt((x*x)+(y*y));
    }

    //Metodo getter de x
    public double abcisa( ) { return x; }

    //Metodo getter de y
    public double ordenada( ) { return y; }

    public int getMagnitud( ) { return magnitud; }

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
    @Override
    public String toString( ) {
        return "[" + x + "," + y + "] Magnitud: " + magnitud;
    }
}

class Sortbymagnitud implements Comparator<Coordenada>{
	public int compare(Coordenada c1, Coordenada c2)
	{
		return c1.getMagnitud() - c2.getMagnitud();
	}
}
