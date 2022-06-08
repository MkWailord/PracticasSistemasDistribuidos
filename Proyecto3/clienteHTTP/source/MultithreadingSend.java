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

class MultithreadingSend extends Thread{
	private List<String> palabras = null;
	private String worker;
	public MultithreadingSend(List<String> palabras, String worker){
		this.palabras = palabras;
		this.worker = worker;
	}

	public void run()
	{
		try{
			for(String palabra : palabras)
				mandarPalabra(palabra, worker);
		}catch(Exception e){
			System.out.println("Excepcion");
		}
	}

        public static void mandarPalabra(String palabra, String worker)
        {
		Aggregator aggregator = new Aggregator();
		System.out.println("Enviando palabra '" + palabra + "' a la direccion " + worker);
		List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(worker), Arrays.asList(palabra));
		String result = results.get(0);
		//Extraemos el body de la lista results (El body deberia contener unicamente las ocurrencias)
		String ocurrencias = result.substring(result.lastIndexOf("Response Body:")+16, result.length()-1);
		System.out.println("		Palabra '"+ palabra +"' encontrada "+ ocurrencias +" veces");
    	}
}
