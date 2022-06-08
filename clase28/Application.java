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

import java.util.Arrays;
import java.util.List;

public class Application {
	//Se definen las cadenas de los end points para ambos servidores
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/searchipn";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/searchipn";
    private static final String WORKER_ADDRESS_3 = "http://localhost:8083/searchipn";
    private static final String WORKER_ADDRESS_4 = "http://localhost:8084/searchipn";

    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator(); //Se instancia un objeto Aggregator
        	//Cadenas para multiplicar en cada servidor
	//String task1 = "10,200";
        //String task2 = "123456789,100000000000000,700000002342343";
	String task = "175600,ipn";
		//Metodo sendTaskToWorkers para enviar las tareas a los trabajadores
		//Se maneja un arreglo para la lista de trabajadores y otro arreglo para la lista de tareas
        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2, WORKER_ADDRESS_3, WORKER_ADDRESS_4),
                Arrays.asList(task, task, task, task));
		//Se imprimen los resultados
        for (String result : results) {
            System.out.println(result);
        }
    }
}
