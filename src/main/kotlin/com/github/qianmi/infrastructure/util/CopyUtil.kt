package com.github.qianmi.infrastructure.util

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

object CopyUtil {

    @JvmStatic
    fun copy(text: String) {
        val selection = StringSelection(text)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, selection)
    }
}