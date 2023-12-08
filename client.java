package actividad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
	public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 50001); // Conexión al servidor

            // Configura la entrada y salida de datos
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Lee y muestra las opciones del servidor
            String serverMessage;
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println("Opciones del servidor:");
                System.out.println(serverMessage);

                // Elige una opción y la envía al servidor
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Seleccione una opción: ");
                String choice = userInput.readLine();
                writer.println(choice);

                // Muestra la respuesta del servidor
                String serverResponse = reader.readLine();
                System.out.println("Respuesta del servidor: " + serverResponse);

                if (choice.equals("5")) {
                    break; // Sale del bucle si elige salir
                }
            }

            // Cierra la conexión
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
