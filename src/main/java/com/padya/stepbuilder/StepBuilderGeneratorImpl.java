package com.padya.stepbuilder;

import com.google.common.collect.ImmutableList;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.CodeStyleManager;
import java.util.List;
import com.padya.stepbuilder.element.ElementGenerator;
import com.padya.stepbuilder.model.Pojo;
import com.padya.stepbuilder.model.Property;
import com.padya.stepbuilder.model.StepBuilderPattern;

import static com.intellij.psi.JavaPsiFacade.getElementFactory;
import static com.padya.stepbuilder.model.Pojo.Builder.pojo;

/**
 * @author makasprzak
 */
public class StepBuilderGeneratorImpl implements StepBuilderGenerator {
    @Override
    public void generateBuilderPattern(final List<Property> properties, final PsiClass psiClass, PsiElement currentElement) {
        new WriteCommandAction.Simple(psiClass.getProject()) {
            @Override
            protected void run() throws Throwable {
                includeInPojo(composeBuilderFor(pojo()
                        .withName(psiClass.getName())
                        .withProperties(properties)
                        .withConstructorInjection(containsConstructorWithArgs(psiClass))
                        .build()));
            }

            private StepBuilderPattern composeBuilderFor(Pojo pojo) {
                return composer(getProject()).build(pojo);
            }

            private void includeInPojo(StepBuilderPattern stepBuilderPattern) {
                for (PsiClass inner : ImmutableList.<PsiClass>builder()
                        .add(stepBuilderPattern.getBuilderClass())
                        .addAll(stepBuilderPattern.getStepInterfaces())
                        .build()) {
                    reformat(inner);
                    psiClass.add(inner);
                }
            }


            private void reformat(PsiClass psiClass) {
                CodeStyleManager.getInstance(getProject()).reformat(psiClass);
            }
        }.execute();
    }

    private BuilderPatternComposerImpl composer(Project project) {
        return new BuilderPatternComposerImpl(getElementFactory(project), new ElementGenerator());
    }

    private boolean containsConstructorWithArgs(PsiClass psiClass) {
        for (PsiMethod constructor : psiClass.getConstructors()) {
            if (constructor.getParameterList().getParametersCount() > 0) return true;
        }
        return false;
    }

}
