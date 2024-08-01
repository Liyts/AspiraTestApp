package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.pojo.Category;
import org.example.pojo.League;

import java.util.List;
import java.util.stream.Collectors;

public class PrematchParserDemoApp extends ApiJsonParser {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            List<Category> categories = gson.fromJson(getJsonFromApi(
                    configBundle.getProperty("url")),
                    new TypeToken<List<Category>>(){}.getType());
            List<Category> filteredCategories = filterCategories(categories);

            MatchesParse.addMatchesToLeagues(filteredCategories);
            showResult(filteredCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Category> filterCategories(List<Category> categories) {
        return categories.parallelStream()
                .filter(category -> extractCategoryType.contains(category.getName()))
                .peek(category -> category.setRegions(category.getRegions().parallelStream()
                        .filter(region -> region.getLeagues().parallelStream().anyMatch(League::isTop))
                        .peek(region -> region.setLeagues(region.getLeagues().parallelStream()
                                .filter(League::isTop)
                                .collect(Collectors.toList())))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public static void showResult(List<Category> categories) {
        categories.forEach(category -> {
            if(category.getRegions().size() != 0) {
                category.getRegions().forEach(region -> {
                    System.out.print(category.getName() + ", " + region.getName() + " - ");
                    region.getLeagues().forEach(league -> {
                        System.out.println(league.getName());
                        league.getMatches().forEach(match -> {
                            System.out.println("     " + match.getName() + ", "
                                    + DateUtils.getFormattedDate(match.getKickoff()) + ", "
                                    + match.getId());
                            match.getMarkets().forEach(market -> {
                                System.out.println("          " + market.getName());
                                market.getRunners().forEach(runner -> {
                                    System.out.println("               " + runner.getName()
                                            + ", " + runner.getPriceStr() + ", "
                                            + runner.getId());
                                });
                            });
                        });
                    });
                });
            } else {
                System.out.println(category.getName() + " CATEGORY DOES NOT HAVE TOP LEAGUES!");
            }
        });
    }
}