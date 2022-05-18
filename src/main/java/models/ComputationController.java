package models;

import utils.json.Exportable;

import java.util.List;
import java.util.Optional;

public abstract class ComputationController<M extends ComputationModel<?>> {
    private List<M> models;

    public String execute(int index, String input, int steps) {
        return models.get(index).execute(input, steps);
    }

    protected void setModels(List<M> models) {
        this.models = models;
    }

    public List<M> getModels() {
        return models;
    }

    protected abstract Optional<List<M>> parseJson();
}
