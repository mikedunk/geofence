

package hello;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by mayeyemi on 8/29/19,
 */
@Component
public class DefaultLocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
    public DefaultLocalDateTimeConverter() {
    }

    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        } else {
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);


        }
    }

    public LocalDateTime convertToEntityAttribute(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = Instant.ofEpochMilli(date.getTime());

            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }
}

