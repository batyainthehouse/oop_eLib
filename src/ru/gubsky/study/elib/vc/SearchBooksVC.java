/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.vc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ru.gubsky.study.elib.models.Book;
import ru.gubsky.study.elib.models.BookTableModel;
import ru.gubsky.study.elib.models.ClientModel;
import ru.gubsky.study.elib.views.SearchBooksView;

/**

 @author GG
 */
public class SearchBooksVC
{
    // use directly
    private ClientModel model_;
    // don't use directly, use getters
    private SearchBooksView view_;

    public SearchBooksVC(ClientModel model)
    {
        model_ = model;
        getView().getGenreList_().setListData(model.getGenres());
        getView().setSearchButtonListener(searchButtonListener());
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
                switch (index) {
                    case 0:
                        getView().getGenreList_().setListData(model_.getGenres());
                        break;
                    case 1:
                        getView().getAuthorsList_().setListData(model_.getAuthors());
                        break;
                    case 2:
                        BookTableModel bookModel = new BookTableModel(model_.getPopularBook(0, 100));
                        getView().updatePopular(bookModel);
                        break;
                    case 3:
                        break;
                }
            }
        };
        return changeListener;
    }
    
    
    private ListSelectionListener listSelectionListener()
    {
        ListSelectionListener listSelListener = new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int id = (int) getView().getSearchBukzTable().getModel().getValueAt(
                        getView().getSearchBukzTable().getSelectedRow(), 5);
                BookTableModel bm = (BookTableModel) getView().getSearchBukzTable().getModel();
                getView().getThumbArea().setText(bm.getTextAt(id).substring(0, 200));
                getView().getOpenBookButton();
            }
        };
        return listSelListener;
    }

    private ActionListener searchButtonListener()
    {
        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String searchText = getView().getSearchTf().getText();
                ArrayList<Book> bukz = model_.getBooksBySearching(searchText);
                BookTableModel bookModel = new BookTableModel(bukz);
                getView().updateSearch(bookModel, listSelectionListener());
            }
        };
        return al;
    }

    private SearchBooksView getView()
    {
        if (view_ == null) {
            view_ = new SearchBooksView();
            view_.setTabChangeListener(tabChangeListener());
            view_.setVisible(true);
        }
        return view_;
    }
}
