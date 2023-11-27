package hu.wob.assignment.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import hu.wob.assignment.processor.SyncListingStatusesProcessor;
import hu.wob.assignment.processor.SyncListingsProcessor;
import hu.wob.assignment.processor.SyncLocationsProcessor;
import hu.wob.assignment.processor.SyncMarketplacesProcessor;
import lombok.RequiredArgsConstructor;

/**
 * Commands that synchronize application database content with data provided by an external REST API.
 */
@ShellComponent
@RequiredArgsConstructor
public class DataSyncCommand extends AbstractBaseCommand {

    private final SyncMarketplacesProcessor syncMarketplacesProcessor;
    private final SyncListingStatusesProcessor syncListingStatusesProcessor;
    private final SyncLocationsProcessor syncLocationsProcessor;
    private final SyncListingsProcessor syncListingsProcessor;

    @ShellMethod(key = "syncMarketplaces", value = "Save marketplace content to database fetched from external REST API.")
    public String syncMarketplaces() {
        return executeWithExceptionMapping(syncMarketplacesProcessor::syncMarketplaceList);
    }

    @ShellMethod(key = "syncListingStatuses", value = "Save listing status content to database fetched from external REST API.")
    public String syncListingStatuses() {
        return executeWithExceptionMapping(syncListingStatusesProcessor::syncListingStatusList);
    }

    @ShellMethod(key = "syncLocations", value = "Save location content to database fetched from external REST API.")
    public String syncLocations() {
        return executeWithExceptionMapping(syncLocationsProcessor::syncLocationList);
    }

    @ShellMethod(key = "syncListings", value = "Save listing content to database fetched from external REST API.")
    public String syncListings() {
        return executeWithExceptionMapping(syncListingsProcessor::syncListingList);
    }
}
