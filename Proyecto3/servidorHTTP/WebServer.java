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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServer {
	//Archivo de texto para buscar coincidencias
	private String filePath = "BIBLIA_COMPLETA.txt";
	private String texto = null;

	//Endpoints
	private static final String STATUS_ENDPOINT = "/status";
	private static final String SEARCHBIBLIA_ENDPOINT = "/searchbiblia";

	//Puerto
	private final int port;

	//HttpServer
	private HttpServer server;


	public static void main(String[] args){
		//Puerto default
		int serverPort = 8081;
		//Si se especifica puerto como argumento
		if(args.length == 1){
			serverPort = Integer.parseInt(args[0]);
		}

		//Instancia de WebServer
		try{
			WebServer webServer = new WebServer(serverPort);
			webServer.startServer();
			System.out.println("Servidor escuchando en el puerto " + serverPort);
		}catch(IOException e){
			e.printStackTrace();
			return;
		}
	}//Fin main

	//Constructor
	public WebServer(int port) throws IOException{
		this.port = port;
		this.texto = Files.readString(Paths.get(filePath), StandardCharsets.ISO_8859_1);
		this.texto = this.texto.toLowerCase();
	}

	//Metodo startServer()
	public void startServer(){
		try{
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch(IOException e){
			e.printStackTrace();
			return;
		}


		//Objetos HttpContext
		HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
		HttpContext searchBibliaContext = server.createContext(SEARCHBIBLIA_ENDPOINT);

		//Asignación de metodos a los objetos HttpContext
		statusContext.setHandler(this::handleStatusCheckRequest);
		searchBibliaContext.setHandler(this::handleSearchBibliaRequest);

		server.setExecutor(Executors.newFixedThreadPool(8));
		server.start();
	}//Fin metodo startServer()


	//////////////////////////    Metodos Handles    //////////////////////////////

		//Handle Status
	private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
		if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
			exchange.close();
			return;
		}

		String responseMessage = "El servidor está vivo \n";
		sendResponse(responseMessage.getBytes(), exchange);
	}

	private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(200, responseBytes.length);
		OutputStream outputStream = exchange.getResponseBody();

		outputStream.write(responseBytes);
		outputStream.flush();
		outputStream.close();
		exchange.close();
	}
		//Fin metodos Handle Status

		//Handle SearchBiblia
	private void handleSearchBibliaRequest(HttpExchange exchange) throws IOException {
		if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
			exchange.close();
			return;
		}

		Headers headers = exchange.getRequestHeaders();
		boolean isDebugMode = false;
		if(headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")){
			isDebugMode = true;
		}

		long startTime = System.nanoTime();
		byte[] requestBytes = exchange.getRequestBody().readAllBytes();
		byte[] responseBytes = calculateSearchResponse(requestBytes);
		long finishTime = System.nanoTime();

		if(isDebugMode){
			String debugMessage = String.format("La operación tomo %d nanosegundos", finishTime - startTime);
			exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
			byte[] timeBytes = String.format("\nLa operación tomo %d nanosegundos \n", finishTime - startTime).getBytes();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(responseBytes);
			outputStream.write(timeBytes);
			responseBytes = outputStream.toByteArray();
		}

		sendResponse(responseBytes, exchange);
	}

	private byte[] calculateSearchResponse(byte[] requestBytes) {
		//bodyString deberia contener unicamente una palabra para buscar dentro del texto
		String bodyString = new String(requestBytes);
		String palabra = bodyString.toLowerCase();
		int coincidencias = 0;
		int indice = 0;

		while (indice != -1){
			indice = texto.indexOf(palabra, indice);
			if(indice != -1){
				coincidencias++;
				indice += 4;
			}
		}
		System.out.println("Palabra recibida: " + bodyString);
		System.out.println("Coincidencias: " + coincidencias);
		return String.format("%d", coincidencias).getBytes();
	}
		//Fin metodos Handle SearchBiblia
}//Fin WebServer
