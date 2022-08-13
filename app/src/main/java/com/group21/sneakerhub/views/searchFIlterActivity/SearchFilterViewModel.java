package com.group21.sneakerhub.views.searchFIlterActivity;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterViewModel extends ViewModel {

    /**
     * Fields
     */
    private List<Float> priceFilterValues;
    private String QueryString;
    private List<String> selectedBrands;
    private List<String> selectedColours;
    private boolean submitButtonPressed;

    /**
     * Required getters and setters
     * @return
     */
    public List<Float> getPriceFilterValues() {
        return priceFilterValues;
    }

    public String getQueryString() {
        return QueryString;
    }

    public List<String> getSelectedBrands() {
        return selectedBrands;
    }

    public List<String> getSelectedColours() {
        return selectedColours;
    }

    public boolean isSubmitButtonPressed() {
        return submitButtonPressed;
    }

    public void setPriceFilterValues(List<Float> priceFilterValues) {
        this.priceFilterValues = priceFilterValues;
    }

    public void setQueryString(String queryString) {
        QueryString = queryString;
    }

    public void setSubmitButtonPressed(boolean submitButtonPressed) {
        this.submitButtonPressed = submitButtonPressed;
    }

    /**
     * Add to selected brands
     */
    public void addToBrands(String currentBrand){
        if (selectedBrands == null){
            selectedBrands = new ArrayList<String>();
        }

        selectedBrands.add(currentBrand);
    }
    /**
     * Remove from selected brands
     */

    public void removeFromBrands(String currentBrand){
        selectedBrands.remove(selectedBrands.indexOf(currentBrand));
    }

    /**
     * Add to selected colours
     */
    public void addToColours(String currentColour){
        if (selectedColours == null){
            selectedColours = new ArrayList<String>();
        }

        selectedColours.add(currentColour);
    }

    /**
     * Remove to selected colours
     */
    public void removeFromColours(String currentColour){
        selectedColours.remove(selectedColours.indexOf(currentColour));
    }




}
