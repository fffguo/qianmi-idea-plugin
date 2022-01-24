package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.MyBundle
import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.extend.StringExtend.isBlank
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.github.qianmi.infrastructure.util.NotifyUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.project.Project

abstract class BaseLink {

    open var isSupport: Boolean = false

    fun openBrowser(project: Project) {
        val url = getBrowserUrl(project)
        if (url.isBlank()) {
            urlNoExistHandler(project)
        } else {
            BrowserUtil.open(url)
        }
    }

    /**
     * 获取浏览器url
     */
    abstract fun getBrowserUrl(project: Project): String

    /**
     * 获取link项目的名称
     */
    protected abstract fun getLinkName(): String

    /**
     * 获取对应的bugatti项目
     */
    open fun getBugattiProject(): BugattiProjectEnum? {
        return null
    }

    /**
     * url不存在执行后续处理
     */
    open fun urlNoExistHandler(project: Project) {
        val msg = "[${getLinkName()}]暂未配置[${EnvConfig.getInstance().env.envName}]环境地址，请联系[${MyBundle.getAuthor()}]进行处理~"

        val bugattiProject = getBugattiProject()

        if (bugattiProject == null) {
            NotifyUtil.notifyError(project, msg)
        } else {
            NotifyUtil.notifyErrorWithAction(project, msg, BugattiAction.actionOf(bugattiProject))
        }
    }


}