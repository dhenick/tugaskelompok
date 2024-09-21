package id.littlequery.tugaskelompok;
import android.content.Context;
import android.content.SharedPreferences;
public class SharedPrefManager {
    public static final String SP_PEGAWAI_APP = "spPegawaiApp";
    public static final String SP_NAMA = "spNama";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    static SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_PEGAWAI_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public static Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN,false);
    }


}
