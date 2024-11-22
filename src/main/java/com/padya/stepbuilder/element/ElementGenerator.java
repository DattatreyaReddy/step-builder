package com.padya.stepbuilder.element;

import com.padya.stepbuilder.model.Pojo;
import com.padya.stepbuilder.model.Property;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.transform;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

public class ElementGenerator {
    public String startFoldComment() {
        return "// <editor-fold defaultstate=\"collapsed\" desc=\"Step-Builder\">";
    }

    public String endFoldComment() {
        return "// </editor-fold>";
    }

    public String builderClass(Pojo pojo) {
        String steps = on(", ").join(transform(pojo.getProperties(), this::stepName));
        if (pojo.getProperties().isEmpty()) {
            return "public static class Builder implements BuildStep {}";
        } else {
            return "public static class Builder implements " + steps + ", BuildStep {}";
        }
    }

    public String fieldDeclaration(Property property) {
        return format("private %s %s;", property.getType().replaceAll("\\s?\\.\\.\\.", "[]"),
            property.getName());
    }

    public String builderConstructor() {
        return "private Builder() {}";
    }

    public String builderFactoryMethod(Pojo pojo) {
        if (pojo.getProperties().isEmpty()) {
            return format("""
                public static %1$s %2$s() {
                    return new %1$s();
                }""", pojo.getName(), uncapitalize(pojo.getName()));
        }
        return format("""
            public static %s %s() {
                return new Builder();
            }""", stepName(pojo.getProperties().get(0)), uncapitalize(pojo.getName()));
    }

    public String stepMethod(Property forProperty, Property nextProperty) {
        return format("""
                @Override
                public %1$s with%4$s(%3$s %2$s) {
                \tthis.%2$s = %2$s;
                \treturn this;
                }
                """,
            nextProperty != null ? stepName(nextProperty) : "BuildStep",
            forProperty.getName(),
            forProperty.getType(),
            capitalize(forProperty.getName()));
    }

    public String buildMethod(final Pojo pojo) {
        if (pojo.isConstructorInjection()) {
            return format("""
                @Override
                public %1$s build() {
                    return new %1$s(
                    %2$s
                );
                }""", pojo.getName(), on(",\n    ").join(transform(pojo.getProperties(),
                property -> "this." + property.getName())));
        }
        return format("""
                @Override
                public %1$s build() {
                    %1$s %2$s = new %1$s();
                    %3$s
                    return %2$s;
                }""", pojo.getName(), uncapitalize(pojo.getName()),
            on("\n    ").join(transform(pojo.getProperties(),
                property -> format("%s.set%s(this.%s);", uncapitalize(pojo.getName()),
                    capitalize(property.getName()), property.getName()))));
    }

    public String stepInterface(Property forProperty, Property nextProperty) {
        return format("""
                public static interface %1$s {
                \t%2$s with%3$s(%4$s %5$s);
                }
                """,
            stepName(forProperty),
            nextProperty != null ? stepName(nextProperty) : "BuildStep",
            capitalize(forProperty.getName()),
            forProperty.getType(),
            forProperty.getName()
        );
    }

    public String buildStepInterface(Pojo pojo) {
        return format("""
            public static interface BuildStep {
                %s build();
            }""", pojo.getName());
    }

    private String stepName(Property property) {
        return capitalize(property.getName()) + "Step";
    }
}
