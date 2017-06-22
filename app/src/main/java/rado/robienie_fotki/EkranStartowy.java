package rado.robienie_fotki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EkranStartowy extends AppCompatActivity
{

    Button buttonZamelduj;
    Button buttonWyjdz;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran_startowy);

        buttonZamelduj = (Button)findViewById(R.id.button4);
        buttonWyjdz = (Button)findViewById(R.id.button5);

        buttonZamelduj.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
            }
        });

        buttonWyjdz.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                System.exit(0);
            }
        });
    }




}
