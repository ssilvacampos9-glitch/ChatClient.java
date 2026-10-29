import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String ENDERECO_SERVIDOR = "127.0.0.1";
    private static final int PORTA = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(ENDERECO_SERVIDOR, PORTA);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Thread separada para ler as mensagens vindas do servidor
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String resposta;
                    while ((resposta = in.readLine()) != null) {
                        System.out.println(resposta);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão com o servidor encerrada.");
                }
            }).start();

            // Loop principal do cliente para enviar mensagens
            System.out.println("Conectado ao servidor. Pode começar a digitar.");
            while (scanner.hasNextLine()) {
                String mensagem = scanner.nextLine();
                out.println(mensagem);
                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}