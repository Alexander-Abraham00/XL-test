package gui.menu;

import gui.XL;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class SaveMenuItem extends MenuItem {
  public SaveMenuItem(XL xl, Stage stage) {
    super("Save");
    setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters()
          .add(new FileChooser.ExtensionFilter("XL files (*.xl)", "*.xl"));
      File file = fileChooser.showSaveDialog(stage);
      if (file != null) {
        // TODO
        try {
          xl.saveFile(file);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
