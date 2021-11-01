package com.github.qianmi.util

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object QianmiIconUtil : AllIcons() {

    @JvmStatic
    val qianmi: Icon = IconLoader.getIcon("/icons/qianmi.svg", javaClass)

    @JvmStatic
    val bugatti: Icon = IconLoader.getIcon("/icons/bugatti.svg", javaClass)

    @JvmStatic
    val es: Icon = IconLoader.getIcon("/icons/elasticSearch.svg", javaClass)

    @JvmStatic
    val gitlab: Icon = IconLoader.getIcon("/icons/gitlab.svg", javaClass)

    @JvmStatic
    val shell: Icon = IconLoader.getIcon("/icons/shell.svg", javaClass)
}