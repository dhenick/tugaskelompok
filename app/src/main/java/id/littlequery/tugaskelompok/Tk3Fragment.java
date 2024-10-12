package id.littlequery.tugaskelompok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView; // Import TextView

import java.util.List;

public class Tk3Fragment extends Fragment {

    private RecyclerView recyclerView;
    private SurveyAdapter surveyAdapter;
    private SurveyDatabaseHelper dbHelper;
    private Button addButton;
    private TextView noDataTextView; // TextView untuk keterangan data kosong

    public Tk3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tk3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noDataTextView = view.findViewById(R.id.no_data_text); // Inisialisasi TextView
        dbHelper = new SurveyDatabaseHelper(getContext());
        loadSurvey();

        addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> {
            // Navigasi ke AddSurveyFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, new AddSurveyFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadSurvey() {
        List<Survey> surveyList = dbHelper.getAllSurveys();

        if (surveyList.isEmpty()) {
            // Jika tidak ada data, tampilkan keterangan
            noDataTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE); // Sembunyikan RecyclerView
        } else {
            // Jika ada data, sembunyikan keterangan dan tampilkan RecyclerView
            noDataTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            surveyAdapter = new SurveyAdapter(surveyList, getContext(), new SurveyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Survey survey) {
                    // Pastikan Anda menggunakan ID yang benar untuk navigasi
                    int surveyId = survey.getId(); // Ambil ID dari objek Survey

                    // Navigasi ke SurveyDetailFragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flFragment, SurveyDetailFragment.newInstance(surveyId)) // Gunakan ID
                            .addToBackStack(null)
                            .commit();
                }

                @Override
                public void onEditClick(Survey survey) {
                    // Navigasi ke EditSurveyFragment
                    // Anda dapat menambahkan logika di sini jika ingin
                }

                @Override
                public void onDeleteClick(Survey survey) {
                    // Hapus survei dan reload data
                    // dbHelper.deleteSurvey(survey.getNik());
                    // loadSurvey(); // Reload data setelah hapus
                }
            });

            recyclerView.setAdapter(surveyAdapter); // Atur adapter untuk RecyclerView
        }
    }
}
