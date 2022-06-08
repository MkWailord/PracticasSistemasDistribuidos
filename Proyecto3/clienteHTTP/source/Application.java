/*	INSTITUTO 	POLITECNICO		 NACIONAL
*		ESCUELA SUPERIOR DE COMPUTO
*
*	  DESARROLLO DE SISTEMAS DISTRIBUIDOS
*	   PROF. CORONILLA CONTRERAS UKRANIO
*
*		PROYECTO 3
*		ALUMNO: CASAS CARBAJAL JOSÉ MAURICIO
*		GRUPO: 4CM11
*/
import java.util.*;

public class Application {

    public static void main(String[] args) {
	String direccionWorker1 = "http://" + args[0] + "/searchbiblia";
	String direccionWorker2 = "http://" + args[1] + "/searchbiblia";
	String direccionWorker3 = "http://" + args[2] + "/searchbiblia";
	List<String> palabras = new ArrayList<String>();
	List<String> lista1 = new ArrayList<String>();
	List<String> lista2 = new ArrayList<String>();
	List<String> lista3 = new ArrayList<String>();

	System.out.println("Direcciones de Workers ingresadas: ");
	System.out.println(direccionWorker1);
	System.out.println(direccionWorker2);
	System.out.println(direccionWorker3);

	//int cantPalabras = args.length - 3;
	for(int i = 3; i<args.length; i++)
		palabras.add(args[i]);
	int i = 1;
	for(String palabra: palabras){
	    if(i == 1)
		lista1.add(palabra);
	    else if(i == 2)
		lista2.add(palabra);
	    else if(i ==3)
		lista3.add(palabra);
	    i++;
	    if(i == 4)
		i = 1;
	}

	System.out.println("\nSe han ingresado "+ palabras.size() +" palabras. ");
	System.out.println("La lista de palabras para el worker 1 contiene "+ lista1.size() +" palabras");
	System.out.println("La lista de palabras para el worker 2 contiene "+ lista2.size() +" palabras");
	System.out.println("La lista de palabras para el worker 3 contiene "+ lista3.size() +" palabras\n");

	MultithreadingSend worker1 = new MultithreadingSend(lista1, direccionWorker1);
	MultithreadingSend worker2 = new MultithreadingSend(lista2, direccionWorker2);
	MultithreadingSend worker3 = new MultithreadingSend(lista3, direccionWorker3);

	worker1.start();
	worker2.start();
	worker3.start();
        //String task = "dios";
	//mandarPalabra(task, WORKER_ADDRESS_1);
    }


}
