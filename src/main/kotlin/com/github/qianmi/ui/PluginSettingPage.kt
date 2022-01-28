package com.github.qianmi.ui

import com.github.qianmi.infrastructure.ConfigInitService
import com.github.qianmi.infrastructure.extend.HttpExtend.isOk
import com.github.qianmi.infrastructure.storage.AccountConfig
import com.github.qianmi.infrastructure.storage.ShellConfig
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.JMessageUtil
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.*

class PluginSettingPage(private var project: Project) : Configurable {

    private var accountConfig: AccountConfig = AccountConfig.getInstance()
    private var shellConfig: ShellConfig = ShellConfig.getInstance()

    //根容器
    private lateinit var contentPanel: JPanel

    //tab容器
    private lateinit var tabPanel: JTabbedPane

    //域账号容器
    private lateinit var domainTabPanel: JPanel

    /* 域账号 */
    //域账号-用户名
    private lateinit var domainAccountPanel: JPanel
    private lateinit var domainAccountJLabel: JLabel
    private lateinit var domainAccountText: JTextField

    //域账号-密码
    private lateinit var domainPasswordPanel: JPanel
    private lateinit var domainPasswordJLabel: JLabel
    private lateinit var domainPasswordText: JPasswordField

    //域账号-测试连接
    private lateinit var domainTestLink: JButton


    /* shell账号 */
    //shell账号容器
    private lateinit var shellTabPanel: JPanel

    //shell账号-用户
    private lateinit var shellAccountPanel: JPanel
    private lateinit var shellAccountJLabel: JLabel
    private lateinit var shellAccountText: JTextField

    //shell账号-密码
    private lateinit var shellPasswordPanel: JPanel
    private lateinit var shellPasswordJLabel: JLabel
    private lateinit var shellPasswordText: JPasswordField

    //shell账号-端口
    private lateinit var shellPortPanel: JPanel
    private lateinit var shellPortJLabel: JLabel
    private lateinit var shellPortText: JTextField

    //shell账号-jvm端口
    private lateinit var shellJvmPortPanel: JPanel
    private lateinit var shellJvmPortJLabel: JLabel
    private lateinit var shellJvmPortText: JTextField

    //shell账号-工作目录
    private lateinit var shellDirPanel: JPanel
    private lateinit var shellDirJLabel: JLabel
    private lateinit var shellDirText: JTextField


    init {
        this.domainTestLink.addActionListener { testLinkListener() }
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
    override fun getPreferredFocusedComponent(): JComponent {
        return domainAccountText
    }

    /**
     * 创建视图
     */
    override fun createComponent(): JComponent {
        return contentPanel
    }

    /**
     * 配置修改标识
     */
    override fun isModified(): Boolean {
        //域账号
        val domainModified = accountConfig.userName != this.domainAccountText.text
                || accountConfig.passwd != String(this.domainPasswordText.password)
        //shell账号
        val linuxModified = shellConfig.userName != this.shellAccountText.text
                || shellConfig.passwd != String(this.shellPasswordText.password)
                || shellConfig.port != this.shellPortText.text
                || shellConfig.jvmPort != this.shellJvmPortText.text
                || shellConfig.dir != this.shellDirText.text
        return domainModified || linuxModified
    }

    /**
     * 应用配置
     */
    override fun apply() {
        //域账号
        accountConfig.userName = this.domainAccountText.text
        accountConfig.passwd = String(this.domainPasswordText.password)
        //shell账号
        shellConfig.userName = this.shellAccountText.text
        shellConfig.passwd = String(this.shellPasswordText.password)
        shellConfig.port = this.shellPortText.text
        shellConfig.jvmPort = this.shellJvmPortText.text
        shellConfig.dir = this.shellDirText.text

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
        this.domainAccountText.text = accountConfig.userName
        this.domainPasswordText.text = accountConfig.passwd
        //shell账号
        this.shellAccountText.text = shellConfig.userName
        this.shellPasswordText.text = shellConfig.passwd
        this.shellPortText.text = shellConfig.port
        this.shellJvmPortText.text = shellConfig.jvmPort
        this.shellDirText.text = shellConfig.dir
    }

    private fun testLinkListener() {
        val httpResponse =
            BugattiHttpUtil.login(this.domainAccountText.text, String(this.domainPasswordText.password))

        if (httpResponse.isOk()) {
            JMessageUtil.showTrue("测试结果", "success")
        } else {
            JMessageUtil.showError("测试结果", "验证失败:${httpResponse.body()}")
        }
    }

}