package cisc.barcamp.passscanner.network.calls;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import cisc.barcamp.passscanner.network.models.Todos;
import cisc.barcamp.passscanner.network.responses.RetrieveAPI;
import retrofit2.Call;
import retrofit2.Response;

public class TodosTask extends AsyncTask<Call, Void, String> {

    RetrieveAPI retrieveAPI;

    public TodosTask(RetrieveAPI retrieveAPI) {
        this.retrieveAPI = retrieveAPI;
    }

    @Override
    protected String doInBackground(Call... calls) {

        return calls[0].toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        retrieveAPI.data(s);
    }
}
