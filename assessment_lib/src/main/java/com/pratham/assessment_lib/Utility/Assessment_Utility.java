package com.pratham.assessment_lib.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import com.isupatches.wisefy.WiseFy;
import com.pratham.assessment_lib.custom.custom_dialogs.ZoomImageDialog_;

import java.io.File;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.pratham.assessment_lib.Utility.Assessment_Constants.SELECTED_LANGUAGE;

public class Assessment_Utility {
    private static List<Integer> colors;
    public static Integer selectedColor = 0;
    public static ColorStateList colorStateList;
    public static WiseFy wiseF;

    public static void initWiseF(Context context) {
        wiseF = new WiseFy.Brains(context).logging(true).getSmarts();
    }



    public static void setSelectedColor(int defaultColor) {
        colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{
                        Color.TRANSPARENT //disabled
                        , selectedColor //enabled
                }
        );
    }

    public static void setDefaultColor(int color) {
        selectedColor = color;
        setSelectedColor(selectedColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getInternalPath(Context context) {


        File[] intDir = context.getExternalFilesDirs("");
        try {
            if (intDir.length > 1) {
                try {
                    File file = new File(intDir[1].getAbsolutePath(), "hello.txt");
                    if (!file.exists())
                        file.createNewFile();
                    file.delete();
                    Assessment_Constants.STORING_IN = "SD-Card";
                    return intDir[1].getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                    Assessment_Constants.STORING_IN = "Internal Storage";
                    return intDir[0].getAbsolutePath();
                }
            } else {
                Assessment_Constants.STORING_IN = "Internal Storage";
                return intDir[0].getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("getInternalPath@@@", e.getMessage());
            return intDir[0].getAbsolutePath();
        }
    }

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(cal.getTime());
    }

    public static void HideInputKeypad(Activity act) {

        InputMethodManager inputManager = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (act.getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(act.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static String getFileName(String qid, String photoUrl) {
        String[] splittedPath = photoUrl.split("/");
        String fileName = qid + "_" + splittedPath[splittedPath.length - 1];
        return fileName;
    }

    public static String getFileExtension(String fileName) {
        String extension = "";
        String[] splitted = fileName.split("\\.");
        extension = splitted[splitted.length - 1];
        return extension;
    }

    public static void setOdiaFont(Context context, View view) {
        try {
            String currentLang = SELECTED_LANGUAGE;
            if (currentLang.equalsIgnoreCase("12")) {
                Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/lohit_oriya.ttf");
                if (view instanceof CheckBox) {
                    ((CheckBox) view).setTypeface(font);
                } else if (view instanceof RadioButton) {
                    ((RadioButton) view).setTypeface(font);
                } else if (view instanceof TextView) {
                    ((TextView) view).setTypeface(font);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showZoomDialog(Context context, String path, String localPath) {
        Intent intent = new Intent(context, ZoomImageDialog_.class);
        intent.putExtra("onlinePath", path);
        intent.putExtra("localPath", localPath);
        context.startActivity(intent);
//        ZoomImageDialog zoomImageDialog = new ZoomImageDialog(context, path, localPath);
//        zoomImageDialog.show();
    }
    public static String removeSpecialCharacters(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");
    }

}
