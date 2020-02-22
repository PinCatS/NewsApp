package com.example.android.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static ArrayList<News> extractNews(String jsonString) {

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject newsQueryJson = new JSONObject(jsonString);
            JSONObject newsQueryResponse = newsQueryJson.getJSONObject("response");
            JSONArray results = newsQueryResponse.getJSONArray("results");

            JSONObject newsObject;
            JSONObject fields;
            JSONArray tags;
            String contributorName = "";
            String thumbnailUrl = null;
            int rating = -1;

            for (int i = 0; i < results.length(); i++) {
                newsObject = results.getJSONObject(i);

                if (newsObject.has("fields")) {
                    fields = newsObject.getJSONObject("fields");
                    if (fields.has("starRating")) {
                        rating = fields.getInt("starRating");
                    }

                    if (fields.has("thumbnail")) {
                        thumbnailUrl = fields.getString("thumbnail");
                    }
                }

                /*
                * Retrieve contributors names.
                * There can be 0 or more contributors
                * if > 1, it shows only the first one and appends " and others"
                * */
                tags = newsObject.getJSONArray("tags");
                if (tags.length() != 0) {
                    contributorName = tags.getJSONObject(0).getString("webTitle");
                }

                if (tags.length() > 1) {
                    contributorName += " and others";
                }

                news.add(new News(newsObject.getString("webTitle"),
                            newsObject.getString("sectionName"),
                            newsObject.getString("webUrl"),
                        thumbnailUrl,
                        rating,
                            contributorName,
                            newsObject.getString("webPublicationDate")));

                // Clean up
                thumbnailUrl = null;
                rating = -1;
            }
        }
        catch(JSONException e) {
            Log.e(LOG_TAG, "Failed to extract news from JSON response: ", e);
        }

        return news;
    }

    private static URL createUrl(String url) {

        URL urlObject = null;

        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Building URL object failed: ", e);
        }

        return urlObject;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonString = null;

        if (url == null) {
            return null;
        }

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                jsonString = readFromInputStream(is);
            } else {
                Log.e(LOG_TAG, "Bad response from the server: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to connect: ", e);
        } finally {
            connection.disconnect();

            if (is != null) {
                is.close();
            }
        }

        return jsonString;
    }

    private static Bitmap makeHttpRequestForImage(URL url) throws IOException {
        Bitmap img = null;

        if (url == null) {
            return null;
        }

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                if (is != null) {
                    img = BitmapFactory.decodeStream(is);
                } else {
                    Log.e(LOG_TAG, "InputStream for thumbnail is empty");
                }
            } else {
                Log.e(LOG_TAG, "Bad response from the server while fetching thumbnail: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to connect while fetching thumbnail: ", e);
        } finally {
            connection.disconnect();

            if (is != null) {
                is.close();
            }
        }

        return img;
    }

    private static String readFromInputStream(InputStream is) throws IOException {
        BufferedReader bufferedReader;
        StringBuilder output = new StringBuilder();

        bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = bufferedReader.readLine();
        while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }

        return output.toString();
    }

    static List<News> fetchNewsData(String queryUrl) {

        URL url = createUrl(queryUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to make the HTTP request", e);
        }

        return extractNews(jsonResponse);
    }

    static Bitmap fetchThumbnail(String queryUrl) {

        URL url = createUrl(queryUrl);

        Bitmap img = null;
        try {
            img = makeHttpRequestForImage(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to make the HTTP request for thumbnail", e);
        }

        return img;
    }
}
