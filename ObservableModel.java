package model;

import gui.ModelObserver;

public interface ObservableModel {
    public void addObs(ModelObserver obs);



    public void clearAllObs();
    public void updateObs(String adress, String newValue);

}
