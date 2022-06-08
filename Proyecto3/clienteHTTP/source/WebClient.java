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
package networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    private HttpClient client;
	//Se crea un objeto HttpClient llamado client, protocolo http 1.1
    public WebClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }
	//Metodo sendTask recibe la dirección para la conexion y los datos a enviar para el servidor, retorna CompletableFuture en tipo String
    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) {
        HttpRequest request = HttpRequest.newBuilder()	//HttpRequest para crear una solicitud HTTP con el metodo post
                //.header("X-Debug","true")
		.POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(respuesta -> {
				String response="\nResponse HTTP Protocol Version: \n";
				response = response + respuesta.version().toString() + "\n";
				response = response + "\nResponse Headers: \n";
				response = response + respuesta.headers().toString() + "\n";
				response = response + "\nURI: \n";
				response = response + respuesta.uri().toString() + "\n";
				response = response + "\nResponse Body: \n";
				response = response + respuesta.body() + "\n";
			return response;}
			); //sendAsync para enviar la solicitud request de manera asincrona
    }
}
