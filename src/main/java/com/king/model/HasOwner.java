package com.king.model;

import java.util.List;

public interface HasOwner {
    List<String> getOwnedIds();
    void addOwned(String id);
    boolean removeOwned(String id);
}
