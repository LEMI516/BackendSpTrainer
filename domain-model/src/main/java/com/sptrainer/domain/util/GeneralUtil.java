package com.sptrainer.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.MessageResult;
import com.sptrainer.domain.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GeneralUtil {

    public static ObjectMapper mapper = new ObjectMapper();

    public static long difMinutes(Date start, Date end) {
        Calendar startCld = Calendar.getInstance();
        Calendar endCls = Calendar.getInstance();
        startCld.setTime(start);
        endCls.setTime(end);
        long milis1 = startCld.getTimeInMillis();
        long milis2 = endCls.getTimeInMillis();
        long dif = milis2 - milis1;
        long difMinutes = Math.abs(dif / (60 * 1000));
        return difMinutes;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static User getUser(User user) {
        user.setPassword(null);
        return user;
    }

    public static MessageResult<double[]> validateCoordinate(String coordinate) {
        try {
            coordinate = coordinate.replace("[", "").replace("]", "");
            String[] coor = coordinate.split(",");
            double a = Double.parseDouble(coor[0]);
            double b = Double.parseDouble(coor[1]);
            return MessageResult.successful(new double[]{a, b}, "OK");
        } catch (Exception e) {
            return MessageResult.error(null, e.getMessage());
        }
    }

    public static double distanceCoordinate(double lat1, double lng1, double lat2, double lng2) {
        double radioTierra = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;
        return distancia;
    }

    public static double round(double value, int prec) {
        BigDecimal decimal = new BigDecimal(value);
        BigDecimal scaled = decimal.setScale(prec, RoundingMode.HALF_UP);
        return scaled.doubleValue();
    }

    public static User getUser(User u, List<GroupTrainer> list) {
        List<GroupTrainer> l = list.stream().filter(x -> x.getUser().getId().equals(u.getId())).collect(Collectors.toList());
        u.setQuantitygroup(l.size());
        return u;
    }

    public static int getQuantityGroupsByCategory(String c, List<GroupTrainer> list) {
        List<GroupTrainer> l = list.stream().filter(x -> x.getCategory().equals(c)).collect(Collectors.toList());
        return l.size();
    }

    public static List<String> getListByString(String txt, String separator) {
        String[] l = txt.split(separator);
        List<String> list = new ArrayList<>();
        for (String x : l) list.add(x);
        return list;
    }

}
