import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.util.concurrent.CompletionException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class WebServer{
		//END POINTS
	private static final String STATUS_ENDPOINT = "/status";
	private static final String TOKEN_ENDPOINT = "/token";
	private static final String INICIAR_ENDPOINT ="/iniciar";
		//VARIABLES GLOBALES
	private final int port;
	private static int numServer;
	private HttpServer server;
	private static Server server1 = new Server();
	private static Server server2 = new Server();
	private static Server server3 = new Server();
		//Main
	public static void main(String[] args) {
		int serverPort = 81;
		serverPort = Integer.parseInt(args[0]);
		numServer= Integer.parseInt(args[1]);
		server1.setDireccion(args[2]);
		server2.setDireccion(args[3]);
		server3.setDireccion(args[4]);

		WebServer webServer = new WebServer(serverPort);
		webServer.startServer();

		System.out.println("Servidor escuchando en el puerto "+ serverPort);
	}
		//Constructor
	public WebServer(int port){
		this.port = port;
	}

		//Metodo startServer()
	public void startServer(){
		try{
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e){
			e. printStackTrace();
			return;
		}
			//Http Contexts
		HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
		HttpContext tokenContext = server.createContext(TOKEN_ENDPOINT);
		HttpContext iniciarContext = server.createContext(INICIAR_ENDPOINT);
			//Handlers
		statusContext.setHandler(this::handleStatusCheckRequest);
		tokenContext.setHandler(this::handleTokenSend);
		iniciarContext.setHandler(this::handleIniciarToken);

		server.setExecutor(Executors.newFixedThreadPool(8));
		server.start();
	}

		//Handle /status
	private void handleStatusCheckRequest(HttpExchange exchange) throws IOException{
		if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
			exchange.close();
			return;
		}

		String responseMessage = "El servidor esta vivo\n";
		sendResponse(responseMessage.getBytes(), exchange);
	}

	private void handleTokenSend(HttpExchange exchange) throws IOException{
		System.out.println("\n\n####################################################");
		System.out.println("		Recibiendo Conexión en server "+(numServer+1));
		System.out.println("		Reteniendo por dos segundos");
		if(!exchange.getRequestMethod().equalsIgnoreCase("post")){
			exchange.close();
			return;
		}
		try{
			Thread.sleep(2000);
		}catch(InterruptedException e){
			System.out.println(e);
			return;
		}
		byte[] serializado = exchange.getRequestBody().readAllBytes();
		Token token = (Token) SerializationUtils.deserialize(serializado);
		System.out.println(token);
		long sysTime = System.nanoTime();
		System.out.println("Actualizando tiempo en server "+(numServer+1)+ ": "+sysTime);
		token.putTime(numServer, sysTime);
		serializado = SerializationUtils.serialize(token);
		WebClient webClient = new WebClient();
			//Comprobar Status del Servidor
		String msg;
		if(checkStatus(server1.getDireccion()))
			webClient.sendTask(server1 + "token",serializado);
		else if(checkStatus(server2.getDireccion()))
			webClient.sendTask(server2 + "token",serializado);
		else if(checkStatus(server3.getDireccion()))
			webClient.sendTask(server3 + "token",serializado);
		else{
			System.out.println("Todos los servidores estan muertos");
			System.out.println("Terminando proceso");
			return;
		}
		msg = "Token Enviado";
		sendResponse(msg.getBytes(), exchange);
	}

	private boolean checkStatus(String direccion){
		WebClient webClient = new WebClient();
		try{
			System.out.println("Pidiendo status a: "+direccion);
			String status = webClient.getTask(direccion+"status").join();
			System.out.print(status);
			System.out.println("Enviando token a: "+direccion);
			return true;
		}catch(CompletionException CE){
			System.out.println("El servidor esta muerto");
			return false;
		}
	}

	private void handleIniciarToken(HttpExchange exchange) throws IOException{
		Token token = new Token();
		WebClient webClient = new WebClient();
		long sysTime = System.nanoTime();
		for(int i = 0; i<=3; i++)
			token.putTime(i, sysTime);
		System.out.println("Token Iniciado: ");
		System.out.println(token);
		byte[] serializado = SerializationUtils.serialize(token);
		System.out.println("Enviando");
		webClient.sendTask(server1 + "token", serializado);
		String msg = "Token Enviado";
		sendResponse(msg.getBytes(), exchange);
	}

	private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(200, responseBytes.length);
		OutputStream outputStream = exchange.getResponseBody();
		outputStream.write(responseBytes);
		outputStream.flush();
		outputStream.close();
		exchange.close();
	}
}






