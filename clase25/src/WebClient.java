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

    public WebClient() {
        this.client = HttpClient.newBuilder() //Primero se crea un cliente HTTP. Este cliente puede ser usado para multiples solicitudes. Mas información en: https://openjdk.java.net/groups/net/httpclient/intro.html
                .version(HttpClient.Version.HTTP_1_1)
                .build(); //Ver como funciona este método
    }

    //Metodo que recibe la dirección de hacia donde vamos a enviar una solicitud, y el message body como un arreglo byte[] 
    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) {
        HttpRequest request = HttpRequest.newBuilder()  //Crea un HttpRequest el cual se puede usar para especificar el URI, método GET, PUT o POST el
        //cuerpo del mensaje, un timeout y request headers. Ya consturido es inmutable y se puede usar para multiples peticiones.
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload)) 
                .uri(URI.create(url))
                //Agregando header
                .header("X-debug","true")
                .build();

        //return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        //return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(respuesta -> {return respuesta.body();});

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> { return response.headers().toString() +"\n " +response.body() + response.version().toString() + response.request().toString() +"\n\n";});
    }
}
