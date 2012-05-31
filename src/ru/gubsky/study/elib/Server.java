/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import ru.gubsky.study.elib.models.Book;
import ru.gubsky.study.elib.models.ServerModel;

/**

 @author GG
 */
public class Server extends JFrame
{
    final static int PORT = 15755;
    private Connection conn_;
    private Statement stat_;
    private JTextField tfName_;
    private JTextField tfAuthor_;
    private JTextField tfGenre_;
    private JTextArea taText_;

    public Server(Properties sqlProperties) throws SQLException
    {
        super();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }

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

        this.createTables();

        this.createGui();

        try {
            System.out.println("Server is running");
            ServerSocket ss = new ServerSocket(PORT);
            while (true) {
                System.out.println("Server: перед принятием");
                Socket s = ss.accept();
                System.out.println("Server : чтото принято");
                ServerModel sm = new ServerModel(s, sqlProperties);
                sm.start();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void createGui()
    {
        this.setSize(888, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("eLib. Сервер");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new GridLayout(3, 2));

        topPanel.add(new JLabel("Название"));
        tfName_ = new JTextField();
        tfName_.setSize(100, 20);
        topPanel.add(tfName_);

        topPanel.add(new JLabel("Автор"));
        tfAuthor_ = new JTextField(12);
        topPanel.add(tfAuthor_);

        topPanel.add(new JLabel("Жанр"));
        tfGenre_ = new JTextField(12);
        topPanel.add(tfGenre_);

        this.add(topPanel);

        this.add(new JLabel("Текст"));  
        taText_ = new JTextArea(50, 20);
        JScrollPane scrollPane = new JScrollPane(taText_);
        this.add(scrollPane);

        JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Book book = new Book();
                book.author = tfAuthor_.getText();
                book.genre = tfGenre_.getText();
                book.name = tfName_.getText();
                saveBook(book);
            }
        });
        this.add(sendButton);

        this.setVisible(true);
    }

    private int getEntryId(String table, String field, String value,
            boolean createNew) throws SQLException
    {
        int result = 0;

        String sqlSelect = "SELECT id FROM " + table + " WHERE "
                + field + " = ?;";
        PreparedStatement preps = conn_.prepareStatement(sqlSelect);
        preps.setString(1, value);
        ResultSet rs = preps.executeQuery();

        if (rs.next()) {
            result = rs.getInt("id");
            rs.close();
        } else if (createNew == false) {
            result = 0;
            rs.close();
        } else {
            String sqlInsert = "INSERT INTO " + table + "(" + field + ") "
                    + " VALUES(?);";
            PreparedStatement ps = conn_.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, value);
            try {
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("ex: " + ex.getMessage());
                System.out.println("value: " + value);
            } finally {
                rs.close();
                ps.close();
            }
        }
        return result;
    }

    private void saveBook(Book book)
    {
        try {
            int genreId = getEntryId("genre", "name", book.genre, true);
            int authorId = getEntryId("author", "name", book.author, true);
            
            String query = "INSERT INTO book(id_genre, id_author, name, text) values(?, ?, ?, ?)";
            PreparedStatement ps = conn_.prepareStatement(query);
            ps.setInt(1, genreId);
            ps.setInt(2, authorId);
            ps.setString(3, book.name);
            ps.setString(4, book.text);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createTables() throws SQLException
    {
        final String[] query = new String[] {
            "CREATE TABLE IF NOT EXISTS book("
            + "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "id_genre INTEGER NOT NULL,"
            + "id_author INTEGER NOT NULL,"
            + "name TEXT,"    
            + "text TEXT,"
            + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "views INTEGER DEFAULT 0) CHARACTER SET utf8;",
            "CREATE TABLE IF NOT EXISTS genre("
            + "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name TEXT) CHARACTER SET utf8;",
            "CREATE TABLE IF NOT EXISTS author("
            + "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name TEXT) CHARACTER SET utf8;"
        };
        for (String q : query) {
            stat_.executeUpdate(q);
        }
    }

    public static void main(String[] arg)
    {
        Properties sqlProperties = new Properties();
        sqlProperties.setProperty("server", "localhost");
        sqlProperties.setProperty("port", "3306");
        sqlProperties.setProperty("user", "root");
        sqlProperties.setProperty("pass", "12345");
        sqlProperties.setProperty("db", "oop_kurs");

        try {
            Server server = new Server(sqlProperties);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}