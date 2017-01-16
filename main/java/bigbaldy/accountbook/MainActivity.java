package bigbaldy.accountbook;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static String TAG="MainActivity";
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        float total = pref.getFloat("total", 0.0f);
        textView.setText(String.format("%.2f",total)+"￥");
    }

    public void onClick_reset(View view)
    {
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putFloat("total",0.0f);
        editor.commit();
        textView.setText(String.valueOf(0.0f)+"￥");
    }

    public void onClick_record(View view)
    {
        try {
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            float total = pref.getFloat("total", 0.0f);
            float value = Float.valueOf(editText.getText().toString());
            total += value;
            SharedPreferences.Editor editor = pref.edit();
            editor.putFloat("total", total);
            editor.commit();
            textView.setText(String.format("%.2f", total) + "￥");
        }
        catch (Exception ex)
        {
            Log.e(TAG,ex.getMessage());
        }
    }
}
