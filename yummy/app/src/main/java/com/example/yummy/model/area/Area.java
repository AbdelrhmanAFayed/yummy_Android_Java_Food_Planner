package com.example.yummy.model.area;

import java.util.Map;

public class Area {

    private String strArea ;

    private String thumb ;

    public static final Map<String, String> COUNTRY_TO_CODE = Map.ofEntries(
            Map.entry("American", "US"),   // United States
            Map.entry("British", "GB"),    // United Kingdom
            Map.entry("Canadian", "CA"),   // Canada
            Map.entry("Chinese", "CN"),    // China
            Map.entry("Croatian", "HR"),   // Croatia
            Map.entry("Dutch", "NL"),      // Netherlands
            Map.entry("Egyptian", "EG"),   // Egypt
            Map.entry("Filipino", "PH"),   // Philippines
            Map.entry("French", "FR"),     // France
            Map.entry("Greek", "GR"),      // Greece
            Map.entry("Indian", "IN"),     // India
            Map.entry("Irish", "IE"),      // Ireland
            Map.entry("Italian", "IT"),    // Italy
            Map.entry("Jamaican", "JM"),   // Jamaica
            Map.entry("Japanese", "JP"),   // Japan
            Map.entry("Kenyan", "KE"),     // Kenya
            Map.entry("Malaysian", "MY"),  // Malaysia
            Map.entry("Mexican", "MX"),    // Mexico
            Map.entry("Moroccan", "MA"),   // Morocco
            Map.entry("Polish", "PL"),     // Poland
            Map.entry("Portuguese", "PT"), // Portugal
            Map.entry("Russian", "RU"),    // Russian Federation
            Map.entry("Spanish", "ES"),    // Spain
            Map.entry("Thai", "TH"),       // Thailand
            Map.entry("Tunisian", "TN"),   // Tunisia
            Map.entry("Turkish", "TR"),    // Turkey
            Map.entry("Ukrainian", "UA"),  // Ukraine
            Map.entry("Uruguayan", "UY"),  // Uruguay
            Map.entry("Vietnamese", "VN")  // Vietnam
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
