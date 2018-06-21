package com.web.crawl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlThread {

    public static void main(String[] args) {
    	CrawlThread cThread = new CrawlThread();
//    	cThread.processFiles(args[0]);
    	cThread.processFiles("C:\\Challenges\\WebCrawler\\data");
    }
	public void processFiles(String path) {

		FilenameFilter mp3Filter = new FilenameFilter() {
			public boolean accept(File file, String name) {
				if (name.endsWith(".json")) {
					return true;
				} else {
					return false;
				}
			}
		};

		File dir = new File(path);
		File[] files = dir.listFiles(mp3Filter);

		if (files.length > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(5);
			for (File aFile : files) {
			    Runnable worker = new Crawler(aFile.getAbsolutePath());
			    executor.execute(worker);
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
			}

		}

	}
}
