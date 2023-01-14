package com.hylab.ota.hook

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        debugLog {
            tag = "HYLAB"
        }
    }

    override fun onHook() = encase {
        // Your code here.
        loadApp(name = "com.android.updater") {
            findClass("miui.util.FeatureParser").hook {
                injectMember {
                    method {
                        name = "hasFeature"
                        paramCount = 2
                    }
                    beforeHook {
                        if (args[0].toString() == "support_ota_validate") {
                            resultFalse()
                        }
                    }
                }
            }
        }
    }
}