package com.product.insightly.service

import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class VideoFileSceneService {
    private val sceneChangeThreshold = 0.1
    private val sceneCombineFrameRate = 5


    fun extractEssentialScene(inputPath: Path, outputPath: Path) {
        println(inputPath.toAbsolutePath().toString())
        println(outputPath.resolve("frame_%04d.jpg").toAbsolutePath().toString())

        val command = listOf(
            "ffmpeg",
            "-i", inputPath.toAbsolutePath().toString(),
            "-vf", "select='gt(scene\\,$sceneChangeThreshold)',showinfo",
            "-vsync", "vfr",
            outputPath.resolve("frame_%04d.jpg").toAbsolutePath().toString()
        )

        runCommand(command)
    }

    fun combineScenesToVideo(frameDir: Path, outputVideo: Path) {
        val command = listOf(
            "ffmpeg",
            "-framerate", sceneCombineFrameRate.toString(),
            "-i", frameDir.resolve("frame_%04d.jpg").toAbsolutePath().toString(),
            "-c:v", "libx264",
            "-pix_fmt", "yuv420p",
            outputVideo.toAbsolutePath().toString()
        )

        runCommand(command)
    }

    private fun runCommand(command: List<String>) {
        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().use { reader ->
            reader.lines().forEach { println(it) }
        }

        process.waitFor()
    }
}