/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Matching problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    private static String arrString(ArrayList<Integer> arr) {
        if (arr.size() == 0) {
            return "[]";
        }

        String result = "[";
        for (Integer i : arr.subList(0, arr.size() - 1)) {
            result += i + ", ";
        }
        return result + arr.get(arr.size() - 1) + "]";
    }

    private static void setupMaps(Matching m,
                           HashMap<Apartment, Landlord> alMap,
                           HashMap<Apartment, Tenant> atMap,
                           HashMap<Tenant, Apartment> taMap) {
        for (int landlord = 0; landlord < m.getLandlordCount(); ++landlord) {
            for (Integer apt : m.getLandlordOwners().get(landlord)) {
                alMap.put(new Apartment(apt), new Landlord(landlord, m));
            }
        }

        for (int tenant = 0; tenant < m.getTenantCount(); ++tenant) {
            Integer apartment = m.getTenantMatching().get(tenant);
            atMap.put(new Apartment(apartment), new Tenant(tenant, m));
            taMap.put(new Tenant(tenant, m), new Apartment(apartment));
        }
    }

    public boolean isStableMatching(Matching g) {
        HashMap<Apartment, Landlord> alMap =
            new HashMap<Apartment, Landlord>();
        // Bimap of Tenant/Apartment relationships
        HashMap<Apartment, Tenant> atMap = new HashMap<Apartment, Tenant>();
        HashMap<Tenant, Apartment> taMap = new HashMap<Tenant, Apartment>();

        setupMaps(g, alMap, atMap, taMap);

        for (int i = 0; i < g.getTenantCount(); ++i) {
            Tenant tenant = new Tenant(i, g);
            Apartment currentApt = taMap.get(tenant);
            ArrayList<Apartment> betterApts = new ArrayList<Apartment>();

            for (int a = 0; a < g.getTenantCount(); ++a) {
                Apartment apt = new Apartment(a);
                if (tenant.getRank(apt) < tenant.getRank(currentApt)) {
                    betterApts.add(apt);
                }
            }

            for (Apartment testApt : betterApts) {
                Landlord testLord = alMap.get(testApt);
                Tenant testTenant = atMap.get(testApt);

                // Check if testApt likes tenant more than testTenant
                if (testLord.getRank(tenant) < testLord.getRank(testTenant)) {
                    return false;
                }
            }
        }
/*
        // DEBUG
        System.out.println("Apartment Ownership:");  
        for (Apartment a : alMap.keySet()){
            System.out.println(a + " " + alMap.get(a));  
        }

        // DEBUG
        System.out.println("Bimap Relations:");  
        for (Apartment a : atMap.keySet()){
            System.out.println(a + " " + atMap.get(a));  
        }
*/
        return true;
    }

    /**
     * Determines a solution to the Stable Matching problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMatchingGaleShapley(Matching given_matching) {
    }
}
