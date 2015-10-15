package com.penner.android.model.bottomtab.penner;

/**
 * Created by PennerYu on 15/10/15.
 */
public class DatabindingUser {

    private String firstName;
    private String lastName;

    public DatabindingUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
