package com.stockmarket.financialliteracy.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import com.stockmarket.financialliteracy.model.DailySecurityPrice;
import com.stockmarket.financialliteracy.model.ListedSecurity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stockmarket.financialliteracy.constants.AppConstants.*;

public class CSVParserUtil {

//    public static final Logger LOGGER = LoggerFactory.getLogger(CSVParserUtil.class);

    private final static ObjectMapper mapper = new ObjectMapper();

    private static List<Map<String, String>> parseCSVFileAsMap(InputStream stream, Map<String, String> modifyHeaders) throws CsvValidationException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String[] headers = br.readLine().split(COMMA_TRIM_SPLIT_REGREX);

            if (Objects.nonNull(modifyHeaders) && !modifyHeaders.isEmpty()) {
                List<String> headersList = Arrays.asList(headers);
                modifyHeaders.keySet().stream().forEach(key -> {
                    int index = headersList.indexOf(key);
                    if (index > -1) {
                        headers[index] = modifyHeaders.get(key);
                    }
                });
            }
            return br.lines()
                    .map(row -> row.split(COMMA_TRIM_SPLIT_REGREX))
                    .map(column -> IntStream.range(0, column.length)
                            .boxed()
                            .collect(Collectors.toMap(key -> headers[key], value -> column[value])))
                    .collect(Collectors.toList());
        } catch (Exception e) {
//            LOGGER.error("Exception while parsing CSV: " + e.getMessage());
            throw new CsvValidationException(e.getMessage());
        }
    }

    public static <T> List<T> parseMapAsObject(List<Map<String, String>> entries, final Class<T> clazz, List<Map<String, String>> invalidRecords) throws CsvValidationException {
        List<T> records = null;
        if (Objects.nonNull(entries) && !entries.isEmpty()) {
            records = new ArrayList<>();
            for (Map<String, String> entry : entries) {
                try {
                    records.add(mapper.convertValue(entry, clazz));
                } catch (Exception e) {
                    entry.put("error", e.getMessage());
//                    LOGGER.error("Invalid Entry: " + e.getMessage());
                    if (invalidRecords != null) {
                        invalidRecords.add(entry);
                    }
                }
            }
        }
        return records;
    }

    public static <T> List<T> parseCSVFileAsObject(InputStream stream, final Class<T> clazz, List<Map<String, String>> invalidRecords) throws CsvValidationException {
        List<Map<String, String>> entries = parseCSVFileAsMap(stream, null);
        return parseMapAsObject(entries, clazz, invalidRecords);
    }

    public static List<DailySecurityPrice> parseCSVFileAsDailySecurityPrices(InputStream stream, List<Map<String, String>> invalidRecords) throws CsvValidationException {
        List<Map<String, String>> entries = parseCSVFileAsMap(stream, null);

        List<String> validSeries = Arrays.asList(SERIES_BE, SERIES_EQ);
        entries = entries.stream().filter(entry -> validSeries.contains(entry.get(SERIES).toUpperCase()))
                .map(entry -> {
                    if (entry.get(DELIVERY_QUANTITY).equalsIgnoreCase("-")) {
                        entry.put(DELIVERY_QUANTITY, "0");
                        entry.put(DELIVERY_PERCENTAGE, "0.0");
                    }
                    return entry;
                }).collect(Collectors.toList());

        return parseMapAsObject(entries, DailySecurityPrice.class, invalidRecords);
    }

    public static List<ListedSecurity> parseCSVFileAsListedSecurities(InputStream stream, List<Map<String, String>> invalidRecords) throws CsvValidationException {
        Map<String, String> modifyHeaders = new HashMap<>();
        modifyHeaders.put("NAME OF COMPANY", "COMPANY_NAME");
        List<Map<String, String>> entries = parseCSVFileAsMap(stream, modifyHeaders);
        return parseMapAsObject(entries, ListedSecurity.class, invalidRecords);
    }


}
