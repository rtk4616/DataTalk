package com.ivonhoe.parser.app.category;

import com.ivonhoe.parser.Unit;
import com.ivonhoe.parser.io.IWriter;
import util.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ivonhoe on 15-1-27.
 */
public class CategoryWriter extends IWriter {

    ArrayList<ArrayList<AppInfo>> mCategoryInfo = new ArrayList<ArrayList<AppInfo>>();
    HashMap<String, Integer> categoryMap = new HashMap<String, Integer>();

    String path;

    public CategoryWriter(String path) {
        this.path = path;
    }

    @Override
    public void onWrite(List<Unit> list) {
        for (Unit unit : list) {
            AppInfo tbAppInfo = (AppInfo) unit;
            String category = tbAppInfo.getCategory();

            Integer index = categoryMap.get(category);
            ArrayList<AppInfo> infos = null;
            if (index == null) {
                infos = new ArrayList<AppInfo>();
                mCategoryInfo.add(infos);
                categoryMap.put(category, mCategoryInfo.size() - 1);
            } else {
                infos = mCategoryInfo.get(index);
            }

            infos.add(tbAppInfo);
        }

        FileWriter fileWriter;
        try {
            int sum = 0;
            fileWriter = new FileWriter(path, true);
            for (int i = 0; i < mCategoryInfo.size(); i++) {
                ArrayList<AppInfo> appInfos = mCategoryInfo.get(i);
                String category = appInfos.get(0).getCategory();
                Log.d("" + category + ":" + appInfos.size());
                fileWriter.write("{" + category + ":" + appInfos.size() + "}" + ";");
            }
            for (int i = 0; i < mCategoryInfo.size(); i++) {
                ArrayList<AppInfo> appInfos = mCategoryInfo.get(i);
                for (AppInfo info : appInfos) {
                    fileWriter.write(info.getPackageName() + ";");
                    sum++;
                }
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void close() {

    }
}
