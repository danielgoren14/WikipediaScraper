import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WikipediaScraper {
    public static void main(String[] args) {
        try {
            Challenge.showInstructions();
            List<River> riverList = new ArrayList<>();
            String url = "https://en.wikipedia.org/wiki/List_of_river_systems_by_length";
            Document document = Jsoup.connect(url).get();
//            System.out.println("Check "+document.title());

//            Element table2 = document.select("table").get(3);
            Elements table2 = document.select("table.wikitable.sortable");
            Elements rows = table2.select("tr");

            System.out.println("");

            int i = 1;
            for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
                Element row = rows.get(rowIndex);
                Elements cells = row.select("td");


                Element cellOfRiverName = cells.get(0);
//                Elements childrenOfRivers = cellOfRiverName.children();
//                String riverNames = "";

//                Elements links = cellOfRiverName.select("a");
//                for (Element link : links) {
//                    riverNames += link.text() + "\n";
//                }
//                for (Element child : childrenOfRivers) {
//                    riverNames += child.text() + "\n";
//                }

                String riverNames = cellOfRiverName.text();
                Element cellOfLengthKm = cells.get(1);


//                Elements childrenOfLengthKm = cellOfLengthKm.children();
//                String lengthKmString = "";
//                for (Element child : childrenOfLengthKm) {
//                    lengthKmString += child.text();
//                }
                String lengthKmString = cellOfLengthKm.text();
                lengthKmString = getParsableString(lengthKmString);
                int lengthKmInt = Integer.valueOf(lengthKmString);

//                System.out.println(i+ ") " + lengthKmString);


                Element cellOfLengthMiles = cells.get(2);
                String lengthMilesString = cellOfLengthMiles.text();
                lengthMilesString = getParsableString(lengthMilesString);
                int lengthMilesInt = Integer.valueOf(lengthMilesString);


                Element cellOfDrainageArea = cells.get(3);
                String drainageAreaString = cellOfDrainageArea.text();
                drainageAreaString = getParsableString(drainageAreaString);
                int drainageAreaInt = Integer.valueOf(drainageAreaString);


//                System.out.println(i + ") " + "\n"
//                        + "river names : " + riverNames + "\n"
//                        + "lengthKmString = " + lengthKmInt + "\n"
//                        + "length Miles = " + lengthMilesInt + "\n"
//                        + "drainageArea = " + drainageAreaInt);


                River river = new River(riverNames,lengthKmInt,lengthMilesInt,drainageAreaInt);
                riverList.add(river);
//                Elements links = cellOfRiverName.select("a");
//                for (Element link : links) {
//                    String linkText = link.text();
//                    System.out.println(i + " ) Link text: "+ linkText);
//                }



                i++;
            }

            for (River river : riverList) {
                System.out.println(river.toString());
            }

//            for (Element table : elements) {
//                System.out.println(elements1.toString());
//            }

            //Write your code here
            ApiUtil.send(riverList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getParsableString (String str) {
        str = str.replace(",", "");
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!Character.isDigit(a)) {
                str = str.substring(0, i);
                break;
            }
        }
        if (str.equals("")) {
            str = "0";
        }
        return str;
    }
}
