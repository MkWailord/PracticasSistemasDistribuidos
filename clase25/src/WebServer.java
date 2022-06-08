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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;

public class WebServer {
    /*Definicion de las cadenas de endpoints*/
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SEARCHIPN_ENDPOINT = "/searchipn";

    /*Variables globales privadas para el puesto y un servidor*/
    private final int port;
    private HttpServer server;

    /*Inicializacion de la configuracion del seevidor*/
    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    /*Constructor inicializar puerto*/
    public WebServer(int port) {
        this.port = port;
    }

    /*Metodo para correr el servidor*/
    public void startServer() {

        /*Creacion del server socket tcp vinculado a puesto 'port' con numero de solicitudes pendientes '0'*/
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        /*inicializacion de nuestros endpoints*/
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);
        HttpContext searchContext = server.createContext(SEARCHIPN_ENDPOINT);

        /*Asignacion de los Handles para cada endpoint*/
        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);
        searchContext.setHandler(this::handleSearchRequest);

        /*Arrancar servidor*/
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }


    /*Manejador del endpoint Task
    * Es de tipo Post
    * Verifica los encabezados perzonalizados
    *  X-test y X-Debug
    * Realiza la operacion matematica y regresa el resultado*/
    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        /*Se recupera la informacion del cuerpo de la peticion y se ejecuta el metodo para obtener la operacion matematica*/
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
	byte[] responseBytes = calculateResponse(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private void handleSearchRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        /*Se recupera la informacion del cuerpo de la peticion y se ejecuta el metodo para obtener la operacion matematica*/
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = searchResponse(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] calculateResponse(byte[] requestBytes) {
    //Modificacion realizada en la clase 24 para recibir un objeto Demo serializado
	//Demo objeto = null;
	//objeto = (Demo)SerializationUtils.deserialize(requestBytes);
    //Modificacion realizada en la clase 25 para recibir un objeto PoligonoIrreg serializado
	PoligonoIrreg polIrreg = null;
	Coordenada coord = new Coordenada(10,30);
	polIrreg = (PoligonoIrreg)SerializationUtils.deserialize(requestBytes);
        System.out.println("Objeto al ser recibido: ");
        System.out.println(polIrreg);

	polIrreg.anadeVertice(coord);

	System.out.println("Objeto despues de ser modificado: \n");
	System.out.println(polIrreg);

	byte[] serializado = SerializationUtils.serialize(polIrreg);

	//return String.format("a = %d \nb = %s", objeto.a, objeto.b).getBytes();
	return serializado;
    }

    private byte[] searchResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringData = bodyString.split(",");

        int n = Integer.parseInt(stringData[0]);
        byte[] cadenota = new byte[n*4];
        Random rnd = new Random();
        int cantidad = 0;

        for (int i = 0; i < n; i++) {
            if((i)%4 == 0){
                cadenota[i] = ' ';
                if( i!= 0 && (char) cadenota[i-3] == stringData[1].charAt(0) && (char) cadenota[i-2] ==stringData[1].charAt(1) && (char) cadenota[i-1] ==stringData[1].charAt(2) ){
                    cantidad++;
                }

            }else {
                cadenota[i] = (byte) (rnd.nextInt(26)+'A');
            }
        }

        return String.format("\"Se encontraron  %s\n", cantidad).getBytes();
    }

    /*Metodo para manejar el enpoint Status
    * Si el metodo de solicitud es Get, regresa que esta vivo el server*/
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    /*Metodo para responder todos los endpoints despues de pasar por el sus respectivos handles*/
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
