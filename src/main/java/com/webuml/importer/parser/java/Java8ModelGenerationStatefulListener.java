package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassCandidateRelationType;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import com.webuml.importer.parser.candidate.PackageResolveState;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Java8ModelGenerationStatefulListener extends Java8BaseListener {

  private String packageName = null;
  private ClassDeclarationCandidate classDeclarationCandidate = null;
  private List<ClassRelationCandidate> classRelationCandidates = new ArrayList<>();
  private List<String> packageImports = new ArrayList<>();
  private List<String> wildCardPackageImports = new ArrayList<>();

  @Override
  public void exitImportDeclaration(@NotNull Java8Parser.ImportDeclarationContext ctx) {
    if (isWildCardImport(ctx)) {
      String importName = ctx.qualifiedName().getText();
      wildCardPackageImports.add(importName);
    }
    else {
      String importName = ctx.qualifiedName().getText();
      packageImports.add(importName);
    }
  }

  private boolean isWildCardImport(Java8Parser.ImportDeclarationContext ctx) {
    List<ParseTree> children = ctx.children;
    if (children.size() > 3) {
      if (children.get(children.size() - 3).getText().equals(".")
          && children.get(children.size() - 2).getText().equals("*")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void exitPackageDeclaration(@NotNull Java8Parser.PackageDeclarationContext ctx) {
    packageName = ctx.qualifiedName().getText();
  }

  @Override
  public void exitClassDeclaration(@NotNull Java8Parser.ClassDeclarationContext ctx) {
    String className = ctx.Identifier().getSymbol().getText();
    classDeclarationCandidate = new ClassDeclarationCandidate();
    classDeclarationCandidate.setClassName(className);
    classDeclarationCandidate.setPackageName(packageName != null ? packageName : "");
    classDeclarationCandidate.setClassRelationCandidates(classRelationCandidates);
  }

  @Override
  public void exitMemberDeclaration(@NotNull Java8Parser.MemberDeclarationContext ctx) {
    Java8Parser.FieldDeclarationContext fieldDeclarationContext = ctx.fieldDeclaration();
    if (fieldDeclarationContext != null) {
      ClassRelationCandidate classRelation = new ClassRelationCandidate();
      classRelation.setAttributeName(getVariableName(fieldDeclarationContext));
      classRelation.setRelationType(ClassCandidateRelationType.MEMBER);
      classRelation.setPackageResolveState(PackageResolveState.UNRESOLVED);
      String typeName = getTypeName(fieldDeclarationContext);

      classRelation.setToClassName(typeName);
      for (String packageImport : packageImports) {
        if (packageImport.endsWith("." + typeName)) {
          String packageName = packageImport.replace("." + typeName, "");
          classRelation.setToPackageName(packageName);
          classRelation.setPackageResolveState(PackageResolveState.RESOLVED);
        }
      }
      for (String packageImport : wildCardPackageImports) {
        classRelation.getPossibleToPackageNames().add(packageImport);
        classRelation.setPackageResolveState(PackageResolveState.WILDCARD);
      }
      classRelationCandidates.add(classRelation);
    }
  }

  private String getTypeName(Java8Parser.FieldDeclarationContext fieldDeclarationContext) {
    Java8Parser.TypeContext type = fieldDeclarationContext.type();
    Java8Parser.ClassOrInterfaceTypeContext classOrInterfaceTypeContext = type.classOrInterfaceType();
    if (classOrInterfaceTypeContext != null) {
      return classOrInterfaceTypeContext.getChild(0).getText();
    }
    else if (type.primitiveType() != null) {
      Java8Parser.PrimitiveTypeContext primitiveTypeContext = type.primitiveType();
      ParseTree child = primitiveTypeContext.getChild(0);
      return child.getText();
    }
    return "";
  }

  private String getVariableName(Java8Parser.FieldDeclarationContext fieldDeclarationContext) {
    return fieldDeclarationContext.variableDeclarators().variableDeclarator().get(0).children.get(0).getChild(0).getText();
  }

  public List<ClassDeclarationCandidate> getClassDeclarationCandidates() {
    if (classDeclarationCandidate != null) {
      return Arrays.asList(classDeclarationCandidate);
    }
    return new ArrayList<>();
  }
}
