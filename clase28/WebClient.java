/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

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
                .header("X-Debug","true")
		.POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(respuesta -> {
				//String response="\nResponse HTTP Protocol Version: \n";
				//response = response + respuesta.version().toString() + "\n";
				//response = response + "\nResponse Headers: \n";
				//response = response + respuesta.headers().toString() + "\n";
				//response = response + "\nURI: \n";
				//response = response + respuesta.uri().toString() + "\n";
				//response = response + "\nResponse Body: \n";
				//response = response + respuesta.body() + "\n";
				String response = respuesta.body();
				return response;}
			); //sendAsync para enviar la solicitud request de manera asincrona
    }
}
