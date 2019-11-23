package cisc.barcamp.passscanner.network.Requests;

import java.util.List;

import cisc.barcamp.passscanner.network.models.QRData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConfirmarRequest {
    @POST("API/confirmar")
    Call<QRData> confirm(@Query("id") String id);
}

