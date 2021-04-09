package android.bignerdranch.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Requests.LoginRequest;
import Results.LoginResult;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}