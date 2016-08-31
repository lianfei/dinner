package com.lianfei.dinner;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lianfei1314 on 2016/8/24.
 */
public class DinnerCarte implements Serializable {

    private static final String TAG = "DINNERCARTE";
    public static final String DINNERTABLE_KEY = "com.lianfei.dinner.dinnertable";
    public static final String DINNERTABLE_ID_KEY = "com.lianfei.dinner.dinnertable.id";

    // 菜单
    private List<DinnerFood> list_dinnerfoods = null;
    //
    private Map<Integer, List<Integer>> map_tableid_numbers = null;

    //
    private Map<String, List<DinnerFood>> map_type_dinnerfoods = null;

    //
    private Map<String, Integer> map_name_position = null;

    private List<String> list_types = null;

    public List<DinnerFood> GetDinnerFoodsByType(String type) {return map_type_dinnerfoods.get(type);}
    public List<String> GetDinnerFoodsTypes() {return list_types;}

    public int GetDinnerFoodNumber(int tabldid,String type,int position) {
        List<Integer> listnumber = map_tableid_numbers.get(tabldid);
        List<DinnerFood> dinnerfoods = map_type_dinnerfoods.get(type);
        if (dinnerfoods != null && listnumber != null && listnumber.size() == list_dinnerfoods.size()) {
            DinnerFood food = dinnerfoods.get(position);
            if (food != null) {
                int newpos = map_name_position.get(food.GetFoodName());
                return listnumber.get(newpos);
            }
        }
        return 0;
    }

    public DinnerCarte(String filepathname) {
        super();
        // 当前类别
        String dinnertype = "";
        String encoding="UTF-8";
        list_dinnerfoods = new ArrayList<>();
        map_type_dinnerfoods = new HashMap<>();
        map_name_position = new HashMap<>();
        list_types = new ArrayList<>();
        if (filepathname != null && !filepathname.equals("")) {
            File file = null;
            InputStreamReader read = null;
            BufferedReader bufferedReader = null;
            try {
                file = new File(filepathname);
                if (file.isFile() && file.exists()) {
                    read = new InputStreamReader(new FileInputStream(file), encoding);
                    bufferedReader = new BufferedReader(read);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.trim();
                        if (line.equals(""))
                            continue;
                        String [] linesplits = line.split(":");
                        switch (linesplits.length) {
                            case 0:
                                break;
                            case 1:
                                dinnertype = line;
                                list_types.add(line);
                                break;
                            case 2:
                                if (!dinnertype.equals("")) {
                                    // 当前type不是空
                                    DinnerFood food = new DinnerFood(linesplits[0],Integer.valueOf(linesplits[1]).intValue(),dinnertype);
                                    list_dinnerfoods.add(food);
                                    List<DinnerFood> dinnerfoods = null;
                                    if ((dinnerfoods = map_type_dinnerfoods.get(dinnertype)) != null) {

                                    } else {
                                        dinnerfoods = new ArrayList<>();
                                        map_type_dinnerfoods.put(dinnertype,dinnerfoods);
                                    }
                                    dinnerfoods.add(food);
                                    map_name_position.put(food.GetFoodName(),list_dinnerfoods.size() - 1);
                                    Log.d(TAG, "菜名: " + linesplits[0] + " 单价: " + linesplits[1] + "，类别: " + dinnertype);
                                }
                                break;
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (read != null) {
                    try {
                        read.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public DinnerCarte(InputStream is) {
        super();
        // 当前类别
        String dinnertype = "";
        list_dinnerfoods = new ArrayList<>();
        map_type_dinnerfoods = new HashMap<>();
        map_name_position = new HashMap<>();
        list_types = new ArrayList<>();
        if (is != null) {
            InputStreamReader read = null;
            BufferedReader bufferedReader = null;
            try {
                read = new InputStreamReader(is);
                bufferedReader = new BufferedReader(read);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    if (line.equals(""))
                        continue;
                    String [] linesplits = line.split(":");
                    switch (linesplits.length) {
                        case 0:
                            break;
                        case 1:
                            dinnertype = line;
                            list_types.add(line);
                            break;
                        case 2:
                            if (!dinnertype.equals("")) {
                                // 当前type不是空
                                DinnerFood food = new DinnerFood(linesplits[0],Integer.valueOf(linesplits[1]).intValue(),dinnertype);
                                list_dinnerfoods.add(food);
                                List<DinnerFood> dinnerfoods = null;
                                if ((dinnerfoods = map_type_dinnerfoods.get(dinnertype)) != null) {

                                } else {
                                    dinnerfoods = new ArrayList<>();
                                    map_type_dinnerfoods.put(dinnertype,dinnerfoods);
                                }
                                dinnerfoods.add(food);
                                map_name_position.put(food.GetFoodName(),list_dinnerfoods.size() - 1);
                                Log.d(TAG, "菜名: " + linesplits[0] + " 单价: " + linesplits[1] + "，类别: " + dinnertype);
                            }
                            break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (read != null) {
                    try {
                        read.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void DinnerStart(int tableindex) {
        if (map_tableid_numbers == null) {
            map_tableid_numbers = new HashMap<>();
        }
        List<Integer> listnumber = new ArrayList<>();
        if (map_tableid_numbers.get(tableindex) == null) {
            for (int index = 0; index < list_dinnerfoods.size(); ++index) {
                listnumber.add(0);
            }
            // 添加tableid => listnumber关联
            map_tableid_numbers.put(tableindex, listnumber);
        }
    }

    public int DinnerHaveStart(int tableindex) {
        if (map_tableid_numbers == null || map_tableid_numbers.get(tableindex) == null)
            return 0;
        return 1;
    }

    public String GetDinnerInfo(int tableindex) {
        String str = "名称         单价      数量\n";
        List<Integer> listnumber = map_tableid_numbers.get(tableindex);
        if (listnumber != null) {
            for (int index = 0; index < listnumber.size(); ++index) {
                if (listnumber.get(index).intValue() > 0) {
                    str += list_dinnerfoods.get(index).GetFoodName() + "  " +
                            list_dinnerfoods.get(index).GetFoodPrice() + "  " +
                            listnumber.get(index).intValue() + "\n";
                }
            }
        }
        str += "---------------------------\n";
        str += "        共计:      " + DinnerAccount(tableindex) + " 元";
        return str;
    }

    public void DinnerOrder(int tableindex, String type, int position, int number) {
        List<Integer> listnumber = map_tableid_numbers.get(tableindex);
        List<DinnerFood> dinnerfoods = map_type_dinnerfoods.get(type);
        if (dinnerfoods != null && listnumber != null && listnumber.size() == list_dinnerfoods.size()) {
            DinnerFood food = dinnerfoods.get(position);
            if (food != null) {
                int newpos = map_name_position.get(food.GetFoodName());
                if (number < 0)  number = 0;
                if (number >= 1000)  number = 999;
                if (newpos >= 0 && newpos < listnumber.size()) {
                    listnumber.set(newpos,number);
                }
            }
        }
    }

    public int DinnerAccount(int tableindex) {
        int account = 0;
        List<Integer> listnumber = map_tableid_numbers.get(tableindex);
        if (listnumber != null && listnumber.size() == list_dinnerfoods.size()) {
            for (int index = 0; index < list_dinnerfoods.size(); ++index) {
                account += list_dinnerfoods.get(index).GetFoodPrice() * listnumber.get(index).intValue();
            }
        }
        return account;
    }

    public void DinnerEnd(int tableindex) {
        map_tableid_numbers.remove(tableindex);
    }
}
