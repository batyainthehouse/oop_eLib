/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.models;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**

 @author GG
 */
public class BookTableModel extends AbstractTableModel
{
    @Override
    public int getRowCount()
    {
        return books_.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex) {
            case 0:
                return "Автор";
            case 1:
                return "Название";
            case 2:
                return "Жанр";
            case 3:
                return "Просмотров";
            case 4:
                return "Дата добавления";
            case 5:
                return "id";
            default:
                return "Непонятно";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
                return String.class;
            case 3:
                return int.class;
            case 4:
                return Date.class;
            case 5:
                return int.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Book book = books_.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return book.author;
            case 1:
                return book.name;
            case 2:
                return book.genre;
            case 3:
                return book.popularity;
            case 4:
                return book.date;
            case 5:
                return book.id;
            default:
                return 0;
        }
    }
    
    private ArrayList<Book> books_;
    public String getTextAt(int id)
    {
        for (Book b : books_) {
            if (b.id == id) {
                return b.text;
            }
        }
        return "";
    }
    
    public BookTableModel(ArrayList<Book> books_)
    {
        this.books_ = books_;
    }
    
    
}
