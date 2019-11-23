package cisc.barcamp.passscanner.network.Requests;

import java.util.List;

import cisc.barcamp.passscanner.network.models.QRData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QRDataRequest {
    @POST("API/registros")
    Call<List<QRData>> getData();
}
