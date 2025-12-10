package com.example.intentexploration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentexploration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val REQUEST_SELECT_CONTACT = 1
    val REQUEST_SELECT_TEMPLATE = 2
    val REQUEST_CAMERA = 3

    fun takePicture() {
        val i = Intent()

        i.action = MediaStore.ACTION_IMAGE_CAPTURE

        startActivityForResult(i, REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CAMERA ->
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
            } else {
                Toast.makeText(this, "You need to grant permission to access camera", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // btn camera
        binding.fabPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // request ulang
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )
            } else {
                takePicture()
            }
        }

        // btn send ditekan
        binding.btnSend.setOnClickListener {
            val intent = Intent()

            intent.action =Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, binding.txtMessage.text.toString())
            intent.type = "text/plain"

            val shareIntent = Intent.createChooser(intent, "Choose an app")

            startActivity(intent)
        }

        // btn person
        binding.textInputLayoutHp.setEndIconOnClickListener {
            val intent = Intent()

            intent.action = Intent.ACTION_PICK
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }

        // button pick template
        binding.btnPick.setOnClickListener {
            val intent = Intent(this, MessageTemplateActivity::class.java)
            startActivityForResult(intent, REQUEST_SELECT_TEMPLATE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==RESULT_OK)
            if (requestCode == REQUEST_SELECT_CONTACT) {
                val contactUri = data?.data
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val cursor = contentResolver.query(contactUri!!, projection, null, null, null)

                if (cursor != null && cursor.moveToFirst()) {
                    val hp = cursor.getString(0)

                    binding.textInputLayoutHp.editText?.setText(hp)
                    cursor.close()
                }
            }

            if (requestCode == REQUEST_SELECT_TEMPLATE) {
                val message = data?.getStringExtra(Intent.EXTRA_TEXT)

                binding.txtMessage.setText(message)
            }

            if (requestCode == REQUEST_CAMERA) {
                val extras = data!!.extras
                val imageBitmap: Bitmap = extras!!.get("data") as Bitmap

                binding.imgPhoto.setImageBitmap(imageBitmap)
            }
    }
}