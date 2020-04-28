package kpa.custom.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class CustomLogPlugin implements Plugin<Project> {
    void apply(Project project) {
        System.out.println("======CustomLogPlugin===")
        def android = project.extensions.getByType(AppExtension)
        println '----------registering AutoTrackTransform -------'
        CustomLogTransform transform = new CustomLogTransform()
        android.registerTransform(transform)
    }
}