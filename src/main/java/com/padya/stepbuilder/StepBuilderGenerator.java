package com.padya.stepbuilder;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import java.util.List;
import com.padya.stepbuilder.model.Property;

/**
 * Created by Maciej Kasprzak on 2014-09-27.
 */
public interface StepBuilderGenerator {
    void generateBuilderPattern(List<Property> properties, PsiClass psiClass,
        PsiElement currentElement);
}
