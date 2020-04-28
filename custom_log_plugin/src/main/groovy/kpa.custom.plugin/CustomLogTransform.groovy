package kpa.custom.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import groovy.io.FileType
import kpa.custom.asm.CustomClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

/**
 * Transform 主要作用是检索项目编译过程中的所有文件
 */
public class CustomLogTransform extends Transform {

    /**
     * Returns the unique name of the transform.
     *
     * <p>This is associated with the type of work that the transform does. It does not have to be
     * unique per variant.
     * 设置自定义的Transform 对应的task名称， Gradle 在编译的时候，会将这个名称显示
     * 在控制台上
     * eg: Task:app:transformClassesWithXXXForDebug
     */
    @Override
    String getName() {
        return "CustomLogTransform"
    }

    /**
     * Returns the type(s) of data that is consumed by the Transform. This may be more than
     * one type.
     * 在项目中会有各种各样格式的文件，通过getInputType可以设置
     * CustomLogTransform接收的文件类型，此方法返回的类型是Set<QualifiedContent.ContentType>集合
     * ContentType:
     * CLASSES： 只检索.class文件
     * RESOURCES： 检索Java标准资源文件
     * <strong>This must be of type {@link QualifiedContent.DefaultContentType}</strong>
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * Returns the scope(s) of the Transform. This indicates which scopes the transform consumes.
     * 这个方法规定自定义 Transform 检索的范围，具体有以下几种取值：
     * PROJECT： 只有项目内容
     * SUB_PROJECTS： 只有子项目
     * EXTERNAL_LIBRARIES：只有外部库
     * TESTED_CODE： 由当前变量(包括依赖项)测试的代码
     * PROVIDED_ONLY： 只提供本地或远程依赖项
     * SUB_PROJECTS_LOCAL_DEPS： 只有子项目的本地依赖项(本地jar)
     * PROJECT_LOCAL_DEPS: 只有项目的本地依赖(本地jar)
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    /**
     * Returns whether the Transform can perform incremental work.
     *
     * <p>If it does, then the TransformInput may contain a list of changed/removed/added files, unless
     * something else triggers a non incremental run.
     * 是否支持增量编译 不需要直接返回false
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 自定义时最重要的方法，在这个方法中可以获取两个数据的流向
     * inputs: inputs 中传过来的输入流，其中有两种格式，jat包格式，directory（目录格式）
     * outputProvider：outputProvider 获取到输出目录，最后将修改的文件复制到输出目录，这一步必须做，否则编译会报错。
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        // 拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }
        transformInputs.each { TransformInput transformInput ->
            transformInput.jarInputs.each { JarInput jarInput ->
                File file = jarInput.file
                System.out.println("find jar input: " + file.name)
                def dest = outputProvider.getContentLocation(jarInput.name,
                        jarInput.contentTypes,
                        jarInput.scopes, Format.JAR)
                FileUtils.copyFile(file, dest)
            }
            // // 遍历directoryInputs(文件夹中的class文件) directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            //            // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dir = directoryInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        System.out.println("find class: " + file.name)
                        // 对Class 文件进行读取与解析
                        ClassReader classReader = new ClassReader(file.bytes)
                        // class 文件写入
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        // 访问class 文件相应的内容、解析某一个结构就会通知到ClassVisitor的相应方法
                        CustomClassVisitor classVisitor = new CustomClassVisitor(classWriter)
                        // 依次调用ClassVisitor 接口的各个方法
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        // 将最终修改的字节码以byte数组形式返回
                        byte[] bytes = classWriter.toByteArray()
                        // 通过文件流写入方式覆盖原先的内容，实现class文件的改写
                        FileOutputStream fileOutputStream = new FileOutputStream(file.path)
                        fileOutputStream.write(bytes)
                        fileOutputStream.close()
                    }
                    // 处理完输入之后吧输出传给下一个文件
                    def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
            }
        }

    }
}