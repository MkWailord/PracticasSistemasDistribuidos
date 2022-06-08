import java.util.Random;
public class PruebaPoligono {

    public static void main (String[] args) {
	long time = System.nanoTime();
	Coordenada vertice;
        PoligonoIrreg pol = new PoligonoIrreg(10);
	//System.out.println(pol);
	for (int i=0; i< 11; i++){
		vertice = new Coordenada(Math.random()*100, Math.random()*100);
		pol.anadeVertice(vertice);
	}
	System.out.println(pol);
	pol.ordenaVertices();
	System.out.println(pol);
	System.out.println("Tiempo: " + time + " nanosegundos");
    }
}
