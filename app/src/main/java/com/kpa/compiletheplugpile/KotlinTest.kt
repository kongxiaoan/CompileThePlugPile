package com.kpa.compiletheplugpile

import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**
 *    @projectName: CompileThePlugPile
 *    @ClassName : KotlinTest
 *    author : kpa
 *    e-mail : kpa@super0.net
 *    @CreateDate   : 2020/4/293:10 PM
 *    @UpdateDate   : 2020/4/293:10 PM
 *    desc   : 一个线程框架
 *    @UpdateRemark : 更新说明
 *    version: 1.0
 */
fun main() {
    // 非阻塞式
    thread(name = "测试线程") {
        println("threadName -->${Thread.currentThread().name}")
    }
    println("main ThreadName--->${Thread.currentThread().name}")
    GlobalScope.launch {
        delay(1000L)
        println("ThreadName---->${Thread.currentThread().name}")
        print("world")
        val avatar = async { }
        val logo = async { }
        supendingMerge(avatar, logo) // 合并内容
    }

    GlobalScope.launch(Dispatchers.IO) {
        println("dispatchs.main--->${Thread.currentThread().name}")
    }
    println("Hello")
    Thread.sleep(2000L)

    GlobalScope.launch {
        println("运行在线程中的协程")
    }
}

suspend fun supendingMerge(any: Any, any1: Any) {

}
