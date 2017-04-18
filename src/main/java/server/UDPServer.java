package server;

import com.google.gson.Gson;
import jsonDAO.InMessage;
import jsonDAO.Order;
import jsonDAO.OutMessage;
import processor.OrderProcessor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UDPServer {
    private static final int BUFFER_SIZE = 1024;
    private static byte[] buffer;
    private int outSeq=0;
    private List<Long> executionTimeList;

    public static void main(String[] args) {
        int port =1099;//test data

        new UDPServer().run(port);
    }

    private void run(int port) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            buffer = new byte[BUFFER_SIZE];
            executionTimeList = new ArrayList<>();


            System.out.printf("Listening on udp:%s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);
            DatagramPacket receivePacket = new DatagramPacket(buffer, BUFFER_SIZE);
            OrderProcessor processor = new OrderProcessor();


            while (true) {

                serverSocket.receive(receivePacket);
                long startTime = System.nanoTime();//start measure latency

                String in_message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                //System.out.println("RECEIVED: " + in_message);

                Gson gson = new Gson();
                InMessage staff = gson.fromJson(in_message, InMessage.class);

                List<Order> orders = staff.getMessages();
                processor.startProcessing(orders);

                long latency = System.nanoTime()- startTime;//end measure latency
                executionTimeList.add(latency);
                LongSummaryStatistics statistics = executionTimeList.stream().mapToLong((x) -> x.longValue()).summaryStatistics();
                /*LATENCY*/
//                System.out.println("Max = "+ statistics.getMax());
//                System.out.println("Min = "+ statistics.getMin());
//                System.out.println("Average = "+ statistics.getAverage());
//                System.out.println("Quantity = "+ statistics.getCount());

                InetAddress IPAddress = receivePacket.getAddress();

                OutMessage out_message = new OutMessage(++outSeq ,processor.getAggregatedData());
                String sendString = gson.toJson(out_message);

                byte[] sendData = sendString.getBytes("UTF-8");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, e);

                    }
    }


}
