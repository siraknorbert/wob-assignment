package hu.wob.assignment.command;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import hu.wob.assignment.constant.IShellTerminalColorCodeConstants;
import hu.wob.assignment.exception.DataMappingException;
import hu.wob.assignment.exception.DataSyncRestException;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.functionalinterfaces.OneParamGenericExceptionFunction;
import hu.wob.assignment.functionalinterfaces.ZeroParamGenericExceptionFunction;

public abstract class AbstractBaseCommand {

    protected <T> String executeWithExceptionMapping(OneParamGenericExceptionFunction<String, String> function, String parameter) {
        String result;
        try {
            result = buildSuccessMessage(function.apply(parameter));
        } catch (Exception e) {
            result = handleGenericException(e);
        }
        return result;
    }

    protected String executeWithExceptionMapping(ZeroParamGenericExceptionFunction<String> function) {
        String result;
        try {
            result = buildSuccessMessage(function.apply());
        } catch (Exception e) {
            result = handleGenericException(e);
        }
        return result;
    }

    private String handleGenericException(Exception e) {
        if (e instanceof InvalidInputException) {
            return buildErrorMessage("Invalid input occurred:", e);
        } else if (e instanceof DataSyncRestException) {
            return buildErrorMessage("Error occurred during the REST synchronization:", e);
        } else if (e instanceof DataMappingException) {
            return buildErrorMessage("Error occurred during data mapping:", e);
        }
        return buildErrorMessage("Error occurred during the process:", e);
    }

    private String buildSuccessMessage(String uniqueMessage) {
        StringBuilder msg = new StringBuilder();
        msg.append(IShellTerminalColorCodeConstants.GREEN);
        msg.append("Command has run successfully: ");
        msg.append(uniqueMessage);
        msg.append(IShellTerminalColorCodeConstants.RESET);
        return msg.toString();
    }

    private String buildErrorMessage(String uniqueMessage, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        StringBuilder msg = new StringBuilder();
        msg.append(IShellTerminalColorCodeConstants.RED);
        msg.append(uniqueMessage).append("\n");
        msg.append(MessageFormat.format("Message: {0}", e.getMessage())).append("\n");
        msg.append(sw);
        msg.append(IShellTerminalColorCodeConstants.RESET);
        return msg.toString();
    }
}
