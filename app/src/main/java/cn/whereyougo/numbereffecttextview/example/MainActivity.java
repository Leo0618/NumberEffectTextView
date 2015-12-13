package cn.whereyougo.numbereffecttextview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.whereyougo.numbereffevcttextview.NumberEffectTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
        NumberEffectTextView numberEffectTextView = (NumberEffectTextView) findViewById(R.id.tv);
        numberEffectTextView.withNumber("9,999,999.99").setDuration(1000).start();
    }

    public void clear(View v) {
        NumberEffectTextView numberEffectTextView = (NumberEffectTextView) findViewById(R.id.tv);
        numberEffectTextView.setText("0.00");
    }
}
