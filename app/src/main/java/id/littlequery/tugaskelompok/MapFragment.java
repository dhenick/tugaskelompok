package id.littlequery.tugaskelompok;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationTitleTextView;
    private TextView coordinatesTextView;
    private TextView streetNameTextView; // Untuk nama jalan
    private GoogleSignInClient googleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        locationTitleTextView = view.findViewById(R.id.locationTitle);
        streetNameTextView = view.findViewById(R.id.streetName); // Inisialisasi nama jalan
        coordinatesTextView = view.findViewById(R.id.coordinates);
        ImageView userIcon = view.findViewById(R.id.user_icon);

        // Setup location services
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Set up Google Sign-In client
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);

        // Handle user icon click
        userIcon.setOnClickListener(v -> confirmSignOut());

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Update map and marker
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    map.addMarker(new MarkerOptions().position(currentLatLng).title("Lokasi Anda Sekarang"));

                    // Update UI
                    locationTitleTextView.setText("Lokasi Anda");
                    coordinatesTextView.setText("Koordinat: " + location.getLatitude() + ", " + location.getLongitude());

                    // Menampilkan nama jalan
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            String streetName = addresses.get(0).getThoroughfare(); // Ambil nama jalan
                            streetNameTextView.setText("Nama Jalan: " + (streetName != null ? streetName : "Tidak Diketahui"));
                        } else {
                            streetNameTextView.setText("Nama Jalan: Tidak Diketahui");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        streetNameTextView.setText("Nama Jalan: Tidak Diketahui");
                    }
                }
            }
        });
    }

    private void confirmSignOut() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Yakin Akan Keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            // Show confirmation message
            Snackbar.make(requireView(), "Anda telah keluar", Snackbar.LENGTH_SHORT).show();

            // Navigate back to Tk4Fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, new Tk4Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
}
