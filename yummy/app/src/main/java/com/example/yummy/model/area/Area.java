package com.example.yummy.model.area;

import java.util.Map;

public class Area {

    private String strArea ;

    private String thumb ;

    public static final Map<String, String> COUNTRY_TO_CODE = Map.ofEntries(
            Map.entry("American", "us"),   // United States
            Map.entry("British", "gb"),    // United Kingdom
            Map.entry("Canadian", "ca"),   // Canada
            Map.entry("Chinese", "cn"),    // China
            Map.entry("Croatian", "hr"),   // Croatia
            Map.entry("Dutch", "nl"),      // Netherlands
            Map.entry("Egyptian", "eg"),   // Egypt
            Map.entry("Filipino", "ph"),   // Philippines
            Map.entry("French", "fr"),     // France
            Map.entry("Greek", "gr"),      // Greece
            Map.entry("Indian", "in"),     // India
            Map.entry("Irish", "ie"),      // Ireland
            Map.entry("Italian", "it"),    // Italy
            Map.entry("Jamaican", "jm"),   // Jamaica
            Map.entry("Japanese", "jp"),   // Japan
            Map.entry("Kenyan", "ke"),     // Kenya
            Map.entry("Malaysian", "my"),  // Malaysia
            Map.entry("Mexican", "mx"),    // Mexico
            Map.entry("Moroccan", "ma"),   // Morocco
            Map.entry("Polish", "pl"),     // Poland
            Map.entry("Portuguese", "pt"), // Portugal
            Map.entry("Russian", "ru"),    // Russian Federation
            Map.entry("Spanish", "es"),    // Spain
            Map.entry("Thai", "th"),       // Thailand
            Map.entry("Tunisian", "tn"),   // Tunisia
            Map.entry("Turkish", "tr"),    // Turkey
            Map.entry("Ukrainian", "ua"),  // Ukraine
            Map.entry("Uruguayan", "uy"),  // Uruguay
            Map.entry("Vietnamese", "vn")  // Vietnam
    );

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
