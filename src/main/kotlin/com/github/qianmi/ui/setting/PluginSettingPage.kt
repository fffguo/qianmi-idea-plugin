package com.github.qianmi.ui.setting

import com.github.qianmi.infrastructure.extend.HttpExtend.isOk
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.JMessageUtil
import com.intellij.openapi.project.Project
import javax.swing.*


class PluginSettingPage(private val project: Project) : JPanel() {

    //根容器
    lateinit var contentPanel: JPanel

    //tab容器
    lateinit var tabPanel: JTabbedPane

    //域账号容器
    lateinit var domainTabPanel: JPanel

    //远程debug容器
    lateinit var remoteJvmDebugTabPanel: JPanel

    //日志配置容器
    lateinit var logTabPanel: JPanel

    /* 域账号 */
    //域账号-用户名
    lateinit var domainAccountPanel: JPanel
    lateinit var domainAccountJLabel: JLabel
    lateinit var domainAccountText: JTextField

    //域账号-密码
    lateinit var domainPasswordPanel: JPanel
    lateinit var domainPasswordJLabel: JLabel
    lateinit var domainPasswordText: JPasswordField

    //域账号-测试连接
    lateinit var domainTestLink: JButton


    /* shell账号 */
    //shell账号容器
    lateinit var shellTabPanel: JPanel

    //shell账号-用户
    lateinit var shellAccountPanel: JPanel
    lateinit var shellAccountJLabel: JLabel
    lateinit var shellAccountText: JTextField


    //shell账号-密码
    lateinit var shellPasswordPanel: JPanel
    lateinit var shellPasswordJLabel: JLabel
    lateinit var shellPasswordText: JPasswordField

    //shell账号-端口
    lateinit var shellPortPanel: JPanel
    lateinit var shellPortJLabel: JLabel
    lateinit var shellPortText: JTextField

    //shell账号-jvm端口
    lateinit var shellJvmPortPanel: JPanel
    lateinit var shellJvmPortJLabel: JLabel
    lateinit var shellJvmPortText: JTextField

    //shell账号-工作目录
    lateinit var shellDirPanel: JPanel
    lateinit var shellDirJLabel: JLabel
    lateinit var shellDirText: JTextField

    //shell账号-日志目录
    lateinit var shellLogCommandPanel: JPanel
    lateinit var shellLogCommandJLabel: JLabel
    lateinit var shellLogCommandText: JTextField

    init {
        this.domainTestLink.addActionListener { testLinkListener() }
    }

    private fun testLinkListener() {
        val httpResponse =
            BugattiHttpUtil.login(this.domainAccountText.text, String(this.domainPasswordText.password))

        if (httpResponse.isOk()) {
            JMessageUtil.showTrue(project, "测试结果", "success")
        } else {
            JMessageUtil.showError(project, "测试结果", "验证失败:${httpResponse.body()}")
        }
    }

}