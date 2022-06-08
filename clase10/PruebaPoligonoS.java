import java.util.Random;
public class PruebaPoligono {

    public static void main (String[] args) {
	long time = System.nanoTime();
	Coordenada vertice;
        PoligonoIrregS pol = new PoligonoIrregS(10000000);
	for(int i=0; i<10000000; i++){
		double x = (double) (new Random().nextInt(1000 + 1000) - 1000);
                double y = (double) (new Random().nextInt(1000 + 1000) - 1000);
                vertice = new Coordenada(x,y);
		pol.setVertice(vertice, i);
	}
	//System.out.println(pol);
	System.out.println("Tiempo: " + time + " nanosegundos");
    }
}
