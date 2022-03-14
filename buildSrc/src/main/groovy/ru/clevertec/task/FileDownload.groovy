package ru.clevertec.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class FileDownload extends DefaultTask{
    @Input
    String image

    @OutputFile
    File file

    @TaskAction
    void download() throws IOException {
        URL url = new URL(image)
        try (InputStream inputStream = url.openStream()
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] bytes = inputStream.readAllBytes()
            outputStream.write(bytes)
        }
    }
}