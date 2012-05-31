/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

/**

 @author GG
 */
public class ClientModel
{
    public String[] getGenres()
    {
        String[] str = new String[]{
            "новеллы",
            "рассказы",
            "компьютерная литература"
        };
        return str;
    }
    
    public String[] getAuthors()
    {
        String[] str = new String[]{
            "гоша",
            "иванов",
            "николай иванович"
        };
        return str;
    }
    
}
