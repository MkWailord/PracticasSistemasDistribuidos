import java.util.Random;

public class PoligonoIrregS{
	private Coordenada[] vertices;
	private int nVertices;

	public PoligonoIrregS(int n){
		nVertices = n;
		vertices = new Coordenada[nVertices];
	}

	@Override
	public String toString( ) {
		String aRetornar ="";
		for(int i =0; i<nVertices; i++){
			aRetornar = aRetornar + "Coordenada " + (i+1) + " : " + vertices[i] + "\n";
		}
		return aRetornar;
	}

	public void setVertice(Coordenada vert, int n){
		this.vertices[n] = vert;
	}
}

