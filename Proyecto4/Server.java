
public class Server{
String direccion;
boolean vivo;
	public Server(){
		this.vivo = true;
	}

	public void setStatus(boolean vivo){
		this.vivo = vivo;
	}

	public void setDireccion(String direccion){
		this.direccion = "http://" + direccion + "/";
	}

	public boolean getStatus(){
		return vivo;
	}

	public String getDireccion(){
		return direccion;
	}

	@Override
	public String toString(){
		return direccion;
	}
}
