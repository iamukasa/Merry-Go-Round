package com.example.makeshift.merrygoround;

/**
 * Created by irving on 4/20/15.
 */
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class SmsNotifications extends BroadcastReceiver{

    /**
     * This method will be invoked when the sms sent or sms delivery broadcast intent is received
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        /**
         * Getting the intent action name to identify the broadcast intent ( whether sms sent or sms delivery )
         */
        String actionName = intent.getAction();


        if(actionName.equals("in.merrygoround.sent")){
            switch(getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context, "Message is sent successfully" , Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "Error in sending Message", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        if(actionName.equals("in.merrygoround.delivered")){
            switch(getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context, "Message is delivered" , Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "Error in the delivery of message", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}