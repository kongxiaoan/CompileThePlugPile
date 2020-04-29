package com.kpa.hotfix_lib

import com.kpa.hotfix_lib.interfaces.IAmHotfixInterface

public class IAmHotFix: IAmHotfixInterface {
    override fun doSomething(): String {
        return "我是修复好的"
    }
}
