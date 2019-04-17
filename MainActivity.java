package com.example.user.textrecog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
ImageView imageView ;
Button btn,btn1;
TextView text;
TextToSpeech textToSpeech;
    int result;
    private Bitmap bitmap;


    @Override
    protected void onActivityResult(int requestCode, 0int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final StringBuilder stringBuilder= new StringBuilder();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView)findViewById(R.id.imageView);
        btn=(Button)findViewById(R.id.button2);
        text=(TextView)findViewById(R.id.text1);


        btn1=(Button)findViewById(R.id.button)  ;
        Button btn2=(Button)findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("");
                stringBuilder.setLength(0);
                TextRecognizer textRecognizer=new TextRecognizer.Builder(getApplicationContext()).build();
                if(!textRecognizer.isOperational())
                {
                    Log.e("ERROR","Detector dependencies are not yet available");}
                    else {
                    Frame frame=new Frame.Builder().setBitmap(bitmap).build();//we create a frame from bitmap
                    SparseArray<TextBlock> items= textRecognizer.detect(frame);//se the text recognizer of google services vision to detect the frame created

                    for(int i=0;i<items.size();++i)
                    {TextBlock item=items.valueAt(i);
                    stringBuilder.append(item.getValue());
                    stringBuilder.append("\n");
                    }
                    text.setText(stringBuilder.toString());
                     }

            }
        });
        textToSpeech =new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {//initialize txt to speech
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    result=textToSpeech.setLanguage(Locale.UK);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stringBuilder.toString()!=null)//no text
                { if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED )
                {
                    Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   String text = stringBuilder.toString();
                   textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
            }}
        });


    }

}
