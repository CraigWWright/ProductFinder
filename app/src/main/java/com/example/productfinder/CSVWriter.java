package com.example.productfinder;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CSVWriter {

    public static void saveStringAsCSV(Context context, String data, String fileName) {
        // Check if external storage is available
        if (isExternalStorageWritable()) {
            File csvFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName + ".txt");

            try {
                FileOutputStream outputStream = new FileOutputStream(csvFile);
                outputStream.write(data.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Check if external storage is available for read and write
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
