package com.padya.stepbuilder;

import com.google.common.collect.ImmutableList;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParserFacade;
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
    public void generateBuilderPattern(final List<Property> properties, final PsiClass psiClass,
        PsiElement currentElement) {
        WriteCommandAction.runWriteCommandAction(psiClass.getProject(), () -> {
            Pojo pojo = createPojo(psiClass, properties);
            StepBuilderPattern stepBuilderPattern = composeBuilderFor(pojo, psiClass.getProject());
            includeInPojo(stepBuilderPattern, psiClass);
        });
    }

    private Pojo createPojo(PsiClass psiClass, List<Property> properties) {
        return pojo()
            .withName(psiClass.getName())
            .withProperties(properties)
            .withConstructorInjection(containsConstructorWithArgs(psiClass))
            .build();
    }

    private StepBuilderPattern composeBuilderFor(Pojo pojo, Project project) {
        return composer(project).build(pojo);
    }

    private void includeInPojo(StepBuilderPattern stepBuilderPattern, PsiClass psiClass) {
        for (PsiClass inner : ImmutableList.<PsiClass>builder()
            .add(stepBuilderPattern.getBuilderClass())
            .addAll(stepBuilderPattern.getStepInterfaces())
            .build()) {
            reformat(inner);
            psiClass.add(inner);
        }
        if (psiClass.getAllInnerClasses().length == 0) {
            return;
        }
        List<PsiClass> innerClasses = List.of(psiClass.getAllInnerClasses());
        if (!innerClasses.isEmpty()) {
            PsiClass firstInnerClass = innerClasses.get(0);
            PsiClass lastInnerClass = innerClasses.get(innerClasses.size() - 1);

            PsiParserFacade parserFacade = PsiParserFacade.getInstance(psiClass.getProject());
            PsiElement newLine = parserFacade.createWhiteSpaceFromText("\n\n\t");

            // Fold start
            psiClass.addBefore(newLine, firstInnerClass);
            psiClass.addBefore(stepBuilderPattern.getFoldStartComment(), firstInnerClass);

            // Fold End
            psiClass.addAfter(stepBuilderPattern.getFoldEndComment(), lastInnerClass);
            psiClass.addAfter(newLine, lastInnerClass);
        }
    }

    private void reformat(PsiClass psiClass) {
        CodeStyleManager.getInstance(psiClass.getProject()).reformat(psiClass);
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
