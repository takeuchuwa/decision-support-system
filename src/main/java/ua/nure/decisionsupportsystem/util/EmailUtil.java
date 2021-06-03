package ua.nure.decisionsupportsystem.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EmailUtil {

    @Value("${confirmationMessage}")
    private String confirmationMessage;

    @Value("${confirmationUrlWithToken}")
    private String confirmationUrlWithToken;

    @Value("${contactMessage}")
    private String contactMessage;
}
