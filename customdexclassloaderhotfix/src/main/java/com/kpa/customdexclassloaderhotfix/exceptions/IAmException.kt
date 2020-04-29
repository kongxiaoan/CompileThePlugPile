package com.kpa.customdexclassloaderhotfix.exceptions

import com.kpa.customdexclassloaderhotfix.interfaces.IAMInterface

/**
 *    @projectName: CompileThePlugPile
 *    @ClassName : IAmException
 *    author : kpa
 *    e-mail : kpa@super0.net
 *    @CreateDate   : 2020/4/2911:29 AM
 *    @UpdateDate   : 2020/4/2911:29 AM
 *    desc   :
 *    @UpdateRemark : 更新说明
 *    version: 1.0
 */
class IAmException: IAMInterface {
    override fun doSomething(): String {
        return "假装是一个异常"
    }
}