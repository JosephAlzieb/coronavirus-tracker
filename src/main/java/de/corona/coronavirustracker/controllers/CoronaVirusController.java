package de.corona.coronavirustracker.controllers;

import de.corona.coronavirustracker.models.LocationStats;
import de.corona.coronavirustracker.services.CoronaVirusDataService;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoronaVirusController {

  private final CoronaVirusDataService service;

  public CoronaVirusController(CoronaVirusDataService service) {
    this.service = service;
  }

  @GetMapping("/")
  public String home(Model model) throws IOException, InterruptedException {
    List<LocationStats> allStats = service.fetchVirusData();

    int totalReportedCases = service.getTotalReportedCases();
    int totalNewCases = service.totalNewCases();

    model.addAttribute("locationStats", allStats);
    model.addAttribute("totalReportedCases", totalReportedCases);
    model.addAttribute("totalNewCases", totalNewCases);

    return "index";
  }
}
