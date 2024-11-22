package com.padya.stepbuilder.dialog;

/**
 * @author mkasprzak
 */
public enum DialogFactories {
    FROM_METHODS(new FromSettersDialogFactory());

    private final DialogFactory dialogFactory;

    DialogFactories(DialogFactory dialogFactory) {
        this.dialogFactory = dialogFactory;
    }

    public DialogFactory get() {
        return this.dialogFactory;
    }
}
