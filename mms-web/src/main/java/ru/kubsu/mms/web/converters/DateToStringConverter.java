package ru.kubsu.mms.web.converters;

import org.dozer.DozerConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DZRock on 09.04.2016.
 */
public class DateToStringConverter extends DozerConverter<Date,String> {

    public DateToStringConverter() {
        super(Date.class, String.class);
    }

    @Override
    public String convertTo(Date date, String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if(date==null)return "";
        return simpleDateFormat.format(date);
    }

    @Override
    public Date convertFrom(String s, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if(s==null)return null;
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
