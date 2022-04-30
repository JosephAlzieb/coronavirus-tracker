package de.corona.coronavirustracker.models;

public class LocationStats {

  private String state;
  private String country;
  private int latestTotalCases;
  private int diffFromPrevDay;



  public void setState(String state) {
    this.state = state;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setLatestTotalCases(int latestTotalCases) {
    this.latestTotalCases = latestTotalCases;
  }

  public void setDiffFromPrevDay(int diffFromPrevDay) {
    this.diffFromPrevDay = diffFromPrevDay;
  }

  public int getLatestTotalCases() {
    return latestTotalCases;
  }

  public int getDiffFromPrevDay() {
    return diffFromPrevDay;
  }

  @Override
  public String toString() {
    return "LocationState{" +
        "state='" + state + '\'' +
        ", country='" + country + '\'' +
        ", latestTotalCases=" + latestTotalCases +
        ", diffFromPrevDay=" + diffFromPrevDay +
        '}';
  }

}
