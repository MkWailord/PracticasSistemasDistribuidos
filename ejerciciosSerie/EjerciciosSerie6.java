import java.util.Random;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;
//import com.google.common.primitives.Bytes;
public class EjerciciosSerie6{
	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		StringBuilder cadenota = new StringBuilder(n*4);
		int i,j,index=0,cont=0;
		long time = System.nanoTime();
		String ipn = "IPN ";
		for(j=0; j<n; j++){

			String palabra = "";
			for(i=0; i<=2; i++){
				Random r = new Random();
				char c = (char)(r.nextInt(26) + 'a');
				palabra = palabra + c;
			}
			palabra = palabra.toUpperCase();
			palabra= palabra + " ";
			cadenota.append(palabra);
		}
		while(index != -1){
			index = cadenota.indexOf(ipn, index);
			if(index != -1){
				cont++;
				index++;
			}
		}
		//System.out.println(cadenota.toString());
		System.out.println("Ocurrencias encontradas: "+ cont);
		System.out.println("Tiempo de ejecucion: "+time+" nanosegundos");
	}
}
