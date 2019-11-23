package cisc.barcamp.passscanner.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcampAPI {
    public static final Retrofit retroFit = new Retrofit.Builder()
            .baseUrl("https://form.barcamp.org.do/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
