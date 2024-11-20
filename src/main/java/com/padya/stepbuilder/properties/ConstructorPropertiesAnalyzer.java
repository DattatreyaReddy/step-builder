package com.padya.stepbuilder.properties;

import com.intellij.psi.PsiClass;
import java.util.List;
import com.padya.stepbuilder.PsiClassAdapter;
import com.padya.stepbuilder.model.Property;

import static com.google.common.collect.Lists.transform;
import static com.padya.stepbuilder.mappers.PsiParameterMapper.toProperty;

public class ConstructorPropertiesAnalyzer {

    public List<Property> getBiggestConstructorArgs(PsiClass psiClass) {
        return transform(PsiClassAdapter.forA(psiClass).getBiggestConstructorsArgs(), toProperty());
    }
}
