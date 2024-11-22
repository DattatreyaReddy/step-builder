package com.padya.stepbuilder.model;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiComment;
import java.util.List;

public class StepBuilderPattern {
    private final PsiClass builderClass;

    private final List<PsiClass> stepInterfaces;

    private final PsiComment foldStartComment;

    private final PsiComment foldEndComment;

    public StepBuilderPattern(PsiClass builderClass, List<PsiClass> stepInterfaces,
        PsiComment foldStartComment, PsiComment foldEndComment) {
        this.builderClass = builderClass;
        this.stepInterfaces = stepInterfaces;
        this.foldStartComment = foldStartComment;
        this.foldEndComment = foldEndComment;
    }

    public PsiClass getBuilderClass() {
        return builderClass;
    }

    public List<PsiClass> getStepInterfaces() {
        return stepInterfaces;
    }

    public PsiComment getFoldStartComment() {
        return foldStartComment;
    }

    public PsiComment getFoldEndComment() {
        return foldEndComment;
    }

    @Override public String toString() {
        return "StepBuilderPattern{" +
            "builderClass=" + builderClass +
            ", stepInterfaces=" + stepInterfaces +
            ", foldStartComment=" + foldStartComment +
            ", foldEndComment=" + foldEndComment +
            '}';
    }

    public static interface BuilderClassStep {
        StepInterfacesStep withBuilderClass(PsiClass builderClass);
    }

    public static interface StepInterfacesStep {
        FoldStartCommentStep withStepInterfaces(List<PsiClass> stepInterfaces);
    }

    public static interface FoldStartCommentStep {
        FoldEndCommentStep withFoldStartComment(PsiComment foldStartComment);
    }

    public static interface FoldEndCommentStep {
        BuildStep withFoldEndComment(PsiComment foldEndComment);
    }

    public static interface BuildStep {
        StepBuilderPattern build();
    }

    public static class Builder
        implements BuilderClassStep, StepInterfacesStep, FoldStartCommentStep, FoldEndCommentStep,
        BuildStep {
        private PsiClass builderClass;

        private List<PsiClass> stepInterfaces;

        private PsiComment foldStartComment;

        private PsiComment foldEndComment;

        private Builder() {
        }

        public static BuilderClassStep stepBuilderPattern() {
            return new Builder();
        }

        @Override
        public StepInterfacesStep withBuilderClass(PsiClass builderClass) {
            this.builderClass = builderClass;
            return this;
        }

        @Override
        public FoldStartCommentStep withStepInterfaces(List<PsiClass> stepInterfaces) {
            this.stepInterfaces = stepInterfaces;
            return this;
        }

        @Override
        public FoldEndCommentStep withFoldStartComment(PsiComment foldStartComment) {
            this.foldStartComment = foldStartComment;
            return this;
        }

        @Override
        public BuildStep withFoldEndComment(PsiComment foldEndComment) {
            this.foldEndComment = foldEndComment;
            return this;
        }

        @Override
        public StepBuilderPattern build() {
            return new StepBuilderPattern(
                this.builderClass,
                this.stepInterfaces,
                this.foldStartComment,
                this.foldEndComment
            );
        }
    }
}
