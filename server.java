package actividad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class server {
	public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(50001); // Puerto de escucha

            while (true) {
                System.out.println("Esperando conexiones...");
                Socket clientSocket = serverSocket.accept(); // Espera a que un cliente se conecte

                // Crea un hilo para atender al cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Configura la entrada y salida de datos
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Envía las opciones al cliente
            writer.println("1. ¿Qué día es hoy?");
            writer.println("2. ¿Qué hora es?");
            writer.println("3. ¿Cómo se llama la universidad?");
            writer.println("4. ¿Para qué asignatura es este programa?");
            writer.println("5. Salir");

            // Espera la opción del cliente
            String clientChoice = reader.readLine();

            // Procesa la opción y envía la respuesta
            switch (clientChoice) {
            case "1":
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
                String currentDate = dateFormat.format(new Date());
                writer.println("Hoy es " + currentDate);
                break;
            case "2":
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String currentTime = timeFormat.format(new Date());
                writer.println("La hora actual es " + currentTime);
                break;
            case "3":
                writer.println("La universidad se llama Universidad Europea de Madrid.");
                break;
            case "4":
                writer.println("Este programa es para la asignatura de Programación Concurrente y Distribuida.");
                break;
            case "5":
                writer.println("Adiós");
                clientSocket.close(); // Cierra la conexión
                break;
            default:
                writer.println("Opción no válida");
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}