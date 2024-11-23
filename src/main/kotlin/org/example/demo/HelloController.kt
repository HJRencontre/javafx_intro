package org.example.demo

import com.github.sarxos.webcam.Webcam
import javafx.fxml.FXML
import javafx.scene.control.Label
import org.example.demo.services.GeoLocationService
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.sound.sampled.*

class HelloController {
    private var line: TargetDataLine? = null
    private var isRecording = false
    @FXML private lateinit var welcomeText: Label
    private val geoLocationService = GeoLocationService()

    @FXML
    private fun onGetGeolocation(): String {
        geoLocationService.welcomeText = welcomeText
        return geoLocationService.getUserGeolocation()
    }

    @FXML
    private fun startRecording() {
        val audioFile = File("audio.wav")
        val format = AudioFormat(16000f, 16, 2, true, true)
        val info = DataLine.Info(TargetDataLine::class.java, format)

        try {
            line = AudioSystem.getLine(info) as TargetDataLine
            line?.let {
                it.open(format)
                it.start()
                isRecording = true

                val recordingThread = Thread {
                    try {
                        AudioInputStream(it).use { ais ->
                            println("Recording started...")
                            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        it.stop()
                        it.close()
                    }
                }
                recordingThread.start()
            }
        } catch (e: LineUnavailableException) {
            e.printStackTrace()
        }
    }

    @FXML
    private fun onTakePicture() {
        val webcam: Webcam = Webcam.getDefault()
        webcam.open()
        try {
            val image = webcam.image
            if (image != null) {
                ImageIO.write(image, "PNG", File("photo.png"))
            } else {
                println("Failed to capture image")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            webcam.close()
        }
    }
}