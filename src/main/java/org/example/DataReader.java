package org.example;

public class DataReader {
    private final XmlDoc objects;
    private final XmlDoc hierarchy;

    public DataReader(XmlDoc objects, XmlDoc hierarchy) {
        this.objects = objects;
        this.hierarchy = hierarchy;
    }

    public DataReader(XmlDoc objects) {
        this(objects, null);
    }

    public void showAdresses(String date, String... objId) {
        System.out.println("showAdresses");
        var list = objects.getObjectXmls().stream().filter(x -> x.updateDate().equals(date)).toList();
        for (String id : objId) {
            list.stream()
                    .filter(x -> x.objectId().equals(id))
                    .forEach(objectXml -> System.out.println(objectXml.objectId() + ":" + objectXml.typeName() + " " + objectXml.name()));
        }

    }

    public void showFullAdressesCain(String date, String... objId) {
        System.out.println("showFullAdressesCain");
        var list = objects.getObjectXmls().stream().filter(x -> x.updateDate().equals(date)).toList();
        for (String id : objId) {
            String parentId = hierarchy.getHierarchyXmls().stream().filter(x -> x.objectId().equals(id)).findFirst().get().parentObjId();
            var listId = hierarchy.getHierarchyXmls()
                    .stream()
                    .filter(x -> x.parentObjId().equals(parentId))
                    .map(HierarchyXml::objectId)
                    .toList();
            for (String obId : listId) {
                list.stream()
                        .filter(objectXml -> objectXml.objectId().equals(obId))
                        .filter(x -> x.typeName().equalsIgnoreCase("проезд"))
                        .forEach(x -> System.out.println(x.typeName() + " " + x.name()));
            }
        }
    }
}
