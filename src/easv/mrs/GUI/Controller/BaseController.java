package easv.mrs.GUI.Controller;

import easv.mrs.GUI.Model.MRSModel;
import easv.mrs.GUI.Model.MovieModel;

public abstract class BaseController {

    private MRSModel model;

    public void setModel(MRSModel model) {
        this.model = model;
    }

    public MRSModel getModel() {
        return model;
    }

    public abstract void setup();
}
