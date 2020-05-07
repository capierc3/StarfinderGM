package ships;


import java.io.*;
import java.util.ArrayList;


import ArchivesBuilder.SQLite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


public class ShipDatabaseBuilder {

    InputStream is;
    Document doc;
    Elements page;
    String pageName;

    public ShipDatabaseBuilder() {
        SQLite.build("ships");
        try {
            is = this.getClass().getResourceAsStream("/archives.xml");
            doc = Jsoup.parse(is,null,"", Parser.xmlParser());
            Elements pages = doc.getElementsByTag("ship").get(0).children();
            for (int i = 0; i < pages.size(); i++) {
                page = pages.get(i).children();
                pageName = pages.get(i).nodeName();
                read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void read() throws IOException {
        if (page.get(1).text().equalsIgnoreCase("table")) {
            tableReader(page.get(0).text());
        } else {
            //ToDo: other page types.
        }
    }

    private void tableReader(String url) throws IOException {
        //parse URL
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .timeout(0).followRedirects(true).execute().parse();

        Elements tables = doc.getElementsByTag("table");
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
