package com.google.javascript.cl2dts;

import static com.google.javascript.cl2dts.ProgramSubject.assertThatProgram;
import static java.util.Collections.singletonList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class MultiFileTest {

  @Rule
  public TestName name = new TestName();

  // Repro for https://github.com/angular/closure-to-dts/issues/101
  @Test
  public void shouldPruneProvidesFromNonrootFile() throws Exception {
    String expected = DeclarationGeneratorTests.getTestFileText(input("require.d.ts"));
    assertThatProgram(
        singletonList(input("require.js")),
        singletonList(input("provide.js")))
        .generatesDeclarations(false, expected);
  }

  private File input(String filename) {
    Path testDir = FileSystems.getDefault().getPath("src", "test", "java");
    String packageName = ProgramSubject.class.getPackage().getName();
    Path myPackage = testDir.resolve(packageName.replace('.', File.separatorChar));
    return myPackage.resolve(name.getMethodName()).resolve(filename).toAbsolutePath().toFile();
  }
}