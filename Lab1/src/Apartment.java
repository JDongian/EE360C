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
        if (a instanceof Apartment) {
            return value == ((Apartment) a).getValue();
        } else {
            return super.equals(a);
        }
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

