package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class GeoIpServiceTests {
    @Test
    public void testGeoIPService() {
        String isoCountryName = new GeoIPService().getGeoIPServiceSoap12()
                .getCountryISO2ByName("USA");
        assertEquals(isoCountryName, "<GeoIP><Country>US</Country></GeoIP>");
    }

    @Test
    public void testInvilidCountry() {
        String isoCountryName = new GeoIPService().getGeoIPServiceSoap12()
                .getCountryISO2ByName("USAAA");
        assertEquals(isoCountryName, "<GeoIP><Country>US</Country></GeoIP>");
    }
}
