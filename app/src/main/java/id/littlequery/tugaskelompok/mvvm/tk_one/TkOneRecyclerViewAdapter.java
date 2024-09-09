package id.littlequery.tugaskelompok.mvvm.tk_one;

import static android.content.ContentValues.TAG;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.littlequery.tugaskelompok.R;
import id.littlequery.tugaskelompok.model.User;

public class TkOneRecyclerViewAdapter extends RecyclerView.Adapter<TkOneRecyclerViewAdapter.TkOneViewHolder> {
    public TkOneRecyclerViewAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    ArrayList<User> listUser;

    @NonNull
    @Override
    public TkOneRecyclerViewAdapter.TkOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tk_one, parent
                , false);
        return new TkOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TkOneRecyclerViewAdapter.TkOneViewHolder holder, int position) {
        final User user = listUser.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class TkOneViewHolder extends RecyclerView.ViewHolder {

        TextView name,email;
        public TkOneViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTextView);
            email = itemView.findViewById(R.id.emailTextView);
        }
    }
}
