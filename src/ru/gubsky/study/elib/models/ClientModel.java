/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**

 @author GG
 */
public class ClientModel
{
    final static String HOST = "localhost";
    final static int PORT = 15755;
    final static int OPERATION_SEARCH = 1;
    final static int OPERATION_ADD_VIEW = 2;
    final static int OPERATION_POPULAR = 3;
    
    public ArrayList<Book> getBooksBySearching(String text)
    {
//        if (text == null || text.isEmpty()) {
//            return new ArrayList<>();
//        }

        ArrayList<Book> bukz = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_SEARCH);
            outStream.writeObject(text);
            outStream.flush();

            // read
            ObjectInputStream inStream = new ObjectInputStream(sock.getInputStream());
            bukz = (ArrayList<Book>) inStream.readObject();

            inStream.close();
            outStream.close();
            sock.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("Client model: " + bukz);
        return bukz;
    }

    public void addViewForBookId(int id)
    {
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_ADD_VIEW);
            outStream.writeInt(id);
            outStream.flush();

            outStream.close();
            sock.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ArrayList<Book> getPopBooks()
    {

        ArrayList<Book> bukz = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_POPULAR);
            outStream.flush();

            // read
            ObjectInputStream inStream = new ObjectInputStream(sock.getInputStream());
            bukz = (ArrayList<Book>) inStream.readObject();

            inStream.close();
            outStream.close();
            sock.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("Client model: " + bukz);
        return bukz;
    }

}
