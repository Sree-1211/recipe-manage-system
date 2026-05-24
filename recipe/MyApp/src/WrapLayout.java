//import java.awt.*;
//import javax.swing.JScrollPane;
//import javax.swing.SwingUtilities;
//
///**
// * A FlowLayout that wraps components to the next line automatically.
// */
//public class WrapLayout extends FlowLayout {
//    public WrapLayout() {
//        super();
//    }
//
//    public WrapLayout(int align) {
//        super(align);
//    }
//
//    public WrapLayout(int align, int hgap, int vgap) {
//        super(align, hgap, vgap);
//    }
//
//    @Override
//    public Dimension preferredLayoutSize(Container target) {
//        return layoutSize(target, true);
//    }
//
//    @Override
//    public Dimension minimumLayoutSize(Container target) {
//        Dimension minimum = layoutSize(target, false);
//        minimum.width -= (getHgap() + 1);
//        return minimum;
//    }
//
//    private Dimension layoutSize(Container target, boolean preferred) {
//        synchronized (target.getTreeLock()) {
//            int targetWidth = target.getWidth();
//            Container container = target;
//
//            while (container.getSize().width == 0 && container.getParent() != null)
//                container = container.getParent();
//
//            targetWidth = container.getSize().width;
//
//            if (targetWidth == 0)
//                targetWidth = Integer.MAX_VALUE;
//
//            int hgap = getHgap();
//            int vgap = getVgap();
//            Insets insets = target.getInsets();
//            int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);
//            int x = 0, y = insets.top + vgap, rowHeight = 0;
//
//            Dimension dim = new Dimension(0, 0);
//
//            for (Component comp : target.getComponents()) {
//                if (!comp.isVisible())
//                    continue;
//
//                Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();
//
//                if (x == 0 || (x + d.width) <= maxWidth) {
//                    if (x > 0)
//                        x += hgap;
//                    x += d.width;
//                    rowHeight = Math.max(rowHeight, d.height);
//                } else {
//                    x = d.width;
//                    y += vgap + rowHeight;
//                    rowHeight = d.height;
//                }
//                dim.width = Math.max(dim.width, x);
//                dim.height = y + rowHeight;
//            }
//
//            dim.width += insets.left + insets.right + hgap * 2;
//            dim.height += insets.bottom + vgap;
//            return dim;
//        }
//    }
//}

import java.awt.*;

/**
 * A FlowLayout that wraps components to the next line automatically.
 */
public class WrapLayout extends FlowLayout {
    private static final long serialVersionUID = 1L;
    
    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getWidth();
            Container container = target;

            while (container.getSize().width == 0 && container.getParent() != null) {
                container = container.getParent();
            }

            targetWidth = container.getSize().width;

            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);
            int x = 0, y = insets.top + vgap, rowHeight = 0;

            Dimension dim = new Dimension(0, 0);

            for (Component comp : target.getComponents()) {
                if (!comp.isVisible()) {
                    continue;
                }

                Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();

                if (x == 0 || (x + d.width) <= maxWidth) {
                    if (x > 0) {
                        x += hgap;
                    }
                    x += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                    x = d.width;
                    y += vgap + rowHeight;
                    rowHeight = d.height;
                }
                dim.width = Math.max(dim.width, x);
                dim.height = y + rowHeight;
            }

            dim.width += insets.left + insets.right + hgap * 2;
            dim.height += insets.bottom + vgap;
            return dim;
        }
    }
}