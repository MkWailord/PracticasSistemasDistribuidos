public class Triangulo{
	private Coordenada superior, inferiorIzq, inferiorDer;

	public Triangulo(){
		superior = new Coordenada(5,10);
		inferiorIzq = new Coordenada(0,0);
		inferiorDer = new Coordenada(10,0);
	}

	public Triangulo(double superiorX, double superiorY, double inferiorIzqX, double inferiorIzqY, double inferiorDerX, double inferiorDerY){
		superior = new Coordenada(superiorX, superiorY);
		inferiorIzq = new Coordenada(inferiorIzqX, inferiorIzqY);
		inferiorDer = new Coordenada(inferiorDerX, inferiorDerY);
	}

	public Triangulo(Coordenada sup, Coordenada infIzq, Coordenada infDer){
		superior = sup;
		inferiorIzq = infIzq;
		inferiorDer = infDer;
	}

	public Coordenada superior(){ return superior;}

	public Coordenada inferiorIzq(){ return inferiorIzq;}

	public Coordenada inferiorDer(){ return inferiorDer;}

	@Override
	public String toString(){
		return "Superior: "+superior+" Inferior Izquierda: "+inferiorIzq+" Inferior Derecha: "+inferiorDer +"\n";
	}

}
