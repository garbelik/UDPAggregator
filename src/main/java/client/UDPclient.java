package client;

import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


class UDPClient {

    private static DatagramSocket datagramSocket;

    private static final int BUFFER_SIZE = 1024;
    private static byte[] buffer;

    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("usage: java -jar JavaUDPClient.jar <IP address> <port>");
//            System.exit(1);
//        }
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            int port =1099;
            //Integer.parseInt(args[1]);



            InputStream is = new FileInputStream("src/incomingmsg1.txt");
            byte[] bytes = IOUtils.readFully(is,-1,true);



            datagramSocket = new DatagramSocket();

            DatagramPacket out_datagramPacket = new DatagramPacket(
                    bytes,
                    bytes.length,
                    inetAddress,
                    port);

            datagramSocket.send(out_datagramPacket);

//            buffer = new byte[BUFFER_SIZE];
//            DatagramPacket in_datagramPacket =
//                    new DatagramPacket(buffer, BUFFER_SIZE);
//            datagramSocket.receive(in_datagramPacket);
//
//            String serverEcho = new String(
//                    in_datagramPacket.getData(),
//                    0,
//                    in_datagramPacket.getLength());
//            System.out.println(serverEcho);


            InputStream is2 = new FileInputStream("src/incomingmsg2.txt");
            byte[] bytes2 = IOUtils.readFully(is2,-1,true);



           // datagramSocket = new DatagramSocket();

            DatagramPacket out_datagramPacket2 = new DatagramPacket( bytes2,bytes2.length, inetAddress, port);

            datagramSocket.send(out_datagramPacket2);

            buffer = new byte[BUFFER_SIZE];
            DatagramPacket in_datagramPacket2 =
                    new DatagramPacket(buffer, BUFFER_SIZE);
            datagramSocket.receive(in_datagramPacket2);

            String serverEcho2 = new String(
                    in_datagramPacket2.getData(),
                    0,
                    in_datagramPacket2.getLength());
            System.out.println(serverEcho2);

        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            datagramSocket.close();
        }
    }

}