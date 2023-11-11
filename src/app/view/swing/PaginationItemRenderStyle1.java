/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.view.swing;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
/**
 *
 * @author tuanl
 */
public class PaginationItemRenderStyle1 extends DefaultPaginationItemRender {
     @Override
    public JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable) {
        JButton button = super.createButton(value, isPrevious, isNext, enable);
        button.setUI(new ButtonUI());
        return button;
    }

    @Override
    public Object createPreviousIcon() {
        return "Previous";
    }

    @Override
    public Object createNextIcon() {
        return "Next";
    }
}
