package com.padya.stepbuilder.dialog;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.psi.PsiElement;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import com.padya.stepbuilder.model.PsiPropertyContainer;

public class PropertyCellRenderer implements ListCellRenderer<PsiPropertyContainer> {
    private final DefaultPsiElementCellRenderer renderer = new DefaultPsiElementCellRenderer();

    private PsiElement getValue(Object value) {
        return ((PsiPropertyContainer) value).getPsiElement();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends PsiPropertyContainer> list,
        PsiPropertyContainer value, int index, boolean isSelected, boolean cellHasFocus) {
        return renderer.getListCellRendererComponent(list, getValue(value), index, isSelected,
            cellHasFocus);
    }
}
