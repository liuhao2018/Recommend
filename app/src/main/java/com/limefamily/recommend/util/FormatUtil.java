package com.limefamily.recommend.util;

import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;

/**
 * Created by liuhao on 2018/4/13.
 */

public class FormatUtil {

    private static final int SEX_MAN = 1;
    private static final int SEX_WOMAN = 2;

    private final int SPLIT_START = 3;
    private final int SPLIT_END = 7;
    private final String REPLACE_CONTENT = "****";

    private static final FormatUtil instance = new FormatUtil();

    private FormatUtil() {

    }

    public static FormatUtil getInstance() {
        return instance;
    }

    public String formatSex(int sexCode) {
        if (sexCode == SEX_MAN ) {
            return RecommendApplication.getInstance().getResources().getStringArray(R.array.array_sex)[SEX_MAN - 1];
        }else if (sexCode == SEX_WOMAN ) {
            return RecommendApplication.getInstance().getResources().getStringArray(R.array.array_sex)[SEX_WOMAN - 1];
        }
        return RecommendApplication.getInstance().getString(R.string.text_unknown);
    }

    public String formatDetailDate(String detailDate) {
        try {
            String[] array = detailDate.split(" ");
            if (array != null && array.length > 0 ) {
                return array[0];
            }
        }catch (Exception e) {
            return RecommendApplication.getInstance().getString(R.string.text_empty);
        }
        return RecommendApplication.getInstance().getString(R.string.text_empty);
    }

    public String formatMobile(String mobile) {
        try {
            StringBuilder stringBuilder = new StringBuilder(mobile);
            stringBuilder.replace(SPLIT_START,SPLIT_END, REPLACE_CONTENT);
            return stringBuilder.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return RecommendApplication.getInstance().getString(R.string.text_unknown);
        }
    }

}
