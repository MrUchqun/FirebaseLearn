package com.example.firebaselearn.managers

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class StorageManager {
    companion object {
        private val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        var photosRef = storageRef.child("photos")

        fun uploadPhoto(context: Context, uri: Uri, handler: StorageHandler) {

            val cR = context.contentResolver
            val mime = MimeTypeMap.getSingleton()
            val type: String = mime.getExtensionFromMimeType(cR.getType(uri)) ?: return

            Log.d("@@@", "uploadPhoto: $type")

            val filename = System.currentTimeMillis().toString() + type
            val uploadTask: UploadTask = photosRef.child(filename).putFile(uri)
            uploadTask.addOnSuccessListener { it ->
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {
                    val imgUrl = it.toString()
                    handler.onSuccess(imgUrl)
                }.addOnFailureListener { e ->
                    handler.onError(e)
                }
            }.addOnFailureListener { e ->
                handler.onError(e)
            }
        }
    }
}