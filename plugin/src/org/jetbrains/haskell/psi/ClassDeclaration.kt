package org.jetbrains.haskell.psi

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import org.jetbrains.haskell.parser.ElementFactory
import com.intellij.psi.util.PsiTreeUtil

/**
 * Created by atsky on 23/04/14.
 */
public class ClassDeclaration(node : ASTNode) : ASTWrapperPsiElement(node) {
    class object : ElementFactory {
        override fun create(node: ASTNode) = ClassDeclaration(node)
    }

    fun getValueDeclarationList() : List<ValueDeclaration> {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, javaClass())
    }
}