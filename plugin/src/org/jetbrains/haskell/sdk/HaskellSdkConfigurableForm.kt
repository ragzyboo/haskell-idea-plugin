package org.jetbrains.haskell.sdk

import javax.swing.*
import java.awt.GridBagLayout
import org.jetbrains.haskell.util.*
import java.awt.GridBagConstraints
import java.awt.Insets
import com.intellij.ui.DocumentAdapter
import javax.swing.event.DocumentEvent
import com.intellij.openapi.ui.TextFieldWithBrowseButton

class HaskellSdkConfigurableForm {
    var isModified: Boolean = false
    private val ghciPathField: TextFieldWithBrowseButton = TextFieldWithBrowseButton()
    private val ghcpkgPathField: TextFieldWithBrowseButton = TextFieldWithBrowseButton()
    private val cabalPathField: TextFieldWithBrowseButton = TextFieldWithBrowseButton()


    fun getContentPanel(): JComponent {
        val panel = JPanel(GridBagLayout())

        val listener : DocumentAdapter = object : DocumentAdapter() {

            override fun textChanged(e: DocumentEvent?) {
                isModified = true
            }

        }

        ghciPathField.textField.document.addDocumentListener(listener)
        ghcpkgPathField.textField.document.addDocumentListener(listener)
        cabalPathField.textField.document.addDocumentListener(listener)


        addLine(panel, 0, "ghci", ghciPathField)
        addLine(panel, 1, "ghc-pkg", ghcpkgPathField)
        addLine(panel, 2, "cabal", cabalPathField)

        return panel

    }

    private fun addLine(panel: JPanel, y: Int, label: String, textField: TextFieldWithBrowseButton) {
        val base = gridBagConstraints {
            insets = Insets(2, 0, 2, 3)
            gridy = y
        }
        panel.add(JLabel(label), base.setConstraints {
            anchor = GridBagConstraints.LINE_START
            gridx = 0
        })

        panel.add(textField, base.setConstraints {
            gridx = 1
            fill = GridBagConstraints.HORIZONTAL
            weightx = 1.0
        })

        panel.add(Box.createHorizontalStrut(1), base.setConstraints {
            gridx = 2
            weightx = 0.1
        })
    }

    fun getCabalPath(): String {
        return cabalPathField.text
    }

    fun getGhciPath(): String {
        return ghciPathField.text
    }

    fun getGhcpkgPath(): String {
        return ghcpkgPathField.text
    }


    fun init(ghciPath : String,
                    ghcpkgPath : String,
                    cabalPath : String): Unit {

        ghciPathField.text = ghciPath
        ghcpkgPathField.text = ghcpkgPath
        cabalPathField.text = cabalPath

    }


}
