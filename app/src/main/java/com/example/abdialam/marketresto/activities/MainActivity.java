package com.example.abdialam.marketresto.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abdialam.marketresto.R;
import com.example.abdialam.marketresto.fragment.FavoriteFragment;
import com.example.abdialam.marketresto.fragment.RestoFragment;
import com.example.abdialam.marketresto.fragment.SearchFragment;
import com.example.abdialam.marketresto.fragment.WalletFragment;
import com.example.abdialam.marketresto.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {



    Context mContext;
    SessionManager sessionManager;
    BottomNavigationView bottomNavigationView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext =this;
        sessionManager = new SessionManager(this);
        checkSessionLogin();
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigation);


        //loading the default fragment
        loadFragment(new RestoFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_resto:
                        fragment = new RestoFragment();
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.action_favorite:
                        fragment = new FavoriteFragment();
                        break;

                    case R.id.action_wallet:
                        fragment = new WalletFragment();
                        break;
                    case R.id.action_account:
                        fragment = new RestoFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });




    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        if(item.getItemId()==R.id.setting){
            startActivity(new Intent(this, SettingActivity.class));

        }else if (item.getItemId()==R.id.help){

        }

        return true;
    }






    private void setPhoneNumber(){
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        try{
  //          tvPhoneNumber.setText(user.getPhoneNumber());
        }catch (Exception e){
            Toast.makeText(this,"Phone Number Not Found",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSessionLogin (){
        if(!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(mContext ,SigninActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    }
}
