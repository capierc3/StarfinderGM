package ships;

import ArchivesBuilder.SQLite;

import java.io.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


public class ShipDatabaseBuilder {

    private Elements page;
    private String pageName;
    private Document webDoc;
    private Elements webElms;

    /**
     * Builds a new database of the Ship components from aonsrd.com.
     */
    public ShipDatabaseBuilder() {
        SQLite.build("ships");
        try {
            InputStream is = this.getClass().getResourceAsStream("/archives.xml");
            Document doc = Jsoup.parse(is, null, "", Parser.xmlParser());
            Elements pages = doc.getElementsByTag("ship").get(0).children();
            for (Element element : pages) {
                page = element.children();
                pageName = element.nodeName();
                read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the page and sends the info to the correct methods for parsing and inserting.
     * @throws IOException e
     */
    private void read() throws IOException {
        if (page.get(1).text().equalsIgnoreCase("table")) {
            //tableReader(page.get(0).text());
        } else {
            textReader(page.get(0).text());
        }
    }

    /**
     *  Reads the page element and gets the needed information from tables on the page.
     * @param url String of the page's url
     * @throws IOException e
     */
    private void tableReader(String url) throws IOException {
        //parse URL
        webDoc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .timeout(0).followRedirects(true).execute().parse();
        Elements tables = webDoc.getElementsByTag("table");

        //main list is if there is more then one table on the page,
        //2nd list is the whole row
        //3rd list is the item in the row.
        ArrayList<ArrayList<ArrayList<String>>> tablesArray = new ArrayList<>();
        ArrayList<String> columnsHeadings = new ArrayList<>();
        for (Element e:tables) {
            ArrayList<ArrayList<String>> table = new ArrayList<>();
            Elements tr = e.getElementsByTag("tr");
            for (Element element: tr) {
                Elements td = element.getElementsByTag("td");
                ArrayList<String> row = new ArrayList<>();
                for (Element e2 : td) {
                    row.add(e2.text());
                }
                if (row.size() > 0) {
                    table.add(row);
                }
                Elements th = element.getElementsByTag("th");
                for (Element e3:th) {
                    if (columnsHeadings.isEmpty()) {
                        columnsHeadings.add(e3.text());
                    } else if (columnsHeadings.get(0).equalsIgnoreCase(e3.text())) {
                        break;
                    } else {
                        String heading = e3.text().replace("(","");
                        heading = heading.replace(")","");
                        heading = heading.replace(" ","_");
                        columnsHeadings.add(heading);
                    }
                }
            }
            tablesArray.add(table);
        }
        createTable(columnsHeadings);
        for (ArrayList<ArrayList<String>> arrayLists : tablesArray) {
            addEntries(columnsHeadings, arrayLists);
        }
    }

    private void textReader(String url) throws IOException {
        webDoc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .timeout(0).followRedirects(true).execute().parse();
        //Finds the information and stores it in arrays.
        Elements blocks = webDoc.getElementsByTag("body");
        Element block = blocks.get(0).getElementById("main");
        block = block.getElementById("ctl00_MainContent_DataListAll");
        blocks = block.getElementsByTag("tr");
        for (int i = 0; i < blocks.size(); i++) {
            ArrayList<String> headings = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            String title = blocks.get(i).getElementsByClass("title").get(0).text();
            if (blocks.get(i).getElementsByTag("b").size() == 1) {
                headings.add(blocks.get(i).getElementsByTag("b").get(0).text());
                Element a = blocks.get(i).getElementsByTag("b").get(0).nextElementSibling();
                values.add(a.getElementsByTag("i").text());
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < blocks.get(i).getElementsByTag("br").size(); j++) {
                    Element elm = blocks.get(i).getElementsByTag("br").get(j);
                    while (elm.nextElementSibling() != null) {
                        sb.append(elm.nextSibling());
                        if (elm.nextElementSibling().tag().toString().equalsIgnoreCase("a")) {
                            sb.append(elm.nextElementSibling().text());
                            sb.append(elm.nextElementSibling().getElementsByTag("i").text());
                            sb.append("\n");
                        }
                        elm = elm.nextElementSibling();
                    }
                }
                values.add(sb.toString());
            } else {
                //Element a = blocks.get(i).getElementsByTag("b").get(0).nextElementSibling();
                Elements elms = blocks.get(i).getElementsByTag("b");
                for (int j = 0; j < elms.size(); j++) {
                    headings.add(elms.get(j).text());
                    if (elms.get(j).nextSibling().toString()
                            .equalsIgnoreCase(" ")) {
                        values.add(blocks.get(i).getElementsByTag("i").text());
                    } else {
                        values.add(elms.get(j).nextSibling().toString());
                    }
                }





            }
            System.out.println(title);
            for (int j = 0; j < values.size(); j++) {
                if (j < headings.size()) {
                    System.out.print(headings.get(j) + ": ");
                }
                System.out.print(values.get(j) + "\n");
            }
            System.out.println();
        }
    }


    /**
     * Takes the list of column headings and the list containing the lists for the rows
     * and adds them to the database.
     * Used for bulk new insertions into the database.
     * @param columnHeadings ArrayList if strings
     * @param rows ArrayList of Lists of strings.
     */
    private void addEntries(ArrayList<String> columnHeadings, ArrayList<ArrayList<String>> rows) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(pageName).append("(");
        for (int i = 0; i < columnHeadings.size(); i++) {
            sql.append(columnHeadings.get(i).replace(" ","_"));
            if (i == columnHeadings.size() - 1) {
                sql.append(")");
            } else {
                sql.append(",");
            }
        }
        sql.append("VALUES ('");
        ArrayList<String> inserts = new ArrayList<>();
        String base = sql.toString();
        for (ArrayList<String> values:rows) {
            sql = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                sql.append(values.get(i));
                if (i == values.size() - 1) {
                    sql.append("');");
                } else {
                    sql.append("','");
                }
            }
            inserts.add(base + sql.toString());
        }
        SQLite.AddRecord("ships",inserts,pageName);
    }

    /**
     * creates the table in the database ships with the found column headings.
     * @param columnHeadings ArrayList
     */
    private void createTable(ArrayList<String> columnHeadings) {
        StringBuilder table = new StringBuilder();
        table.append("CREATE TABLE ")
                .append(pageName)
                .append("(");
        for (int i = 0; i < columnHeadings.size(); i++) {
            table.append(columnHeadings.get(i).replace(" ","_"));
            if (i == 0) {
                table.append("     TEXT NOT NULL, ");
            } else if (i == columnHeadings.size() - 1) {
                table.append("     TEXT)");
            } else {
                table.append("     TEXT, ");
            }
        }
        SQLite.createTable("ships",pageName,table.toString());
    }
}
