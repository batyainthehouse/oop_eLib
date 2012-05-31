/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib.vc;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ru.gubsky.study.elib.ClientModel;
import ru.gubsky.study.elib.views.SearchBooksView;

/**

 @author GG
 */
public class SearchBooksVC extends JFrame
{
    // use directly
    private ClientModel model_;
    
    // don't use directly, use getters
    private SearchBooksView view_;
    
    public SearchBooksVC(ClientModel model)
    {
        super();
        model_ = model;
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

                //model_.getGenres();
            }
        };
        return changeListener;
    }
    
    private SearchBooksView getView()
    {
        if (view_ == null) {
            view_ = new SearchBooksView();
            view_.setTabChangeListener(tabChangeListener());
        }
        return view_;
    }
}
