import java.util.ArrayList;

public class Tenant {
    private Integer value;
    private ArrayList<Apartment> preferences;
    private Matching matching;

    public Tenant(Integer v, Matching m) {
        value = v;
        matching = m;
        preferences = null;
    }

    public Integer getValue() {
        return value;
    }

    public ArrayList<Apartment> getPrefs() {
        // Lazy eval
        if (preferences == null) {
            ArrayList<Integer> prefs = matching.getTenantPref().get(value);
            preferences = new ArrayList<Apartment>(prefs.size());
            for (Integer p: prefs) {
                preferences.add(new Apartment(p)); 
            }
        }
        return preferences;
    }

    public Integer getRank(Apartment a) {
        return getPrefs().get(a.getValue()).getValue();
    }

    @Override
    public boolean equals(Object t){
        if (t instanceof Tenant) {
            return value == ((Tenant) t).getValue();
        } else {
            return super.equals(t);
        }
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
