package com.sinx.sample

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import okio.Buffer
import okio.source
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Writer
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val url = BuildConfig.WEB_SOCKET_URL
    private val port = 8003
    private var thread: Thread? = null
    private var writer: Writer? = null
    fun onButtonClick() {
        log("url $url")
        if (thread != null) {
            kotlin.runCatching {
                log("finish 1")
                thread?.interrupt()
                log("finish 2")
                thread?.join()
            }
            log("finish 3")
            thread = null
        } else {
            thread = Thread(Runnable {
                try {
                    val socket = Socket(InetAddress.getByName(url), port).use { socket ->
                        socket.keepAlive = false
                        socket.tcpNoDelay = true
//                        Buffer().use { buffer ->
                            PrintWriter(
                                OutputStreamWriter(
                                    socket.getOutputStream(),
                                    Charsets.UTF_8
                                )
                            ).use { writer ->
                                this.writer = writer
                                socket.getInputStream().use { reader ->
                                    log("in bufreader")
                                    writer.apply {
                                        println("GET /socket.io/?EIO=3&transport=websocket HTTP/1.1")
                                        println("Upgrade: websocket")
                                        println("Accept-Encoding: gzip, deflate, br")
                                        println("Accept: */*")
                                        println("Connection: Upgrade")
                                        println("Sec-WebSocket-Key: dGHlIHNhbXBsZSBub25jZQ==")
                                        println("Sec-WebSocket-Version: 13")
                                        println("Sec-WebSocket-Extension: permessage-deflate; client_max_window_bits")
                                        println("Host: ${url + port}")
                                        println("")
                                        flush()
                                    }
//                                    reader.timeout().timeout(80, TimeUnit.SECONDS)
                                    do {
                                        log("before readeline")
                                        val buffer = ByteArray(1024)
                                        val read: Int
                                        if (Thread.currentThread().isInterrupted) {
                                            Thread.currentThread().interrupt()
                                            throw InterruptedException()
                                        } else {
                                            read = reader.read(buffer)
                                            if (read != -1) {
                                                val bytes = buffer.copyOfRange(0, read)
                                                val str = String(bytes)
                                                log("answer $str")
                                                if (str.contains("Привет")) {
                                                    Parser.read(bytes)
                                                    val s = str.substringBefore("Пр") + "addTask"
                                                    log(s)
//                                                    writer.print(s)
//                                                    writer.flush()
                                                }
                                            }
                                        }
                                    } while (read != -1)
                                    log("endreader")
                                }
                            }
                        }

                } catch (t: Throwable) {
                    log("interup b ${t.toString()}")
                    val a = t
                } finally {
                    log("interup c")
                    thread?.interrupt()
                }
            })
        }
        thread?.start()
    }

    fun log(msg: String) {
        Log.d("socketio", msg)
    }
}