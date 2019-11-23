package cisc.barcamp.passscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cisc.barcamp.passscanner.network.BarcampAPI;
import cisc.barcamp.passscanner.network.Requests.ConfirmarRequest;
import cisc.barcamp.passscanner.network.Requests.QRDataRequest;
import cisc.barcamp.passscanner.network.Requests.TodosRequest;
import cisc.barcamp.passscanner.network.calls.TodosTask;
import cisc.barcamp.passscanner.network.models.QRData;
import cisc.barcamp.passscanner.network.models.Todos;
import cisc.barcamp.passscanner.network.responses.RetrieveAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RetrieveAPI {

    Button mbQRScan;
    Button btnTestApi;
    Retrofit retroFit = BarcampAPI.retroFit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUI();

    }

    void loadUI() {
        mbQRScan = findViewById(R.id.mbQRScan);
        btnTestApi = findViewById(R.id.btnTestApi);
        clickListeners();
    }

    void clickListeners() {
        mbQRScan.setOnClickListener(v -> startActivity(new Intent(this, QRScanView.class)));
        btnTestApi.setOnClickListener(v -> {
//            new TodosTask(this).execute();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading");
            progressDialog.show();
//            QRDataRequest service = retroFit.create(QRDataRequest.class);
//            Call<List<QRData>> call = service.getData();
//            call.enqueue(new Callback<List<QRData>>() {
//                @Override
//                public void onResponse(Call<List<QRData>> call, Response<List<QRData>> response) {
//                    progressDialog.dismiss();
//                    for (QRData t :
//                            response.body()) {
//                        Log.e("response", t.getId() + "\n " + t.getCedula() + "\n " + t.getNombre() + "\n " + t.getSize());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<QRData>> call, Throwable t) {
//                    progressDialog.dismiss();
//                    Log.e("error", t.getMessage());
//                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                }
//            });
            ConfirmarRequest confirm = retroFit.create(ConfirmarRequest.class);
            Call<QRData> callConfirm = confirm.confirm("77");
            callConfirm.enqueue(new Callback<QRData>() {
                @Override
                public void onResponse(Call<QRData> call, Response<QRData> response) {
                    progressDialog.dismiss();
                    Log.e("data", response.code() + "-");
//                        Log.e("response", response.body().getId() + "\n " + response.body().getCedula() + "\n " + response.body().getNombre() + "\n " + response.body().getSize());
                }

                @Override
                public void onFailure(Call<QRData> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("error", t.getMessage());
                }
            });
        });
    }

    @Override
    public void data(List<Todos> data) {

    }
}
