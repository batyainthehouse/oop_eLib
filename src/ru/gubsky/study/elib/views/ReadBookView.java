/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.views;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.gubsky.study.elib.models.Book;

/**

 @author GG
 */
public class ReadBookView extends JFrame
{
    public ReadBookView(Book book)
    {
        super();
        setTitle("eLib. Чтение книги " + book.name);
        setSize(800, 600);

        /*
         JLabel lblCaption = new JLabel("Название: " + book.name);
         JLabel lblAuthor = new JLabel("Автор: " + book.author);
         JLabel lblGenre = new JLabel("Жанр: " + book.genre);
         JLabel lblViews = new JLabel("Просмотров: " + book.popularity);
         */

        String str = "Название: " + book.name
                + "\nАвтор: " + book.author
                + "\nЖанр: " + book.genre
                + "\nПросмотров: " + book.popularity + "\n\n"
                + book.text;
        
        JTextArea text = new JTextArea(str);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(text);
        add(scrollPane);
    }

}
