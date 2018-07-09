package com.example.korir.ihearyou;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button start;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(Button)findViewById(R.id.start);



        layout=(LinearLayout)findViewById(R.id.screen);
        playsound(R.raw.welcome);

        //start the voice input
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               delayInput();
            }
        });


    }
    // delay input
    public void delayInput(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
               getVoice();
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,1000);
    }
    //method to switch sound
    public  void swtchSound(String color){
        switch(color){
            case "red":
                playsound(R.raw.red);
                break;
            case "blue":
                playsound(R.raw.blue);
                break;
//            case "green":
//                playsound(R.raw.green);
//                break;
//            case "yellow":
//                playsound(R.raw.yellow);
//
//                break;


        }

    }

    //method to play sound
    public void playsound(int resourcefile){
        MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this, resourcefile);
        mediaPlayer.start();
    }

    //method to get the voice input
    public void getVoice(){

        Toast.makeText(this, "Voice input started", Toast.LENGTH_SHORT).show();
        Intent voiceIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"hello say something");
        try{
            startActivityForResult(voiceIntent,99);
        }catch (Exception e){
            Toast.makeText(this, "You device might not support speech", Toast.LENGTH_SHORT).show();

        }

    }

    //method to get the result from the recognition activity

    public void onActivityResult(int request_code,int result_code,Intent i){
        super.onActivityResult(request_code,result_code,i);

        //test for the request_code

        if(request_code==99){
            //create the arraylist to store the result
            try{
                ArrayList<String> arrayList= i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String result=arrayList.get(0);
                //for now just to the result
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                 changeColor(result);
                 swtchSound(result);
            }catch (Exception e){
                e.printStackTrace();

            }
        }

    }

    //method to change the backgouned color
    public void changeColor(String text){
        switch(text){
            case "red":
                layout.setBackgroundColor(Color.RED);
                break;
            case "blue":
                layout.setBackgroundColor(Color.BLUE);
                break;
//            case "green":
//                layout.setBackgroundColor(Color.GREEN);
//                break;
//            case "yellow":
//                layout.setBackgroundColor(Color.YELLOW);

//                break;
                default:
                    playsound(R.raw.error);
        }




    }

}
