package ipca.example.bimbyrecipes;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by lourenco on 25/10/17.
 */

public class Utils {

    public static String saveBitmap(Bitmap bm){
        if (bm!=null){
            String dir=getDirPath();
            if (dir!=null){
                // save image code
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

                FileOutputStream fos;
                try {
                    File baseDir= new File(dir);
                    File file=new File(baseDir,"/"+System.currentTimeMillis()+".png");
                    fos = new FileOutputStream(file);
                    baos.writeTo(fos);
                    baos.flush();
                    fos.flush();
                    baos.close();
                    fos.close();
                    return  file.getAbsolutePath();
                } catch (FileNotFoundException e) {
                    Log.d(MainActivity.TAG, e.toString());
                    return null;
                } catch (IOException e) {
                    Log.d(MainActivity.TAG, e.toString());
                    return null;
                }
            }
        }
        return null;
    }

    public static String getDirPath(){
        File baseDir = Environment.getExternalStorageDirectory();
        File bimbyDir = new File(baseDir, "/BimbyRecipes");

        if (!bimbyDir.exists()) {
            try {
                bimbyDir.mkdirs();
                return bimbyDir.getAbsolutePath();
            } catch (Exception e) {
                return null;
            }
        }else{
            return bimbyDir.getAbsolutePath();
        }

    }

}
