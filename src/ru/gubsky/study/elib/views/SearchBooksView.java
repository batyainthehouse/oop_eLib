/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.views;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.ChangeListener;

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

    public ChangeListener getTabChangeListener()
    {
        return tabChangeListener_;
    }

    public void setTabChangeListener(ChangeListener tabChangeListener)
    {
        this.tabChangeListener_ = tabChangeListener;
    }   
    
    private JList getAuthorsList_()
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
            tabbedPane_.addChangeListener(getTabChangeListener());
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
        add(getTabbedPane_(), BorderLayout.CENTER);

        // search
        add(searchPanel(), BorderLayout.PAGE_START);
    }

    private JPanel searchPanel()
    {
        JPanel searchPanel = new JPanel();

        JTextField searchTf = new JTextField(30);
        searchPanel.add(searchTf);

        JButton searchButton = new JButton("Найти");
        searchPanel.add(searchButton);

        return searchPanel;
    }
}
