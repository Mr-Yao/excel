package me.ly.tools.excel.imp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel
 *
 * @author Created by LiYao on 2017-04-20 21:34.
 */
public class Excel {

    private String key;
    private String name;

    private List<Sheet> sheetList = new ArrayList<>();

    public Sheet createSheet() {
        int num = sheetList.size();
        if (sheetList.isEmpty()) {
            num = 1;
        }
        return this.createSheet(num);
    }

    private Sheet createSheet(int num) {
        Sheet sheet = new Sheet();
        sheetList.add(num - 1, sheet);
        return sheet;
    }

    public Sheet getSheet(int num) {
        if (num <= 0) {
            return null;
        }
        return this.sheetList.get(num - 1);
    }

    public List<Sheet> getAllSheet() {
        return this.sheetList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
