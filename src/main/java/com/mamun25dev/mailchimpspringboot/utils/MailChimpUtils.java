package com.mamun25dev.mailchimpspringboot.utils;

import java.util.UUID;

public class MailChimpUtils {
    public static String getUniqueGroupIdForSegments(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
