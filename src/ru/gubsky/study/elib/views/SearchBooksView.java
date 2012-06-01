/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import ru.gubsky.study.elib.models.Book;
import ru.gubsky.study.elib.models.BookTableModel;

/**

 @author GG
 */
public class SearchBooksView extends JFrame
{
    // use getters, don't use directly
    private JPanel genrePanel_;
    private JList genreList_;
    private JPanel authorsPanel_;
    private JList authorsList_;
    private JPanel popularPanel_;
    private JPanel noveltyPanel_;
    private JTabbedPane tabbedPane_;
    private ChangeListener tabChangeListener_;
    private ActionListener searchButtonListener_;
    private JTable popBukzTable_;
    private JTable searchBukzTable_;
    private JButton searchButton_;
    private JTextField searchTf_;
    private JPanel searchPanel_;
    
    public JTextField getSearchTf()
    {
        if (searchTf_ == null) {
            searchTf_ = new JTextField(30);
        }
        return searchTf_;
    }
    
    public void setSearchButtonListener(ActionListener searchButtonListener)
    {
        getSearchButton().removeActionListener(this.searchButtonListener_);
        this.searchButtonListener_ = searchButtonListener;
        getSearchButton().addActionListener(searchButtonListener);
    }
    
    private JTable getPopBukzTable()
    {
        if (popBukzTable_ == null) {
            popBukzTable_ = new JTable();
        }
        return popBukzTable_;
    }
    
    public JTable getSearchBukzTable()
    {
        if (searchBukzTable_ == null) {
            searchBukzTable_ = new JTable();
            JScrollPane scrollPane = new JScrollPane(searchBukzTable_);
            searchBukzTable_.setFillsViewportHeight(true);
            getSearchPanel().add(scrollPane);
        }
        return searchBukzTable_;
    }

    public void setTabChangeListener(ChangeListener tabChangeListener)
    {
        getTabbedPane_().removeChangeListener(this.tabChangeListener_);
        this.tabChangeListener_ = tabChangeListener;
        getTabbedPane_().addChangeListener(this.tabChangeListener_);
    }

    public JList getAuthorsList_()
    {
        if (authorsList_ == null) {
            authorsList_ = new JList();
        }
        return authorsList_;
    }

    private JPanel getAuthorsPanel_()
    {
        if (authorsPanel_ == null) {
            authorsPanel_ = new JPanel();
            authorsPanel_.setLayout(new FlowLayout(FlowLayout.LEADING));
            authorsPanel_.add(getAuthorsList_());
        }
        return authorsPanel_;
    }

    public JList getGenreList_()
    {
        if (genreList_ == null) {
            genreList_ = new JList();
        }
        return genreList_;
    }

    private JPanel getGenrePanel_()
    {
        if (genrePanel_ == null) {
            genrePanel_ = new JPanel();
            genrePanel_.setLayout(new FlowLayout(FlowLayout.LEADING));
            genrePanel_.add(getGenreList_());
        }
        return genrePanel_;
    }

    private JPanel getNoveltyPanel_()
    {
        if (noveltyPanel_ == null) {
            noveltyPanel_ = new JPanel();
        }
        return noveltyPanel_;
    }

    private JPanel getPopularPanel_()
    {
        if (popularPanel_ == null) {
            popularPanel_ = new JPanel();
            JScrollPane scrollPane = new JScrollPane(getPopBukzTable());
            getPopBukzTable().setFillsViewportHeight(true);
            popularPanel_.add(scrollPane);
        }
        return popularPanel_;
    }

    public JTabbedPane getTabbedPane_()
    {
        if (tabbedPane_ == null) {
            tabbedPane_ = new JTabbedPane();
            tabbedPane_.addTab("Жанры", null, getGenrePanel_(), "Показать жанры");
            tabbedPane_.addTab("Авторы", getAuthorsPanel_());
            tabbedPane_.addTab("Популярное", getPopularPanel_());
            tabbedPane_.addTab("Новинки", getNoveltyPanel_());
            tabbedPane_.addTab("Поиск", getSearchPanel());
        }
        return tabbedPane_;
    }

    public SearchBooksView()
    {
        super();
        createGui();
    }

    private void createGui()
    {
        setSize(888, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("eLib. Поиск книг");
        //setLayout(new GridLayout(1, 2));

        // tab
        add(getTabbedPane_(), BorderLayout.PAGE_START);
    }

    private JButton getSearchButton()
    {
        if (searchButton_ == null) {
            searchButton_ = new JButton("Найти");
        }
        return searchButton_;
    }
    
    private JPanel getSearchPanel()
    {
        if (searchPanel_ == null) {
            searchPanel_ = new JPanel(new FlowLayout(FlowLayout.LEADING));
            JPanel topPanel = new JPanel();
            topPanel.add(getSearchTf());
            topPanel.add(getSearchButton());
            searchPanel_.add(topPanel);
        }
        return searchPanel_;
    }
    
    private JTextArea thumbArea_;
    public JTextArea getThumbArea()
    {
        if (thumbArea_ == null) {
            thumbArea_ = new JTextArea(10, 30);
            thumbArea_.setLineWrap(true);
            thumbArea_.setWrapStyleWord(true);
            thumbArea_.setEditable(false);
            getSearchPanel().add(thumbArea_);
        }
        return thumbArea_;
    }

    public void updatePopular(BookTableModel bookTableModel)
    {
        getPopBukzTable().setModel(bookTableModel);
    }
    
    public void updateSearch(BookTableModel bookTableModel, ListSelectionListener l)
    {
        JTable table = getSearchBukzTable();
        table.setModel(bookTableModel);
        table.getSelectionModel().addListSelectionListener(l);
        table.removeColumn(table.getColumnModel().getColumn(5));
    }
    
    
}
