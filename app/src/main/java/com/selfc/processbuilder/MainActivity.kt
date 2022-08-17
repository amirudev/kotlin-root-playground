package com.selfc.processbuilder

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnProcessBuilderFirst: Button
    private lateinit var tvProcessBuilderResult: TextView
    private lateinit var tvLogContainer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnProcessBuilderFirst = findViewById(R.id.btn_pb_sudo)
        btnProcessBuilderFirst.setOnClickListener(this)
        tvProcessBuilderResult = findViewById(R.id.tv_pb_result)
        tvLogContainer = findViewById(R.id.tv_log_container)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_pb_sudo -> {
                try {
                    val pb = ProcessBuilder("sudo ls /")
//                    pb.directory(File("Pictures"))
//                    val log = File("log")
//                    pb.redirectErrorStream(true)

    //                Request Min API Level 26
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log))
                        val p = pb.start()
//                        assert(pb.redirectInput() == ProcessBuilder.Redirect.PIPE)
//                        assert(pb.redirectOutput().file() == log)
//                        assert(p.inputStream.read() == -1)
                        tvProcessBuilderResult.text = String(p.inputStream.readBytes())
                    } else {
                        tvProcessBuilderResult.text = "Minimum Android API Level is 26 and your phone is on ${android.os.Build.VERSION.SDK_INT}"
                    }
                } catch (e: Exception) {
                    tvProcessBuilderResult.text = "Exception occured \n $e"
                    printLog(e.toString())
                }
            }
        }
    }

    private fun printLog(text: String) {
        val c = Calendar.getInstance()

        tvLogContainer.text = "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.DATE)} ${c.get(Calendar.HOUR)}:${c.get(Calendar.MINUTE)}:${c.get(Calendar.SECOND)}:${c.get(Calendar.MILLISECOND)} - ${text}\n${tvLogContainer.text}"
    }
}