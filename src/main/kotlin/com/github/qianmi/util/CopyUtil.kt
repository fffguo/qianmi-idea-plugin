package com.github.qianmi.util

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

object CopyUtil {

    @JvmStatic
    fun copy(text: String) {
        val selection = StringSelection(text)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, selection)
    }
}