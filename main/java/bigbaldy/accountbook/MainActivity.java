package bigbaldy.accountbook;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final static String TAG = "MainActivity";
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        float total = pref.getFloat("total", 0.0f);
        textView.setText(String.format("%.2f", total) + "￥ - " + getDays() + "Days");
    }

    public void onClick_reset(View view) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putFloat("total", 0.0f);
        editor.putString("date",sdf.format(new Date()));
        editor.commit();
        textView.setText(String.valueOf(0.0f) + "￥ - " + getDays() + "Days");
    }

    public void onClick_record(View view) {
        try {
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            float total = pref.getFloat("total", 0.0f);

            float value = Float.valueOf(editText.getText().toString());
            total += value;
            SharedPreferences.Editor editor = pref.edit();
            editor.putFloat("total", total);
            editor.commit();

            textView.setText(String.format("%.2f", total) + "￥ - " + getDays() + "Days");
            editText.setText("");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private long getDays() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String date = pref.getString("date", "");
        Date now = new Date();
        if(date==null || date.length()==0) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("date",sdf.format(now));
            editor.commit();
            return 1;
        }
        Date start = null;
        try {
            start = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = now.getTime() - start.getTime();
        return diff / (1000 * 60 * 60 * 24) + 1;
    }
}
