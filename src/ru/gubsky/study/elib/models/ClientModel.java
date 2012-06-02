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
    final static int OPERATION_NEWS = 4;
    final static int OPERATION_GENRES = 5;
    final static int OPERATION_GET_BY_GENRE = 6;
    final static int OPERATION_AUTHORS = 7;
    final static int OPERATION_GET_BY_AUTHORS = 8;
    
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

    public ArrayList<Book> getNewBooks()
    {
        ArrayList<Book> bukz = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_NEWS);
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

    public String[] getGenres()
    {
        String[] genres = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_GENRES);
            outStream.flush();

            // read
            ObjectInputStream inStream = new ObjectInputStream(sock.getInputStream());
            genres = (String[]) inStream.readObject();

            inStream.close();
            outStream.close();
            sock.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return genres;
    }

    public ArrayList<Book> getBooksByGenre(String genre)
    {
        ArrayList<Book> bukz = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_GET_BY_GENRE);
            outStream.writeObject(genre);
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
        return bukz;
    }

    public String[] getAuthors()
    {
        String[] authors = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_AUTHORS);
            outStream.flush();

            // read
            ObjectInputStream inStream = new ObjectInputStream(sock.getInputStream());
            authors = (String[]) inStream.readObject();

            inStream.close();
            outStream.close();
            sock.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return authors;
    }

    public ArrayList<Book> getBooksByAuthor(String author)
    {
        ArrayList<Book> bukz = null;
        try {
            Socket sock = new Socket(HOST, PORT);

            // write
            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());
            outStream.writeInt(OPERATION_GET_BY_AUTHORS);
            outStream.writeObject(author);
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
        return bukz;
    }
}
