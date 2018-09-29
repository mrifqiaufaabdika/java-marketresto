package com.example.abdialam.marketresto.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.example.abdialam.marketresto.fragment.AccountFragment;
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
        getSupportActionBar().setElevation(0);

        //loading the default fragment
        Fragment f = new RestoFragment();

        if (getIntent().hasExtra("aksi")){
            String alamat = getIntent().getStringExtra("aksi");
            Bundle b = new Bundle();
            b.putString("aksi",alamat);
            f.setArguments(b);
        }

        loadFragment(f);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_resto:
                        fragment = new RestoFragment();
                        getSupportActionBar().setTitle("Market Resto");
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        getSupportActionBar().setTitle("Search");
                        break;

                    case R.id.action_favorite:
                        getSupportActionBar().setTitle("Favorit");
                        fragment = new FavoriteFragment();

                        break;

                    case R.id.action_wallet:
                        fragment = new WalletFragment();
                        getSupportActionBar().setTitle("Resto Pay");
                        break;
                    case R.id.action_account:
                        getSupportActionBar().setTitle("Akun");
                        fragment = new AccountFragment();


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
