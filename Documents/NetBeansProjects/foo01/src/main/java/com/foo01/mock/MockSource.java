/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.mock;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author konecny
 */
public class MockSource {

    static List<Source> list;

    public static List<Source> mockSources() {
        if (list == null) {
            list = new ArrayList<Source>();
            
            Source s = new Source();
            s.setName("Source1");
            list.add(s);
            
            s = new Source();
            s.setName("Source2");
            list.add(s);
            
            s = new Source();
            s.setName("Source3");
            list.add(s);
        }
        return list;
    }
}
