package com.github.qianmi.action.shell.jvm

import com.github.qianmi.action.shell.ShellSelectedAction
import com.github.qianmi.domain.project.tools.ShellElement
import com.github.qianmi.storage.EnvConfig
import com.github.qianmi.storage.ShellConfig
import com.github.qianmi.storage.TempConfig
import com.intellij.execution.ExecutionManager
import com.intellij.execution.ExecutorRegistry
import com.intellij.execution.ExecutorRegistryImpl
import com.intellij.execution.RunManager
import com.intellij.execution.impl.RunConfigurationLevel
import com.intellij.execution.impl.RunManagerImpl
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl
import com.intellij.execution.remote.RemoteConfiguration
import com.intellij.execution.remote.RemoteConfigurationType
import com.intellij.execution.runners.ExecutionEnvironmentBuilder
import com.intellij.ide.macro.MacroManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

class RemoteSelectedAction(override var ele: ShellElement) : ShellSelectedAction() {

    override fun open(e: AnActionEvent) {
        val project = e.project!!

        MacroManager.getInstance().cacheMacrosPreview(e.dataContext)
        val executorRegistry = ApplicationManager.getApplication().getService(ExecutorRegistry::class.java)
        val debugExecutor = (executorRegistry as ExecutorRegistryImpl).getExecutorById("Debug")!!

        val runnerSetting = buildConfig(project)
        //添加配置
        RunManager.getInstance(project).addConfiguration(runnerSetting)
        TempConfig.getInstance(project).remoteJvmDebugConfigList.add(runnerSetting)
        //选中配置
        RunManager.getInstance(project).selectedConfiguration = runnerSetting

        val environment = ExecutionEnvironmentBuilder.createOrNull(debugExecutor, runnerSetting)!!
            .activeTarget().dataContext(e.dataContext).build()
        //启动
        ExecutionManager.getInstance(project).restartRunProfile(environment)
    }

    private fun buildConfig(project: Project): RunnerAndConfigurationSettingsImpl {

        val remoteConfig = RemoteConfigurationType.getInstance()
            .createTemplateConfiguration(project) as RemoteConfiguration

        remoteConfig.HOST = ele.ip
        remoteConfig.PORT = ShellConfig.getInstance().jvmPort

        val runnerSetting = RunnerAndConfigurationSettingsImpl(
            RunManagerImpl.getInstanceImpl(project),
            remoteConfig,
            false,
            RunConfigurationLevel.PROJECT)

        runnerSetting.name = " ${EnvConfig.getInstance().env.envName}(${ele.group}):${ele.ip}"
        return runnerSetting
    }


}
