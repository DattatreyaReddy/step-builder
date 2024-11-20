package com.padya.stepbuilder;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.List;
import com.padya.stepbuilder.model.Property;
import com.padya.stepbuilder.properties.*;
import org.jetbrains.annotations.NotNull;

public class StepBuilderGeneratorAction extends AnAction {

    private final StepBuilderGenerator stepBuilderGenerator = new StepBuilderGeneratorImpl();

    @Override public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(AnActionEvent e) {
        boolean val = getCurrentClass(e) != null;
        e.getPresentation().setEnabled(val);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        PsiClass psiClass = getPsiClass(getCurrentElement(actionEvent));
        PropertiesProvider propertiesProvider = new PropertiesProviderFactory().createFor(psiClass);
        generate(getCurrentElement(actionEvent), psiClass, propertiesProvider);
    }

    private PsiClass getPsiClass(PsiElement currentElement) {
        return currentElement == null ? null : PsiTreeUtil.getParentOfType(currentElement, PsiClass.class);
    }

    private void generate(final PsiElement currentElement, final PsiClass psiClass, PropertiesProvider propertiesProvider) {
        propertiesProvider.getProperties(psiClass, new PropertiesConsumer() {
            @Override
            public void consume(List<Property> properties) {
                stepBuilderGenerator.generateBuilderPattern(properties, psiClass, currentElement);
            }
        });
    }

    private PsiClass getCurrentClass(AnActionEvent e) {
        PsiElement currentElement = getCurrentElement(e);
        return currentElement == null ? null : PsiTreeUtil.getParentOfType(currentElement, PsiClass.class);
    }

    private PsiElement getCurrentElement(AnActionEvent e) {
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        return psiFile.findElementAt(offset);
    }
}
