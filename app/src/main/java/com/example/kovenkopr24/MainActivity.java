package com.example.kovenkopr24;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;cd C:\Projects\KovenkoPR24

    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView cowImageView = findViewById(R.id.image_cow);
        ImageView chickenImageView = findViewById(R.id.image_chicken);
        ImageView catImageView = findViewById(R.id.image_cat);
        ImageView duckImageView = findViewById(R.id.image_duck);
        ImageView sheepImageView = findViewById(R.id.image_sheep);
        ImageView dogImageView = findViewById(R.id.image_dog);

        cowImageView.setOnClickListener(this);
        chickenImageView.setOnClickListener(this);
        catImageView.setOnClickListener(this);
        duckImageView.setOnClickListener(this);
        sheepImageView.setOnClickListener(this);
        dogImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_cow) {
            playSound(mCowSound);
        } else if (id == R.id.image_chicken) {
            playSound(mChickenSound);
        } else if (id == R.id.image_cat) {
            playSound(mCatSound);
        } else if (id == R.id.image_duck) {
            playSound(mDuckSound);
        } else if (id == R.id.image_sheep) {
            playSound(mSheepSound);
        } else if (id == R.id.image_dog) {
            playSound(mDogSound);
        }
    }

    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(3)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName, Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            createOldSoundPool();
        } else {
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        mCatSound = loadSound("cat.ogg");
        mChickenSound = loadSound("chicken.ogg");
        mCowSound = loadSound("cow.ogg");
        mDogSound = loadSound("dog.ogg");
        mDuckSound = loadSound("duck.ogg");
        mSheepSound = loadSound("sheep.ogg");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }
}