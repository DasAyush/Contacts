package com.example.ayush.contactsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.contactsapp.adapters.MessagesAdapter;
import com.example.ayush.contactsapp.fragments.MessageListFragment;
import com.example.ayush.contactsapp.models.Contact;
import com.example.ayush.contactsapp.models.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Twilio account credentials are stored in the following 2 variables
     */
    public static final String ACCOUNT_SID = "ACf2ad180ffa73d286afb9bea55c5ac084";
    public static final String AUTH_TOKEN = "347b479567fa39912ac73a7b02394d36";
    private TextView otp;
    private Button send;
    private String sendTime;
    private String body, from, to, otpString;
    private Message message = new Message();
    private Contact contact;
    private List<Message> messages = new ArrayList<>();

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        ButterKnife.bind(this);
        otp = findViewById(R.id.otpText);
        send = findViewById(R.id.sendButton);

        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null){
            contact = (Contact) intent.getExtras().get("cont");
            to = contact.getNumber();
        }

        /**
         * 6-digit random number generated using the following function
         */
        final int random = new Random().nextInt(100000) + 100000;
        otpString = getApplicationContext().getString(R.string.sample_otp_text)
                + "" + random;
        otp.setText(otpString);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);

        sendTime = TimeUtility.getCurrentDateTime();
        body = otp.getText().toString();
        from = "+16106867699"; //registered Twilio number

        String base64EncodedCredentials = "Basic " + android.util.Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                android.util.Base64.NO_WRAP);

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", to);     //verified caller ID to which the SMS is sent
        data.put("Body", body);

        /**
         * base URL is put into the retrofit builder
         * twilio api object is created from the class
         */
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.twilio.com/2010-04-01/")
                .build();
        TwilioApi api = retrofit.create(TwilioApi.class);

        /**
         * message is posted using Retrofit
         */
        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        if (response.isSuccessful())
                            Toast.makeText(getApplicationContext(), "Response successful"
                                    + "\n" + otpString + " " + sendTime,
                                    Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        message.setFirst(contact.getFirst());
                        message.setLast(contact.getLast());
                        message.setOtp(otpString);
                        message.setTime(sendTime);
                        messages.add(message);

                        /**
                         * data is sent to the messagesListFragment using Bundle
                         */
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("msg", message);
                        MessageListFragment fragment = new MessageListFragment();
                        fragment.setArguments(bundle);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Unsuccessful",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
