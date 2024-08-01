package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.pojo.Market;
import org.example.pojo.Match;
import org.example.pojo.Runner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarketParse extends ApiJsonParser{
    public static List<Market> addMarketsToMatches(Match match){
        List<Market> markets = new ArrayList<>();
        try {
            Gson gson = new Gson();
            String url = String.format(configBundle.getProperty("market_url"), match.getId());
            JsonObject jsonObject = gson.fromJson(getJsonFromApi(url), JsonObject.class);
            JsonArray eventsArray = jsonObject.getAsJsonArray("markets");
            markets = gson.fromJson(eventsArray, new TypeToken<List<Market>>(){}.getType());
        } catch (Exception e){
            e.printStackTrace();
        }

        assert markets != null : "Markets list should not be null";
        return mergeMarkets(markets);
    }

    private static List<Market> mergeMarkets(List<Market> markets) {
        Set<String> uniqMarkets = new HashSet<>();
        markets.forEach(market -> {
            uniqMarkets.add(market.getName());
        });
        List<Market> mergedMarkets = new ArrayList<>();

        for (String uniqMarket: uniqMarkets) {
            List<Runner> runners = new ArrayList<>();
            long marketId = 0;
            for (Market market: markets) {
                if(uniqMarket.equals(market.getName())) {
                    runners.addAll(market.getRunners());
                    marketId = market.getId();
                }
            }
            if(marketId != 0)
                mergedMarkets.add(new Market(marketId, uniqMarket, runners));
        }

        return mergedMarkets;
    }
}
