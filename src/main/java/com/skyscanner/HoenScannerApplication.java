package com.skyscanner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.eclipse.jetty.util.IO;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {

    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment)throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> carResults = Arrays.asList(
            mapper.readValue(
                getClass().getClassLoader().getResource("rental_cars.json"),
                SearchResult[].class
            )
        );
        List<SearchResult> HotelResults= Arrays.asList(
            mapper.readValue(
                getClass().getClassLoader().getResource("hotels.json"),
                SearchResult[].class
        )
        );
    List<SearchResult> SearchResults = new ArrayList<>();
        SearchResults.addAll(carResults);
        SearchResults.addAll(HotelResults);
        final SearchResource searchResource = new SearchResource(SearchResults);
        environment.jersey().register(searchResource);
    }

}
