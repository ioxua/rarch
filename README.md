## InjecThor

Just some notes for later.
My design is really not good. My Injector interface is responsible for too 
many things. Maybe splitting concerns between an Instantiator and an Injector
interfaces could be better. 

Currently, context must be recalculated in a lot of places. boxing the Field
class and giving it total context could help:
```java
class Target {
    Field where;
    Set<String> groups;
    Set<String> contextualGroups;
    Set<String> names;
    Set<String> contextualNames;
}
``` 

Contextual groups and names would help when composing complex injectors 
(as currently is GroupedMapInjector)