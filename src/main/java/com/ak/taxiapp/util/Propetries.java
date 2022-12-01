package com.ak.taxiapp.util;

import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Propetries {

    public static final HashMap<String, HashMap<String, String>> COMPANY = new HashMap<String, HashMap<String, String>>() {{
        put("INFO", new HashMap<String, String>() {{
            put("NAME" , "CYTRANSOLUTIONS LTD");
            put("ADDRESS" , "Amathountos 34, Shop 8,\nLimassol, 4532");
            put("VAT" ,"10361018V");
            put("TEL" ,"+35799667777");
            put("EMAIL" , "99667777cy@gmail.com");
        }});

        put("BANK", new HashMap<String, String>() {{
            put("ACCOUNT NUMBER" , "357026026038");
            put("ACCOUNT NAME" , "CYTRANSOLUTIONS LTD");
            put("IBAN" , "CY470020001950000357026026038");
            put("BIC" , "BCYPCY2N");
        }});
    }};



    public void Properties() {
    }

}
