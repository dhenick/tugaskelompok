package id.littlequery.tugaskelompok;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

public class EditSurveyFragment extends Fragment {

    private EditText etNik, etNama, etAlamat, etJumlahPenghuni;
    private Button buttonSave, buttonBack;
    private Spinner spnPropinsi, spnKabupaten, spnMinatSubsidi;
    private RadioGroup rgPenghasilan;
    private SurveyDatabaseHelper dbHelper;
    private int id; // ID untuk survei yang sedang diedit

    public EditSurveyFragment() {

    }

    public static EditSurveyFragment newInstance(int id) {
        EditSurveyFragment fragment = new EditSurveyFragment();
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
        return inflater.inflate(R.layout.fragment_edit_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi View
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

        populateSpinnerData();

        loadSurveyDetails();

        spnPropinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateKabupatenSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonSave.setOnClickListener(v -> {
            if (isInputValid()) {
                // Simpan perubahan
                Survey survey = new Survey(
                        id,
                        etNik.getText().toString(),
                        etNama.getText().toString(),
                        etAlamat.getText().toString(),
                        spnPropinsi.getSelectedItem().toString(),
                        spnKabupaten.getSelectedItem().toString(),
                        getSelectedPenghasilan(),
                        Integer.parseInt(etJumlahPenghuni.getText().toString()),
                        spnMinatSubsidi.getSelectedItem().toString()
                );
                dbHelper.updateSurvey(id, survey); // Memperbarui survei
                Toast.makeText(getContext(), "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Mohon lengkapi semua data dengan benar.", Toast.LENGTH_LONG).show();
            }
        });

        buttonBack.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
    }

    private void populateSpinnerData() {
        ArrayAdapter<CharSequence> adapterPropinsi = ArrayAdapter.createFromResource(
                getContext(),
                R.array.propinsi_array,
                android.R.layout.simple_spinner_item
        );
        adapterPropinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPropinsi.setAdapter(adapterPropinsi);

        ArrayAdapter<CharSequence> adapterKabupaten = ArrayAdapter.createFromResource(
                getContext(),
                R.array.kabupaten_default,
                android.R.layout.simple_spinner_item
        );
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKabupaten.setAdapter(adapterKabupaten);

        ArrayAdapter<CharSequence> adapterMinatSubsidi = ArrayAdapter.createFromResource(
                getContext(),
                R.array.minat_subsidi_array,
                android.R.layout.simple_spinner_item
        );
        adapterMinatSubsidi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMinatSubsidi.setAdapter(adapterMinatSubsidi);
    }

    private void loadSurveyDetails() {
        Survey survey = dbHelper.getSurveyById(id);
        if (survey != null) {
            etNik.setText(survey.getNik());
            etNama.setText(survey.getNama());
            etAlamat.setText(survey.getAlamat());
            setSpinnerSelection(spnPropinsi, survey.getPropinsi());
            updateKabupatenSpinner(spnPropinsi.getSelectedItemPosition());
            setSpinnerSelection(spnKabupaten, survey.getKabupaten());
            setPenghasilanSelection(survey.getPenghasilan());
            etJumlahPenghuni.setText(String.valueOf(survey.getJumlahPenghuni()));
            setSpinnerSelection(spnMinatSubsidi, survey.getMinatSubsidi());
        }
    }

    private void updateKabupatenSpinner(int propinsiPosition) {
        int kabupatenArrayId;
        switch (propinsiPosition) {
            case 0:
                kabupatenArrayId = R.array.kabupaten_propinsi_1;
                break;
            case 1:
                kabupatenArrayId = R.array.kabupaten_propinsi_2;
                break;
            case 2:
                kabupatenArrayId = R.array.kabupaten_propinsi_3;
                break;
            case 3:
                kabupatenArrayId = R.array.kabupaten_propinsi_4;
                break;
            case 4:
                kabupatenArrayId = R.array.kabupaten_propinsi_5;
                break;
            default:
                kabupatenArrayId = R.array.kabupaten_default;
        }

        ArrayAdapter<CharSequence> adapterKabupaten = ArrayAdapter.createFromResource(
                getContext(),
                kabupatenArrayId,
                android.R.layout.simple_spinner_item
        );
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKabupaten.setAdapter(adapterKabupaten);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setPenghasilanSelection(String penghasilan) {
        for (int i = 0; i < rgPenghasilan.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rgPenghasilan.getChildAt(i);
            if (rb.getText().toString().equalsIgnoreCase(penghasilan)) {
                rb.setChecked(true);
                break;
            }
        }
    }

    private String getSelectedPenghasilan() {
        int selectedId = rgPenghasilan.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = rgPenghasilan.findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }

    private boolean isInputValid() {
        String nik = etNik.getText().toString();
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String jumlahPenghuni = etJumlahPenghuni.getText().toString();
        // Validasi
        return !nik.isEmpty() &&
                nik.length() == 15 &&
                nik.matches("\\d+")&&
                !nama.isEmpty() &&
                nama.length() >= 3 &&
                !alamat.isEmpty() &&
                !jumlahPenghuni.isEmpty() ;
                //spnPropinsi.getSelectedItemPosition() != 0 &&
                //spnKabupaten.getSelectedItemPosition() != 0 &&
                //rgPenghasilan.getCheckedRadioButtonId() != -1 &&
                //Integer.parseInt(jumlahPenghuni) > 0 &&
                //spnMinatSubsidi.getSelectedItemPosition() != 0;
    }

}
