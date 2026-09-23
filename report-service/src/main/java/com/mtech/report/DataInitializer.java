package com.mtech.report;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeReports(ReportRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                repository.save(
                        new Report(
                                "Unadjusted Report",
                                "UNADJUSTED",
                                true
                        )
                );

                repository.save(
                        new Report(
                                "Adjusted Report",
                                "ADJUSTED",
                                true
                        )
                );

                repository.save(
                        new Report(
                                "Liquidity Report",
                                "LIQUIDITY",
                                true
                        )
                );
            }
        };
    }
}