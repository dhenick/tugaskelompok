package id.littlequery.tugaskelompok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.littlequery.tugaskelompok.model.User;


public class MvvmFragment extends Fragment {

    private TextView tvUserName,tvUserEmail;
    private final UserViewModel userViewModel = new UserViewModel();
    public MvvmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mvvm, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUserName = getView().findViewById(R.id.tvUserName);
        tvUserEmail= getView().findViewById(R.id.tvUserEmail);

        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvUserName.setText(user.getName());
                tvUserEmail.setText(user.getEmail());
            }
        });

    }
}