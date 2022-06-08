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
// Disponible en: "/mnt/c/Documents and Settings/AN1/Documents/DSD_2022/clase21"
// En la LAP en: "/mnt/c/Documents and Settings/ukran/Documents/DSD_2022/clase21"
//como datos recibe el numero de tokens de tres letras y la cadena a buscar
//Ejecutar cliente curl como:  time curl -v --header 'X-Debug:true' --data '1757600,IPN' localhost:8080/searchipn
/*
Para compilar en la carpeta source donde están los tres archivos de código:
javac -d ../classes *.java
cd ../classes
jar cf Application.jar Application.class Aggregator.class networking/WebClient.class
java -cp Application.jar Application 
*/

import java.util.Arrays;
import java.util.List;

//Clase Application para el cliente web HTTP con dos servidores trabajadores
public class Application {

    //Cadenas de los endpoints en cada servidor
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/task"; //Trabajadores en distintos puertos pues están en la misma PC
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/task";


    public static void main(String[] args) {

        Aggregator aggregator = new Aggregator();  //Un objeto Aggregator y dos tareas
        //Factores a multiplicar

        Demo object = new Demo(2022, "Prueba serializacion y deserializacion NUmero 1");
        Demo object2 = new Demo(2023, "Prueba serializacion y deserializacion Numero 2");
        System.out.println("Objeto al ser enviado: ");
        System.out.println(object);
        System.out.println("Objeto al ser enviado: ");
        System.out.println(object2);

        byte[] serializado1 = SerializationUtils.serialize(object);
        byte[] serializado2 = SerializationUtils.serialize(object2);

        // sendTasksToWorkers envia tareas a los trabajadores
        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2),
                Arrays.asList( serializado1, serializado2)); //Envía las tareas a los trabajadores

        for (String result : results) { //Imprime los resultados. Ejercicio modificar el cliente HTTP para pasar el header X-Debug
            //Y ver cuanto tiempo tarda el procesamiento en cada cliente
            System.out.println(result);
        }
    }
}
