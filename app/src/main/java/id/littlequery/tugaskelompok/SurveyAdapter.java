package id.littlequery.tugaskelompok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {

    private List<Survey> surveyList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Survey survey);
        void onEditClick(Survey survey);
        void onDeleteClick(Survey survey);
    }

    public SurveyAdapter(List<Survey> surveyList, Context context, OnItemClickListener listener) {
        this.surveyList = surveyList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_survey, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {
        Survey survey = surveyList.get(position);
        holder.bind(survey, listener);
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    public static class SurveyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNik;
        private TextView tvNama;
        private TextView tvAlamat;

        public SurveyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
        }

        public void bind(Survey survey, OnItemClickListener listener) {
            tvNik.setText(survey.getNik());
            tvNama.setText(survey.getNama());
            tvAlamat.setText(survey.getAlamat());

            itemView.setOnClickListener(v -> listener.onItemClick(survey));
        }
    }
}
