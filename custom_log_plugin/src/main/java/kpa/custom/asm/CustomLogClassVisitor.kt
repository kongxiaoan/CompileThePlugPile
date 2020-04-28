package kpa.custom.asm

import org.apache.http.util.TextUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *    @projectName: CompileThePlugPile
 *    @ClassName : CustomLogClassVisitor
 *    author : kpa
 *    e-mail : kpa@super0.net
 *    @CreateDate   : 2020/4/273:07 PM
 *    @UpdateDate   : 2020/4/273:07 PM
 *    desc   :
 *    @UpdateRemark : 更新说明
 *    version: 1.0
 */
class CustomLogClassVisitor(cv: ClassVisitor) : ClassVisitor(Opcodes.ASM5, cv) {
    private var className = ""
    private var superName = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name ?: ""
        this.superName = superName ?: ""
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("ClassVisitor visitMethod name--$name, superName--$superName")
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (superName == "androidx/appcompat/app/AppCompatActivity") {
            if (!TextUtils.isEmpty(name) && name != null) {
                if (name.startsWith("")) {
                    return CustomLogMethodVisitor(mv,className,name)
                }
            }
            return mv
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}