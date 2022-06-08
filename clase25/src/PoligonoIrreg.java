public class PoligonoIrreg implements java.io.Serializable{
    private Coordenada[] vertices;
    private  int  indicador;



    public PoligonoIrreg(int nCoordenadas){
        vertices = new Coordenada[nCoordenadas];
        this.indicador = 0;
    }

    public Coordenada[] getVertices() {
        return vertices;
    }

    public void setVertices(Coordenada[] vertices) {
        this.vertices = vertices;
    }

    public boolean anadeVertice(Coordenada coordenada){
        if(indicador>=vertices.length){
            return false;
        }
        vertices[indicador] =  coordenada;
        indicador++;
        return true;
    }


//Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (Coordenada vertice : vertices) {
            resultado.append(vertice.toString());
        }
        return resultado.toString();
    }
}
