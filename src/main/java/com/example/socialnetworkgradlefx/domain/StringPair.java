package com.example.socialnetworkgradlefx.domain;


public class StringPair {
    private String trimis;
    private String primit;

    /**
     * StringPair constructor
     * @param trimis int
     * @param primit int
     */
    public StringPair(String trimis, String primit) {
        this.trimis = trimis;
        this.primit = primit;
    }

    /**
     * Getter for the sent text from the pair
     * @return String
     */
    public String getTrimis() {
        return trimis;
    }

    /**
     * Setter for the sent text from the pair
     * @param trimis String
     */
    public void setTrimis(String trimis) {
        this.trimis = trimis;
    }

    /**
     * Getter for the received text from the pair
     * @return String
     */
    public String getPrimit() {
        return primit;
    }

    /**
     * Setter for the received text from the pair
     * @param primit String
     */
    public void setPrimit(String primit) {
        this.primit = primit;
    }
}