package com.planchella.enums;

public enum MembershipType {
    GUEST("guest"),
    MEMBER("memeber"),
    TOP_CONTRIBUTOR("top_contributor"),
    Creator("creator");

    private final String val;
    MembershipType(String val) {
        this.val = val;
    }
}
