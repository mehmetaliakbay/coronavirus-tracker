package com.example.coronavirustracker.controllers;

import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;


    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCasesInt = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        int totalNewCasesInt = allStats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();


        String totalReportedCases = addZero(totalReportedCasesInt );
        model.addAttribute("locationStats",allStats);
        model.addAttribute("totalReportedCases",totalReportedCases);

        String totalNewCases = addZero(totalNewCasesInt);
        model.addAttribute("totalNewCases",totalNewCases);
        return "home";
    }

    private String addZero(int sum) {
        StringBuilder stringBuilder = new StringBuilder();
        while (sum != 0)
        {
            int remaining = sum %1000;
            sum = sum /1000;
            if(sum !=0)
            {

             String padded = "."+String.format("%03d",remaining);
             stringBuilder.insert(0,padded);

            }
            else{
                stringBuilder.insert(0,remaining);
            }

        }
        return stringBuilder.toString();
    }

}
