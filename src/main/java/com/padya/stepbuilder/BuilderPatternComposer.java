package com.padya.stepbuilder;

import com.padya.stepbuilder.model.Pojo;
import com.padya.stepbuilder.model.StepBuilderPattern;

public interface BuilderPatternComposer {
    StepBuilderPattern build(Pojo pojo);
}
