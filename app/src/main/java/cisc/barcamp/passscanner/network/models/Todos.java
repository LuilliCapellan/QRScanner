package cisc.barcamp.passscanner.network.models;

import com.google.gson.annotations.SerializedName;

public class Todos {

    @SerializedName("userId")
    private String userId;
    @SerializedName("id")
    private String id;
    @SerializedName("tittle")
    private String tittle;
    @SerializedName("completed")
    private String completed;

    public Todos(String userId, String id, String tittle, String completed) {
        this.userId = userId;
        this.id = id;
        this.tittle = tittle;
        this.completed = completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
