package com.rjy.dagger.utils

import android.graphics.Bitmap
import android.media.FaceDetector
import android.util.Log

class FaceDetectorUtil {


    fun findFaces(bitmap:Bitmap){
        val faceDetector = FaceDetector(bitmap.width, bitmap.height, 1)
        var face: FaceDetector.Face? = null
        val faces: Array<FaceDetector.Face?> = arrayOf(face)
        val faceNum = faceDetector.findFaces(bitmap, faces)
        if (faceNum > 0){
            Log.d("face","${faces[0]!!.pose(FaceDetector.Face.EULER_X)}")
            Log.d("face","${faces[0]!!.pose(FaceDetector.Face.EULER_Y)}")
            Log.d("face","${faces[0]!!.pose(FaceDetector.Face.EULER_Z)}")
            Log.d("face","${faces[0]!!.confidence()}")
            Log.d("face","${faces[0]!!.eyesDistance()}")
        }
    }

}