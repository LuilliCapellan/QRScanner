package cisc.barcamp.passscanner.network.Requests;

import java.util.List;

import cisc.barcamp.passscanner.network.models.Todos;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TodosRequest {
    @GET("todos")
    Call<List<Todos>> getTodos();
}
