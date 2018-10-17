package server;

import java.net.*;
import java.io.*;

public class ClientHandler {
    
    //Служебный класс, которому сервер передаст сокет
    //По сокету будет происходить общение с подключившимся клиентом
   
    private MServer server; //ссылка на сервер для клиента
    private Socket socket;
    private DataInputStream  in; //поток ввода, используется UTF-кодировка
    private DataOutputStream out;//поток вывода, используется UTF-кодировка
    private String nickname;
    
    public ClientHandler(MServer server, Socket socket)
    {
       try{
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
                 //Множество клиентов потребует множество потоков обработки
                 new Thread(()-> {
                         try
                         {
                            while(true)//цикл авторизации
                            {
                                /*
                                /auth login password
                                */
                                String str = in.readUTF();
                                String[] tokens = str.split("\\s");//разбиваем входящее сообщение по частям, считая пробелы разделителями
                                
                                if(tokens[0].equals("/auth"))//от клиента пришёл запрос на авторизацию 
                                {
                                  String nickFromDB = SQLHandler.getNickByLogPass(tokens[1], tokens[2]);
                                 
                                  if(nickFromDB != null)
                                  {
                                     sendMsg("/authok");//если клиент авторизовался, послать ему уведомление 
                                     server.add_client(this);
                                     break;//вышли из цикла авторизации
                                  }
                                }
                            }
                            while(true)//цикл общения с клиентом
                            {
                                String str = in.readUTF();//блокирующая операция!!! Читаем то, что пришло от клиента
                                                          
                                System.out.println("A message from a client: " + str);
                                
                                   if(str.equalsIgnoreCase("/end")) 
                                   {
                                      break;
                                   }
                                
                                server.broadcastMsg(str);//Cервер разослал сообщение String str = in.readUTF() ВСЕМ подключенным клиентам
                                   
                                out.flush();
                            }
                         }
                          catch(IOException ex)
                         {
                            ex.printStackTrace();
                         }
                         finally//освобождаем ресурсы
                         {
                            try
                            {
                                in.close();
                                //out.close();   //в отдельный блок try - catch
                                //socket.close();//в отдельный блок try - catch
                            }
                            catch(IOException ex)
                            {
                                ex.printStackTrace();
                            }
                            try
                            {
                               out.close();
                            }
                            catch(IOException ex)
                            {
                                ex.printStackTrace();
                            }
                            try
                            {
                                socket.close();
                            }
                            catch(IOException ex)
                            {
                                ex.printStackTrace();
                            }
                            
                            server.remove_client(this);//Клиент отключился и больше не активен
                            
                         }//finally
                     
                 }).start();
       
       }
       catch(IOException ex)
       {
          ex.printStackTrace();
       }
    }//public ClientHandler
    
    //если нужно послать сообщение клиенту
    public void sendMsg(String msg)
    {
       try
       {
         out.writeUTF(msg);//отослать сообщение клиенту
       }
       catch(IOException ex)
       {
         ex.printStackTrace();
       }
    }
}//class ClientHandler
