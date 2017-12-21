package loadpdfinwebview.com.javamailapisample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    SendMailTask sendMailTask;
    //Send button
    private Button buttonSend, buttonAttachment;
    String mPath;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonAttachment = (Button) findViewById(R.id.buttonAttachment);
        //Adding click listener
        buttonSend.setOnClickListener(this);
        buttonAttachment.setOnClickListener(this);
    }


    private void sendEmail() {
        String fromEmail = "enter from email";//ex:bvc@gmail.com
        String fromPassword = "enter password";//enter valid password
        String toEmails = "enter reciptient mail id";//ex:abc@gmail.com
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "screen shot";
        String emailBody = "Dear ........\n\n\n" + " " + "Hi this is test mail from shankar R&D" + "\n\n\n" + "Regards" + "\n\n" + "Shaan";
        sendMailTask = new SendMailTask(this);
        String attachments = imageFile.toString();
        sendMailTask.execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody, attachments);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSend:
                sendEmail();
                break;
            case R.id.buttonAttachment:
                takeScreenshot(this);
                break;
            default:
                break;
        }
    }

    private void takeScreenshot(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh_mm_ss");
        String now = sdf.format(new Date());

        try {
            mPath = "screen" + now + ".jpg";
            View v1 = ((Activity) context).getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File mFolder = new File(getFilesDir() + "/sample");
            imageFile = new File(mFolder.getAbsolutePath() + "/", mPath);
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

}