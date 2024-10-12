package id.littlequery.tugaskelompok;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyDetailFragment extends Fragment {

    private TextView tvNik, tvNama, tvAlamat, tvPropinsi, tvKabupaten, tvPenghasilan, tvJumlahPenghuni, tvMinat;
    private Button btnEdit, btnDelete, btnBack;
    private SurveyDatabaseHelper dbHelper;
    private int id;

    public static SurveyDetailFragment newInstance(int id) {
        SurveyDetailFragment fragment = new SurveyDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListeners();
        loadSurveyDetails();
    }

    private void initViews(View view) {
        tvNik = view.findViewById(R.id.tv_nik);
        tvNama = view.findViewById(R.id.tv_nama);
        tvAlamat = view.findViewById(R.id.tv_alamat);
        tvPropinsi = view.findViewById(R.id.tv_propinsi);
        tvKabupaten = view.findViewById(R.id.tv_kabupaten);
        tvPenghasilan = view.findViewById(R.id.tv_penghasilan);
        tvJumlahPenghuni = view.findViewById(R.id.tv_jumlah_penghuni);
        tvMinat = view.findViewById(R.id.tv_minat);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnBack = view.findViewById(R.id.btn_back);
        dbHelper = new SurveyDatabaseHelper(getContext());
    }

    private void initListeners() {
        btnEdit.setOnClickListener(v -> navigateToEditSurvey());
        btnDelete.setOnClickListener(v -> showDeleteConfirmation());
        btnBack.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }

    private void navigateToEditSurvey() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, EditSurveyFragment.newInstance(id))
                .addToBackStack(null)
                .commit();
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Hapus Survei")
                .setMessage("Apakah Anda yakin ingin menghapus survei ini?")
                .setPositiveButton("Ya", (dialog, which) -> deleteSurvey())
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void deleteSurvey() {
        dbHelper.deleteSurvey(id);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void loadSurveyDetails() {
        Survey survey = dbHelper.getSurveyById(id);
        if (survey != null) {
            tvNik.setText(survey.getNik());
            tvNama.setText(survey.getNama());
            tvAlamat.setText(survey.getAlamat());
            tvPropinsi.setText(survey.getPropinsi());
            tvKabupaten.setText(survey.getKabupaten());
            tvPenghasilan.setText(survey.getPenghasilan());
            tvJumlahPenghuni.setText(String.valueOf(survey.getJumlahPenghuni()));
            tvMinat.setText(survey.getMinatSubsidi());
        } else {
            Toast.makeText(getContext(), "Data survei tidak ditemukan", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}

