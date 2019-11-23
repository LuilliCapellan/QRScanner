package cisc.barcamp.passscanner.network.responses;

import java.util.List;

import cisc.barcamp.passscanner.network.models.Todos;

public interface RetrieveAPI {
    void data(List<Todos> data);
}
