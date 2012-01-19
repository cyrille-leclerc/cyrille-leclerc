/*
 * Created on Oct 7, 2004
 */
package cyrille.rmi.iiop.ior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// IOR.java
//
// Copyright 1997, 1998, 1999 Entrance Software GmbH, Kassel
//
// Author : 03/09/1997 Paul Watzlaw
// Last update: 11/10/1997
// 11/18/1997 Paul Watzlaw
// Skip a tagged profile's profile_data if the
// corresponding tag is TAG_MULTIPLE_COMPONENTS or
// a new tag.
// 12/29/1998 Paul Watzlaw
// Changed package name from DE.ENTRANCE.CORBA20
// to de.entrance.CORBA20.
// 12/30/1998 Paul Watzlaw
// Methods getNumProfiles, getProfileData, getTag
// and getTypeId added.
//
// pwatzlaw@entrance.de

// Class IOR is not an implementation of any CORBA20 module or class, but
// it contains some members of the modules IOP and IIOP as specified in
// The Common Object Request Broker : Architecture and Specification,
// Revision 2.0, July 1995 (pp. 10-15, 12-31
/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class IOR {

    // Module IOP.

    public static final long TAG_INTERNET_IOP = 0;

    public static final long TAG_MULTIPLE_COMPONENTS = 1;

    protected long numProfiles;

    protected String iorString;

    protected String prefix; // IOP::IOR.

    protected String type_id; // IOP::IOR.

    // IOP::IOR. This vector contains the tags (ProfileId) and the
    // profile_data
    // (octet sequences).
    protected List profiles = null;

    public IOR(String iorString) {
        this.iorString = iorString;
        parse();
    }

    public long getNumProfiles() {
        return this.numProfiles;
    }

    public String getProfileData(long profileNo, String profileItem) {
        String profile_item;
        Map profile_data;

        profile_data = (Map) this.profiles.get((int) profileNo * 2 + 1);
        profile_item = profile_data.get(profileItem).toString();

        return profile_item;
    }

    public int getProfileTag(long profileNo) {
        int tag;
        Long tagObj;

        tagObj = (Long) this.profiles.get((int) profileNo * 2);
        tag = tagObj.intValue();

        return tag;
    }

    public String getTypeId() {
        return this.type_id;
    }

    public void print() {
        if (this.profiles != null) {
            int tag;
            Map profile_data;

            System.out.println("Type ID           : " + this.type_id);
            System.out.println("Number of profiles: " + this.numProfiles);
            for (int i = 0; i < this.numProfiles; i++) {
                System.out.println("Profile No. " + (i + 1));
                tag = ((Long) this.profiles.get(i * 2)).intValue();
                profile_data = (Map) this.profiles.get(i * 2 + 1);
                switch (tag) {
                case (int) TAG_INTERNET_IOP:
                    System.out.println("  Tag               : TAG_INTERNET_IOP");
                    System.out.println("  Version.major     : " + profile_data.get("Version.major"));
                    System.out.println("  Version.minor     : " + profile_data.get("Version.minor"));
                    System.out.println("  Host              : " + profile_data.get("ProfileBody.host"));
                    System.out.println("  Port              : " + profile_data.get("ProfileBody.port"));
                    System.out.println("  Object key        : " + profile_data.get("ProfileBody.object_key"));
                    break;
                case (int) TAG_MULTIPLE_COMPONENTS:
                    System.out.println("  Tag               : TAG_MULTIPLE_COMPONENTS");
                    System.out.println("  Profile data      : " + profile_data.get("ProfileData"));
                    break;
                default:
                    System.out.println("  New tag           : " + tag);
                    System.out.println("  Profile data      : " + profile_data.get("ProfileData"));
                    break;
                }
            }
        }
    }

    protected void parse() {
        long tag;

        // IIOP::Version and IIOP::ProfileBody. This hashtable contains the
        // version (char major, char minor), the host (string), the port
        // (unsigned short) and the object_key (octet sequences).

        Map profile_data;
        CDR iorCdr;
        CDR profileCdr;

        this.prefix = this.iorString.substring(0, 4);
        iorCdr = new CDR();
        iorCdr.parseHexString(this.iorString.substring(4));
        this.type_id = iorCdr.getString();
        this.numProfiles = iorCdr.getULong();
        // Store the tags and the profile_data sequences from IOP::TaggedProfile
        // in the Vector _profiles.
        this.profiles = new ArrayList();
        for (int i = 0; i < this.numProfiles; i++) {
            tag = iorCdr.getULong();
            this.profiles.add(new Long(tag));
            profile_data = new HashMap(5);
            if (tag == TAG_INTERNET_IOP) {
                profileCdr = new CDR();
                // In future use ByteArrays instead of Strings.

                profileCdr.parseByteArray(iorCdr.getSequence());

                profile_data.put("Version.major", new Integer(profileCdr.getChar()));
                profile_data.put("Version.minor", new Integer(profileCdr.getChar()));
                profile_data.put("ProfileBody.host", profileCdr.getString());
                profile_data.put("ProfileBody.port", new Integer(profileCdr.getUShort()));
                profile_data.put("ProfileBody.object_key", new String(profileCdr.getSequence()));
            } else {
                // If a MultipleComponentProfile or a profile with a new tag was
                // discovered, skip the following sequence.
                profile_data.put("ProfileData", new String(iorCdr.getSequence()));
            }
            this.profiles.add(profile_data);
        }
    }

}