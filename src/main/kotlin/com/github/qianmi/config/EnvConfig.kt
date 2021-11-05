package com.github.qianmi.config

import com.github.qianmi.enums.BugattiEnum
import com.github.qianmi.enums.DubboAdminEnum
import com.github.qianmi.enums.EnvEnum
import com.intellij.ide.util.PropertiesComponent
import com.intellij.util.containers.stream


object EnvConfig {

    private const val currentEnvKey = "qianmiCurrentEnv"

    fun getCurrentEnv(): EnvEnum {
        val envCode: String = PropertiesComponent.getInstance().getValue(currentEnvKey, EnvEnum.TEST1.code)
        return EnvEnum.values()
            .stream()
            .filter { enum -> enum.code == envCode }
            .findFirst()
            .orElse(EnvEnum.TEST1)
    }

    fun setCurrentEnv(env: EnvEnum) {
        PropertiesComponent.getInstance().setValue(currentEnvKey, env.code)
    }

    @JvmStatic
    fun getBugattiEnum(): BugattiEnum {
        return BugattiEnum.values()
            .stream()
            .filter { enum -> enum.env == getCurrentEnv() }
            .findFirst()
            .orElse(BugattiEnum.TEST1)
    }

    @JvmStatic
    fun getDubboAdminEnum(): DubboAdminEnum {
        return DubboAdminEnum.values()
            .stream()
            .filter { enum -> enum.env == getCurrentEnv() }
            .findFirst()
            .orElse(DubboAdminEnum.TEST1)
    }

}