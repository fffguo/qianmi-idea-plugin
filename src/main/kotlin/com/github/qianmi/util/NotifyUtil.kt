package com.github.qianmi.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.Nullable


object NotifyUtil {

    @JvmStatic
    private var notification = NotificationGroupManager.getInstance().getNotificationGroup("qianmi-idea-plugin")

    @JvmStatic
    fun notifyError(@Nullable project: Project?, content: String?) {
        notification.createNotification(content!!, NotificationType.ERROR).notify(project)
    }


    @JvmStatic
    fun notifyErrorWithAction(@Nullable project: Project?, content: String?, action: AnAction) {
        notification
            .createNotification(content!!, NotificationType.ERROR)
            .addAction(action)
            .notify(project)
    }

    @JvmStatic
    fun notifyInfo(@Nullable project: Project?, content: String?) {
        notification.createNotification(content!!, NotificationType.INFORMATION).notify(project)
    }

    @JvmStatic
    fun notifyInfoWithAction(@Nullable project: Project?, content: String?, action: AnAction) {
        notifyInfoWithAction(project, content, listOf(action))
    }

    @JvmStatic
    fun notifyInfoWithAction(@Nullable project: Project?, content: String?, actions: Collection<AnAction>) {
        val createNotification = notification.createNotification(content!!, NotificationType.INFORMATION)
        actions.forEach { createNotification.addAction(it) }
        createNotification.notify(project)
    }

}