package com.wimea.hp.wimea_ict;

public class PendingUtils {
  private String category;
  private String date;
  private String Time;

  public PendingUtils(String category, String date, String Time) {
    this.category = category;
    this.date = date;
    this.Time = Time;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return Time;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
