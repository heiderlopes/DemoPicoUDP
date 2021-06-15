package br.com.allan.demopicoudp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.Inet4Address

class MainActivity : AppCompatActivity() {

    //val host = "your_ip_address"
    val port = 4321
    //var sendData = "test".toByteArray()
    var result: Boolean = false

    private lateinit var btLigar: Button
    private lateinit var btDesligar: Button
    private lateinit var etIP: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btLigar = findViewById(R.id.btLigar)
        btDesligar = findViewById(R.id.btDesligar)
        etIP = findViewById(R.id.etIP)

        //button event
        btDesligar.setOnClickListener {
            val host = etIP.text.toString()
            result = sendPacket(host, port, "off\r\n".toByteArray())
            //Log.d("Main", result.toString())
            if(result){
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
            }

        }

        btLigar.setOnClickListener {
            val host = etIP.text.toString()
            result = sendPacket(host, port, "on\r\n".toByteArray())
            if(result){
                Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendPacket(host: String, port: Int, data: ByteArray, senderPort: Int = 0): Boolean {
        var ret = false
        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(senderPort)
            val address = Inet4Address.getByName(host)
            val packet = DatagramPacket(data, data.size, address, port)
            socket.send(packet)
            ret = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            socket?.close()
        }
        return ret
    }
}