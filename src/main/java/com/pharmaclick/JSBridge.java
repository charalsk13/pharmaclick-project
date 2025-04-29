package com.pharmaclick;

public class JSBridge {
    private FrontpageUserController controller;

    public JSBridge(FrontpageUserController controller) {
        this.controller = controller;
    }

    // Καλείται από το JavaScript όταν πατάς marker
    public void openPharmacy(String name) {
        System.out.println("📞 JSBridge: Κλήση openPharmacy με " + name);
        controller.openPharmacyDetails(name);
    }
}

