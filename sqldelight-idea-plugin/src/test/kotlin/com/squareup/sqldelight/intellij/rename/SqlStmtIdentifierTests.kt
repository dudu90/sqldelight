package com.squareup.sqldelight.intellij.rename

import com.alecstrong.sqlite.psi.core.psi.SqliteTableName
import com.alecstrong.sqlite.psi.core.psi.SqliteViewName
import com.google.common.truth.Truth.assertThat
import com.intellij.psi.PsiElement
import com.squareup.sqldelight.core.lang.psi.StmtIdentifierMixin
import com.squareup.sqldelight.intellij.SqlDelightProjectTestCase

class SqlStmtIdentifierTests : SqlDelightProjectTestCase() {
  fun testRenamingIdentifierRenamesKotlinAndJavaUsages() {
    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/sqldelight/com/example/Main.sq")!!
    )
    val identifier = searchForElement<StmtIdentifierMixin>("someQuery").single()

    myFixture.renameElement(identifier, "newSomeQuery")

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/java/com/example/SampleClass.java")!!
    )
    assertThat(searchForElement<PsiElement>("newSomeQuery")).hasSize(1)
    assertThat(searchForElement<PsiElement>("someQuery")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/KotlinClass.kt")!!
    )
    assertThat(searchForElement<PsiElement>("newSomeQuery")).hasSize(1)
    assertThat(searchForElement<PsiElement>("someQuery")).isEmpty()
  }

  fun testRenamingQueryWithMultipleMethods() {
    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/sqldelight/com/example/Main.sq")!!
    )
    val identifier = searchForElement<StmtIdentifierMixin>("multiQuery").single()

    myFixture.renameElement(identifier, "newMultiQuery")

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/KotlinClass.kt")!!
    )
    assertThat(searchForElement<PsiElement>("newMultiQuery")).hasSize(2)
    assertThat(searchForElement<PsiElement>("multiQuery")).isEmpty()
  }

  fun testRenamingQueryWithCustomTypes() {
    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/sqldelight/com/example/Main.sq")!!
    )
    val identifier = searchForElement<StmtIdentifierMixin>("generatesType").single()
    myFixture.renameElement(identifier, "newGeneratesType")

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/GeneratesTypeImpl.kt")!!
    )
    assertThat(searchForElement<PsiElement>("NewGeneratesType")).hasSize(1)
    assertThat(searchForElement<PsiElement>("GeneratesType")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/KotlinClass.kt")!!
    )
    assertThat(searchForElement<PsiElement>("NewGeneratesType")).hasSize(1)
    assertThat(searchForElement<PsiElement>("GeneratesType")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/java/com/example/GeneratesTypeImplJava.java")!!
    )
    assertThat(searchForElement<PsiElement>("NewGeneratesTypeModel")).hasSize(1)
    assertThat(searchForElement<PsiElement>("GeneratesTypeModel")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/java/com/example/SampleClass.java")!!
    )
    assertThat(searchForElement<PsiElement>("NewGeneratesType")).hasSize(1)
    assertThat(searchForElement<PsiElement>("GeneratesType")).isEmpty()
  }

  fun testRenamingTableName() {
    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/sqldelight/com/example/Main.sq")!!
    )
    val identifier = searchForElement<SqliteTableName>("main").first()
    myFixture.renameElement(identifier, "newMain")

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/MainImpl.kt")!!
    )
    assertThat(searchForElement<PsiElement>("NewMain")).hasSize(1)
    assertThat(searchForElement<PsiElement>("Main")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/java/com/example/MainImplJava.java")!!
    )
    assertThat(searchForElement<PsiElement>("NewMainModel")).hasSize(1)
    assertThat(searchForElement<PsiElement>("MainModel")).isEmpty()
  }

  fun testRenamingViewName() {
    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/sqldelight/com/example/Main.sq")!!
    )
    val identifier = searchForElement<SqliteViewName>("someView").first()
    myFixture.renameElement(identifier, "newView")

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/kotlin/com/example/ViewImpl.kt")!!
    )
    assertThat(searchForElement<PsiElement>("NewView")).hasSize(1)
    assertThat(searchForElement<PsiElement>("SomeView")).isEmpty()

    myFixture.openFileInEditor(
        tempRoot.findFileByRelativePath("src/main/java/com/example/ViewImplJava.java")!!
    )
    assertThat(searchForElement<PsiElement>("NewViewModel")).hasSize(1)
    assertThat(searchForElement<PsiElement>("SomeViewModel")).isEmpty()
  }
}