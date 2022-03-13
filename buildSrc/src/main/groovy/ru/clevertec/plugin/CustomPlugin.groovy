package ru.clevertec.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.task.FileDownload

class CustomPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def map = [group: "clevertec" ,type: FileDownload]
        project.task(map,"downloadCheck"){
            image = "https://check.ofd.ru/assets/bottom-check.ecc8e7e.png"
            file = new File("src/main/resources/check.png")
        }
    }
}
