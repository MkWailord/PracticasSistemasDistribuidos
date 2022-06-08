import java.io.*;

class Ejecutar {
	public static void main(String[] args)
	{
		int n=4;
		for(int i = 0; i < n; i++){
			MultithreadingCurl object = new MultithreadingCurl();
			object.start();
		}
	}
}

class MultithreadingCurl extends Thread {
	public void run()
	{
		try{
			mandarCurl();
		}
		catch  (Exception e){
			System.out.println("Excepcion atrapada");
		}
	}

	public void mandarCurl(){
		Runtime rt = Runtime.getRuntime();
		String[] commands = {"curl", "-v", "--header", "X-Debug:true", "--data", "1757600,IPN", "34.125.186.227:80/searchipn"};
		try{
			Process proc = rt.exec(commands);

			BufferedReader stdInput = new BufferedReader(new 
     				InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
     				InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
    				System.out.println(s);
			}

			// Read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
    				System.out.println(s);
			}
		}
		catch (Exception e){
			System.out.println("Excepcion atrapada");
		}
	}
}
