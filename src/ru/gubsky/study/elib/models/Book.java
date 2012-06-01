/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.io.Serializable;
import java.util.Date;

/**

 @author GG
 */
public class Book implements Serializable
{
    public int id;
    public String author;
    public String name;
    public String genre;
    public Date date;
    public int popularity = 0;
    public String text = "";
}
