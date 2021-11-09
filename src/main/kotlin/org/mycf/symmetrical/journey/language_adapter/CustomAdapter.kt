@file:Suppress("unused")

package org.mycf.symmetrical.journey.language_adapter

import net.fabricmc.loader.api.LanguageAdapter
import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.launch.common.FabricLauncherBase

@Suppress("UNCHECKED_CAST")
class CustomAdapter : LanguageAdapter {
    override fun <T : Any?> create(mod: ModContainer?, value: String?, type: Class<T>?): T {
        val clazz = Class.forName(value?.trim(), true, FabricLauncherBase.getLauncher().targetClassLoader) as Class<Any>
        return clazz.kotlin.objectInstance as T
    }
}