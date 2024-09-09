package id.littlequery.tugaskelompok.mvvm.tk_one;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import id.littlequery.tugaskelompok.model.User;

public class TkOneMvvmViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<User>> userMutableLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<User>> getUser() {
        return userMutableLiveData;
    }

    public void setUser() {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("Dedik Haryanto", "dedik.haryanto@gmail.com"));
        userList.add(new User("Panca Adnan Andrian", "acari.panca21@gmail.com"));
        userList.add(new User("Alexander Purwoko", "alexander.purwoko@gmail.com")); // Sesuaikan email jika diperlukan

        userMutableLiveData.setValue(userList);
    }
}
