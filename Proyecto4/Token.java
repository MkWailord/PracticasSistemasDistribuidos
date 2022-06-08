import java.util.*;
import java.io.*;
public class Token implements Serializable{
	private List<Long> serversTime = new ArrayList<Long>();
	private List<String> direcciones = new ArrayList<String>();
	private long nanoTimeProm;

	public Token(){
		long time = 0;
		this.serversTime.add(time);
		this.serversTime.add(time);
		this.serversTime.add(time);
		this.serversTime.add(time);
		//this.direcciones.add("http://"+dir1+"/");
		//this.direcciones.add("http://"+dir2+"/");
		//this.direcciones.add("http://"+dir3+"/");
		//this.direcciones.add("http://"+dir4+"/");
		this.nanoTimeProm = 0;
	}

	public long putTime(int pos, long time){
		long sum = 0;
		serversTime.set(pos, time);
		for(long tm: serversTime)
			sum = sum + tm;
		nanoTimeProm = sum / 4;

		return nanoTimeProm;
	}

	public long getTime(int pos){
		return serversTime.get(pos);
	}

	@Override
	public String toString(){
		int i = 0;
		String respuesta = "";
		for(long time: serversTime){
			respuesta = respuesta + "Server "+(i+1)+" Tiempo: "+ time +"\n";
			i++;
		}
		respuesta = respuesta + "Tiempo promedio: "+nanoTimeProm +"\n";
		return respuesta;
	}


}
