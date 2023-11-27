package hu.wob.assignment.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

import hu.wob.assignment.constant.IFilePathConstants;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.pojo.InvalidListingDataPojo;

/**
 * Helper class for writing invalid listing data to csv file.
 */
@Component
public class ListingDataLoggingHelper {

    public void logInvalidListingDataToCsv(List<InvalidListingDataPojo> invalidListingDataList) throws InvalidInputException {
        if (invalidListingDataList == null) {
            throw new InvalidInputException("invalidListingDataList parameter is null");
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(IFilePathConstants.IMPORT_LOG_CSV, true))) {
            File file = new File(IFilePathConstants.IMPORT_LOG_CSV);
            boolean isNewFile = !file.exists() || file.length() == 0;

            if (isNewFile) {
                String[] header = { "listingId", "field", "errorDetails" };
                writer.writeNext(header);
            } else {
                writer.writeNext(new String[] { "" });
            }

            List<String[]> newData = new ArrayList<>();

            for (InvalidListingDataPojo data : invalidListingDataList) {
                String[] rowData = { data.getId(), data.getInvalidField(), data.getViolationDetails() };
                newData.add(rowData);
            }

            writer.writeAll(newData);
        } catch (IOException e) {
            System.err.println("Error appending data to CSV: " + e.getMessage());
        }
    }
}
