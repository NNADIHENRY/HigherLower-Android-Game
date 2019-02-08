package com.htaka.higherlower;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Handler handler; // Our new additio
    Runnable updateCurrentTime;

    List<Terms> terms = new ArrayList<Terms>();
    List<Integer> answeredQues = new ArrayList<Integer>();
    List<Integer> unansweredQues = new ArrayList<Integer>();

    TextView text1, text2, scoreTxt,
            textAvg1,textAvg2;
    Button btn1, btn2;

    int randNum1, randNum2, countUntilX;
    int score = 0, counter = 0;

    boolean startin = true,runTimer=false;
    Random rand = new Random();

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        intent = new Intent(MainActivity.this,MenuActivity.class);
        Intent hI = getIntent();
        score =  hI.getIntExtra("Score",score);
        Log.i("Score is",""+score) ;
        storage = FirebaseStorage.getInstance();

        SignIn();
        SetViews();
        SetAllTrends();
        SetQuestion();

        handler = new Handler();
        updateCurrentTime = new Runnable(){
            @Override
            public void run()
            {
                if(countUntilX<terms.get(randNum2).getAverage()) {
                    countUntilX += 1 * (terms.get(randNum2).getAverage())/100;
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String avrgFrmater = formatter.format(countUntilX);
                    textAvg2.setText(avrgFrmater);  // Update the current Time
                    handler.postDelayed(this, 1);  // Run this again in 1 second
                }   else{
                    countUntilX = 0;
                    runTimer=false;
                }
            }
        };
    }

    private void SignIn() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("NOTHING", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("NOTHING", "onAuthStateChanged:signed_out");
                }
            }
        };
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Shit", "signInAnonymously:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("Shit", "signInAnonymously", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SetAllTrends() {
        terms.add(new Terms("حقوق المراة", 8100));
        terms.add(new Terms("الله", 301000));
        terms.add(new Terms("ديموقراطية", 260));
        terms.add(new Terms("نانسي عجرم", 368000));
        terms.add(new Terms("فيسبوك", 68000000));
        terms.add(new Terms("تويتر", 5000000));
        terms.add(new Terms("اسلام", 27100));
        terms.add(new Terms("سوريا", 165000));
        terms.add(new Terms("فلسطين", 90500));
        terms.add(new Terms("فارس كرم", 33100));
        terms.add(new Terms("هيفاء وهبي", 450000));
        terms.add(new Terms("اثاث", 12100));
        terms.add(new Terms("محرمات", 2900));
        terms.add(new Terms("ابراج الفلك", 201000));
        terms.add(new Terms("نور ستارز", 135000));
        terms.add(new Terms("مفيش صاحب يتصاحب", 673000));
        terms.add(new Terms("ميرنا المهندس", 246000));
        terms.add(new Terms("انت معلم", 1500000));
        terms.add(new Terms("نور الشريف", 165000));
        terms.add(new Terms("نجوى كرم", 247000));
        terms.add(new Terms("ذا فويس", 110000));
        terms.add(new Terms("شاهد نت", 2740000));
        terms.add(new Terms("كرة القدم", 165000));
        terms.add(new Terms("كرة السلة", 40500));
        terms.add(new Terms("القران", 165000));
        terms.add(new Terms("العيد", 368000));
        terms.add(new Terms("رمضان", 1220000));
        terms.add(new Terms("الحج", 246000));
        terms.add(new Terms("العاب", 11100000));
        terms.add(new Terms("داعش", 1000000));
        terms.add(new Terms("جالاكسي", 11000000));
        terms.add(new Terms("ايفون", 5000000));
        terms.add(new Terms("الحب", 550000));
        terms.add(new Terms("السعودية", 246000));
        terms.add(new Terms("العراق", 110000));
        terms.add(new Terms("الاردن", 9900));
        terms.add(new Terms("يلا شوت", 16000000));
        terms.add(new Terms("الكورة اون لاين", 11100000));
        terms.add(new Terms("بوكيمون", 3350000));
        terms.add(new Terms("تامر حسني", 246000));
        terms.add(new Terms("معجون كولجيت", 165000));
        terms.add(new Terms("حقوق الحيوان", 33100));
        terms.add(new Terms("التوراة", 9900));
        terms.add(new Terms("الانجيل", 27100));
        terms.add(new Terms("مكعبات ليجو", 50000000));
        terms.add(new Terms("ميسي", 301000));
        terms.add(new Terms("كريستيانو رونالدو",201000 ));
        terms.add(new Terms("عمليات تجميل", 18100));
        terms.add(new Terms("شرطة", 90500));
        terms.add(new Terms("مصارعة", 165000));
        terms.add(new Terms("نبات", 8100));
        terms.add(new Terms("بي ام دبليو",6120000));
        terms.add(new Terms("سناب شات", 1220000));
        terms.add(new Terms("انستقرام", 2240000));
        terms.add(new Terms("المغرب", 110000));
        terms.add(new Terms("حبوب القهوة", 45000));
        terms.add(new Terms("لوحة الموناليزا", 27100));
        terms.add(new Terms("باريس", 135000));
        terms.add(new Terms("العنصرية", 201000));
        terms.add(new Terms("رياضة", 135000));
        terms.add(new Terms("سبيستون", 90500));
        terms.add(new Terms("دولار", 16500));
        terms.add(new Terms("القدس", 110000));
        terms.add(new Terms("دبي", 100000));
        terms.add(new Terms("ارز", 110000));
        terms.add(new Terms("مطعم", 90500));
        terms.add(new Terms("اكل", 22200));
        terms.add(new Terms("اليسا", 368000));
        terms.add(new Terms("حيوان", 18100));
        terms.add(new Terms("قتال", 40500));
        terms.add(new Terms("شارع", 12100));
        terms.add(new Terms("نظارات", 9900));
        terms.add(new Terms("لعبة لو خيروك", 165000));
        terms.add(new Terms("كراهية", 2900));
        terms.add(new Terms("عطلة", 5400));
        terms.add(new Terms("مدرسة", 110000));
        terms.add(new Terms("صيد", 27100));
        terms.add(new Terms("طب", 14800));
        terms.add(new Terms("هندسة", 6600));
        terms.add(new Terms("كحول", 1600));
        terms.add(new Terms("فيراري", 401000));
        terms.add(new Terms("لامبورجيني", 350000));
        terms.add(new Terms("بحث", 135000));
        terms.add(new Terms("بحر", 36000));
        terms.add(new Terms("انثى", 8100));
        terms.add(new Terms("ذكر", 18100));
        terms.add(new Terms("اوليمبياد", 330100));
        terms.add(new Terms("سعد المجرد", 450000));
        terms.add(new Terms("ملابس", 74000));
        terms.add(new Terms("حسين الديك", 74000));
        terms.add(new Terms("علي الديك", 90500));
        terms.add(new Terms("محمد عساف", 135000));
        terms.add(new Terms("ناصيف زيتون", 60600));
        terms.add(new Terms("طيور الجنة", 1500000));
        terms.add(new Terms("وادي الذياب", 246000));
        terms.add(new Terms("كاظم الساهر", 368000));
        terms.add(new Terms("ون بيس", 135000));
        terms.add(new Terms("ناروتو", 110000));
        terms.add(new Terms("دراغون بول", 164000));
        terms.add(new Terms("بربس", 673000));
        terms.add(new Terms("ريال مدريد", 1220000));
        terms.add(new Terms("برشلونة", 1120000));
        terms.add(new Terms("رياض محرز", 201000));
        terms.add(new Terms("الرسالة", 18800));
        terms.add(new Terms("توم وجيري",1500000 ));
        terms.add(new Terms("مستر بين", 368000));
        terms.add(new Terms("باراك اوباما", 33100));
        terms.add(new Terms("اليابان", 274000));
        terms.add(new Terms("شركة ابل", 13600000));
        terms.add(new Terms("البرازيل", 45000));
        Log.i("Size",""+terms.size());
    }

    private void SetViews() {
        text1 = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView2);
        textAvg1 = (TextView) findViewById(R.id.textView4);
        textAvg2 = (TextView) findViewById(R.id.textView3);
        scoreTxt = (TextView) findViewById(R.id.scoreTextView);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerQuestion(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerQuestion(2);
            }
        });
    }

    void SetQuestion() {
        if (counter == terms.size()-1) {
            startActivity(intent);
            return;
        }

        Findquestion();

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String avergFrmater = formatter.format(terms.get(randNum1).average);

        text1.setText(terms.get(randNum1).title);
        text2.setText(terms.get(randNum2).title);
        textAvg1.setText(avergFrmater);
        textAvg2.setText("عدد مرات البحث");
        scoreTxt.setText("النتيجة : "+(score));

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://higherlower-b6b84.appspot.com");

        storageRef.child(terms.get(randNum1).title+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView img = (ImageView)findViewById(R.id.imageView1);
                Glide.with(getApplication().getBaseContext()).load(uri).into(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("Failure Getting File", exception.getMessage());
            }
        });

        storageRef.child(terms.get(randNum2).title + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView img = (ImageView) findViewById(R.id.imageView2);
                Glide.with(getApplication().getBaseContext()).load(uri).into(img);
                Log.e("Succes Getting File", uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("Failure Getting File", exception.getMessage());
            }
        });


        btn1.setEnabled(true);
        btn2.setEnabled(true);
    }

    void Findquestion() {
        if (startin) {
            randNum1 = rand.nextInt(terms.size());
            randNum2 = rand.nextInt(terms.size());
            while (randNum2 == randNum1) {
                randNum2 = rand.nextInt(terms.size());
                Log.d("number two", "" + randNum2);
            }
            startin = false;
            Log.d("number one", "" + randNum1);

        } else {
            randNum1 = randNum2;
            randNum2 = RandomNumber();
        }
    }

    int RandomNumber() {
        int num;
        unansweredQues.clear();
        for (int i = 0; i < terms.size(); i++) {
            if (i != randNum1 && !answeredQues.contains(i)) {
                unansweredQues.add(i);
            }
        }
        num = rand.nextInt(unansweredQues.size());
        return unansweredQues.get(num);
    }

    public void AnswerQuestion(final int id) {

        btn2.setEnabled(false);
        btn1.setEnabled(false);

        handler.postDelayed(updateCurrentTime,1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                int avg1 = terms.get(randNum1).average,
                        avg2 = terms.get(randNum2).average;

                counter++;
                answeredQues.add(randNum1);

                intent.putExtra("Score", score);

                if (avg1 < avg2) {
                    if (id == 1)
                    {score++;SetQuestion();}
                    else{
                        startActivity(intent);
                    }
                } else if (avg1 > avg2) {
                    if (id == 2)
                    {score++;SetQuestion();}
                    else{
                        startActivity(intent);
                    }

                } else {
                    score++;
                    SetQuestion();
                }

            }
        }, 3000);


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}
