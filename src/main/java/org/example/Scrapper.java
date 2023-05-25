package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;

import java.util.List;
import java.io.*;
public class Scrapper {

    public void ScrapperAllPage() throws Exception {
        int page = 1;
        String baseUrl = "https://www.leboncoin.fr/recherche?category=9&text=maison&page=";
        String url = baseUrl + page;

        // création du dossier Resultat
        File rep = new File("Resultat");
        rep.mkdir();

        // fichier texte
        String nomFichierSortieTxt = "Resultat" + File.separator +  "result.txt";
        PrintWriter txt = new PrintWriter(new BufferedWriter(new FileWriter(nomFichierSortieTxt)));

        // fichier json
        String nomFichierSortieJson = "Resultat" + File.separator + "result.json";
        FileWriter json = new FileWriter(nomFichierSortieJson);

        StringBuffer res= new StringBuffer() ;
        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        int i = 0;
        json.write("{\"data\":[");

        while (page < 5) {

            HtmlPage htmlPage = null;
            try {
                htmlPage = webClient.getPage(url);
            } catch (Exception e) {
                System.out.println("La requête a échoué: " + e.getMessage());
                break;
            }

            List<HtmlElement> listeElement = htmlPage.getByXPath("//div[contains(@class, 'styles_adCard__HQRFN')]");

            for (HtmlElement e : listeElement) {
                List<HtmlElement> elements = e.getByXPath(".//span|.//p|.//a");

                ObjectNode objNode = new ObjectMapper().createObjectNode();

                for (HtmlElement j : elements) {
                    String dataQaId = j.getAttribute("data-qa-id");
                    String css_class = j.getAttribute("class");

                    if ("aditem_container".equals(dataQaId)) {
                        HtmlAnchor anchor = (HtmlAnchor) j;
                        String href = anchor.getHrefAttribute();
                        System.out.println("href : https://www.leboncoin.fr" + href);
                        txt.println("href : https://www.leboncoin.fr" + href);
                        objNode.put("href", "https://www.leboncoin.fr" + href);
                    } else if ("aditem_price".equals(dataQaId)) {
                        String texte = j.getTextContent();
                        System.out.println("price : " + texte);
                        txt.println("price : " + texte);
                        objNode.put("price", texte);
                    } else if ("aditem_title".equals(dataQaId)) {
                        String texte = j.getTextContent();
                        System.out.println("title : " + texte);
                        txt.println("title : " + texte);
                        objNode.put("title", texte);
                    } else if ("sc-3b75e541-0 eyQSsJ".equals(css_class)) {
                        String texte = j.getTextContent();
                        System.out.println("€/m2 : " + texte);
                        txt.println("€/m2 : " + texte);
                        objNode.put("€/m2", texte);
                    } else if ("_2k43C _1rwR5 _137P- _3j0OU P4PEa".equals(css_class)) {
                        String texte = j.getTextContent();
                        System.out.println("adresse : " + texte);
                        txt.println("adresse : " + texte);
                        objNode.put("adresse", texte);
                    }
                }
                System.out.println("================================================");

                String value = e.asXml();
                txt.println("================================================");
                if(i > 0){
                    json.write(",");
                }
                json.write(objNode.toString());
                i++;
            }

            Thread.sleep(5000);

            page++;
            url = baseUrl + page;
        }
        json.write("]}");
        json.close();
        txt.println(res);
        txt.close();
    }
}
