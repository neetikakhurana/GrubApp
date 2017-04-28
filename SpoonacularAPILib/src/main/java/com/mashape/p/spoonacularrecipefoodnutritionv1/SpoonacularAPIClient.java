/*
 * SpoonacularAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package com.mashape.p.spoonacularrecipefoodnutritionv1;

import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.*;
import com.mashape.p.spoonacularrecipefoodnutritionv1.http.client.HttpClient;

public class SpoonacularAPIClient {
    /**
     * Singleton access to Client controller
     * @return	Returns the APIController instance 
     */
    public APIController getClient() {
        return APIController.getInstance();
    }

    /**
     * Get the shared http client currently being used for API calls
     * @return The http client instance currently being used
     */
    public HttpClient getSharedHttpClient() {
        return BaseController.getClientInstance();
    }
    
    /**
     * Set a shared http client to be used for API calls
     * @param httpClient The http client to use
     */
    public void setSharedHttpClient(HttpClient httpClient) {
        BaseController.setClientInstance(httpClient);
    }

    /**
     * Default constructor 
     */     
    public SpoonacularAPIClient() {	
	}

}