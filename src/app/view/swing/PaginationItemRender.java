/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.view.swing;

import javax.swing.JButton;

/**
 *
 * @author tuanl
 */
public interface PaginationItemRender {
     public JButton createPaginationItem(Object value, boolean isPrevious, boolean isNext, boolean enable);

    public JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable);

    public Object createPreviousIcon();

    public Object createNextIcon();
}
