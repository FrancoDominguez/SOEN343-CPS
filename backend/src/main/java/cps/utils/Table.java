package cps.utils;

import java.util.ArrayList;

public class Table<String> {
  private String title;
  private String[] columns;
  private ArrayList<String[]> rows;

  public Table(String title, String[] columns, ArrayList<String[]> rows) {
    this.title = title;
    this.columns = columns;
    this.rows = rows;
  }

  public Table(String[] columns, ArrayList<String[]> rows) {
    this.columns = columns;
    this.rows = rows;
  }

  public String[] getRow(int i){

  }

}
