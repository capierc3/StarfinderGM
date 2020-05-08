package ArchivesBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class URLRipper  {

    /**
     * Takes the URL and parses it into a txt file.
     * @param url String
     * @param folder String
     * @param fileName String
     * @throws IOException e
     */
    public static void tablesLayout(String url, String folder, String fileName)throws IOException {
        //parse URL
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .timeout(0).followRedirects(true).execute().parse();

        Elements tables = doc.getElementsByTag("table");
        ArrayList<ArrayList<String>> tablesArray = new ArrayList<>();
        ArrayList<String> headings = new ArrayList<>();
        ArrayList<String> boo = new ArrayList<>();
        Elements h2 = doc.getElementsByTag("h2");
        for (Element element: h2) {
            headings.add(element.text());
        }
        for (Element e:tables) {
            ArrayList<String> table = new ArrayList<>();
            Elements tr = e.getElementsByTag("tr");
            for (Element element: tr) {
                StringBuilder sb = new StringBuilder();
                Elements td = element.getElementsByTag("td");
                for (Element e2 : td) {
                    sb.append(e2.text());
                    sb.append("?");
                }
                if (sb.length() > 0) {
                    table.add(sb.toString());
                }
                Elements th = element.getElementsByTag("th");
                for (Element e3:th) {
                    if (boo.isEmpty()) {
                        boo.add(e3.text());
                    } else if (boo.get(0).equalsIgnoreCase(e3.text())) {
                        break;
                    } else {
                        boo.add(e3.text().replace(" ","_"));
                    }
                }
            }
            tablesArray.add(table);
        }
        //Write Text
        StringBuilder sb = new StringBuilder();
        for (String s: boo) {
            sb.append(s).append("?");
        }
        File dir = new File("Archives");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File subDir = new File(dir + "/" + folder);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        PrintWriter pw = new PrintWriter(new File(dir + "/" + folder + "/" + fileName + ".txt"));
        pw.print("~" + url + "\n");
        pw.print("<>" + sb.toString() + "\n");
        ArrayList<String> headingsFinal = new ArrayList<>();
        if (headings.size() <= 2) {
            headingsFinal.add("-");
        } else {
            for (int i = 2; i < headings.size(); i++) {
                headingsFinal.add(headings.get(i));
            }
        }
        int headingMod = 0;
        for (int i = 0; i < tablesArray.size(); i++) {
            if (!tablesArray.get(i).get(0).equalsIgnoreCase("?")) {
                if (headingsFinal.size() > i - headingMod) {
                    if (headingsFinal.get(i - headingMod).contains("Advanced Melee |")) {
                        pw.print(":::-\n");
                    } else {
                        pw.print(":::" + headingsFinal.get(i - headingMod) + "\n");
                    }
                }
                for (int j = 0; j < tablesArray.get(i).size(); j++) {
                    pw.print(tablesArray.get(i).get(j));
                    pw.print("\n");
                }
                pw.print("\n");
            } else {
                headingMod++;
            }
        }
        pw.close();
    }

    public static void textLayout(String url,String folder, String fileName) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .timeout(0).followRedirects(true).execute().parse();
        Elements td = doc.getElementsByTag("td");
        ArrayList<String> items = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tableHeadings = new ArrayList<>();
        for (Element e:td.get(0).children()) {
            if (e.tag().toString().equalsIgnoreCase("span")) {
                boolean fillHeadings = true;
                for (Element e2: e.children()) {
                    if (e2.tag().toString().equalsIgnoreCase("h2")) {
                        if (sb.length() > 0) {
                            items.add(sb.toString());
                            sb = new StringBuilder();
                            fillHeadings = false;
                        }
                        sb.append(e2.text()).append("?");
                    } else if (e2.tag().toString().equalsIgnoreCase("b")) {
                        if (fillHeadings) {
                            tableHeadings.add(e2.text());
                        }
                        Node node = e2;
                        Element next = e2.nextElementSibling();
                        sb.append(e2.text()).append(":");
                        System.out.println(e2.text() + ": ");
                        boolean inB = true;
                        if (next == null) {

                        }
                        while (inB) {
                            if (next != null) {
                                if (next.tag().toString().equalsIgnoreCase("b")) {
                                    inB = false;
                                    sb.append(e2.nextSibling().toString()).append("?");
                                } else {
                                    sb.append(next.text());
                                    System.out.println(next.text());
                                }
                                next = next.nextElementSibling();
                            } else {
                                inB = false;
                            }
                        }
                    }
                }
                if (sb.length() > 0) {
                    items.add(sb.toString());
                }
            }
        }
        File dir = new File("Archives");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File subDir = new File(dir + "/" + folder);
        if (!subDir.exists()) {
            subDir.mkdir();
        }
        PrintWriter pw = new PrintWriter(new File(dir + "/" + folder + "/" + fileName + ".txt"));
        //Top URL
        pw.print("~" + url + "\n");
        //SQL heading tags
        pw.print("<>");
        for (String s:tableHeadings) {
            pw.print(s + "?");
        }
        pw.print("\n");
        //Type tag
        pw.print(":::");
        pw.print("\n");
        //Information
        for (String s:items) {
            pw.print(s + "\n");
        }
        pw.close();
    }


}
