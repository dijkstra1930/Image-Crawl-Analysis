package demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;

import util.FlickrURLGenerator;
import util.XMLDownloader;

public class GeneratorRun {
	private static final String targetURL = "https://api.flickr.com/services/rest/";


	public static String generateParameter(String method, String api_key,
			String per_page, String page) {

		return "?method=" + method + "&api_key=" + api_key + "&per_page="
				+ per_page + "&page=" + page;
	}

	public static void main(String[] args) {
		BufferedWriter bw = null;
		try {

			int per_page = 10;// Maximum per page
			int pages = 5;
			String export_filename =  "/tmp/photo_url.txt";
		
			File file = new File(export_filename);
			bw = new BufferedWriter(new FileWriter(file, true)); // true mean append
			
			for (int i = 1; i <= pages; i++) {
				String params = GeneratorRun.generateParameter(
						"flickr.photos.getRecent",
						"314859c708417e548a161f1385dd9990", String.valueOf(per_page), String.valueOf(i));
			
				// here use the two utils 
				Document xmlDoc = XMLDownloader.excuteDownload(targetURL, params);
				ArrayList<String> urls = FlickrURLGenerator.generateURL(xmlDoc);
				
				for (String str : urls) {
					bw.write(str);
					bw.newLine();
				}
				System.out.println(i +" completed!");
			}

			bw.close();
			System.out.println("Succeed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
}
