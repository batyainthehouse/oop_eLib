/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.vc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ru.gubsky.study.elib.ClientModel;

/**

 @author GG
 */
public class SearchBooksVC extends JFrame
{
    private ClientModel model_;

    public SearchBooksVC()
    {
        super();
        model_ = null;
        createGui();
    }

    public void setModel(ClientModel model)
    {
        this.model_ = model;
    }

    private void createGui()
    {
        setSize(888, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("eLib. Поиск книг");
        setLayout(new GridLayout(1, 2));

        // tab
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Жанры", null, genrePanel(), "Показать жанры");
        tabbedPane.addTab("Авторы", authorsPanel());
        tabbedPane.addTab("Популярное", popularPanel());
        tabbedPane.addTab("Новинки", noveltyPanel());
        tabbedPane.setMaximumSize(new Dimension(100, Integer.MAX_VALUE));
        tabbedPane.setSize(new Dimension(100, Integer.MAX_VALUE));
        tabbedPane.addChangeListener(tabChangeListener());
        this.add(tabbedPane, BorderLayout.LINE_START);
        tabbedPane.setSelectedIndex(0);
        
        // search
        this.add(searchPanel(), BorderLayout.PAGE_START);

        // show
        this.setVisible(true);
    }

    private ChangeListener tabChangeListener()
    {
        ChangeListener changeListener = new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent changeEvent)
            {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            }
        };
        return changeListener;
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

    private JPanel genrePanel()
    {
        System.out.println("genrePanel");
        JPanel genrePanel = new JPanel();
        

        return genrePanel;
    }

    private JPanel authorsPanel()
    {
        System.out.println("authorsPanel");
        return null;
    }

    private JPanel popularPanel()
    {
        System.out.println("popularPanel");
        return null;
    }

    private JPanel noveltyPanel()
    {
        System.out.println("noveltyPanel");
        return null;
    }
}
