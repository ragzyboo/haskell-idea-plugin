package org.jetbrains.haskell.debugger.frames

import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.frame.XStackFrame
import com.intellij.ui.SimpleTextAttributes
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator
import com.intellij.xdebugger.frame.XCompositeNode
import org.jetbrains.debugger.VariableView
import org.jetbrains.debugger.VariableContextBase
import org.jetbrains.debugger.EvaluateContext
import org.jetbrains.debugger.DebuggerViewSupport
import org.jetbrains.debugger.values.PrimitiveValue
import com.intellij.openapi.application.ApplicationManager
import com.intellij.xdebugger.frame.XValueChildrenList
import org.jetbrains.debugger.VariableImpl
import org.jetbrains.debugger.values.ValueType
import com.intellij.xdebugger.XDebuggerUtil
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File
import org.jetbrains.haskell.debugger.utils.HaskellUtils

public class HaskellStackFrame(private val stackFrameInfo: HaskellStackFrameInfo?) : XStackFrame() {

    class object {
        private val STACK_FRAME_EQUALITY_OBJECT = Object()
    }

    override fun getEqualityObject(): Any? = STACK_FRAME_EQUALITY_OBJECT

    private val _sourcePosition =
        if(stackFrameInfo != null) {
            XDebuggerUtil.getInstance()!!.createPosition(
                    LocalFileSystem.getInstance()?.findFileByIoFile(File(stackFrameInfo.filePosition.filePath)),
                    HaskellUtils.haskellLineNumberToZeroBased(stackFrameInfo.filePosition.startLine))
        }
        else null

    override fun getSourcePosition(): XSourcePosition? = _sourcePosition

    /**
     * This method should return evaluator (to use 'Evaluate expression' and other such tools) but this functionality
     * is not supported yet
     */
    override fun getEvaluator(): XDebuggerEvaluator? = null

    /**
     * Stack frame appearance customization, not implemented yet, default implementation is used
     */
    //    override fun customizePresentation(component: ColoredTextContainer)

    /**
     * This method should compute local variables and other frame data to show in 'Variables' panel of 'Debug' tool window.
     * So we need to get ghci output, parse it, convert to XValueChildrenList and pass to node.addChildren() method
     */
    override fun computeChildren(node: XCompositeNode) {
        if (node.isObsolete()) {
            return
        }
        ApplicationManager.getApplication()!!.executeOnPooledThread(object : Runnable {
            override fun run() {
                try {
                    if(stackFrameInfo != null) {
                        val list = XValueChildrenList()
                        //                    list.add(createVariable("ten", "10"))
                        for (binding in stackFrameInfo.bindings) {
                            list.add(HsDebugValue(binding))
                        }
                        node.addChildren(list, true)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    node.setErrorMessage("Unable to display frame variables")
                }

            }
        })
    }

    public fun createVariable(name: String, value: String, valueType: ValueType = ValueType.STRING): VariableView {
        return VariableView(VariableImpl(name, PrimitiveValue(valueType, value)),
                object : VariableContextBase() {
                    override fun getEvaluateContext(): EvaluateContext {
                        throw UnsupportedOperationException()
                    }
                    override fun watchableAsEvaluationExpression(): Boolean {
                        return false
                    }
                    override fun getDebugProcess(): DebuggerViewSupport {
                        return DebuggerViewSupport.SimpleDebuggerViewSupport()
                    }

                })
    }
}
