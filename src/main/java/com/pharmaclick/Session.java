package com.pharmaclick;

public class Session {
    private static String loggedInEmail;
    private static int loggedInUserId;
    private static int loggedInPharmacyId;
    public static void setLoggedInEmail(String email) {
        loggedInEmail = email;
    }

    public static String getLoggedInEmail() {
        return loggedInEmail;
    }

    public static void setLoggedInUserId(int id) {
        loggedInUserId = id;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    

public static void setLoggedInPharmacyId(int id) {
    loggedInPharmacyId = id;
}

public static int getLoggedInPharmacyId() {
    return loggedInPharmacyId;
}

}
