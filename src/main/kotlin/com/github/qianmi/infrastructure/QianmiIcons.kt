package com.github.qianmi.infrastructure

import com.intellij.openapi.util.IconLoader

/**
 * icon生成器
 * https://bjansen.github.io/intellij-icon-generator/
 */
object QianmiIcons {


    @JvmField
    val qianmi = IconLoader.getIcon("/icons/qianmi.svg", javaClass)

    @JvmField
    val gitlab = IconLoader.getIcon("/icons/gitlab.svg", javaClass)

    @JvmField
    val shell = IconLoader.getIcon("/icons/shell.svg", javaClass)

    @JvmField
    val sftp = IconLoader.getIcon("/icons/sftp.svg", javaClass)

    @JvmField
    val dubboInvoke = IconLoader.getIcon("/icons/dubboInvoke.svg", javaClass)

    @JvmField
    val jenkinsPackage = IconLoader.getIcon("/icons/jenkinsPackage.svg", javaClass)

    @JvmField
    val remoteJvmDebug = IconLoader.getIcon("/icons/remoteJvmDebug.svg", javaClass)

    @JvmField
    val arthas = IconLoader.getIcon("/icons/arthas.svg", javaClass)

    @JvmField
    val tailLog = IconLoader.getIcon("/icons/tailLog.svg", javaClass)

}