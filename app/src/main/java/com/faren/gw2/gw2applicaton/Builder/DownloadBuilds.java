package com.faren.gw2.gw2applicaton.Builder;

import android.os.AsyncTask;

import com.faren.gw2.gw2applicaton.Tool.FileManagerTool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;


public class DownloadBuilds extends AsyncTask<String, Void, Void> {
    private HashMap<String,String> builds = new HashMap<>();
    private String path = "";
    public DownloadBuilds(String path) {
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private String connectClasses(String c) {
        switch (c) {
            case "Daredevil":
                c = "Thief";
                break;
            case "Berseker":
                c = "Warrior";
                break;
            case "Reaper":
                c = "Necromancer";
                break;
            case "Chronomancer":
                c = "Mesmer";
                break;
            case "Scrapper":
                c = "Engineer";
                break;
            case "Herald":
                c = "Revenant";
                break;
            case "Dragonhunter":
                c = "Guardian";
                break;
            case "Druid":
                c = "Ranger";
                break;
            case "Tempest":
                c = "Elementalist";
                break;
        }
        return c.toUpperCase();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            // Connect to the web site
            Document document = Jsoup.connect(params[0]).timeout(10 * 1000).get();
            Elements d = document.select("div.build-card-c");
            for (org.jsoup.nodes.Element e : d) {
                Elements urls = d.select("div.title").select("a");
                for (org.jsoup.nodes.Element url : urls) {
                    String save = "{\"posTrait\":[";
                    String address = url.attr("href");
                    System.out.println(address);
                    Document profession = Jsoup.connect("http://metabattle.com" + address).timeout(10*1000).get();

                    Element number = profession.select("span.number").first();
                    if (number == null || number.text().equals("Not Rated")) continue;
                    Element title = profession.select("h2.title").first();
                    String t = connectClasses(title.text().split(" -")[0]);
                    System.out.println("Title " + t);
                    System.out.println("Rate " + number.text());

                    //Specialization
                    Elements spes = profession.select("div[class*=specialization]");
                    String spesName = "";
                    for (org.jsoup.nodes.Element spe : spes) {
                        spesName += "\"" + spe.select("div[class=label").text() + "\",";

                        Elements majors = spe.select("div[class=major]");

                        for (org.jsoup.nodes.Element major : majors) {
                            Elements traits = major.select("div[class*=mbhi]");
                            int index = 0;
                            for (org.jsoup.nodes.Element trait : traits) {
                                if (trait.select("div[class=mbhi]").size() == 1) {
                                    save += index + ",";
                                }
                                index++;
                            }
                        }
                    }
                    spesName = spesName.substring(0, spesName.length() - 1);
                    save = save.substring(0, save.length() - 1);
                    save += "], \"professions\":\"" + t+"\",\"spe\":[" + spesName + "]}";
                    System.out.println(save);
                    String titleSave = t + "_" + title.text().split(" -")[1].replace("/","").replace("\n","") + "-" + number.text().replace("%","");
                    FileManagerTool.saveFile(path, titleSave, save);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
