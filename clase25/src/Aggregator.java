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


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//Clase que llama al método de la clase WebClient sendTask para mandar solicitudes múltiples y recibir las respuestas. 
public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    //Recibe una lista de servidores trabajadores HTTP, y una lista de tareas
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<byte[]> tasks) {
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()]; //Arreglo para almacenar las respuestas de los 
        //servidores trabajadores HTTP

        for (int i = 0; i < workersAddresses.size(); i++) { //Itera la lista de trabajadores y de tareas
            String workerAddress = workersAddresses.get(i);
            byte[] task = tasks.get(i);


            futures[i] = webClient.sendTask(workerAddress, task); //Envía tareas asincronas
        }
        //Lista para almacenar los resultados provenientes de los servidores web. Ojo en el video se hace diferente

        //Código del video mas entendible

        List<String> results = new ArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            results.add(futures[i].join());
        }

        return results;
    }
}
