package com.padya.stepbuilder.dialog;

import com.intellij.psi.PsiClass;

/**
 * @author mkasprzak
 */
public interface DialogFactory {
   GeneratorDialog create(PsiClass psiClass);
}
