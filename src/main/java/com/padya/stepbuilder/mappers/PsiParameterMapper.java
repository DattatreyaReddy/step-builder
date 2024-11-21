package com.padya.stepbuilder.mappers;

import com.google.common.base.Function;
import com.intellij.psi.PsiParameter;
import com.padya.stepbuilder.model.Property;

import static com.padya.stepbuilder.model.Property.Builder.property;

/**
 * @author mkasprzak
 */
public class PsiParameterMapper implements PsiMapper<PsiParameter> {
    @Override
    public Property map(PsiParameter psiParameter) {
        return property()
            .withName(psiParameter.getName())
            .withType(psiParameter.getType().getPresentableText())
            .build();
    }

    public static Function<PsiParameter, Property> toProperty() {
        return new Function<>() {
            private final PsiParameterMapper mapper = new PsiParameterMapper();

            @Override
            public Property apply(PsiParameter psiParameter) {
                return mapper.map(psiParameter);
            }
        };
    }
}
