package model;

import expr.Environment;
import expr.ErrorExpr;
import expr.ErrorResult;
import expr.ExprResult;
import gui.ModelObserver;
import util.XLBufferedReader;
import util.XLPrintStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class XLModel extends CellFactory implements Environment, ObservableModel{
  public static final int COLUMNS = 10, ROWS = 10;

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */


  Map<String, Cell> cells = new HashMap<>();
  ArrayList<ModelObserver> observer = new ArrayList<>();

  public void update(CellAddress adress, String text) {
    String adressString = adress.toString();
    Cell cell = createCell(text);
    cells.put(adressString, cell);

  }

  public void updateAll(){
    cells.forEach((adress, value) -> {
      String text = cellCalculator(value.toString(),adress);

      this.updateObs(adress, text);
    });
  }

  public void loadFile(File file) throws IOException {
    XLBufferedReader reader = new XLBufferedReader(file);
    clearAll();
    reader.load(cells);
    updateAll();
  }

  public void clearCell(CellAddress adr){
    cells.put(adr.toString(), new EmptyCell());
    updateAll();

  }

  public void clearAll() {
    cells.replaceAll((adress, value) -> new EmptyCell());
    updateAll();
  }

  public void saveFile(File file) throws FileNotFoundException{
    XLPrintStream printer = new XLPrintStream(file.getName());
    printer.save(cells.entrySet());
  }

  @Override
  public ExprResult value(String name) {
    try {
      Cell cell = cells.get(name);
      return cell.check(this);
    }catch (StackOverflowError e ){
      return new ErrorResult("circular");
    }

  }


  @Override
  public void addObs(ModelObserver obs) {
    observer.add(obs);
  }

  @Override
  public void clearAllObs() {
    observer.clear();
  }


  @Override
  public void updateObs(String adress, String newValue) {
    for(ModelObserver observer : observer){
        observer.updateModel(adress, newValue);
    }
  }

  private String cellCalculator(String text, String adr){
    if(text == null || text.equals("")) {
      return "";
    }
    ExprCell cell = new ExprCell(text);

    if(text.startsWith("#")){
      return text.substring(1);
    }
    cells.put(adr, new CircularCell(text));


    try{
      return cell.check(this).toString();
    }catch (StackOverflowError e){
      return new ErrorExpr("circular reference").toString();
    }
    catch (Error error){
      return cell.check(this).toString();
    }
    finally {
      cells.put(adr, cell);
    }
  }

  public String readCell(CellAddress address) {
    if (cells.get(address.toString()) != null) {
      return cells.get(address.toString()).toString();
    }
    return "";
  }


}
