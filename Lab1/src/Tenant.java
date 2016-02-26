/*
 * Name: Joshua Dong
 * EID: jid295
 */

import java.util.ArrayList;
import java.util.ArrayDeque;

public class Tenant {
    private Integer value;
    private ArrayList<Integer> preferences;
    private ArrayDeque<Apartment> topChoices;
    private Matching matching;

    public Tenant(Integer v, Matching m) {
        value = v;
        matching = m;
        preferences = null;
        topChoices = new ArrayDeque<Apartment>();
    }

    public Integer getValue() {
        return value;
    }

    public ArrayList<Integer> getPrefs() {
        // Lazy eval
        if (preferences == null) {
            ArrayList<Integer> prefs = matching.getTenantPref().get(value);
            preferences = new ArrayList<Integer>(prefs.size());
            for (Integer p: prefs) {
                preferences.add(p); 
            }
        }
        return preferences;
    }

    public Integer getRank(Apartment a) {
        return getPrefs().get(a.getValue());
    }

    public void resetChoices() {
        ArrayList<Apartment> choices = new ArrayList<Apartment>();
        for (int a = 0; a < matching.getTenantPref().size(); ++a) {
            Apartment apartment = new Apartment(a);
            choices.add(apartment);
        }
        choices.sort((a1, a2) -> (getRank(a1)).compareTo(getRank(a2)));
        for (Apartment a : choices) {
            topChoices.offer(a);
        }
    }

    public Apartment nextChoice() {
        return topChoices.pop();
    }

    @Override
    public boolean equals(Object t){
        return value.equals(((Tenant) t).getValue());
    }

    @Override
    public int hashCode(){
         return value;
    }

    @Override
    public String toString() {
        return "t_" + value;
    }
}
