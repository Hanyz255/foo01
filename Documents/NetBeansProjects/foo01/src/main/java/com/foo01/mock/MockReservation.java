/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author konecny
 */
public class MockReservation {

    static List<Reservation> list;

    public static List<Reservation> mockReservations() {
        if (list == null) {
            list = new ArrayList<Reservation>();

            Reservation r = new Reservation();
            r.setSource(new Source());
            r.getSource().setName("Source1");
            r.setBeginning(new Timestamp(new Date().getTime()));
            r.setEnding(r.getBeginning());
            r.setDescription("S1-R1");
            r.setUser("John Doe");
            list.add(r);

            r = new Reservation();
            r.setSource(new Source());
            r.getSource().setName("Source1");
            r.setBeginning(new Timestamp(new Date().getTime()));
            r.setEnding(r.getBeginning());
            r.setDescription("S1-R2");
            r.setUser("John Doe");
            list.add(r);

            r = new Reservation();
            r.setSource(new Source());
            r.getSource().setName("Source2");
            r.setBeginning(new Timestamp(new Date().getTime()));
            r.setEnding(r.getBeginning());
            r.setDescription("S2-R1");
            r.setUser("John Doe");
            list.add(r);
        }
        return list;
    }

    public static List<Reservation> getReservationForSource(Source s) {
        if (list == null) {
            mockReservations();
        }
        List<Reservation> result = new ArrayList<Reservation>();
        for (Reservation r : list) {
            if (r.getSource().equals(s)) {
                result.add(r);
            }
        }
        return result;
    }
}
