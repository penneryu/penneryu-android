package com.penner.android.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;

import com.penner.android.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PennerYu on 15/11/4.
 */
public final class SmileUtils {

    public static final String ee_1 = "[):]";

    private static final Spannable.Factory spannableFactory = Spannable.Factory
            .getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<>();

    static {
        addPattern(emoticons, ee_1, R.mipmap.chat_show_1);
    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    /**
     * replace existing spannable with smiles
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()),
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static boolean containsKey(String key){
        boolean b = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }

        return b;
    }

    public static Spannable parseItalicStyleSpan(String content) {
        final Spannable source = (Spannable) Html.fromHtml(content);
        final StyleSpan[] styleSpans = source.getSpans(0, source.length(), StyleSpan.class);
        if (styleSpans != null && styleSpans.length > 0) {
            for (StyleSpan styleSpan : styleSpans) {
                if (Typeface.ITALIC == styleSpan.getStyle()) {
                    source.setSpan(new BackgroundColorSpan(Color.RED), source.getSpanStart(styleSpan), source.getSpanEnd(styleSpan), 0);
                    source.removeSpan(styleSpan);
                }
            }
        }
        return source;
    }
}
