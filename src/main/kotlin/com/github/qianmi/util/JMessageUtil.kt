package com.github.qianmi.util

import com.intellij.ui.mac.MacMessages

object JMessageUtil {

    @JvmStatic
    private val jMessage = MacMessages.getInstance()

    @JvmStatic
    fun showError(title: String, message: String) {
        jMessage.showErrorDialog(title, message, "确定", null)
    }

    @JvmStatic
    fun showTrue(title: String, message: String) {
        jMessage.showOkMessageDialog(title, message, "确定", null)
    }
}