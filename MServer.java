package server;

import java.net.*;
import java.io.*;
import java.util.Vector;
public class MServer {
     
    //здесь будет храниться список клиентов.
    //Vector  - это почти ArrayList, но только потокобезопасный,
    //с ним могут работать несколько потоков сразу.
    private Vector<ClientHandler> clients;    
    
    public MServer()
    {
          try 
          {
               ServerSocket serv_socket = new ServerSocket(12345);
               clients = new Vector<>();//вектор с клиентами
               SQLHandler.connect();//подключаемся к базе данных
               
               while(true)
               {
                   System.out.println("Waiting for a new client!");
                   Socket socket = serv_socket.accept();
                   
                   ClientHandler cl = new ClientHandler(this, socket); //клиент подключился
                   //add_client(cl); //клиент попал в список клиентов после подключения
                                     //нельзя добавлять клиента, пока он не авторизован!
               }
          }
          catch(IOException ex)
          {
                 ex.printStackTrace();      
          }
    }//public MServer()
    
    //добавить клиента в список clients
    public void add_client(ClientHandler client)
    {
        clients.add(client);
    }
    
    //удалить клиента из списка clients
    public void remove_client(ClientHandler client)
    {
        clients.remove(client);
    }
    
    //отослать сообщение всем клиентам сразу     
    public void broadcastMsg(String msg)
    {
         for(ClientHandler client: clients)
         {
            client.sendMsg(msg);
         }
    }
}//public class MServer
