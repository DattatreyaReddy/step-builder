package com.padya.stepbuilder.mappers;

import com.padya.stepbuilder.model.Property;

/**
 * @author mkasprzak
 */
public interface PsiMapper<T> {
   Property map(T psiEntity);
}
