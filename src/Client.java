import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);
        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            while (true) {
                System.out.println("Введите строку с пробелами, для завершения наберите end");
                String msg = scanner.nextLine();
                if (msg.equals("end")) {
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } finally {
            socketChannel.close();
        }
    }
}
