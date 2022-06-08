public class Coordenada implements java.io.Serializable{
    private double x, y, magnitud;


    public Coordenada() {

    }

    public Coordenada(double x, double y) {

        this.x = x;

        this.y = y;

        setMagnitud();

    }

    //Metodo getter de x

    public double abcisa() {
        return x;
    }


    //Metodo getter de y

    public double ordenada() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
        setMagnitud();



    }

    public void setY(double y) {
        this.y = y;
        setMagnitud();


    }

    public double getMagnitud() {
        return magnitud;
    }

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )

    @Override

    public String toString() {

        return ", Coor : [" + x + "," + y + "]"+ " Magn : "+magnitud;

    }

    private void setMagnitud(){
        magnitud = Math.sqrt((x * x) + (y * y));
    }
}
