import java.util.Random;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
//import com.google.common.primitives.Bytes;
public class EjerciciosSerie5{
	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		byte[] cadenota = new byte[n*5];  
		byte[] bpalabra;
		int i,j,cont=0;
		long time = System.nanoTime();
		String ipn = "IPN ";
		byte[] bipn = ipn.getBytes();
		for(j=0; j<n; j++){

			String palabra = "";
			for(i=0; i<=2; i++){
				Random r = new Random();
				char c = (char)(r.nextInt(26) + 'a');
				palabra = palabra + c;
			}
			palabra = palabra.toUpperCase();
			palabra= palabra + " ";
			bpalabra = palabra.getBytes();
			System.arraycopy(bpalabra, 0, cadenota, j*4, bpalabra.length);
		}
		for(j=0;j<n;j++){
			byte[] b = Arrays.copyOfRange(cadenota, j*4, ((j*4)+4));
			if(Arrays.equals(b, bipn))
				cont++;
			//System.out.println(j);
		}
		//String s = new String (cadenota, StandardCharsets.UTF_8);
		//System.out.println(s);
		System.out.println("Ocurrencias encontradas: "+ cont);
		System.out.println("Tiempo de ejecucion: "+time+ " nanosegundos");
	}
}

