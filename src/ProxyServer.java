import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ProxyServer {
    
    private static final int PORT = 8080;
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.out.println("Proxy Server started...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handler to manage client requests in separate threads
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                InputStream clientInput = clientSocket.getInputStream();
                OutputStream clientOutput = clientSocket.getOutputStream();
            ) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));
                PrintWriter writer = new PrintWriter(clientOutput, true);

                // Read the URL from the client
                String url = reader.readLine();
                if (url == null) {
                    return;
                }

                // Fetch the content from the URL
                String content = fetchUrlContent(url);

                // Send the content back to the client
                if (content != null) {
                    writer.println(content);
                } else {
                    writer.println("Error: Unable to fetch content.");
                }
            } catch (IOException e) {
                System.err.println("Error handling client connection: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        // Method to fetch content from the given URL
        private String fetchUrlContent(String urlString) {
            try {
                URL url = new URL(urlString);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append("\n");
                }
                in.close();
                return content.toString();
            } catch (Exception e) {
                System.err.println("Error fetching URL: " + e.getMessage());
                return null;
            }
        }
    }
}

