package com.padya.stepbuilder.dialog;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypes;
import java.util.List;
import com.padya.stepbuilder.mappers.PsiSetterMapper;
import com.padya.stepbuilder.model.PsiPropertyContainer;
import java.util.Objects;

import static com.padya.stepbuilder.model.PsiPropertyContainer.Builder.psiPropertyContainer;
import static java.util.Arrays.asList;

public class FromSettersDialogFactory implements DialogFactory {

    private final PsiSetterMapper psiSetterMapper = new PsiSetterMapper();

    @Override
    public GeneratorDialog create(PsiClass psiClass) {
        return new GeneratorDialog(psiClass, transformFieldsToProperties(psiClass));
    }

    private List<PsiPropertyContainer> transformFieldsToProperties(PsiClass psiClass) {
        List<PsiMethod> allMethods = asList(psiClass.getAllMethods());
        Iterable<PsiMethod> nonConstructorMethods =
            Iterables.filter(allMethods, isNotAConstructor());
        Iterable<PsiMethod> voidMethods = Iterables.filter(nonConstructorMethods, isVoid());
        Iterable<PsiMethod> oneArgVoidMethods = Iterables.filter(voidMethods, hasOneArg());
        Iterable<PsiMethod> setters = Iterables.filter(oneArgVoidMethods, startWithSet());
        Iterable<PsiPropertyContainer> setterContainers =
            Iterables.transform(setters, toPsiPropertyContainer());
        return ImmutableList.copyOf(setterContainers);
    }

    private Predicate<PsiMethod> isNotAConstructor() {
        return psiMethod -> !psiMethod.isConstructor();
    }

    private Function<PsiMethod, PsiPropertyContainer> toPsiPropertyContainer() {
        return psiField -> psiPropertyContainer()
            .withPsiElement(psiField)
            .withProperty(psiSetterMapper.map(psiField))
            .build();
    }

    private Predicate<PsiMethod> startWithSet() {
        return psiMethod -> psiMethod.getName().startsWith("set");
    }

    private Predicate<PsiMethod> hasOneArg() {
        return psiMethod -> psiMethod.getParameterList().getParametersCount() == 1;
    }

    private Predicate<PsiMethod> isVoid() {
        return psiMethod -> Objects.equals(psiMethod.getReturnType(), PsiTypes.voidType());
    }
}
