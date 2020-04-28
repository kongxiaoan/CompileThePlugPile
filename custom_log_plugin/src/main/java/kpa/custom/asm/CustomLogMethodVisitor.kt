package kpa.custom.asm

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *    @projectName: CompileThePlugPile
 *    @ClassName : CustomLogMethodVisitor
 *    author : kpa
 *    e-mail : kpa@super0.net
 *    @CreateDate   : 2020/4/273:08 PM
 *    @UpdateDate   : 2020/4/273:08 PM
 *    desc   :
 *    @UpdateRemark : 更新说明
 *    version: 1.0
 */
class CustomLogMethodVisitor: MethodVisitor {
    private var className = ""
    private var methodName = ""

    constructor(methodVisitor: MethodVisitor, className: String, methodName: String) : super(Opcodes.ASM5, methodVisitor) {
        this.className = className
        this.methodName = methodName
    }

    override fun visitCode() {
        super.visitCode()
        println("MethodVisitor visitCode------")
        mv.visitLdcInsn("TAG")
        mv.visitLdcInsn("$className------>$methodName")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false)
        mv.visitInsn(Opcodes.POP)
    }
}