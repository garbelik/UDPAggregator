package client;

import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class UDPclient {

    private static DatagramSocket datagramSocket;
    private static final int BUFFER_SIZE = 1024;
    private static byte[] buffer;
    public UDPclient() {

    }


    public static void main(String[] args) {

        try  {

            InetAddress inetAddress = InetAddress.getLocalHost();
            int serverport  =1099;
            InputStream is = new FileInputStream("src/incomingmsg1.txt");
            byte[] bytes = IOUtils.readFully(is,-1,true);

            datagramSocket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress( 1300);
            datagramSocket.bind(address);

            InputStream is1 = new FileInputStream("src/incomingmsg1.txt");
            byte[] bytes1 = IOUtils.readFully(is1,-1,true);
            DatagramPacket out_datagramPacket = new DatagramPacket(
                    bytes1,
                    bytes1.length,
                    inetAddress,
                    serverport);

            for(int i=0;i<5;i++) {
                datagramSocket.send(out_datagramPacket);
            }

            InputStream is2 = new FileInputStream("src/incomingmsg2.txt");
            byte[] bytes2 = IOUtils.readFully(is2,-1,true);


            DatagramPacket out_datagramPacket2 = new DatagramPacket(
                    bytes2,
                    bytes2.length,
                    inetAddress,
                    serverport);

            datagramSocket.send(out_datagramPacket2);

            //Waiting for incoming messages with updated data
            buffer = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buffer, BUFFER_SIZE);
            while (true) {

                //DatagramPacket receivePacket = new DatagramPacket(buffer, BUFFER_SIZE);
                datagramSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData());
                System.out.println(receivedMessage);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}