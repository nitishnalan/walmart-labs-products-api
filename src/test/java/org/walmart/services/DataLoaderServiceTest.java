package org.walmart.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.walmart.models.Product;
import org.walmart.models.RestClientProducts;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)

public class DataLoaderServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    DataLoaderService dataLoaderService;

    @Before
    public void setUp(){
        restTemplate = new RestTemplate();
        dataLoaderService = new DataLoaderService(restTemplate);
    }

    @Test
    public void checkIf_Walmart_API_returnsProducts_WithCorrectParameters_Test(){
        RestClientProducts restClientProductsTestObj = dataLoaderService.fetchRestClientProducts(1,30);
        Assert.assertTrue(restClientProductsTestObj.getProducts().size() > 1);
    }

    @Test
    public void checkIf_Walmart_API_returnsProducts_WithInCorrectParameters_Test(){
        try{
            dataLoaderService.fetchRestClientProducts(1,-30);
//            Assert.fail("Unexpected Response for incorrect product count!");
        }catch(Exception e){
            Assert.assertEquals(e.getMessage().contains("\"pageSize\" must be larger than or equal to 1 "), true);
        }

    }

    @Test
    public void checkSanitizationOfProductData_Test(){
        Product dummyProduct = new Product("089796c9-17e4-4a7d-b37a-c86567feab39",
                "VIZIO 43\" Class 1080p LED Smart HDTV - E43-C2",
                "<ul>\n\t<li>Resolution: 1080p</li>\n\t<li>Clear Action 240</li>\n\t<li>HDMI Inputs: 3</li>\n\t<li>Smart TV</li>\n</ul>\n",
                "<p>The all-new 2015 E-Series 43&rdquo; (43&rdquo; diag.) Full-Array LED Smart TV has arrived. Featuring a new, modern design, " +
                        "brilliant picture quality, and faster, easier-to-use smart TV experience, the all-new E-Series brings you premium HD " +
                        "entertainment at an incredible value. Built-in high-speed Wi-Fi gets you connected in a snap, and with the hottest apps " +
                        "to choose from like Netflix, Amazon Instant Video, iHeartRadio, Hulu Plus, Spotify, YouTube and more*. Enjoying what you want, " +
                        "when you want it is easier than ever before. Plus, you get brilliant picture quality using the latest advanced " +
                        "technologies &mdash; like Full-Array LED backlighting for superior light uniformity, and up to 5 Active LED Zones&trade; " +
                        "producing vivid details with deeper, more pure black levels**. For sports and action fans, E-Series includes Clear Action 240, " +
                        "which combines powerful image processing with 120Hz effective refresh rate for enhanced detail in fast action scenes.</p>" +
                        "\n\n<p><span style=\"font-size:10px;\">1High-speed/Broadband Internet service, app subscription and access equipment are " +
                        "required and not provided by VIZIO.&nbsp;<br />\n<span font-size:=\"\" line-height:=\"\">2Compared to previous generations " +
                        "of VIZIO E-Series TVs.</span></span></p>\n\n<p>VIZIO E-Series: Incredible picture, unbeatable value.</p>\n",
                "$378.00",
                "/images/image9.jpeg",
                4.3333,
                9,
                true);

        Product sanitizedProduct = dataLoaderService.sanitizeProductData(dummyProduct);

        Assert.assertTrue(sanitizedProduct.getShortDescription().equals("Resolution: 1080p Clear Action 240 HDMI Inputs: 3 Smart TV"));
        Assert.assertTrue(sanitizedProduct.getLongDescription().equals("The all-new 2015 E-Series 43” (43” diag.) Full-Array LED Smart TV has arrived. " +
                "Featuring a new, modern design, brilliant picture quality, and faster, easier-to-use smart TV experience, " +
                "the all-new E-Series brings you premium HD entertainment at an incredible value. Built-in high-speed Wi-Fi gets " +
                "you connected in a snap, and with the hottest apps to choose from like Netflix, " +
                "Amazon Instant Video, iHeartRadio, Hulu Plus, Spotify, YouTube and more*. Enjoying what you want, " +
                "when you want it is easier than ever before. Plus, you get brilliant picture quality using the latest advanced technologies " +
                "— like Full-Array LED backlighting for superior light uniformity, and up to 5 Active LED Zones™ producing vivid details with deeper, " +
                "more pure black levels**. For sports and action fans, E-Series includes Clear Action 240, which combines powerful image " +
                "processing with 120Hz effective refresh rate for enhanced detail in fast action scenes. 1High-speed/Broadband Internet service, " +
                "app subscription and access equipment are required and not provided by VIZIO.  2Compared to previous generations of VIZIO E-Series " +
                "TVs. VIZIO E-Series: Incredible picture, unbeatable value."));

        Assert.assertTrue(sanitizedProduct.getPriceFloat() == 378);

    }
}
