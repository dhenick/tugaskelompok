package id.littlequery.tugaskelompok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.littlequery.tugaskelompok.model.User;


public class MvcFragment extends Fragment {

    private TextView tvUserName,tvUserEmail;


    public MvcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mvc, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUserName = getView().findViewById(R.id.tvUserName);
        tvUserEmail= getView().findViewById(R.id.tvUserEmail);
        User user = new User("Dedik Haryanto","dedik.haryanto@gmail.com");
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
    }
}