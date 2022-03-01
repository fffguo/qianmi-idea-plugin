package com.github.qianmi.ui

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.extend.JPanelExtend.registerEscEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import javax.swing.JDialog
import javax.swing.JPanel

abstract class MyJDialog : JDialog() {

    //idea项目
    abstract var project: Project

    open fun open() {
        val rootContainer = this.getRootContainer()
        //esc退出
        rootContainer.registerEscEvent(this)
        //容器
        this.contentPane = rootContainer
        //自适应
        pack()
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(project))
        //显示
        isVisible = true
        modalityType = ModalityType.APPLICATION_MODAL
    }

    //获取根节点
    abstract fun getRootContainer(): JPanel

    //idea窗口项目
    fun getMyProject(): IdeaProject.MyProject {
        return IdeaProject.getInstance(this.project)
    }
}