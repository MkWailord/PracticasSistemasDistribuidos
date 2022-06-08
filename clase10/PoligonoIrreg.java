import java.util.*;

public class PoligonoIrreg{
	private List<Coordenada> vertices = new ArrayList<Coordenada>();
	//private Coordenada[] vertices;
	private int nVertices;

	public PoligonoIrreg(int n){
		nVertices = n;
		/*vertices = new Coordenada[nVertices];
		for(int i=0; i<nVertices; i++){
			double x = (double) (new Random().nextInt(1000 + 1000) - 1000);
			double y = (double) (new Random().nextInt(1000 + 1000) - 1000);
			vertices[i] = new Coordenada(x,y);
		}*/
	}

	public void anadeVertice(Coordenada vertice){
		vertices.add(vertice);
	}

	public void ordenaVertices(){
		Collections.sort(vertices, new Sortbymagnitud());
	}

	@Override
	public String toString( ) {
		String aRetornar ="";
		/*for(int i =0; i<nVertices; i++){
			aRetornar = aRetornar + "Coordenada " + (i+1) + " : " + vertices[i] + "\n";
		}*/
		int i = 1;
		for(Coordenada crd: vertices){
			aRetornar = aRetornar + "Coordenada " + i + ": " + crd + "\n";
			i++;
		}
		return aRetornar;
	}
}

