package id.littlequery.tugaskelompok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static id.littlequery.tugaskelompok.SharedPrefManager.SP_NAMA;
import static id.littlequery.tugaskelompok.SharedPrefManager.SP_SUDAH_LOGIN;
import static id.littlequery.tugaskelompok.SharedPrefManager.SP_PEGAWAI_APP;

public class Tk2Fragment extends Fragment {

    private Button tblKeluar, tblUtama;
    SharedPreferences sharedPrefManager;



    public Tk2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tk2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        tblKeluar = getView().findViewById(R.id.buttonLogout);

        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });

        tblUtama = getView().findViewById(R.id.buttonMain);
        tblUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new GaleryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flFragment,fragment).commit();
            }
        });
    }
    private void keluar(){
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
        sharedPrefManager = getActivity().getSharedPreferences(SharedPrefManager.SP_PEGAWAI_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.putString(SP_NAMA,"");
        editor.putBoolean(SP_SUDAH_LOGIN,false);
        editor.apply();
        getActivity().finish();
    }
}