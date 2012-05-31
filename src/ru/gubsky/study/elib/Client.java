/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gubsky.study.elib;

import ru.gubsky.study.elib.models.ClientModel;
import ru.gubsky.study.elib.vc.SearchBooksVC;

/**
 @author GG
 */
public class Client
{
    public static void main(String[] args)
    {
        SearchBooksVC sb = new SearchBooksVC(new ClientModel());
    }
}
