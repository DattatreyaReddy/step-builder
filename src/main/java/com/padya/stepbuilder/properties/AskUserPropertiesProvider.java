package com.padya.stepbuilder.properties;

import com.intellij.psi.PsiClass;
import com.padya.stepbuilder.dialog.DialogFactory;
import com.padya.stepbuilder.dialog.GeneratorDialog;

public class AskUserPropertiesProvider implements PropertiesProvider{

    private DialogFactory dialogFactory;

    public AskUserPropertiesProvider(DialogFactory dialogFactory) {
        this.dialogFactory = dialogFactory;
    }

    @Override
    public void getProperties(PsiClass psiClass, PropertiesConsumer consumer) {
        GeneratorDialog generatorDialog = dialogFactory.create(psiClass);
        generatorDialog.show();
        if (generatorDialog.isOK())
            consumer.consume(generatorDialog.getProperties());
    }
}
