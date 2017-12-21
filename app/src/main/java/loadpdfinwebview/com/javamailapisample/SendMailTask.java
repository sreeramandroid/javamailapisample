/**
 *
 */
package loadpdfinwebview.com.javamailapisample;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Shankar
 */
public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Context context;

    public SendMailTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(context);
        statusDialog.setMessage("Hiii");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        // statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            Log.i("SendMailTask", "About to instantiate GMail...");
            publishProgress("Helloo");
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(), (List) args[2], args[3].toString(),
                    args[4].toString(), args[5].toString());
            publishProgress("Hi this is");
            androidEmail.createEmailMessage();
            publishProgress("Is this Ok");
            androidEmail.sendEmail();
            publishProgress("Still pending");
            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onPostExecute(Object result) {
       /* if (statusDialog != null && statusDialog.isShowing())
            statusDialog.dismiss();*/
        Toast toast = Toast.makeText(context, "hello msg", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void cancelProgressDialog() {
        if (statusDialog != null)
            statusDialog = null;
    }
}

