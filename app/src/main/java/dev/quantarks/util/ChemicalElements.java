package dev.quantarks.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ChemicalElements {
    private int atomicNumber;
    private String name;
    private String symbol;
    private String places;
    private String history;
    private String nameOrigin;
    // private List uses;
    // private List danger;
    private String discoverers;
    private String discovery_year;

    private String json;

    private ChemicalElements(int atomicNumber, String name, String symbol, String places, String history, String nameOrigin, String discoverers, String discovery_year) {
        this.setAtomicNumber(atomicNumber);
        this.setName(name);
        this.setSymbol(symbol);
        this.setPlaces(places);
        this.setHistory(history);
        this.setNameOrigin(nameOrigin);
        this.setDiscoverers(discoverers);
        this.setDiscovery_year(discovery_year);
    }

    public static ChemicalElements findByNumber(int atomicNumber, Context context) {
        ChemicalElements ChemicalElements = null;
        try {
            InputStream data = context.getAssets().open("Element.json");
            int size = data.available();
            byte[] buffer = new byte[size];
            data.read(buffer);
            data.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getInt("atomic_number") == atomicNumber) {
                    ChemicalElements = new ChemicalElements(obj.getInt("atomic_number"), obj.getString("name"), obj.getString("symbol"), obj.getString("place"), obj.getString("history"), obj.getString("name_origin"), obj.getString("discoverer"), obj.getString("discovery_year"));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChemicalElements;
    }

    public static ChemicalElements findByName(String name, Context context) {
        ChemicalElements ChemicalElements = null;
        try {
            InputStream data = context.getAssets().open("Element.json");
            int size = data.available();
            byte[] buffer = new byte[size];
            data.read(buffer);
            data.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("name").equals(name)) {
                    ChemicalElements = new ChemicalElements(obj.getInt("atomic_number"), obj.getString("name"), obj.getString("symbol"), obj.getString("place"), obj.getString("history"), obj.getString("name_origin"), obj.getString("discoverer"), obj.getString("discovery_year"));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ChemicalElements;
    }

    public static ChemicalElements findBySymbol(String symbol, Context context) {
        ChemicalElements ChemicalElements = null;
        try {
            InputStream data = context.getAssets().open("Element.json");
            int size = data.available();
            byte[] buffer = new byte[size];
            data.read(buffer);
            data.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("symbol").equals(symbol)) {
                    ChemicalElements = new ChemicalElements(obj.getInt("atomic_number"), obj.getString("name"), obj.getString("symbol"), obj.getString("place"), obj.getString("history"), obj.getString("name_origin"), obj.getString("discoverer"), obj.getString("discovery_year"));
                    return ChemicalElements;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object findByElectronConf(int[] electronConfiguration) {
        return null;
    }

    private void setDiscovery_year(String discovery_year) {
        this.discovery_year = discovery_year;
    }

    //region getters
    public int getAtomicNumber() {
        return atomicNumber;
    }

    //region setters
    private void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public String getName() {
        return name;
    }

    //endregion

    private void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    private void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPlaces() {
        return places;
    }

    private void setPlaces(String places) {
        this.places = places;
    }

    public String getHistory() {
        return history;
    }

    private void setHistory(String history) {
        this.history = history;
    }

    public String getNameOrigin() {
        return nameOrigin;

    }

    private void setNameOrigin(String nameOrigin) {
        this.nameOrigin = nameOrigin;
    }

    public String getUses() {
        return "wew";
    }
    //endregion

    public String getDangers() {
        return "wew";
    }

    public String getDiscoveryYear() {
        return discovery_year;
    }

    public String getDiscoverers() {
        return "Discoverers";
    }

    private void setDiscoverers(String discoverers) {
        this.discoverers = discoverers;
    }

    //region constants

    public static int MAX_ELEMENT_NUMBER = 117;

    //endregion
}
