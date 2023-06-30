package com.kim.dibt.mernis;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

public class Mernis {
    private Mernis() {
    }

    public static boolean verify(String tcKimlikNo, String ad, String soyad, int dogumYili) {
        String endpointURL = "https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx";
        String soapRequest = buildSOAPRequest(tcKimlikNo, ad, soyad, dogumYili);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/soap+xml; charset=utf-8"), soapRequest);
        Request request = new Request.Builder()
                .url(endpointURL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String soapResponse = response.body().string();
                return parseSOAPResponse(soapResponse);
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String buildSOAPRequest(String tcKimlikNo, String ad, String soyad, int dogumYili) {

        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "<soap12:Body>" +
                "<TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\">" +
                "<TCKimlikNo>" + tcKimlikNo + "</TCKimlikNo>" +
                "<Ad>" + ad + "</Ad>" +
                "<Soyad>" + soyad + "</Soyad>" +
                "<DogumYili>" + dogumYili + "</DogumYili>" +
                "</TCKimlikNoDogrula>" +
                "</soap12:Body>" +
                "</soap12:Envelope>";
    }

    private static boolean parseSOAPResponse(String soapResponse) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            factory.setExpandEntityReferences(false);
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the SOAP response
            Document document = builder.parse(new InputSource(new StringReader(soapResponse)));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Find the TCKimlikNoDogrulaResult element and extract its value
            NodeList resultList = rootElement.getElementsByTagName("TCKimlikNoDogrulaResult");
            if (resultList.getLength() > 0) {
                Element resultElement = (Element) resultList.item(0);
                String resultValue = resultElement.getTextContent();
                return Boolean.parseBoolean(resultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
