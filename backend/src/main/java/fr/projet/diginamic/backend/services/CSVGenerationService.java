package fr.projet.diginamic.backend.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.utils.CalculateMissionPricing;

/**
 * Service class responsible for generating CSV reports related to missions and their bounties.
 * This service utilizes mission data to create detailed CSV reports, summarizing the bounties awarded
 * per month. It supports writing the generated CSV data directly to an output stream.
 */
@Service
public class CSVGenerationService {

  /**
     * Generates a CSV report of bounties per month for a given list of missions.
     * This method processes the provided list of missions, summarizes the bounty amounts per month,
     * and writes the results to the provided output stream in CSV format. The CSV file includes headers
     * and skips empty header records.
     * 
     * @param titleCSV     the title for the CSV report.
     * @param missions     the list of displayed mission DTOs.
     * @param outputStream the output stream to write the CSV file to.
     * @throws IOException if there is an issue writing to the stream.
     */
    public void generateBountiesCsvReport(String titleCSV, List<DisplayedMissionDTO> missions, OutputStream outputStream)
            throws IOException {

        // Headers for the CSV file
        String[] headers = { "Mois", "Total des Primes" };

        try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                CSVPrinter csvPrinter = new CSVPrinter(streamWriter,
                        CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(false).build())) {

            // streamWriter.write(titleCSV);
            // streamWriter.write(System.lineSeparator()); 

            Map<String, Double> monthSum = CalculateMissionPricing.summarizeBountiesPerMonth(missions);

            List<String> listMonths = Arrays.asList(
                "JANVIER", "FÉVRIER", "MARS", "AVRIL", "MAI", "JUIN",
                "JUILLET", "AOÛT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DÉCEMBRE"
            );
            for (String month : listMonths) {
                double totalBounty = monthSum.getOrDefault(month, 0.0);
                csvPrinter.printRecord(month, totalBounty);
            }
            csvPrinter.flush();

        } catch (Exception e) {
            throw new IOException("Failed to generate CSV report", e);
        }
    }

}