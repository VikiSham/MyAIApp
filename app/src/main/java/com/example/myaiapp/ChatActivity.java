package com.example.myaiapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myaiapp.R;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.FutureCallback;

import kotlin.coroutines.EmptyCoroutineContext;

public class ChatActivity extends AppCompatActivity {
    EditText etChat;
    TextView tvChat;
    Button btnChat;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etChat = findViewById(R.id.etChat);
        tvChat = findViewById(R.id.tvChat);
        btnChat = findViewById(R.id.btnChat);
        scrollView = findViewById(R.id.scrollView);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etChat.getText().toString();
                etChat.setText("");
                geminiMessage(message);
                // סגירת המקלדת
                android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etChat.getWindowToken(), 0);
            }
        });

    }
// פעולת שליחת הודעות לצ'אט לגמיניזימון
    private void geminiMessage(String message) {
                ProgressDialog pd = new ProgressDialog(ChatActivity.this);
                pd.setTitle("Connecting");
                pd.setMessage("Wait to Gemini...");
                pd.show();
                String prompt = message.trim();

                GeminiManager.getInstance().sendChatMessage(
                        prompt,
                        EmptyCoroutineContext.INSTANCE, new FutureCallback<String>() {
                            @Override
                            public void onSuccess(String s) {
                                pd.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayChatMessages(tvChat, message, s);
                                    }
                                });
                            }
                            @Override
                            public void onFailure(Throwable throwable) {
                                tvChat.append("\n" + "Error: " + throwable.getMessage() + "\n");
                            }
                        });

    }
//הצ'אט בצבע שונה באותו textviewפעולה אשר מציגה את טקסט
    public void displayChatMessages(TextView textView, String userMessage, String botMessage) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // הודעה מהמשתמש (צבע סגול)
        builder.append("\n"+userMessage);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#683fb5")), 0, userMessage.length(), 0);
        builder.append("\n"); // מעבר שורה

        // הודעה מהבוט (צבע ירוק)
        builder.append("\n"+botMessage);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#315041")), builder.length() - botMessage.length(), builder.length(), 0);
        builder.append("\n"); // מעבר שורה

        textView.append(builder);
        // גלילה אוטומטית לתחתית
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}