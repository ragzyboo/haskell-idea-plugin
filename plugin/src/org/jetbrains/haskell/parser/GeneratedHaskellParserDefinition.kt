package org.jetbrains.haskell.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.IFileElementType
import org.jetbrains.haskell.fileType.HaskellFile
import org.jetbrains.haskell.HaskellLanguage
import org.jetbrains.haskell.parser.lexer.HaskellLexer
import com.intellij.lang.ParserDefinition.SpaceRequirements
import org.jetbrains.haskell.parser.token.*
import com.intellij.extapi.psi.ASTWrapperPsiElement
//import org.jetbrains.grammar.HaskellTokens
import org.jetbrains.haskell.psi.Module
import org.jetbrains.haskell.parser.rules.ParserState
import java.util.ArrayList
import org.jetbrains.grammar.dumb.GLLParser
import org.jetbrains.haskell.parser.lexer.HaskellIndentLexer


public class GeneratedHaskellParserDefinition() : ParserDefinition {
    val HASKELL_FILE = IFileElementType(HaskellLanguage.INSTANCE)

    override fun createLexer(project: Project?): Lexer = HaskellIndentLexer()

    override fun getFileNodeType(): IFileElementType = HASKELL_FILE

    override fun getWhitespaceTokens() = WHITESPACES

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun getStringLiteralElements(): TokenSet = TokenSet.create(STRING)


    override fun createParser(project: Project?): PsiParser =
        object : PsiParser {
            override fun parse(root: IElementType?, builder: PsiBuilder?): ASTNode {
                return org.jetbrains.grammar.HaskellParser(builder!!).parse(root!!)
            }
        }

    override fun createFile(viewProvider: FileViewProvider?): PsiFile =
        HaskellFile(viewProvider!!)


    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?) =
        ParserDefinition.SpaceRequirements.MAY


    override fun createElement(node: ASTNode?): PsiElement {
        val elementType = node!!.getElementType()
        if (elementType is HaskellCompositeElementType) {
            val constructor = elementType.constructor
            if (constructor != null) {
                return constructor(node)
            }
        }
        return ASTWrapperPsiElement(node)
    }

}
