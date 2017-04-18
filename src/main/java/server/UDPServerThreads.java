package server;


import com.google.gson.Gson;
import jsonDAO.InMessage;
import jsonDAO.Order;
import jsonDAO.OutMessage;
import processor.OrderProcessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

public class UDPServerThreads {
    private static final int BUFFER_SIZE = 1024;
    private int outSeq=0;
    private List<Long> executionTimeList;
    int ownPort =1099;//test data
    private DatagramSocket serverSocket;
    private Gson gson;
    private OrderProcessor processor;
    private InetAddress remoteAddress ;
    private int remotePort ;
    private boolean flag;

    public UDPServerThreads() throws SocketException {
        serverSocket = new DatagramSocket(ownPort);
        processor = new OrderProcessor();
        gson = new Gson();
    }

    Thread listener = new Thread(new Runnable() {

         @Override
         public void run() {
             System.out.println("listener started!");
             byte[] buffer = new byte[BUFFER_SIZE];
             executionTimeList = new ArrayList<>();


             DatagramPacket receivePacket = new DatagramPacket(buffer, BUFFER_SIZE);

              //IPAddress = receivePacket.getAddress();

             while (true) {

                 try {
                 Thread.sleep(500);
                 serverSocket.receive(receivePacket);
                 remoteAddress = receivePacket.getAddress();
                 remotePort= receivePacket.getPort();
                 System.out.print("remoteAddress = "+remoteAddress+" remoteport = " + remotePort);

                 long startTime = System.nanoTime();//start measure latency

                 String in_message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                 System.out.println("RECEIVED: " + in_message);

                 InMessage staff = gson.fromJson(in_message, InMessage.class);

                 List<Order> orders = staff.getMessages();
                 processor.startProcessing(orders);

                 long latency = System.nanoTime()- startTime;//end measure latency
                 executionTimeList.add(latency);

                 LongSummaryStatistics statistics = executionTimeList.stream().mapToLong((x) -> x.longValue()).summaryStatistics();
                 System.out.println("Max = "+ statistics.getMax());
                 System.out.println("Min = "+ statistics.getMin());
                 System.out.println("Average = "+ statistics.getAverage());
                 System.out.println("Quantity = "+ statistics.getCount());

                 flag=true;
             } catch (IOException e) {
                 e.printStackTrace();
             } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }

         }

     });

    Thread sender = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("sender started!");

            while (true) {
//            if(processor.getAggregatedData().size()>0){
               try {

                    OutMessage out_message = new OutMessage(++outSeq ,processor.getAggregatedData());
                    String sendString = gson.toJson(out_message);

                    byte[] sendData = sendString.getBytes("UTF-8");
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, remoteAddress , remotePort);
                    serverSocket.send(sendPacket);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//            }
       }
        }
    });


    public void start() {
        listener.start();
        while(!flag){
            if(processor.getAggregatedData().size()>0) {
                sender.start();
            }
        }

    }

    public static void main(String[] args) {
        UDPServerThreads server = null;
        try {
            server = new UDPServerThreads();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        server.start();
    }

}
