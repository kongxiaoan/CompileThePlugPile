package com.kpa.customdexclassloaderhotfix

import com.kpa.customdexclassloaderhotfix.interfaces.IAMInterface

public class IAmHotFix: IAMInterface {
    override fun doSomething(): String {
        return "我是修复好的"
    }
}
