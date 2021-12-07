package com.github.qianmi.ui

import com.github.qianmi.services.ConfigInitService
import com.github.qianmi.storage.DomainConfig
import com.github.qianmi.storage.ShellConfig
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.HttpUtil.isOk
import com.github.qianmi.util.JMessageUtil
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.*

class PluginSettingPage : Configurable {
    private var project: Project? = null
    private var domainConfig: DomainConfig
    private var shellConfig: ShellConfig

    //根容器
    private var contentPanel: JPanel? = null

    //tab容器
    private var tabPanel: JTabbedPane? = null

    //域账号容器
    private var domainTabPanel: JPanel? = null

    /* 域账号 */
    //域账号-用户名
    private var domainAccountPanel: JPanel? = null
    private var domainAccountJLabel: JLabel? = null
    private var domainAccountText: JTextField? = null

    //域账号-密码
    private var domainPasswordPanel: JPanel? = null
    private var domainPasswordJLabel: JLabel? = null
    private var domainPasswordText: JPasswordField? = null

    //域账号-测试连接
    private var domainTestLink: JButton? = null


    /* shell账号 */
    //shell账号容器
    private var shellTabPanel: JPanel? = null

    //shell账号-用户
    private var shellAccountPanel: JPanel? = null
    private var shellAccountJLabel: JLabel? = null
    private var shellAccountText: JTextField? = null

    //shell账号-密码
    private var shellPasswordPanel: JPanel? = null
    private var shellPasswordJLabel: JLabel? = null
    private var shellPasswordText: JPasswordField? = null

    //shell账号-端口
    private var shellPortPanel: JPanel? = null
    private var shellPortJLabel: JLabel? = null
    private var shellPortText: JTextField? = null

    //shell账号-jvm端口
    private var shellJvmPortPanel: JPanel? = null
    private var shellJvmPortJLabel: JLabel? = null
    private var shellJvmPortText: JTextField? = null

    //shell账号-工作目录
    private var shellDirPanel: JPanel? = null
    private var shellDirJLabel: JLabel? = null
    private var shellDirText: JTextField? = null


    constructor(project: Project) {
        this.project = project
        this.domainConfig = DomainConfig.getInstance()
        this.shellConfig = ShellConfig.getInstance()
        this.domainTestLink!!.addActionListener { testLinkListener() }
    }


    /**
     * 设置名称
     */
    override fun getDisplayName(): String {
        return "千米网"
    }

    /**
     * 焦点位置
     */
    override fun getPreferredFocusedComponent(): JComponent? {
        return domainAccountText
    }

    /**
     * 创建视图
     */
    override fun createComponent(): JComponent? {
        return contentPanel
    }

    /**
     * 配置修改标识
     */
    override fun isModified(): Boolean {
        //域账号
        val domainModified = domainConfig.userName != this.domainAccountText!!.text
                || domainConfig.passwd != String(this.domainPasswordText!!.password)
        //shell账号
        val linuxModified = shellConfig.userName != this.shellAccountText!!.text
                || shellConfig.passwd != String(this.shellPasswordText!!.password)
                || shellConfig.port.toString() != this.shellPortText!!.text
                || shellConfig.jvmPort.toString() != this.shellJvmPortText!!.text
                || shellConfig.dir != this.shellDirText!!.text
        return domainModified || linuxModified
    }

    /**
     * 应用配置
     */
    override fun apply() {
        //域账号
        domainConfig.userName = this.domainAccountText!!.text
        domainConfig.passwd = String(this.domainPasswordText!!.password)
        //shell账号
        shellConfig.userName = this.shellAccountText!!.text
        shellConfig.passwd = String(this.shellPasswordText!!.password)
        shellConfig.port = Integer.valueOf(this.shellPortText!!.text)
        shellConfig.jvmPort = Integer.valueOf(this.shellJvmPortText!!.text)
        shellConfig.dir = this.shellDirText!!.text

        //清空cookie
        BugattiHttpUtil.clearCookie()
        //刷新配置
        project!!.getService(ConfigInitService::class.java).init()
    }

    /**
     * 初始化配置
     */
    override fun reset() {
        //域账号
        this.domainAccountText!!.text = domainConfig.userName
        this.domainPasswordText!!.text = domainConfig.passwd
        //shell账号
        this.shellAccountText!!.text = shellConfig.userName
        this.shellPasswordText!!.text = shellConfig.passwd
        this.shellPortText!!.text = shellConfig.port.toString()
        this.shellJvmPortText!!.text = shellConfig.jvmPort.toString()
        this.shellDirText!!.text = shellConfig.dir
    }

    private fun testLinkListener() {
        val httpResponse =
            BugattiHttpUtil.login(this.domainAccountText!!.text, String(this.domainPasswordText!!.password))

        if (httpResponse.isOk()) {
            JMessageUtil.showTrue("测试结果", "success")
        } else {
            JMessageUtil.showError("测试结果", "验证失败:${httpResponse.body()}")
        }
    }

}