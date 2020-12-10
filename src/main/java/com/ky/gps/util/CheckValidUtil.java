package com.ky.gps.util;

import com.ky.gps.entity.SbTerminal;

public class CheckValidUtil {
    public static boolean isValid(SbTerminal sbTerminal) {

        if (sbTerminal.getSbBusPosition().getSbGps() == null) {
            return false;
        } else {
            return true;
        }

    }
}
