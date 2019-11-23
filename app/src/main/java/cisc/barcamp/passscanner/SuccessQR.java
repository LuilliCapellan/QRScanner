package cisc.barcamp.passscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import cisc.barcamp.passscanner.network.BarcampAPI;
import cisc.barcamp.passscanner.network.Requests.ConfirmarRequest;
import cisc.barcamp.passscanner.network.models.QRData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SuccessQR extends AppCompatActivity {
    public static final String DATA = "DATA";
    private TextView tvID, tvCedula, tvNombre, tvSize;

    Retrofit retroFit = BarcampAPI.retroFit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_qr);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        loadUI();
        processIntent();
    }

    private void loadUI() {
        tvID = findViewById(R.id.tvID);
        tvCedula = findViewById(R.id.tvCedula);
        tvNombre = findViewById(R.id.tvNombre);
        tvSize = findViewById(R.id.tvSize);
    }

    private void processIntent() {
        String rawData = getIntent().getStringExtra(DATA);
        QRData data = new QRData(rawData.split(";")[0], rawData.split(";")[1], rawData.split(";")[2], rawData.split(";")[3]);

        ConfirmarRequest confirm = retroFit.create(ConfirmarRequest.class);
        Call<QRData> callConfirm = confirm.confirm("77");
        callConfirm.enqueue(new Callback<QRData>() {
            @Override
            public void onResponse(Call<QRData> call, Response<QRData> response) {
                progressDialog.dismiss();
                Log.e("data", response.code() + "-");
            }

            @Override
            public void onFailure(Call<QRData> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error", t.getMessage());
            }
        });

        tvID.setText(data.getId());
        tvCedula.setText(data.getCedula());
        tvNombre.setText(data.getNombre());
        tvSize.setText(data.getSize());
        Log.e("Data", data.getId() + data.getNombre() + data.getSize() + data.getCedula());
        Toast.makeText(this, data.getId() + data.getNombre() + data.getSize() + data.getCedula(), Toast.LENGTH_SHORT).show();
    }
}
