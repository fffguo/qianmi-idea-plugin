package com.github.qianmi.infrastructure.extend

import com.github.qianmi.ui.MyJDialog
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.KeyStroke

object JPanelExtend {

    fun JPanel.registerEscEvent(dialog: MyJDialog) {
        this.registerKeyboardAction({ dialog.dispose() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
    }

}