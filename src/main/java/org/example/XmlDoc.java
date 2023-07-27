package org.example;

import java.util.ArrayList;
import java.util.List;

public class XmlDoc {
    private final List<ObjectXml> objectXmls = new ArrayList<>();
    private final List<HierarchyXml> hierarchyXmls = new ArrayList<>();

    public List<HierarchyXml> getHierarchyXmls() {
        return hierarchyXmls;
    }

    public List<ObjectXml> getObjectXmls() {
        return objectXmls;
    }
}

record ObjectXml(String id,
        String objectId,
        String objectGuid,
        String changeId,
        String name,
        String typeName,
        String lever,
        String operTypeId,
        String prevId,
        String nextId,
        String updateDate,
        String startDate,
        String endDate,
        String isActual,
        String isActive) {

}

record HierarchyXml(String id,
        String objectId,
        String parentObjId,
        String changeId,
        String prevId,
        String nextId,
        String updateDate,
        String startDate,
        String endDate,
        String isActive) {

}