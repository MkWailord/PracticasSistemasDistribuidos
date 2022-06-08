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

import networking.WebClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aggregator {
    private WebClient webClient;
	//Se instancia el objeto WebClient
    public Aggregator() {
        this.webClient = new WebClient();
    }
	//Metodo que recibe la lista de direcciones de trabajadores y la lista de tareas a desarrollar.
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
		//Para comunicación asincrona se hace uso de la clase CompletableFuture para permitir la ejecución de codigo bloqueante
		// En el arreglo futures se almacena las respuestas futuras de los servidores
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()];
		//Se obtienen las direcciones de los trabajadores y las tareas en las listas
        for (int i = 0; i < workersAddresses.size(); i++) {
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);

            byte[] requestPayload = task.getBytes(); //Las tareas se almacenan en formato de byte
            futures[i] = webClient.sendTask(workerAddress, requestPayload); //Se envian las tareas asincronas y se asocian en el arreglo futures
        }
		//Lista de resultados
        List<String> results = Stream.of(futures).map(CompletableFuture::join).collect(Collectors.toList());

        return results;	//Se retorna la lista de resultados.
    }
}
