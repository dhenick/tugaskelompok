package id.littlequery.tugaskelompok;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddSurveyFragment extends Fragment {

    private EditText etNik, etNama, etAlamat, etJumlahPenghuni;
    private Spinner spnPropinsi, spnKabupaten, spnMinatSubsidi;
    private RadioGroup rgPenghasilan;
    private Button buttonSave, buttonBack;

    private SurveyDatabaseHelper dbHelper;

    public AddSurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNik = view.findViewById(R.id.etNik);
        etNama = view.findViewById(R.id.etNama);
        etAlamat = view.findViewById(R.id.etAlamat);
        spnPropinsi = view.findViewById(R.id.spnPropinsi);
        spnKabupaten = view.findViewById(R.id.spnKabupaten);
        rgPenghasilan = view.findViewById(R.id.rgPenghasilan);
        etJumlahPenghuni = view.findViewById(R.id.etJumlahPenghuni);
        spnMinatSubsidi = view.findViewById(R.id.spnMinatSubsidi);
        buttonSave = view.findViewById(R.id.button_save);
        buttonBack = view.findViewById(R.id.button_back);

        dbHelper = new SurveyDatabaseHelper(getContext());

        setupSpinners();

        buttonSave.setOnClickListener(v -> saveSurvey());
        buttonBack.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }

    private void setupSpinners() {
        // Setup propinsi spinner
        ArrayAdapter<CharSequence> adapterPropinsi = ArrayAdapter.createFromResource(getContext(),
                R.array.propinsi_array, android.R.layout.simple_spinner_item);
        adapterPropinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPropinsi.setAdapter(adapterPropinsi);

        // Setup kabupaten spinner dengan default
        ArrayAdapter<CharSequence> adapterKabupaten = ArrayAdapter.createFromResource(getContext(),
                R.array.kabupaten_default, android.R.layout.simple_spinner_item);
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKabupaten.setAdapter(adapterKabupaten);

        // Setup minat subsidi spinner
        ArrayAdapter<CharSequence> adapterMinatSubsidi = ArrayAdapter.createFromResource(getContext(),
                R.array.minat_subsidi_array, android.R.layout.simple_spinner_item);
        adapterMinatSubsidi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMinatSubsidi.setAdapter(adapterMinatSubsidi);

        // Set listener untuk memilih propinsi
        spnPropinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Load kabupaten berdasarkan propinsi yang dipilih
                ArrayAdapter<CharSequence> adapterKabupaten = ArrayAdapter.createFromResource(getContext(),
                        getKabupatenArray(position), android.R.layout.simple_spinner_item);
                adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnKabupaten.setAdapter(adapterKabupaten);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // kalau g ada yg dipilih
            }
        });
    }


    private int getKabupatenArray(int propinsiId) {
        switch (propinsiId) {
            case 0:
                return R.array.kabupaten_propinsi_1;
            case 1:
                return R.array.kabupaten_propinsi_2;
            case 2:
                return R.array.kabupaten_propinsi_3;
            case 3:
                return R.array.kabupaten_propinsi_4;
            case 4:
                return R.array.kabupaten_propinsi_5;
            default:
                return R.array.kabupaten_default;
        }
    }


    private void saveSurvey() {
        String nik = etNik.getText().toString();
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String propinsi = spnPropinsi.getSelectedItem().toString();
        String kabupaten = spnKabupaten.getSelectedItem().toString();
        String penghasilan = getSelectedPenghasilan();
        int jumlahPenghuni = Integer.parseInt(etJumlahPenghuni.getText().toString());
        String minatSubsidi = spnMinatSubsidi.getSelectedItem().toString();

        // Validasi data
        if (!validateInputs(nik, nama, alamat, jumlahPenghuni)) {
            return;
        }

        // Gunakan ID 0 untuk survei baru
        Survey survey = new Survey(0, nik, nama, alamat, propinsi, kabupaten, penghasilan, jumlahPenghuni, minatSubsidi);
        dbHelper.addSurvey(survey);

        Toast.makeText(getContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        // Kembali ke fragment sebelumnya
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean validateInputs(String nik, String nama, String alamat, int jumlahPenghuni) {
        if (nik.length() != 15 || !nik.matches("\\d+")) {
            Toast.makeText(getContext(), "NIK harus berupa 15 digit angka", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nama.length() < 3) {
            Toast.makeText(getContext(), "Nama minimal 3 huruf", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (alamat.isEmpty()) {
            Toast.makeText(getContext(), "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (jumlahPenghuni < 1 || jumlahPenghuni > 5) {
            Toast.makeText(getContext(), "Jumlah penghuni harus antara 1 sampai 5", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getSelectedPenghasilan() {
        int selectedId = rgPenghasilan.getCheckedRadioButtonId();
        RadioButton radioButton = getView().findViewById(selectedId);
        return radioButton != null ? radioButton.getText().toString() : "";
    }
}
