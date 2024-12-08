import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.UUID;

public class ProxyClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ProxyClient <URL>");
            System.exit(1);
        }

        String url = args[0];
        String serverAddress = "localhost"; // Assuming server is running in the same container
        int serverPort = 8080;

        try (Socket socket = new Socket(serverAddress, serverPort);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();
             PrintWriter writer = new PrintWriter(os, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            // Send URL to the proxy server
            writer.println(url);

            // Read the response (HTML content) from the server
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Save content to an HTML file in the output directory
            saveContentToFile(content.toString());

        } catch (IOException e) {
            System.err.println("Error connecting to proxy server: " + e.getMessage());
        }
    }

    // Save the fetched HTML content to a file with a unique name
    private static void saveContentToFile(String content) {
        String fileName = "output_" + UUID.randomUUID().toString() + ".html";
        Path outputPath = Paths.get("output", fileName);
        
        try {
            Files.createDirectories(outputPath.getParent());  // Ensure output directory exists
            Files.write(outputPath, content.getBytes());
            System.out.println("Content saved to: " + outputPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving content to file: " + e.getMessage());
        }
    }
}

