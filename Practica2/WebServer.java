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
//Librerias para servidor http en java
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;

public class WebServer {
	//Cadenas end points del servidor
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SEARCHIPN_ENDPOINT = "/searchipn";
	//Puerto e implementación servidor HttpServer sencillo
    private final int port;
    private HttpServer server;

    public static void main(String[] args) {
        int serverPort = 8080;	//Puerto default del servidor
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]); //Puerto por linea de comandos
        }

        WebServer webServer = new WebServer(serverPort); //Se instancia un objeto WebServer
        webServer.startServer(); //Se ejecuta el metodo principal de la clase webServer el cual inicializa la configuración del servidor

        System.out.println("Servidor escuchando en el puerto " + serverPort); //Se imprime el puerto de escucha
    }
	//Constructor que recibe el puerto e inicializa la variable port
    public WebServer(int port) {
        this.port = port;
    }
	//Metodo startServer
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0); //El metodo create permite crear una instancia de socket TCP vinculaba a una
									     //ip y al puerto port, el segundo parametro es el tamaño de la lista de solicitudes
									     //pendientes que se permite al servidor http mantener en espera, en cero, se deja
									     //la decisión al sistema
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT); //El metodo createContext crea un objeto HttpContext sin un HttpHandler asociado pero con
									   //la ruta relativa asignada, en este caso Status y Task
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);

	HttpContext searchipnContext = server.createContext(SEARCHIPN_ENDPOINT);

	statusContext.setHandler(this::handleStatusCheckRequest); //El metodo setHandler recibe como parametro el metodo que implementa el manejador y vincula el
								  //handler
        taskContext.setHandler(this::handleTaskRequest);

	searchipnContext.setHandler(this::handleSearchipnRequest);

        server.setExecutor(Executors.newFixedThreadPool(8)); //El metodo setExecutor permite establece un objeto del tipo executor al servidor, se provee un pool de
						    	     //8 hilos y se deja al ejecutor la labor de iniciarlos y asginarles tareas
        server.start(); //Se ejecuta el servidor en un nuevo hilo en segundo plano
    }
	//El metodo hadnleTaskRequest corresponde al manejador del endpoint Task y el argumento encapsula todo lo relacionado con la transacción
    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Se verifica que el metodo obtenido de la transacción http corresponde al metodo post
            exchange.close();					     //Si no, se cierra el exchange y retorna
            return;
        }

        Headers headers = exchange.getRequestHeaders(); //Se recupera del exchange todos los headers
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {  //Si entre los headers se encuentra X-Test y ademas es true
            String dummyResponse = "123\n"; //Se genera la respuesta
            sendResponse(dummyResponse.getBytes(), exchange); //Se envia la respuesta
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) { //Si entre los headers se encuentra X-Debug y ademas es true
            isDebugMode = true; //Se inicializa isDebugMode con true
        }
	//Tiempo que tomo el procesamiento como información de depuración
        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //Se recupera la información del cuerpo del mensaje en la transacción http como bytes
        byte[] responseBytes = calculateResponse(requestBytes); //Se pasan los numeros que se quieren multiplicar al metodo calculateResponse y se guarda
								//la respuesta
        long finishTime = System.nanoTime(); //Tiempo final en el procesamiento

        if (isDebugMode) { //Si isDebugMode = true
	    long elapsedTime = finishTime - startTime;
	    double elapsedTimeSec = elapsedTime / 1000000000;
            String debugMessage = String.format("La operación tomó %f nanosegundos", elapsedTimeSec); //Mensaje de debug del tiempo de procesamiento
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //Se guarda el debugMessage dentro de los headers de respuesta
											    //especificamente en el header X-Debug-Info mediante el metodo put
        }

        sendResponse(responseBytes, exchange); //Se envia la respuesta con el meotdo sendResponse(Respuesta, exchange)
    }
	//Metodo calculateResponse que multiplica dos numeros del tipo bigInteger
    private byte[] calculateResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;
        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
    }
	//metodo handleStatusCheckRequest
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) { //Se verifica que el metodo obtenido en el exchange es de tipo get
            exchange.close(); //si no, se cierra
            return;
        }

        String responseMessage = "El servidor está vivo\n"; //Se responde que el servidor esta vivo
        sendResponse(responseMessage.getBytes(), exchange); //Se envia respuesta
    }

    private void handleSearchipnRequest(HttpExchange exchange) throws IOException {
	if(!exchange.getRequestMethod().equalsIgnoreCase("post")){ //Se verifica que el metodo obtenido en el exchange es de tipo post
	    exchange.close();
	    return;
	}

	Headers headers = exchange.getRequestHeaders(); //Se recupera del exchange todos los headers
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {  //Si entre los headers se encuentra X-Test y ademas es true
            String dummyResponse = "123\n"; //Se genera la respuesta
            sendResponse(dummyResponse.getBytes(), exchange); //Se envia la respuesta
            return;
        }

	boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) { //Si entre los headers se encuentra X-Debug y ademas es true
            isDebugMode = true; //Se inicializa isDebugMode con true
        }
	//Tiempo que tomo el procesamiento como información de depuración
        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //Se recupera la información del cuerpo del mensaje en la transacción http como bytes
        byte[] responseBytes = calculateResponseSearchIPN(requestBytes); //Se pasan los numeros que se quieren multiplicar al metodo calculateResponse y se guarda
								//la respuesta
        long finishTime = System.nanoTime(); //Tiempo final en el procesamiento

        if (isDebugMode) { //Si isDebugMode = true
	    long elapsedTime = finishTime - startTime;
	    double elapsedTimeSec = (double) elapsedTime/1000000000;
            String debugMessage = String.format("La operación tomó %f segundos", elapsedTimeSec); //Mensaje de debug del tiempo de procesamiento
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage)); //Se guarda el debugMessage dentro de los headers de respuesta
											    //especificamente en el header X-Debug-Info mediante el metodo put
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] calculateResponseSearchIPN(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringData = bodyString.split(",");
	int n = Integer.parseInt(stringData[0]);
	String subCadena = stringData[1];

	StringBuilder cadenota = new StringBuilder(n*4);
        int i,j,index=0,cont=0;

	for(j=0; j<n; j++){
            String palabra = "";
            for(i=0; i<=2; i++){
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'a');
                palabra = palabra + c;
            }
            palabra = palabra.toUpperCase();
            palabra= palabra + " ";
            cadenota.append(palabra);
        }
        while(index != -1){
            index = cadenota.indexOf(subCadena, index);
            if(index != -1){
                cont++;
                index++;
            }
        }

        return String.format("Ocurrencias encontradas: %d\n", cont).getBytes();
    }

	//metodo sendResponse
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length); //Se agrega el status code 200 y la longitud de la respuesta
        OutputStream outputStream = exchange.getResponseBody(); //Se escribe en el cuerpo del mensaje
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
