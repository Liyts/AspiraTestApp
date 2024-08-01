package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.pojo.Category;
import org.example.pojo.Market;
import org.example.pojo.Match;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MatchesParse extends ApiJsonParser{
    public static void addMatchesToLeagues(List<Category> filteredCategories) {
        int numberOfThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        try {
            List<Future<Void>> futures = filteredCategories.stream()
                    .map(category -> (Callable<Void>) () -> {
                        category.getRegions().forEach(region -> {
                            region.getLeagues().forEach(league -> {
                                List<Match> matches = null;
                                try {
                                    Gson gson = new Gson();
                                    String url = String.format(configBundle.getProperty("match_url"), league.getId());
                                    JsonObject jsonObject = gson.fromJson(getJsonFromApi(url), JsonObject.class);
                                    JsonArray eventsArray = jsonObject.getAsJsonArray("events");
                                    matches = gson.fromJson(eventsArray, new TypeToken<List<Match>>(){}.getType());
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                assert matches != null : "Matches list should not be null";
                                try {
                                    league.setMatches(matches.subList(0, 2));
                                }catch (IndexOutOfBoundsException ex){
                                    league.setMatches(matches);
                                }
                                league.getMatches().forEach(match -> {
                                    List<Market> markets = MarketParse.addMarketsToMatches(match);
                                    match.setMarkets(markets);
                                });
                            });
                        });
                        return null;
                    })
                    .map(executorService::submit)
                    .collect(Collectors.toList());
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
