/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.util.ArrayList;
import java.util.Date;

/**

 @author GG
 */
public class ClientModel
{
    public String[] getGenres()
    {
        String[] str = new String[] {
            "новеллы",
            "рассказы",
            "компьютерная литература"
        };
        return str;
    }

    public String[] getAuthors()
    {
        String[] str = new String[] {
            "гоша",
            "иванов",
            "николай иванович"
        };
        return str;
    }

    public ArrayList<Book> getPopularBook(int start, int end)
    {
        ArrayList<Book> bukz = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            Book b1 = new Book();
            b1.author = "gosha";
            b1.date = new Date(1000303);
            b1.genre = "genre";
            b1.name = "как я вышел погулять";
            b1.popularity = 10;
            bukz.add(b1);
        }
        return bukz;
    }
}
