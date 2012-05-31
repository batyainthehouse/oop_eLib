/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**

 @author GG
 */
public class ServerModel extends Thread
{
    final static int OPERATION_SEARCH = 1;
    private Socket sock_;
    private Connection conn_;
    private Statement stat_;

    public ServerModel(Socket s, Properties sqlProperties) throws SQLException
    {
        sock_ = s;
        System.out.println("ServerModel: ServerModel");
        String host = sqlProperties.getProperty("server");
        String port = sqlProperties.getProperty("port");
        String login = sqlProperties.getProperty("user");
        String passw = sqlProperties.getProperty("pass");
        String db = sqlProperties.getProperty("db");

        Properties properties = new Properties();
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf8");

        String urlConnection = "jdbc:mysql://" + host + ":" + port + "/"
                + db + "?user=" + login + "&password=" + passw;
        conn_ = DriverManager.getConnection(urlConnection, properties);
        stat_ = conn_.createStatement();
        String setnames = "set names \'utf8\';";
        stat_.execute(setnames);
    }

    public void run()
    {
        System.out.println("ServerModel: run");
        try {
            ObjectInputStream inStream = new ObjectInputStream(sock_.getInputStream());
            System.out.println("Servermodel available: " + inStream.available());
            int op = inStream.readInt();
            System.out.println("sm: operation = " + op);
            Object outObj = null;
            switch (op) {
                case OPERATION_SEARCH:
                    outObj = searchBook((String) inStream.readObject());
                    break;
                default:
                    break;
            }
            System.out.println("out obj: " + outObj);
            ObjectOutputStream outStream = new ObjectOutputStream(sock_.getOutputStream());
            outStream.writeObject(outObj);
            sleep(1000);
            inStream.close();
            outStream.close();
            sock_.close();
            System.out.println("socket close");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ArrayList<Book> searchBook(String text) throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a WHERE a.id = b.id_author and g.id = b.id_genre;";
        PreparedStatement ps = conn_.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            System.out.println(book.author);
            book.genre = rs.getString("genre");
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
        return bukz;
    }
}