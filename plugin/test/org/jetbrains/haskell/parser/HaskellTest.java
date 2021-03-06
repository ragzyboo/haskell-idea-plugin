package org.jetbrains.haskell.parser;

import com.intellij.testFramework.ParsingTestCase;

public class HaskellTest extends ParsingTestCase {
    static {
        System.setProperty("idea.platform.prefix", "Idea");
    }

    public HaskellTest() {
        super("haskellParserTests", "hs", new HaskellParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "data";
    }

    public void testALotIndents() throws Exception { doTest(true); }

    public void testBacktracking() throws Exception { doTest(true); }

    public void testDataType() throws Exception { doTest(true); }

    public void testDoNotation() throws Exception { doTest(true); }

    public void testGADT() throws Exception { doTest(true); }

    public void testGCD() throws Exception { doTest(true); }

    public void testImports() throws Exception { doTest(true); }

    public void testImportsWithData() throws Exception { doTest(true); }

    public void testInstance() throws Exception { doTest(true); }

    public void testFBind() throws Exception { doTest(true); }

    public void testHelloWorld() throws Exception { doTest(true); }

    public void testMaximum() throws Exception { doTest(true); }

    public void testNoModuleKeyword() throws Exception { doTest(true); }

    public void testNumbers() throws Exception { doTest(true); }

}
