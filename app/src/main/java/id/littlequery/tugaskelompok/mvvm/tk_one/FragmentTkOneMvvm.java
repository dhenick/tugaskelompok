package id.littlequery.tugaskelompok.mvvm.tk_one;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.littlequery.tugaskelompok.R;
import id.littlequery.tugaskelompok.model.User;


public class FragmentTkOneMvvm extends Fragment {

    private TkOneRecyclerViewAdapter tkOneRecyclerViewAdapter;
    private final ArrayList<User> userList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tk_one_mvvm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.tk_one_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tkOneRecyclerViewAdapter = new TkOneRecyclerViewAdapter(userList);
        recyclerView.setAdapter(tkOneRecyclerViewAdapter);

        TkOneMvvmViewModel userViewModel = new ViewModelProvider(requireActivity()).get(TkOneMvvmViewModel.class);

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null) {
                    userList.addAll(users);
                    tkOneRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
        userViewModel.setUser();
    }
}