package edu.umbc.cmsc691.util;

/**
 * 
 */

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * @author Shrinivas Kane
 * Class to fetch twites as per handle and store it to flat file
 */
public class FeedFetcher implements Closeable{

	// this is security issue but for now let it be :) 
	private final static String AUTH_TOKEN = "101252945-qQKRpdvNrPyDV1eSOy3PI3YKQrvEYNuBqR9b3ipu";
	private final static String AUTH_SECRET = "GBTGERLeUqn6yDK1Z5E5yqfsHtPKfBow2BIMT0LixVAKa";
	private final static String CUST_KEY = "sL1y7pGYzcEogVT4afLDto7jq";
	private final static String CuST_SECRET_KEY = "8aGA2VKWT5qn4BLrg1yMAZlEorggGtO2mRey7FPyQkE6B0iebT";
	Twitter twitterFeedFetcher;
	AccessToken oathAccessToken;
	FileWriter seqFileWriter;

	// set customer keys 
	public FeedFetcher() {
		twitterFeedFetcher = new TwitterFactory().getInstance();
		oathAccessToken = new AccessToken(AUTH_TOKEN, AUTH_SECRET);
		twitterFeedFetcher.setOAuthConsumer(CUST_KEY, CuST_SECRET_KEY);
		twitterFeedFetcher.setOAuthAccessToken(oathAccessToken);
	}

	/**
	 * Fetch twits from and write to flat file 
	 * @param queryString Twitter handle to query 
	 * @param outputFilePath output filepath 
	 * @throws TwitterException
	 * @throws IOException
	 */
	public void fetch(String queryString, String outputFilePath)  throws TwitterException, IOException  {

		seqFileWriter = new FileWriter(outputFilePath, false);
		Query twitQuery = new Query();
		twitQuery.setCount(1000);
		twitQuery.setQuery(queryString);
		QueryResult result = twitterFeedFetcher.search(twitQuery);
		seqFileWriter.append(result.toString()).flush();
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage java FeedFetcher.jar <QueryHandle> <OutPutFilePath>");
		} else {
			new FeedFetcher().fetch(args[0], args[1]);// run the Twitter client
			System.out.println("done");
		}
	}

	public void close() throws IOException {
		if(seqFileWriter != null)
		seqFileWriter.close();
	}

}
