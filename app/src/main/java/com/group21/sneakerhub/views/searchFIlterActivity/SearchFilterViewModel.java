package com.group21.sneakerhub.views.searchFIlterActivity;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * View Model for Search Filter Activity
 */

public class SearchFilterViewModel extends ViewModel {

    /**
     * Variables
     */
    private volatile String liveQueryString;
    private String finalQueryString;
    private List<String> brandNames;
    private List<String> colours;
    private int lowerPriceRange;
    private int UpperPriceRange;

    // Constructor
    public SearchFilterViewModel(){
        brandNames = new ArrayList<>();
        colours = new ArrayList<>();
    }

    public String getLiveQueryString() {
        return liveQueryString;
    }

    public void setLiveQueryString(String liveQueryString) {
        this.liveQueryString = liveQueryString;
    }

    public String getFinalQueryString() {
        return finalQueryString;
    }

    public void setFinalQueryString(String finalQueryString) {
        this.finalQueryString = finalQueryString;
    }

    public List<String> getBrandNames() {
        return brandNames;
    }

    public void removeColour(String colour){
        colours.remove(colours.indexOf(colour));
    }

    public void removeBrand(String brand){
        brandNames.remove(brandNames.indexOf(brand));
    }

    public void addBrand(String brand){
        if (!brandNames.contains(brand)){
            brandNames.add(brand);
        }
    }

    public void addColour(String colour){
        if (!colours.contains(colour)){
            colours.add(colour);
        }
    }

    public List<String> getColours() {
        return colours;
    }

    public int getLowerPriceRange() {
        return lowerPriceRange;
    }

    public void setLowerPriceRange(int lowerPriceRange) {
        this.lowerPriceRange = lowerPriceRange;
    }

    public int getUpperPriceRange() {
        return UpperPriceRange;
    }

    public void setUpperPriceRange(int upperPriceRange) {
        UpperPriceRange = upperPriceRange;
    }

}
