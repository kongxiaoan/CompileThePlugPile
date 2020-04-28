package kpa.custom.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @projectName: CompileThePlugPile
 * @ClassName : CustomMethodVisitor
 * author : kpa
 * e-mail : kpa@super0.net
 * @CreateDate : 2020/4/2811:16 AM
 * @UpdateDate : 2020/4/2811:16 AM
 * desc   :
 * @UpdateRemark : 更新说明
 * version: 1.0
 */
public class CustomMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public CustomMethodVisitor(MethodVisitor mv, String className, String methodName) {
        super(Opcodes.ASM5, mv);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("MethodVisitor visitCode--->");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className + "---->" + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i",
                "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }
}
