/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**

 @author GG
 */
public class ServerModel extends Thread
{
    final static int OPERATION_SEARCH = 1;
    private Socket sock_;
    
    public ServerModel(Socket s)
    {
        sock_ = s;
    }
    
    public void run()
    {
        try {
            ObjectInputStream inStream = new ObjectInputStream(sock_.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(sock_.getOutputStream());
            int op = inStream.readInt();
            
            Object outObj = null;
            switch (op) {
                case OPERATION_SEARCH:
                    outObj = searchBook((String)inStream.readObject());
                    break;
                default:
                    break;
            }
            outStream.writeObject(outObj);
            sleep(1000);
            inStream.close();
            outStream.close();
            sock_.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private ArrayList<Book> searchBook(String text)
    {
        return null;
    }
}