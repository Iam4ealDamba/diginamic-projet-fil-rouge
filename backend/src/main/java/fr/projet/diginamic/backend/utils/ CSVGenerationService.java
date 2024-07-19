package fr.projet.diginamic.backend.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.services.MissionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CSVGenerationService {

    @Autowired
    private MissionService missionService;

    /**
     * Generates a CSV report of bonuses per month for a given list of missions.
     * 
     * @param missions     the list of displayed mission DTOs.
     * @param outputStream the output stream to write the CSV file to.
     * @throws IOException if there is an issue writing to the stream.
     */
    public void generateBonusCsvReport(List<DisplayedMissionDTO> missions, OutputStream outputStream)
            throws IOException {

        // Headers for the CSV file
        String[] headers = { "Mois", "Total des Primes" };

    }

}