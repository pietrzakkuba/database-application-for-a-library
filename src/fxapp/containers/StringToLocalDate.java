package fxapp.containers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.FormatFlagsConversionMismatchException;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.MissingFormatArgumentException;

public class StringToLocalDate {

    public static LocalDate convert(String date) throws IllegalFormatException {
        DateTimeFormatter formatter = null;

        // Converting 'dd-MM-yyyy' String format to LocalDate
        if (date.matches("^[0-9]{2}-[0-9]{2}--?[0-9]{4}$")) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(date, formatter);
        }

        // Converting 'dd/MM/YY' String format to LocalDate
        if (date.matches("^[0-9]{2}/[0-9]{2}/-?[0-9]{2}$")) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            return LocalDate.parse(date, formatter);
        }

        // Converting 'yyyy/MM/dd' String format to LocalDate
        if (date.matches("^[0-9]{4}/[0-9]{2}/-?[0-9]{2}$")) {
            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return LocalDate.parse(date, formatter);
        }

        // Converting 'dd MM yyyy' String format to LocalDate
        if (date.matches("^[0-9]{2} [0-9]{2} -?[0-9]{4}$")) {
            formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            return LocalDate.parse(date, formatter);
        }
        return null;
    }
}
