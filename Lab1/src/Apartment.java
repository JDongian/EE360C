/*
 * Name: Joshua Dong
 * EID: jid295
 */

import java.util.ArrayList;

public class Apartment {
    private Integer value;

    public Apartment(Integer v) {
        value = v;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object a){
        return value.equals(((Apartment) a).getValue());
    }

    @Override
    public int hashCode(){
         return value;
    }

    @Override
    public String toString() {
        return "a_" + value;
    }
}

