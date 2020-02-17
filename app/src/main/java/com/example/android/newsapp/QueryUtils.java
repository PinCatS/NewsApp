package com.example.android.newsapp;

        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    static final String NEWS_JSON_SAMPLE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":3,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":1,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"film/2019/nov/24/harriet-review-harriet-tubman-biopic-cynthia-erivo-janelle-monae\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2019-11-24T07:00:16Z\",\"webTitle\":\"Harriet review – thrilling drama about the abolitionist Harriet Tubman\",\"webUrl\":\"https://www.theguardian.com/film/2019/nov/24/harriet-review-harriet-tubman-biopic-cynthia-erivo-janelle-monae\",\"apiUrl\":\"https://content.guardianapis.com/film/2019/nov/24/harriet-review-harriet-tubman-biopic-cynthia-erivo-janelle-monae\",\"fields\":{\"headline\":\"Harriet review – thrilling drama about the abolitionist Harriet Tubman\",\"starRating\":\"4\",\"shortUrl\":\"https://gu.com/p/cmkqf\",\"thumbnail\":\"https://media.guim.co.uk/08f585315696c883a47236adef1a2cb7689ecb51/180_0_2958_1776/500.jpg\"},\"tags\":[{\"id\":\"profile/simran-hans\",\"type\":\"contributor\",\"webTitle\":\"Simran Hans\",\"webUrl\":\"https://www.theguardian.com/profile/simran-hans\",\"apiUrl\":\"https://content.guardianapis.com/profile/simran-hans\",\"references\":[],\"bio\":\"<p>Simran Hans is a film critic for the Observer and a culture writer whose work has appeared in, among others, BuzzFeed, Dazed, the FADER and Sight &amp; Sound.</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2018/01/18/Simran-Hans.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2018/01/18/Simran_Hans,_L.png\",\"firstName\":\"Simran\",\"lastName\":\"Hans\",\"twitterHandle\":\"heavier_things \"}],\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"film/2020/jan/24/zola-review-taylour-paige-janicza-bravo\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2020-01-24T23:57:59Z\",\"webTitle\":\"Zola review – stylish viral tweet-based sex and crime caper\",\"webUrl\":\"https://www.theguardian.com/film/2020/jan/24/zola-review-taylour-paige-janicza-bravo\",\"apiUrl\":\"https://content.guardianapis.com/film/2020/jan/24/zola-review-taylour-paige-janicza-bravo\",\"fields\":{\"headline\":\"Zola review – stylish viral tweet-based sex and crime caper\",\"starRating\":\"3\",\"shortUrl\":\"https://gu.com/p/d6cgz\",\"thumbnail\":\"https://media.guim.co.uk/1a58c8d3d2a93ed4fc244ea77a44b3af9acf1b1f/25_0_1032_619/500.jpg\"},\"tags\":[{\"id\":\"profile/benjamin-lee-film\",\"type\":\"contributor\",\"webTitle\":\"Benjamin Lee\",\"webUrl\":\"https://www.theguardian.com/profile/benjamin-lee-film\",\"apiUrl\":\"https://content.guardianapis.com/profile/benjamin-lee-film\",\"references\":[],\"bio\":\"<p>Benjamin Lee is the east coast arts editor at Guardian US, based in New York</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2016/04/21/Benjamin-Lee.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2017/10/06/Benjamin-Lee,-L.png\",\"firstName\":\"Benjamin\",\"lastName\":\"Lee\"}],\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"film/2019/nov/29/eyes-wide-shut-review-stanley-kubrick-tom-cruise-nicole-kidman\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2019-11-29T12:00:06Z\",\"webTitle\":\"Eyes Wide Shut review – chilling secrecy, quaintly soft-porn sex\",\"webUrl\":\"https://www.theguardian.com/film/2019/nov/29/eyes-wide-shut-review-stanley-kubrick-tom-cruise-nicole-kidman\",\"apiUrl\":\"https://content.guardianapis.com/film/2019/nov/29/eyes-wide-shut-review-stanley-kubrick-tom-cruise-nicole-kidman\",\"fields\":{\"headline\":\"Eyes Wide Shut review – chilling secrecy, quaintly soft-porn sex\",\"starRating\":\"4\",\"shortUrl\":\"https://gu.com/p/cnmy8\",\"thumbnail\":\"https://media.guim.co.uk/7c28a52c3586a1594ce89317c1042ebea06ef061/156_299_2676_1605/500.jpg\"},\"tags\":[{\"id\":\"profile/peterbradshaw\",\"type\":\"contributor\",\"webTitle\":\"Peter Bradshaw\",\"webUrl\":\"https://www.theguardian.com/profile/peterbradshaw\",\"apiUrl\":\"https://content.guardianapis.com/profile/peterbradshaw\",\"references\":[],\"bio\":\"<p>Peter Bradshaw is the Guardian's film critic</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2018/01/10/Peter-Bradshaw.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2018/01/10/Peter_Bradshaw,_L.png\",\"firstName\":\"Peter\",\"lastName\":\"Bradshaw\",\"twitterHandle\":\"PeterBradshaw1\"}],\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"}]}}";

    private QueryUtils() {
    }

    private static ArrayList<News> extractNews(String jsonString) {

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject newsQueryJson = new JSONObject(jsonString);
            JSONObject newsQueryResponse = newsQueryJson.getJSONObject("response");

            int newsCount = newsQueryResponse.getInt("total");
            int pages = newsQueryResponse.getInt("pages");
            int pageSize = newsQueryResponse.getInt("pageSize");
            int curentPage = newsQueryResponse.getInt("currentPage");

            JSONArray results = newsQueryResponse.getJSONArray("results");
            JSONObject newsObject = null;
            JSONObject fields = null;
            JSONArray tags = null;
            for (int i = 0; i < results.length(); i++) {
                newsObject = results.getJSONObject(i);
                fields = newsObject.getJSONObject("fields");
                news.add(new News(newsObject.getString("webTitle"),
                            newsObject.getString("sectionName"),
                            newsObject.getString("webUrl"),
                            fields.getInt("starRating"),
                            " ",
                            newsObject.getString("webPublicationDate")));
            }

        }
        catch(JSONException e) {
            Log.e(LOG_TAG, "Failed to extract news from JSON response: " + e);
        }

        return news;
    }

    public static List<News> fetchNewsData(String queryUrl) {
        return extractNews(NEWS_JSON_SAMPLE);
    }
}
