package com.faren.gw2.gw2applicaton.Builder;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class DownloadBuilds extends AsyncTask<String, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            // Connect to the web site
            Document document = Jsoup.connect(params[0]).get();
            Elements d = document.select("div.builds-intro-pvp").select("div.builds-intro-list");
            for (org.jsoup.nodes.Element e : d) {
                Elements ps = d.select("p").select("a[href]");
                for(org.jsoup.nodes.Element p : ps) {
                    String address = p.attr("href");
                    System.out.println(address);
                    Document profession = Jsoup.connect("http://metabattle.com" + address).get();
                    Elements spes = profession.select("div[class*=specialization]");
                    for(org.jsoup.nodes.Element spe : spes) {
                        System.out.println(spe.select("div[class=label").text());


                        Elements majors = spe.select("div[class=major]");

                        for(org.jsoup.nodes.Element major : majors) {
                            Elements traits = major.select("div[class*=mbhi]");
                            int index = 0;
                            for (org.jsoup.nodes.Element trait : traits) {
                                if(trait.select("div[class=mbhi]").size() == 1) {
                                    System.out.println(index);
                                }
                                index++;
                            }
                        }
                    }

                }
            }
            // Get the html document title
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
