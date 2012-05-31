/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import ru.gubsky.study.elib.models.ServerModel;

/**

 @author GG
 */
public class Server
{
    final static int PORT = 3333;
    
    public Server()
    {
        try {
            System.out.println("Server is running");
            ServerSocket ss = new ServerSocket(PORT);
            while (true) {
                Socket s = ss.accept();
                ServerModel sm = new ServerModel(s);
                sm.start();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}