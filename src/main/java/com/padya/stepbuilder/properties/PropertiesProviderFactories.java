package com.padya.stepbuilder.properties;

import com.padya.stepbuilder.dialog.DialogFactories;

public enum PropertiesProviderFactories {
    FROM_SETTERS(new AskUserPropertiesProvider(DialogFactories.FROM_METHODS.get())),
    FROM_CONSTRUCTOR_ARGS(new UseConstructorArgsPropertiesProvider(new ConstructorPropertiesAnalyzer()));

    private final PropertiesProvider propertiesStrategy;

    public PropertiesProvider get(){
        return this.propertiesStrategy;
    }

    private PropertiesProviderFactories(PropertiesProvider propertiesStrategy) {
        this.propertiesStrategy = propertiesStrategy;
    }
}
