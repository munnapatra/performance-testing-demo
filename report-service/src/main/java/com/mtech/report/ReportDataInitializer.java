package com.mtech.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReportDataInitializer implements CommandLineRunner {

    private final ReportDataRepository repository;

    private static final int TOTAL_RECORDS = 1_000_000;
    private static final int BATCH_SIZE = 5_000;

    private final Random random = new Random(42);

    public ReportDataInitializer(
            ReportDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        long existingRecords = repository.count();

        if (existingRecords > 0) {
            System.out.println(
                    "Report data already exists: "
                    + existingRecords
                    + " records"
            );
            return;
        }

        System.out.println(
                "Starting generation of "
                + TOTAL_RECORDS
                + " report records..."
        );

        generateData();

        System.out.println(
                "Report data generation completed."
        );
    }

    @Transactional
    public void generateData() {

        List<ReportData> batch =
                new ArrayList<>(BATCH_SIZE);

        LocalDate startDate =
                LocalDate.of(2026, 1, 1);

        String[] businessUnits = {
                "Treasury",
                "Liquidity",
                "Risk",
                "Finance"
        };

        String[] currencies = {
                "USD",
                "EUR",
                "GBP",
                "INR"
        };

        String[] statuses = {
                "COMPLETED",
                "PENDING",
                "FAILED"
        };

        for (int i = 1; i <= TOTAL_RECORDS; i++) {

            ReportData data = new ReportData();

            data.setReportId(
                    (long) ((i % 3) + 1)
            );

            data.setReportingDate(
                    startDate.plusDays(
                            random.nextInt(365)
                    )
            );

            data.setBusinessUnit(
                    businessUnits[
                            random.nextInt(
                                    businessUnits.length
                            )
                    ]
            );

            data.setCurrency(
                    currencies[
                            random.nextInt(
                                    currencies.length
                            )
                    ]
            );

            data.setAmount(
                    BigDecimal.valueOf(
                            1000
                                    + (random.nextDouble()
                                    * 999000)
                    ).setScale(
                            2,
                            java.math.RoundingMode.HALF_UP
                    )
            );

            data.setStatus(
                    statuses[
                            random.nextInt(
                                    statuses.length
                            )
                    ]
            );

            batch.add(data);

            if (batch.size() == BATCH_SIZE) {

                repository.saveAll(batch);

                repository.flush();

                batch.clear();

                if (i % 50_000 == 0) {
                    System.out.println(
                            "Inserted: "
                            + i
                            + " / "
                            + TOTAL_RECORDS
                    );
                }
            }
        }

        if (!batch.isEmpty()) {
            repository.saveAll(batch);
            repository.flush();
        }
    }
}