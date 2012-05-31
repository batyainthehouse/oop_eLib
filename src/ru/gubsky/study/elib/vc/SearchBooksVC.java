/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.vc;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
                switch(index) {
                    case 0:
                        getView().getGenreList_().setListData(model_.getGenres());
                    case 1:
                        getView().getAuthorsList_().setListData(model_.getAuthors());
                    case 2:
                    case 3:
                }
                //model_.getGenres();
            }
        };
        return changeListener;
    }
    
    private SearchBooksView getView()
    {
        System.out.println("getView");
        if (view_ == null) {
            view_ = new SearchBooksView();
            view_.setTabChangeListener(tabChangeListener());
            view_.setVisible(true);
        }
        return view_;
    }
}
