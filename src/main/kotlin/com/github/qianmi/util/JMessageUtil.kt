package com.github.qianmi.util

import com.intellij.ui.messages.JBMacMessages

object JMessageUtil {

    @JvmStatic
    @SuppressWarnings("all")
    private val jMessage = JBMacMessages.getInstance()

    @JvmStatic
    fun showError(title: String, message: String) {
        jMessage.showErrorDialog(title, message, "确定", null)
    }

    @JvmStatic
    fun showTrue(title: String, message: String) {
        jMessage.showErrorDialog(title, message, "确定", null)
    }
}