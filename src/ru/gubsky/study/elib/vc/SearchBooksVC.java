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
import ru.gubsky.study.elib.views.ReadBookView;
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
        getView().setSearchButtonListener(searchButtonListener());
    }

    private ListSelectionListener listSelListener;
    private ListSelectionListener listSelectionListener()
    {
        if (listSelListener == null) {
            listSelListener = new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e)
                {
                    int id = getView().getSelectedBook().id;
                    BookTableModel bm = (BookTableModel) getView().getSearchBukzTable().getModel();
                    getView().getThumbArea().setText(bm.getTextAt(id).substring(0, 150));
                    getView().getOpenBookButton(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            Book selBook = getView().getSelectedBook();
                            model_.addViewForBookId(selBook.id);
                            ReadBookView readBookView = new ReadBookView(selBook);
                            readBookView.setVisible(true);
                        }

                    });
                }

            };
        }
        return listSelListener;
    }

    private ActionListener searchButtonListener()
    {
        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getView().getSearchBukzTable().getSelectionModel().removeListSelectionListener(listSelectionListener());
                String searchText = getView().getSearchTf().getText();
                ArrayList<Book> bukz = model_.getBooksBySearching(searchText);
                BookTableModel bookModel = new BookTableModel(bukz);
                getView().updateSearch(bookModel);
                getView().getSearchBukzTable().getSelectionModel().addListSelectionListener(listSelectionListener());
            }

        };
        return al;
    }

    private SearchBooksView getView()
    {
        if (view_ == null) {
            view_ = new SearchBooksView();
            view_.setVisible(true);
        }
        return view_;
    }

}
