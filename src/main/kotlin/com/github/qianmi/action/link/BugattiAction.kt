package com.github.qianmi.action.link

import com.github.qianmi.MyBundle
import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.BugattiLink

class BugattiAction : BaseLinkAction() {

    var bugattiLink: BugattiLink? = null

    override fun getLinkProject(): BaseLink {
        return BugattiLink.getInstance()
    }


    companion object {

        /**
         * 跳转到当前bugatti项目
         */
        fun defaultAction(): BugattiAction {
            val bugattiAction = BugattiAction()
            bugattiAction.templatePresentation.text = MyBundle.getGoBugattiText()
            return bugattiAction
        }

        /**
         * 跳转到指定bugatti项目
         */
        fun actionOf(project: BugattiProjectEnum): BugattiAction {
            val bugattiAction = BugattiAction()
            bugattiAction.templatePresentation.text = MyBundle.getGoBugattiText()
            bugattiAction.bugattiLink = BugattiLink.instanceOfBugattiProject(project)
            return bugattiAction
        }
    }

}