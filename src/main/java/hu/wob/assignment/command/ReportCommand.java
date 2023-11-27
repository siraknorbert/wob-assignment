package hu.wob.assignment.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import hu.wob.assignment.processor.GenerateReportProcessor;
import lombok.RequiredArgsConstructor;

/**
 * Commands related to the creation and handling of database content based reports.
 */
@ShellComponent
@RequiredArgsConstructor
public class ReportCommand extends AbstractBaseCommand {

    private final GenerateReportProcessor generateReportProcessor;

    @ShellMethod(key = "genReport", value = "Generate a general JSON report and upload it to an FTP.")
    public String generateReport() {
        return executeWithExceptionMapping(generateReportProcessor::generateTotalReport);
    }
}
