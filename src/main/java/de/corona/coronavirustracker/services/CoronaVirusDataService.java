package de.corona.coronavirustracker.services;

import de.corona.coronavirustracker.models.LocationStats;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CoronaVirusDataService {

  private static final String CORON_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

  public List<LocationStats> fetchVirusData() throws IOException, InterruptedException {
    ArrayList<LocationStats> allStats = new ArrayList();

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(CORON_VIRUS_DATA_URL))
        .build();

    HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyReader = new StringReader(httpResponse.body());

    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
    for (CSVRecord record : records) {
      LocationStats locationStat = new LocationStats();
      locationStat.setState(record.get("Province/State"));
      locationStat.setCountry(record.get("Country/Region"));
      int latestCases = Integer.parseInt(record.get(record.size() - 1));
      int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
      locationStat.setLatestTotalCases(latestCases);
      locationStat.setDiffFromPrevDay(latestCases - prevDayCases);

      allStats.add(locationStat);
    }

    return allStats;
  }

  public int getTotalReportedCases() throws IOException, InterruptedException {
    return fetchVirusData().stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
  }

  public int totalNewCases() throws IOException, InterruptedException {
    return fetchVirusData().stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
  }
}
