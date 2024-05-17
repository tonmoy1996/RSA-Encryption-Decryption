package model;

public class KeyPairs {
    private String pubKey;
    private String pvtKey;

    public KeyPairs() {
    }

    public KeyPairs(String pubKey, String pvtKey) {
        this.pubKey = pubKey;
        this.pvtKey = pvtKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPvtKey() {
        return pvtKey;
    }

    public void setPvtKey(String pvtKey) {
        this.pvtKey = pvtKey;
    }

    @Override
    public String toString() {
        return "KeyPair{" +
                "pubKey='" + pubKey + '\'' +
                ", pvtKey='" + pvtKey + '\'' +
                '}';
    }
}
