package com.padya.stepbuilder;

public class Main {
    public static void main(String[] args) {
        Message.Builder.message()
            .withId("1")
            .withGroupId("1")
            .withDateCreated(1L)
            .withLastModified(2L)
            .withMessage("Hello World!")
            .build();
    }
}
