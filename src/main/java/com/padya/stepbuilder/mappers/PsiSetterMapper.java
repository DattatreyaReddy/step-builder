package com.padya.stepbuilder.mappers;

import com.intellij.psi.PsiMethod;
import com.padya.stepbuilder.model.Property;
import org.apache.commons.lang.StringUtils;

import static com.padya.stepbuilder.model.Property.Builder.property;

public class PsiSetterMapper implements PsiMapper<PsiMethod> {

    @Override
    public Property map(PsiMethod psiMethod) {
        return property()
            .withName(getPropertyName(psiMethod))
            .withType(getPropertyType(psiMethod))
            .build();
    }

    private String getPropertyType(PsiMethod psiMethod) {
        return psiMethod.getParameterList().getParameters()[0].getType().getPresentableText();
    }

    private String getPropertyName(PsiMethod psiMethod) {
        return StringUtils.uncapitalize(psiMethod.getName().substring(3));
    }
}
