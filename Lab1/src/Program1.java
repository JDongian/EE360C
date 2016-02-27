/*
 * Name: Joshua Dong
 * EID: jid295
 */

import java.util.ArrayList;
import java.util.ArrayDeque;
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

    private static void initOwners(Matching m,
                                   HashMap<Apartment, Landlord> alMap) {
        for (int landlord = 0; landlord < m.getLandlordCount(); ++landlord) {
            for (Integer apt : m.getLandlordOwners().get(landlord)) {
                alMap.put(new Apartment(apt), new Landlord(landlord, m));
            }
        }
    }

    private static void initBimap(Matching m,
                                  HashMap<Apartment, Tenant> atMap,
                                  HashMap<Tenant, Apartment> taMap) {
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
       
        initOwners(g, alMap);
        initBimap(g, atMap, taMap);

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
        return true;
    }

    /**
     * Determines a solution to the Stable Matching problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMatchingGaleShapley(Matching g) {
        ArrayDeque<Tenant> freeTenants = new ArrayDeque<Tenant>();
        HashMap<Apartment, Landlord> alMap =
            new HashMap<Apartment, Landlord>();
        // Bimap of Tenant/Apartment relationships
        HashMap<Apartment, Tenant> atMap = new HashMap<Apartment, Tenant>();
        HashMap<Tenant, Apartment> taMap = new HashMap<Tenant, Apartment>();

        initOwners(g, alMap);

        for (int t = 0; t < g.getTenantCount(); ++t) {
            Tenant tenant = new Tenant(t, g);
            tenant.resetChoices();
            freeTenants.offer(tenant);
        }

        while (!freeTenants.isEmpty()) {
            Tenant tenant = freeTenants.pop();
            Apartment apartment = tenant.nextChoice();

            // Check if the apartment is unpaired
            if (atMap.get(apartment) == null) {
                atMap.put(apartment, tenant);
                taMap.put(tenant, apartment);
            } else {
                Tenant otherTenant = atMap.get(apartment);
                Landlord lrd = alMap.get(apartment);
                if (lrd.getRank(tenant) < lrd.getRank(otherTenant)) {
                    atMap.remove(apartment);
                    taMap.remove(tenant);
                    freeTenants.offer(otherTenant);

                    atMap.put(apartment, tenant);
                    taMap.put(tenant, apartment);
                } else {
                    freeTenants.push(tenant);
                }
            }
        }

        // Store our Bimap results into a new Matching object.
        ArrayList<Integer> results = new ArrayList<Integer>();
        for (Integer t = 0; t < g.getTenantCount(); t++) {
            results.add(taMap.get(new Tenant(t, g)).getValue());
        }

        return new Matching(g, results);
    }
}
