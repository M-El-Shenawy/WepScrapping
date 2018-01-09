import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	private static Document doc;
	private static Elements elem;
	private static Response imgResponse;
	private static StringBuilder nameOfImage = new StringBuilder("Balcony");

	public static void main(String[] args) {

		try {
			doc = Jsoup.connect("https://en.wikipedia.org/wiki/Balcony").userAgent("Mozilla").get();
			elem = doc.getElementsByTag("img");
			int nOfImage = 1;
			FileOutputStream result = null;
			for (Element e : doc.select("img")) {
				String iURL = e.attr("src");
				if (iURL.contains("static")) {
					continue;
				}
				if (iURL.contains("jpg")) {
					System.out.println(iURL);
					imgResponse = Jsoup.connect("https:" + iURL).ignoreContentType(true).execute();
					nameOfImage = new StringBuilder(nameOfImage.append(nOfImage).append(".jpg"));
					result = (new FileOutputStream(new java.io.File("imgs/" + nameOfImage.toString())));
					result.write(imgResponse.bodyAsBytes());
					nOfImage++;
					nameOfImage = new StringBuilder("Balcony");
				}

			}
			result.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(doc.title());
	}
}
