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
import java.util.logging.Level;
import java.util.logging.Logger;

/**

 @author GG
 */
public class ServerModel extends Thread
{
    final static int OPERATION_SEARCH = 1;
    final static int OPERATION_ADD_VIEW = 2;
    final static int OPERATION_POPULAR = 3;
    final static int OPERATION_NEWS = 4;
    final static int OPERATION_GENRES = 5;
    final static int OPERATION_GET_BY_GENRE = 6;
    final static int OPERATION_AUTHORS = 7;
    final static int OPERATION_GET_BY_AUTHOR = 8;
    private Socket sock_;
    private Connection conn_;
    private Statement stat_;
    public ServerModel(Socket s, Properties sqlProperties) throws SQLException
    {
        sock_ = s;
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
        try {
            ObjectInputStream inStream = new ObjectInputStream(sock_.getInputStream());
            int op = inStream.readInt();
            Object outObj = null;
            switch (op) {
                case OPERATION_SEARCH:
                    outObj = searchBook((String) inStream.readObject());
                    break;
                case OPERATION_ADD_VIEW:
                    addViewForBookId((int) inStream.readInt());
                    break;
                case OPERATION_POPULAR:
                    outObj = popularBooks();
                    break;
                case OPERATION_NEWS:
                    outObj = newBooks();
                    break;
                case OPERATION_GENRES:
                    outObj = genres();
                    break;
                case OPERATION_GET_BY_GENRE:
                    outObj = getBooksByGenre((String) inStream.readObject());
                    break;
                case OPERATION_AUTHORS:
                    outObj = authors();
                    break;
                case OPERATION_GET_BY_AUTHOR:
                    outObj = getBooksByAuthor((String) inStream.readObject());
                    break;
                default:
                    break;
            }
            if (outObj != null) {
                ObjectOutputStream outStream = new ObjectOutputStream(sock_.getOutputStream());
                outStream.writeObject(outObj);
                sleep(1000);
                outStream.close();
            } else {
                sleep(1000);
            }
            inStream.close();
            sock_.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ArrayList<Book> searchBook(String text) throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date, b.id as id "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a "
                + "WHERE a.id = b.id_author and g.id = b.id_genre "
                + "AND (b.name like ? OR a.name like ?) "
                + "ORDER BY b.name";
        PreparedStatement ps = conn_.prepareStatement(query);
        ps.setString(1, "%" + text + "%");
        ps.setString(2, "%" + text + "%");
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            book.genre = rs.getString("genre");
            book.id = rs.getInt("id");
            System.out.println("id = " + book.id);
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
        return bukz;
    }

    private void addViewForBookId(int id) throws SQLException
    {
        String query = "UPDATE book SET views = views + 1 WHERE id = ?";
        PreparedStatement ps = conn_.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    private ArrayList<Book> popularBooks() throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date, b.id as id "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a "
                + "WHERE a.id = b.id_author and g.id = b.id_genre "
                + "ORDER BY b.views DESC LIMIT 10";
        PreparedStatement ps = conn_.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            book.genre = rs.getString("genre");
            book.id = rs.getInt("id");
            System.out.println("id = " + book.id);
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
        return bukz;
    }

    private ArrayList<Book> newBooks() throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date, b.id as id "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a "
                + "WHERE a.id = b.id_author and g.id = b.id_genre "
                + "ORDER BY b.date DESC LIMIT 10";
        PreparedStatement ps = conn_.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            book.genre = rs.getString("genre");
            book.id = rs.getInt("id");
            System.out.println("id = " + book.id);
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
        return bukz;
    }

    private String[] genres() throws SQLException
    {
        String query = "SELECT name FROM genre ORDER BY NAME";
        PreparedStatement ps = conn_.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        String[] genres = new String[getSizeOfResultSet(rs)];
        int i = 0;
        while (rs.next()) {
            genres[i] = rs.getString(1);
            i++;
        }
        return genres;
    }

    public static int getSizeOfResultSet(ResultSet rs)
    {
        int size = 0;
        if (rs != null) {
            try {
                rs.last();
                size = rs.getRow();
                rs.beforeFirst();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        return size;
    }

    private ArrayList<Book> getBooksByGenre(String genre) throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date, b.id as id "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a "
                + "WHERE a.id = b.id_author and g.id = b.id_genre "
                + "AND g.name = ? ORDER BY b.name";
        PreparedStatement ps = conn_.prepareStatement(query);
        ps.setString(1, genre);
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            book.genre = rs.getString("genre");
            book.id = rs.getInt("id");
            System.out.println("id = " + book.id);
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
        return bukz;
    }

    private String[] authors() throws SQLException
    {
        String query = "SELECT name FROM author ORDER BY name";
        PreparedStatement ps = conn_.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        String[] authors = new String[getSizeOfResultSet(rs)];
        int i = 0;
        while (rs.next()) {
            authors[i] = rs.getString(1);
            i++;
        }
        return authors;
    }

    private ArrayList<Book> getBooksByAuthor(String author) throws SQLException
    {
        String query = "SELECT g.name as genre, a.name as author, b.name as title, "
                + "b.text as text, b.views as views, b.date as date, b.id as id "
                + "FROM book b, (SELECT * FROM genre) AS g, "
                + "(SELECT * FROM author) AS a "
                + "WHERE a.id = b.id_author and g.id = b.id_genre "
                + "AND a.name = ? ORDER BY b.name";
        PreparedStatement ps = conn_.prepareStatement(query);
        ps.setString(1, author);
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> bukz = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.author = rs.getString("author");
            book.genre = rs.getString("genre");
            book.id = rs.getInt("id");
            System.out.println("id = " + book.id);
            book.date = rs.getDate("date");
            book.name = rs.getString("title");
            book.text = rs.getString("text");
            book.popularity = rs.getInt("views");
            bukz.add(book);
        }
                System.out.println("author: " + author);
        System.out.println(bukz);
        return bukz;
    }

}