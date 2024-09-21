package id.littlequery.tugaskelompok;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import static id.littlequery.tugaskelompok.SharedPrefManager.SP_NAMA;
import static id.littlequery.tugaskelompok.SharedPrefManager.SP_SUDAH_LOGIN;
import static id.littlequery.tugaskelompok.SharedPrefManager.SP_PEGAWAI_APP;
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment (Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment,fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.tk1:
                fragment = new HomeFragment();
                break;
            case R.id.tk2:
                fragment = new Tk2Fragment();
                break;
            case R.id.tk3:
                fragment = new Tk3Fragment();
                break;
            case R.id.tk4:
                fragment = new Tk4Fragment();
                break;
        }
        return loadFragment(fragment);
    }
}