/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author ryanl
 */
public class BookNotAvailableException extends Exception {

    /**
     * Creates a new instance of <code>BookNotAvailableException</code> without
     * detail message.
     */
    public BookNotAvailableException() {
    }

    /**
     * Constructs an instance of <code>BookNotAvailableException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookNotAvailableException(String msg) {
        super(msg);
    }
}
