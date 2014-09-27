package makasprzak.idea.plugins;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.ui.CollectionListModel;

import java.util.List;

/**
 * Created by Maciej Kasprzak on 2014-09-27.
 */
public interface StepBuilderGenerator {
    void generateBuilderPattern(List<PsiField> fields, PsiClass psiClass, PsiElement currentElement);
}
