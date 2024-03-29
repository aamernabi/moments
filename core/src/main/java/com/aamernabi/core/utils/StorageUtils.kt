/*
 * Copyright 2020 aamernabi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamernabi.core.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File

const val DEFAULT_IMAGE_PATH = "moments"

@RequiresApi(Build.VERSION_CODES.Q)
fun createUri(
    context: Context,
    filename: String,
    mimeType: String = "image/jpeg",
    path: String = DEFAULT_IMAGE_PATH
): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        put(MediaStore.MediaColumns.RELATIVE_PATH, path)
    }

    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

@Suppress("Deprecation")
fun createUriPreQ(
    filename: String,
    path: String = DEFAULT_IMAGE_PATH
): Uri? {
    val imagesDir: String = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DCIM
    ).toString() + File.separator + path

    val file = File(imagesDir)

    if (!file.exists()) {
        file.mkdir()
    }

    return Uri.fromFile(File(imagesDir, filename))
}
