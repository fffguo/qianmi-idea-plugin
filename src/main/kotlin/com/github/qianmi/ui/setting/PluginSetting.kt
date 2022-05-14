package com.github.qianmi.ui.setting

import com.github.qianmi.infrastructure.ConfigInitService
import com.github.qianmi.infrastructure.storage.AccountConfig
import com.github.qianmi.infrastructure.storage.ProjectConfig
import com.github.qianmi.infrastructure.storage.ShellConfig
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class PluginSetting(private var project: Project) : Configurable {

    private var component: PluginSettingPage = PluginSettingPage(project)

    private var accountConfig: AccountConfig = AccountConfig.getInstance()
    private var shellConfig: ShellConfig = ShellConfig.getInstance()
    private var projectConfig: ProjectConfig = project.getService(ProjectConfig::class.java)

    /**
     * 设置名称
     */
    override fun getDisplayName(): String {
        return "千米网"
    }

    /**
     * 焦点位置
     */
    override fun getPreferredFocusedComponent(): JComponent {
        return this.component.domainAccountText
    }

    /**
     * 创建视图
     */
    override fun createComponent(): JComponent {
        return this.component.contentPanel
    }

    /**
     * 配置修改标识
     */
    override fun isModified(): Boolean {
        //域账号
        val domainModified = accountConfig.userName != this.component.domainAccountText.text
                || accountConfig.passwd != String(this.component.domainPasswordText.password)
        //shell账号
        val linuxModified = shellConfig.userName != this.component.shellAccountText.text
                || shellConfig.passwd != String(this.component.shellPasswordText.password)
                || shellConfig.port != this.component.shellPortText.text
                || shellConfig.jvmPort != this.component.shellJvmPortText.text
                || shellConfig.dir != this.component.shellDirText.text
                || projectConfig.logCommand != this.component.shellLogCommandText.text
        return domainModified || linuxModified
    }

    /**
     * 应用配置
     */
    override fun apply() {
        //域账号
        accountConfig.userName = this.component.domainAccountText.text
        accountConfig.passwd = String(this.component.domainPasswordText.password)
        //shell账号
        shellConfig.userName = this.component.shellAccountText.text
        shellConfig.passwd = String(this.component.shellPasswordText.password)
        shellConfig.port = this.component.shellPortText.text
        shellConfig.jvmPort = this.component.shellJvmPortText.text
        shellConfig.dir = this.component.shellDirText.text
        projectConfig.logCommand = this.component.shellLogCommandText.text
        //清空cookie
        BugattiHttpUtil.clearCookie()
        //刷新配置
        project.getService(ConfigInitService::class.java).init()
    }

    /**
     * 初始化配置
     */
    override fun reset() {
        //域账号
        this.component.domainAccountText.text = accountConfig.userName
        this.component.domainPasswordText.text = accountConfig.passwd
        //shell账号
        this.component.shellAccountText.text = shellConfig.userName
        this.component.shellPasswordText.text = shellConfig.passwd
        this.component.shellPortText.text = shellConfig.port
        this.component.shellJvmPortText.text = shellConfig.jvmPort
        this.component.shellDirText.text = shellConfig.dir
        this.component.shellLogCommandText.text = projectConfig.logCommand
    }

}