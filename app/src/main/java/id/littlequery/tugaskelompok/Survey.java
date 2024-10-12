package id.littlequery.tugaskelompok;

public class Survey {
    private int id;
    private String nik;
    private String nama;
    private String alamat;
    private String propinsi;
    private String kabupaten;
    private String penghasilan;
    private int jumlahPenghuni;
    private String minatSubsidi;

    public Survey(int id, String nik, String nama, String alamat, String propinsi, String kabupaten,
                  String penghasilan, int jumlahPenghuni, String minatSubsidi) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.alamat = alamat;
        this.propinsi = propinsi;
        this.kabupaten = kabupaten;
        this.penghasilan = penghasilan;
        this.jumlahPenghuni = jumlahPenghuni;
        this.minatSubsidi = minatSubsidi;
    }

    public int getId() {
        return id;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPropinsi() {
        return propinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public String getPenghasilan() {
        return penghasilan;
    }

    public int getJumlahPenghuni() {
        return jumlahPenghuni;
    }

    public String getMinatSubsidi() {
        return minatSubsidi;
    }
}
