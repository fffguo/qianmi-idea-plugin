package com.github.qianmi.action.shell.ssh.command.arthas

import com.github.qianmi.action.shell.ShellAction
import com.github.qianmi.action.shell.ssh.command.SshCommandSelectedAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.storage.ShellConfig

/**
 * 一键部署arthas
 */
class ArthasAction : ShellAction<SshCommandSelectedAction>() {

    override fun instanceShellSelectedAction(ele: ShellElement): SshCommandSelectedAction {
        return SshCommandSelectedAction(ele) {
            "su tomcat -c 'cd ${ShellConfig.getInstance().dir} && curl -O https://arthas.aliyun.com/arthas-boot.jar &&  java -jar arthas-boot.jar --select server'"
        }
    }
}