/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texto;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author pablo
 */
public class TxtMinusculas extends TxtText1 {

    public TxtMinusculas() {
        DocumentFilter filter = new LowercaseDocumentFilter();
        ((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
    }

    
    class LowercaseDocumentFilter extends DocumentFilter {

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,
                AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.toLowerCase(), attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {

            fb.replace(offset, length, text.toLowerCase(), attrs);
        }
    }

}
