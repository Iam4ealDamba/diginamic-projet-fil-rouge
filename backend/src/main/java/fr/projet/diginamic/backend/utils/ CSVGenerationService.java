// package fr.projet.diginamic.backend.utils;

// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVPrinter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
// import fr.projet.diginamic.backend.services.MissionService;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.io.OutputStreamWriter;
// import java.nio.charset.StandardCharsets;
// import java.text.SimpleDateFormat;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @Service
// public class CSVGenerationService {

//     @Autowired
//     private MissionService missionService;

//     /**
//      * Generates a CSV report of bounties per month for a given list of missions.
//      * 
//      * @param missions     the list of displayed mission DTOs.
//      * @param outputStream the output stream to write the CSV file to.
//      * @throws IOException if there is an issue writing to the stream.
//      */
//     public void generateBountiesCsvReport(List<DisplayedMissionDTO> missions, OutputStream outputStream)
//             throws IOException {

//         // Headers for the CSV file
//         String[] headers = { "Mois", "Total des Primes" };

//         try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//                 CSVPrinter csvPrinter = new CSVPrinter(streamWriter,
//                         CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(false).build())) {

//             Map<String, Double> monthSum = summarizeBountiesByMonth(missions);

//             for (Map.Entry<String, Double> entry : monthSum.entrySet()) {
//                 csvPrinter.printRecord(entry.getKey(), entry.getValue());
//             }
//             csvPrinter.flush();
//         } catch (Exception e) {
//             throw new IOException("Failed to generate CSV report", e);
//         }
//     }

//     /**
//      * Summarizes bounties by month from a list of mission DTOs.
//      * 
//      * @param missions List of DisplayedMissionDTO
//      * @return A map with month as key and sum of bounties as value.
//      */
//     private Map<String, Double> summarizeBountiesByMonth(List<DisplayedMissionDTO> missions) {
//         Map<String, Double> monthSum = new HashMap<>();
//         for (DisplayedMissionDTO mission : missions) {
//             String monthKey = new SimpleDateFormat("MMMM").format(mission.getStartDate());
//             monthSum.merge(monthKey, mission.getBountyAmount(), Double::sum);
//         }
//         return monthSum;
//     }
// }