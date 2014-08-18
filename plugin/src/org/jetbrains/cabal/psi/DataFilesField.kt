package org.jetbrains.cabal.psi

import com.intellij.lang.ASTNode
import org.jetbrains.cabal.psi.MultiValueField
import org.jetbrains.cabal.psi.PathsField
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

public class DataFilesField(node: ASTNode) : MultiValueField(node), PathsField {

    public override fun getParentDirs(prefixPath: Path, originalRootDir: VirtualFile): List<VirtualFile> {
        var dataDir = prefixPath.getCabalFile().getDataDir()?.getFileWithParent(originalRootDir)  ?:  originalRootDir
        val dirPath = File(prefixPath.getPathWithParent(dataDir)).getParent()
        val dirFile = if (dirPath == null) null else originalRootDir.getFileSystem().findFileByPath(dirPath)
        if (dirFile == null) return listOf()
        return listOf(dirFile)
    }
}