package rado.robienie_fotki;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Rado on 01.06.2017.
 */

public class SendMessage extends AsyncTask<Object, Object, String> {


    String response = "";
    Bitmap photo;
    String dim = "";
    private static final int CAMERA_REQUEST = 1888;

    public SendMessage(Bitmap p){

        this.photo = p;



    }
    @Override
    protected String doInBackground(Object... params) {
        Socket socket = null;
            try {
                try {
                    socket = new Socket("192.168.1.100", 8080);
                    System.out.println("SOCKET = " + socket);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    int size = bs.available();
                    byte[] data = new byte[size];

                    bs.read(data);
                    dos.writeInt(size);
                    dos.write(data);

                    socket.shutdownOutput();

                    response = buffer.readLine();
                    bs.close();

                    //socket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {


        super.onPostExecute(result);
    }
}
