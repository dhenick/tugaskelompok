package id.littlequery.tugaskelompok;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.littlequery.tugaskelompok.model.User;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public UserViewModel(){
        userMutableLiveData.setValue(new User("Friestya Query Bellvania","bellvania.query@gmail.com"));

    }
    public LiveData<User> getUser(){
        return userMutableLiveData;
    }
}
