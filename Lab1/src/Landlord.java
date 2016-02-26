import java.util.ArrayList;

public class Landlord {
    private Integer value;
    private ArrayList<Tenant> preferences;
    private Matching matching;

    public Landlord(Integer v, Matching m) {
        value = v;
        matching = m;
        preferences = null;
    }
    public Integer getValue() {
        return value;
    }

    public ArrayList<Tenant> getPrefs() {
        // Lazy eval
        if (preferences == null) {
            ArrayList<Integer> prefs = matching.getLandlordPref().get(value);
            preferences = new ArrayList<Tenant>(prefs.size());
            for (Integer p: prefs) {
                preferences.add(new Tenant(p, matching)); 
            }
        }
        return preferences;
    }
    
    public Integer getRank(Tenant t) {
        return getPrefs().get(t.getValue()).getValue();
    }

    @Override
    public boolean equals(Object l){
        if (l instanceof Landlord) {
            return value == ((Landlord) l).getValue();
        } else {
            return super.equals(l);
        }
    }

    @Override
    public int hashCode(){
        return value;
    }

    @Override
    public String toString() {
        return "l_" + value;
    }
}

